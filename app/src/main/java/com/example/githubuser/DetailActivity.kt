package com.example.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.githubuser.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    val binding: ActivityDetailBinding by lazy {ActivityDetailBinding.inflate(layoutInflater)}

    companion object{
        const val DATA_EXTRA = "data extra"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = binding.root
        setContentView(view)

        supportActionBar?.hide() // menyembunyikan action bar
        val dataParcel by lazy {intent.getParcelableExtra<UsersItem>(DATA_EXTRA)}
//        val dataParcel = intent.getParcelableExtra<UsersItem>(DATA_EXTRA)
        val potoInt = resources.getIdentifier(dataParcel.avatar, null, packageName)

        with(binding) {
            tvName.text = dataParcel.name
            tvUsername.text = dataParcel.username
            imgPhoto.setImageResource(potoInt)
            tvCompany.text = dataParcel.company
            tvLocation.text = dataParcel.location
            tvRepository.text = dataParcel.repository.toString()
            tvFollower.text = dataParcel.follower.toString()
            tvFollowing.text = dataParcel.following.toString()

            btnBack.setOnClickListener {
                finish()
            }
            tvUsername.setOnClickListener {
                Toast.makeText(applicationContext, "intent ke web github", Toast.LENGTH_SHORT)
                    .show()
            }
            tvCompany.setOnClickListener {
                Toast.makeText(applicationContext, "intent ke web perusahaan", Toast.LENGTH_SHORT)
                    .show()
            }
            tvLocation.setOnClickListener {
                Toast.makeText(applicationContext, "intent ke maps", Toast.LENGTH_SHORT).show()
            }
        }
    }
}