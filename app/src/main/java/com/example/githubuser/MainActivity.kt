package com.example.githubuser

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.DetailActivity.Companion.DATA_EXTRA
import com.example.githubuser.api.DataModel
import com.example.githubuser.api.ItemsItem
import com.example.githubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val myViewModel: MyViewModel by viewModels()

    private fun setActionBar() {
        supportActionBar?.title = TITLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = binding.root
        setContentView(view)

        setActionBar()     // menambahkan title bar

        myViewModel.dataSearches.observe(this, { dataUser ->
            if (dataUser != null) {
                showUser(dataUser) // menampilkan data dari viewModel ke tampilan
                flagGone()
            }

            if (dataUser.items?.isEmpty() == true) {
                flagVisible()
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
                val i = Intent(this@MainActivity, DetailActivity::class.java)
                i.putExtra(DATA_EXTRA, user)
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

    private fun flagGone() {
        binding.textSearch.visibility = View.GONE
        binding.list.visibility = View.GONE
    }

    private fun flagVisible() {
        binding.textSearch.visibility = View.VISIBLE
        binding.list.visibility = View.VISIBLE
    }

    companion object {
        private const val TITLE = "Github User"
    }
}