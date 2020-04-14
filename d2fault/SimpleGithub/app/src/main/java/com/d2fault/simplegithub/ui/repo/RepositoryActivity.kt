package com.d2fault.simplegithub.ui.repo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.d2fault.simplegithub.R

class RepositoryActivity : AppCompatActivity() {
    companion object {
        const val KEY_USER_LOGIN = "user_login"
        const val KEY_REPO_NAME = "repo_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository)
    }
}
