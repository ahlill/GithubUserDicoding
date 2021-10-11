package com.example.githubuser.ui.detail

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser.R
import com.example.githubuser.database.FavoriteDataModel
import com.example.githubuser.databinding.ActivityDetailBinding
import com.example.githubuser.helper.ViewModelFactory
import com.example.githubuser.ui.favorite.Favorite
import com.example.githubuser.ui.insert.FavoriteAddViewModel
import com.example.githubuser.ui.main.MyViewModel
import com.example.githubuser.ui.setting.SettingActivity
import com.google.android.material.tabs.TabLayoutMediator


class DetailActivity : AppCompatActivity(), View.OnClickListener {

    private var favorite: FavoriteDataModel? = null

    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private val myViewModel by viewModels<MyViewModel>()

    private lateinit var favoriteAddViewModel: FavoriteAddViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = binding.root
        setContentView(view)

        supportActionBar?.hide() // menyembunyikan action bar

        val user = intent.getParcelableExtra<Favorite>(DATA_EXTRA)

        myViewModel.findDataFollowers(user?.login)
        myViewModel.findDataFollowings(user?.login)

        favoriteAddViewModel = obtainViewModel(this@DetailActivity)

        // di mapping untuk masuk ke database
        favorite = FavoriteDataModel()
        favorite.let { favorite ->
            favorite?.login = user?.login
            favorite?.id = user?.id
            favorite?.avatarUrl = user?.avatarUrl
            favorite?.htmlUrl = user?.htmlUrl
        }

        bind(user)

        // clicklistener
        with(binding) {
            back.setOnClickListener(this@DetailActivity)
            fabFavorite.setOnClickListener(this@DetailActivity)
            settings.setOnClickListener(this@DetailActivity)

            // share
            facebook.setOnClickListener(this@DetailActivity)
            whatsapp.setOnClickListener(this@DetailActivity)
            twitter.setOnClickListener(this@DetailActivity)

        }

        val sectionPagerAdapter = SectionPagerAdapter(this@DetailActivity)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun bind(user: Favorite?) {
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

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteAddViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteAddViewModel::class.java)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.settings -> {
                val i = Intent(this, SettingActivity::class.java)
                startActivity(i)
            }
            R.id.back -> {
                finish()
            }
            R.id.fab_favorite -> {
                favoriteAddViewModel.insert(favorite!!)
                Toast.makeText(applicationContext, getString(R.string.add_success), Toast.LENGTH_SHORT).show()
            }
            // share
            R.id.facebook -> {
                val uriFb = Uri.parse("https://www.facebook.com")
                val context = this@DetailActivity
                val yourURL = favorite?.htmlUrl
                val uri: Uri
                if (!TextUtils.isEmpty(isFacebookAppInstalled(context))) {
                    uri = Uri.parse("fb://facewebmodal/f?href=$yourURL")
                    val intent: Intent? = context.packageManager.getLaunchIntentForPackage(isFacebookAppInstalled(context))
                    if (intent != null) {
                        intent.action = Intent.ACTION_VIEW
                        intent.data = uri
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        context.startActivity(intent)
                    } else {
                        val intentForOtherApp = Intent(Intent.ACTION_VIEW, uriFb)
                        context.startActivity(intentForOtherApp)
                    }
                }
            }
            R.id.whatsapp -> {
                val i = Intent()
                i.action = Intent.ACTION_SEND
                i.putExtra(Intent.EXTRA_TEXT, favorite?.htmlUrl)
                i.type = "text/plain"
                i.setPackage("com.whatsapp")
                startActivity(Intent.createChooser(i, "action"))
                startActivity(i)
            }
            R.id.twitter -> {
                val tweetUrl = Uri.parse("https://twitter.com/intent/tweet?text=${favorite?.login}&url=${favorite?.htmlUrl}")
                val i = Intent(Intent.ACTION_VIEW, tweetUrl)
                startActivity(i)
            }

        }
    }

    private fun isFacebookAppInstalled(context: Context): String {

        val pm: PackageManager = context.packageManager
        var applicationInfo: ApplicationInfo

        //First check that if the main app of facebook is installed or not
        try {
            applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0)
            return if (applicationInfo.enabled) "com.facebook.katana" else ""
        } catch (ignored: Exception) {
        }

        //Then check that if the facebook lite is installed or not
        try {
            applicationInfo = pm.getApplicationInfo("com.facebook.lite", 0)
            return if (applicationInfo.enabled) "com.facebook.lite" else ""
        } catch (ignored: Exception) {
        }

        //Then check the other facebook app using different package name is installed or not
        try {
            applicationInfo = pm.getApplicationInfo("com.facebook.android", 0)
            return if (applicationInfo.enabled) "com.facebook.android" else ""
        } catch (ignored: Exception) {
        }

        try {
            applicationInfo = pm.getApplicationInfo("com.example.facebook", 0)
            return if (applicationInfo.enabled) "com.example.facebook" else ""
        } catch (ignored: Exception) {
        }
        return ""
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