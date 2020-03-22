package com.d2fault.simplegithub.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.d2fault.simplegithub.R
import com.d2fault.simplegithub.ui.search.SearchActivity

import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            startActivity<SearchActivity>();
        }
    }
}
