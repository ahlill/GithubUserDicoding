package com.example.githubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide() // menyembunyikan action bar
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()}, 3000) //menampilkan logo splashscreen
    }
}