package com.whistle.demo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.whistle.demo.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val fragment = IssuesFragment()
        val fragmentTag = fragment::class.java.simpleName
        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragment, fragmentTag).commit()
    }
}