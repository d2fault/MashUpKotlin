package com.d2fault.simplegithub.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.d2fault.simplegithub.R
import com.d2fault.simplegithub.ui.api.GithubApi
import com.d2fault.simplegithub.ui.api.model.GithubRepo
import com.d2fault.simplegithub.ui.api.model.RepoSearchResponse
import com.d2fault.simplegithub.ui.api.provideGithubApi
import com.d2fault.simplegithub.ui.repo.RepositoryActivity
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity(), SearchAdapter.ItemClickListener {
    // 1. MenuItem 변수 생성(for search icon)
    internal lateinit var menuItemSearch: MenuItem

    // 2. SearchView 추가
    internal lateinit var searchView: SearchView

    // 3. Adapter에 Listener 추가
    internal val searchAdapter by lazy {
        // apply는 객체를 생성함과 동시에 초기화한다.
        SearchAdapter().apply { setItemClickListener(this@SearchActivity) }
    }

    // 4. github api 추가
    internal val api: GithubApi by lazy { provideGithubApi(this) }

    // 5. search model 추가
    internal var searchCall: Call<RepoSearchResponse>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        with(rv_activity_search_list) {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = this@SearchActivity.searchAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_search, menu)
        // 1. click event 처리
        menuItemSearch = menu!!.findItem(R.id.btn_menu_activity_search_query)
        // 2. menuItemSearch.actionView의 type을 SearchView로 캐스팅(as)
        searchView = (menuItemSearch.actionView as SearchView).apply {
            // 4. QueryTextListener 추가(클릭과 동시에 API 호출)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    // !!는 null이 아니라는 의미
                    updateTitle(query!!)
                    hideSoftKeyboard()
                    collapseSearchView()
                    searchRepository(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })
        }
        menuItemSearch.expandActionView()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (R.id.btn_menu_activity_search_query == item.itemId) {
            item.expandActionView()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStop() {
        super.onStop()
        searchCall?.run { cancel() }
    }

    // RecyclerView의 Item 클릭 시 RepositoryActivity 실행
    override fun onItemClick(repository: GithubRepo) {
        startActivity<RepositoryActivity>(
            RepositoryActivity.KEY_USER_LOGIN to repository.owner.login,
            RepositoryActivity.KEY_REPO_NAME to repository.name
        )
    }

    private fun searchRepository(query: String) {
        // 결과 삭제
        clearResults()
        hideError()
        showProgress()

        searchCall = api.searchRepository(query)
        searchCall!!.enqueue(object : Callback<RepoSearchResponse> {
            override fun onResponse(
                call: Call<RepoSearchResponse>,
                response: Response<RepoSearchResponse>
            ) {
                hideProgress()

                val searchResult = response.body()
                if (response.isSuccessful && null != searchResult) {
                    with(searchAdapter) {
                        setItems(searchResult.items)
                        notifyDataSetChanged()
                    }
                    if (0 == searchResult.totalCount) {
                        showError(getString(R.string.no_search_result))
                    }
                } else {
                    showError("Not successful: " + response.message())
                }
            }

            override fun onFailure(call: Call<RepoSearchResponse>, t: Throwable) {
                hideProgress()
                showError(t.message)
            }
        })
    }

    // 검색 후 바꿀 flow
    // 1. ActionBar title 변경
    private fun updateTitle(query: String) {
        supportActionBar?.run { subtitle = query }
    }

    // 2. keyboard hide
    private fun hideSoftKeyboard() {
        (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).run {
            hideSoftInputFromWindow(searchView.windowToken, 0)
        }
    }

    // 3. menuItemSearch 형태 변경(돋보기 아이콘 show)
    private fun collapseSearchView() {
        menuItemSearch.collapseActionView()
    }

    // 4. 결과 삭제
    private fun clearResults() {
        with(searchAdapter) {
            clearItems()
            notifyDataSetChanged()
        }
    }

    // 5. progress show(검색 중일 때)
    private fun showProgress() {
        pb_activity_search.visibility = View.VISIBLE
    }

    // 5. progress hide(검색 완료 후)
    private fun hideProgress() {
        pb_activity_search.visibility = View.GONE
    }

    // 6. 검색 실패일 경우 에러 출력
    private fun showError(message: String?) {
        with(tv_activity_search_message) {
            text = message ?: "Unexpected error."
            visibility = View.VISIBLE
        }
    }

    // 6. 검색 성공일 경우 에러 메시지 먼저 지움
    private fun hideError() {
        with(tv_activity_search_message) {
            text = ""
            visibility = View.GONE
        }
    }
}
