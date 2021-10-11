package com.example.githubuser.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.ui.main.MyViewModel
import com.example.githubuser.api.ItemsItem
import com.example.githubuser.databinding.FragmentFollowingBinding
import com.example.githubuser.ui.adapter.FollowAdapter

class FollowingFragment : Fragment() {

    private val myViewModel by activityViewModels<MyViewModel>()
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        myViewModel.dataFollowings.observe(viewLifecycleOwner, { dataFollowings ->
            if (dataFollowings != null) {
                showFollowings(dataFollowings)
                flagGone()
            }

            if (dataFollowings.isEmpty()) {
                flagVisible()
            }
        })

        myViewModel.isLoadingFollowings.observe(viewLifecycleOwner, { isLoading ->
            showLoading(isLoading)
        })
        super.onViewCreated(view, savedInstanceState)
    }

    private fun flagVisible() {
        binding.textFollowing.visibility = View.VISIBLE
        binding.list.visibility = View.VISIBLE
    }

    private fun flagGone() {
        binding.textFollowing.visibility = View.GONE
        binding.list.visibility = View.GONE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root

    }

    private fun showFollowings(dataFollowings: List<ItemsItem>) = with(binding) {
        rvFollowing.layoutManager = LinearLayoutManager(context)
        rvFollowing.setHasFixedSize(true)
        val adapter = FollowAdapter(dataFollowings)
        rvFollowing.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
