package com.example.githubuser.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.database.FavoriteDataModel
import com.example.githubuser.databinding.ActivityFavoriteListBinding
import com.example.githubuser.helper.ViewModelFactory
import com.example.githubuser.ui.adapter.FavoriteAdapter
import com.example.githubuser.ui.detail.DetailActivity
import com.example.githubuser.ui.detail.DetailActivity.Companion.DATA_EXTRA
import com.example.githubuser.ui.insert.FavoriteAddViewModel

class FavoriteListActivity : AppCompatActivity() {

    private val binding by lazy { ActivityFavoriteListBinding.inflate(layoutInflater) }
    private lateinit var adapter: FavoriteAdapter

    private lateinit var mFavorite: Favorite

    private lateinit var favoriteAddUpdateViewModel: FavoriteAddViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite User"

        favoriteAddUpdateViewModel = obtainViewModel(this)

        setAdapter()
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteAddViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteAddViewModel::class.java)
    }

    private fun setAdapter() {
        adapter = FavoriteAdapter()
        val favoriteVieModel = obtainViewModel(this)
        favoriteVieModel.getAllFavorite().observe(this, { favoriteList ->
            if (favoriteList != null) {
                binding.noData.visibility = View.INVISIBLE
                binding.noDataText.visibility = View.INVISIBLE
                adapter.setListFavorite(favoriteList)
                if (favoriteList.isEmpty()) {
                    binding.noData.visibility = View.VISIBLE
                    binding.noDataText.visibility = View.VISIBLE
                }
            }
        })

        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.setHasFixedSize(true)
        binding.rvFavorite.adapter = adapter

        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onClicked(favorite: FavoriteDataModel) {
                mFavorite = Favorite()
                mFavorite.let {
                    mFavorite.avatarUrl = favorite.avatarUrl
                    mFavorite.htmlUrl = favorite.htmlUrl
                    mFavorite.id = favorite.id
                    mFavorite.login = favorite.login
                }

                val i = Intent(this@FavoriteListActivity, DetailActivity::class.java)
                i.putExtra(DATA_EXTRA, mFavorite)
                startActivity(i)
            }

            override fun onLongClicked(favorite: FavoriteDataModel) {
                showAlertDialog(favorite)
            }

        })

    }

    private fun showAlertDialog(favorite: FavoriteDataModel) {

        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(getString(R.string.title_dialog))
            setMessage(getString(R.string.massage_dialog))
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                favoriteAddUpdateViewModel.delete(favorite)
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        }
        alertDialogBuilder.create().show()
    }
}
