package com.d2fault.simplegithub.ui.search

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.d2fault.simplegithub.R
import com.d2fault.simplegithub.ui.api.model.GithubRepo
import kotlinx.android.synthetic.main.item_repository.view.*

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.RepositoryHolder>() {
    private var items: MutableList<GithubRepo> = mutableListOf()

    private val placeholder = ColorDrawable(Color.GRAY)

    private var listener: ItemClickListener? = null

    // 1. holder class 생성(holder layout load - item_repository.xml)
    class RepositoryHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_repository, parent, false)
    )

    // 2. ViewHolder setting(위에서 만든 RepositoryHolder로)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RepositoryHolder(parent)

    // 3. view와 layout을 바인딩(item_repository.xml 참조)
    override fun onBindViewHolder(holder: RepositoryHolder, position: Int) {
        // 검색 결과를 홀더에 하나씩 연결
        items[position].let { repo ->
            with(holder.itemView) {
                Glide.with(context)
                    .load(repo.owner.avatarUrl)
                    .placeholder(placeholder)
                    .into(ivItemRepositoryProfile)

                tv_item_repository_name.text = repo.fullName
                tv_item_repository_language.text = if (TextUtils.isEmpty(repo.language))
                    context.getText(R.string.no_language_specified)
                else
                    repo.language

                setOnClickListener { listener?.onItemClick(repo) }
            }
        }
    }

    override fun getItemCount() = items.size

    fun setItems(items: List<GithubRepo>) {
        // SearchActivity에서 검색 결과를 추가하기 위한 function
        this.items = items.toMutableList()
    }

    fun setItemClickListener(listener: ItemClickListener?) {
        this.listener = listener
    }

    fun clearItems() {
        this.items.clear()
    }

    interface ItemClickListener {
        fun onItemClick(repository: GithubRepo)
    }
}