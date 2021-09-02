package com.example.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.ActivityMainBinding
import com.google.gson.GsonBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var data: DataModel

    private fun setActionBar(title: String){
        supportActionBar?.title = title
    }

    private val title = "Github User"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setActionBar(title)     // menambahkan title bar
        jsonConverter()         // mengambil data json dan mengkonversi sesuai data model
        showUser(data)          // menampilkan data menggunakan recyclerview

    }

    private fun showUser(data: DataModel){
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        val orang = UserAdapter(data)
        binding.rvUser.adapter = orang
    }

    private fun jsonConverter(){
        val jsonRes = """{

            "users": [{

            "username": "JakeWharton",

            "name": "Jake Wharton",

            "avatar": "@drawable/user1",

            "company": "Google, Inc.",

            "location": "Pittsburgh, PA, USA",

            "repository": 102,

            "follower": 56995,

            "following": 12

        },

            {

                "username": "amitshekhariitbhu",

                "name": "AMIT SHEKHAR",

                "avatar": "@drawable/user2",

                "company": "@MindOrksOpenSource",

                "location": "New Delhi, India",

                "repository": 37,

                "follower": 5153,

                "following": 2

            },

            {

                "username": "romainguy",

                "name": "Romain Guy",

                "avatar": "@drawable/user3",

                "company": "Google",

                "location": "California",

                "repository": 9,

                "follower": 7972,

                "following": 0

            },

            {

                "username": "chrisbanes",

                "name": "Chris Banes",

                "avatar": "@drawable/user4",

                "company": "@google working on @android",

                "location": "Sydney, Australia",

                "repository": 30,

                "follower": 14725,

                "following": 1

            },

            {

                "username": "tipsy",

                "name": "David",

                "avatar": "@drawable/user5",

                "company": "Working Group Two",

                "location": "Trondheim, Norway",

                "repository": 56,

                "follower": 788,

                "following": 0

            },

            {

                "username": "ravi8x",

                "name": "Ravi Tamada",

                "avatar": "@drawable/user6",

                "company": "AndroidHive | Droid5",

                "location": "India",

                "repository": 28,

                "follower": 18628,

                "following": 3

            },

            {

                "username": "jasoet",

                "name": "Deny Prasetyo",

                "avatar": "@drawable/user7",

                "company": "@gojek-engineering",

                "location": "Kotagede, Yogyakarta, Indonesia",

                "repository": 44,

                "follower": 277,

                "following": 39

            },

            {

                "username": "budioktaviyan",

                "name": "Budi Oktaviyan",

                "avatar": "@drawable/user8",

                "company": "@KotlinID",

                "location": "Jakarta, Indonesia",

                "repository": 110,

                "follower": 178,

                "following": 23

            },

            {

                "username": "hendisantika",

                "name": "Hendi Santika",

                "avatar": "@drawable/user9",

                "company": "@JVMDeveloperID @KotlinID @IDDevOps",

                "location": "Bojongsoang - Bandung Jawa Barat",

                "repository": 1064,

                "follower": 428,

                "following": 61

            },

            {

                "username": "sidiqpermana",

                "name": "Sidiq Permana",

                "avatar": "@drawable/user10",

                "company": "Nusantara Beta Studio",

                "location": "Jakarta Indonesia",

                "repository": 65,

                "follower": 465,

                "following": 10

            }

            ]

        }"""
        val gson = GsonBuilder().create()
        data = gson.fromJson(jsonRes, DataModel::class.java)

    }
}