package com.example.githubuser

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser.api.ItemsItem
import com.example.githubuser.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private val myViewModel by viewModels<MyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = binding.root
        setContentView(view)

        supportActionBar?.hide() // menyembunyikan action bar

        val user = intent.getParcelableExtra<ItemsItem>(DATA_EXTRA)

        myViewModel.findDataFollowers(user?.login)
        myViewModel.findDataFollowings(user?.login)

        bind(user)

        binding.back.setOnClickListener {
            finish()
        }

        val sectionPagerAdapter = SectionPagerAdapter(this@DetailActivity)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }

    private fun bind(user: ItemsItem?) {
        with(binding) {
            Glide.with(this@DetailActivity)
                    .load(user?.avatarUrl)
                    .apply(RequestOptions().override(120, 120))
                    .into(imgPhoto)

            tvUsername.text = user?.login
            tvId.text = user?.id.toString()
            tvWeb.text = user?.htmlUrl

            tvWeb.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW, Uri.parse(user?.htmlUrl))
                startActivity(i)
            }
        }
    }

    companion object {
        const val DATA_EXTRA = "data_extra"

        @StringRes
        private val TAB_TITLES = intArrayOf(
                R.string.tab_title_1,
                R.string.tab_title_2
        )
    }
}