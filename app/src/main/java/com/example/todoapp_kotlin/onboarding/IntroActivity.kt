package com.example.todoapp_kotlin.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todoapp_kotlin.R

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        //hide the action bar
        supportActionBar?.hide()
    }
}