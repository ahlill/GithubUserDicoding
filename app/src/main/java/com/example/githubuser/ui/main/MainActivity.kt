package com.example.githubuser.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.api.DataModel
import com.example.githubuser.api.ItemsItem
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.ui.adapter.UserAdapter
import com.example.githubuser.ui.detail.DetailActivity
import com.example.githubuser.ui.detail.DetailActivity.Companion.DATA_EXTRA
import com.example.githubuser.ui.favorite.Favorite
import com.example.githubuser.ui.favorite.FavoriteListActivity
import com.example.githubuser.ui.setting.SettingActivity

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val myViewModel: MyViewModel by viewModels()

    private fun setActionBar() {
        supportActionBar?.title = TITLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setActionBar()     // menambahkan title bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        myViewModel.dataSearches.observe(this, { dataUser ->
            if (dataUser != null) {
                showUser(dataUser) // menampilkan data dari viewModel ke tampilan
                flagGone()
                flagStartGone()
            }

            if (dataUser.items?.isEmpty() == true) {
                flagVisible()
                flagStartGone()
            }
        })

    }

    private fun showUser(dataUser: DataModel) = with(binding) {
        rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
        rvUser.setHasFixedSize(true)
        val adapter = UserAdapter(dataUser.items)
        rvUser.adapter = adapter

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onClicked(user: ItemsItem?) {
                val mUser = Favorite()
                mUser.let {
                    mUser.login = user?.login
                    mUser.id = user?.id
                    mUser.htmlUrl = user?.htmlUrl
                    mUser.avatarUrl = user?.avatarUrl
                }
                val i = Intent(this@MainActivity, DetailActivity::class.java)
                i.putExtra(DATA_EXTRA, mUser)
                startActivity(i)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as androidx.appcompat.widget.SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                flagStartGone()
                flagGone()
                myViewModel.findDataSearches(query)

                myViewModel.isLoading.observe(this@MainActivity, { isLoading ->
                    showLoading(isLoading)
                })
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                val i = Intent(this@MainActivity, FavoriteListActivity::class.java)
                startActivity(i)
            }
            R.id.settings -> {
                val i = Intent(this, SettingActivity::class.java)
                startActivity(i)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun flagGone() {
        binding.textSearch.visibility = View.GONE
        binding.list.visibility = View.GONE
    }

    private fun flagVisible() {
        binding.textSearch.visibility = View.VISIBLE
        binding.list.visibility = View.VISIBLE
    }

    private fun flagStartGone() {
        binding.textDiscovery.visibility = View.GONE
        binding.discovery.visibility = View.GONE
    }

    companion object {
        private const val TITLE = "Github User"
    }
}