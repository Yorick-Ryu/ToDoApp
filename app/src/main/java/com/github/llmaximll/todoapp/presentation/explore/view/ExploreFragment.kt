package com.github.llmaximll.todoapp.presentation.explore.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.llmaximll.todoapp.R
import com.github.llmaximll.todoapp.databinding.FragmentExploreBinding
import com.github.llmaximll.todoapp.presentation.explore.viewmodel.CategoriesResult
import com.github.llmaximll.todoapp.presentation.explore.viewmodel.ExploreViewModel
import com.github.llmaximll.todoapp.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: ExploreViewModel by viewModels()
    private val categoriesAdapter = CategoriesAdapter { id ->

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setupLists()
        viewModel.result.observe(viewLifecycleOwner, ::handleCategories)
    }

    private fun setupListeners() {
        binding.toolBar.setNavigationOnClickListener {
            requireActivity().findViewById<DrawerLayout>(R.id.drawerLayout).open()
        }
        binding.toolBar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.search_fragment -> {
                    findNavController().navigate(R.id.action_ExploreFragment_to_SearchFragment)
                    true
                }
                R.id.notifications_fragment -> {
                    findNavController().navigate(R.id.action_ExploreFragment_to_NotificationsFragment)
                    true
                }
                else -> false
            }
        }
        binding.fab.setOnClickListener {
            binding.constraintLayout.showSnackbar(R.string.placeholder_in_development)
        }
    }

    private fun setupLists() {
        binding.categoriesRecyclerView.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.categoriesRecyclerView.adapter = categoriesAdapter
    }

    private fun handleCategories(state: CategoriesResult) {
        when (state) {
            is CategoriesResult.SuccessResult -> {
                categoriesAdapter.submitList(state.result)
            }
            is CategoriesResult.ErrorResult -> {

            }
            is CategoriesResult.EmptyResult -> {

            }
            is CategoriesResult.Loading -> {

            }
        }
    }

    private fun showLoadingCategories() {

    }

    private fun hideLoadingCategories() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}