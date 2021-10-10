package com.example.githubuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.api.ItemsItem
import com.example.githubuser.databinding.FragmentFollowerBinding

class FollowerFragment : Fragment() {

    private val myViewModel by activityViewModels<MyViewModel>()
    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        myViewModel.dataFollowers.observe(viewLifecycleOwner, { dataFollowers ->
            if (dataFollowers != null) {
                showFollowers(dataFollowers)
                flagGone()
            }

            if (dataFollowers.isEmpty()) {
                flagVisible()
            }
        })

        myViewModel.isLoadingFollowers.observe(viewLifecycleOwner, { isLoading ->
            showLoading(isLoading)
        })
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun showFollowers(dataFollowers: List<ItemsItem>) = with(binding) {
        rvFollower.layoutManager = LinearLayoutManager(context)
        rvFollower.setHasFixedSize(true)
        val adapter = UserAdapter(dataFollowers)
        rvFollower.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun flagGone() {
        binding.textFollower.visibility = View.GONE
        binding.list.visibility = View.GONE
    }

    private fun flagVisible() {
        binding.textFollower.visibility = View.VISIBLE
        binding.list.visibility = View.VISIBLE
    }

}