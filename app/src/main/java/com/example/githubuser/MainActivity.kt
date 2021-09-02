package com.example.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.ActivityMainBinding
import com.google.gson.GsonBuilder

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var data: DataModel

    private fun setActionBar(title: String) {
        supportActionBar?.title = title
    }

    private val title = "Github User"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setActionBar(title)     // menambahkan title bar
        jsonConverter()         // mengambil data json dan mengkonversi sesuai data model
        showUser(data)          // menampilkan data menggunakan recyclerview

    }

    private fun showUser(data: DataModel) = with(binding) {
        rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
        val orang = UserAdapter(data)
        rvUser.adapter = orang
    }

    private fun jsonConverter() {
        val jsonRes = DataJson().jsonRes
        val gson = GsonBuilder().create()
        data = gson.fromJson(jsonRes, DataModel::class.java)

    }
}