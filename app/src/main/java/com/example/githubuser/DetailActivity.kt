package com.example.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.githubuser.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    companion object{
        const val DATA_EXTRA = "data extra"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.hide() // menyembunyikan action bar
        val dataParcel = intent.getParcelableExtra<UsersItem>(DATA_EXTRA)
        val potoInt = resources.getIdentifier(dataParcel.avatar, null, packageName)

        with(binding){
            tvName.text = dataParcel.name
            tvUsername.text = dataParcel.username
            imgPhoto.setImageResource(potoInt)
            tvCompany.text = dataParcel.company
            tvLocation.text = dataParcel.location
            tvRepository.text = dataParcel.repository.toString()
            tvFollower.text = dataParcel.follower.toString()
            tvFollowing.text = dataParcel.following.toString()
        }

        binding.btnBack.setOnClickListener{
            finish()
        }
        binding.tvUsername.setOnClickListener {
            Toast.makeText(applicationContext, "intent ke web github", Toast.LENGTH_SHORT).show()
        }
        binding.tvCompany.setOnClickListener {
            Toast.makeText(applicationContext, "intent ke web perusahaan", Toast.LENGTH_SHORT).show()
        }
        binding.tvLocation.setOnClickListener {
            Toast.makeText(applicationContext, "intent ke maps", Toast.LENGTH_SHORT).show()
        }
    }
}