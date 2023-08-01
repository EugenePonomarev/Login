package com.example.logintest.screens.tabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.logintest.R
import com.example.logintest.databinding.FragmentTabsBinding
import com.example.logintest.ui.delegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TabsFragment : Fragment(R.layout.fragment_tabs) {

    private val binding by viewBinding(FragmentTabsBinding::bind)

    //имплементация идет через Hilt
    private val viewModel: TabsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.data.collect { data ->
                    binding.loginInfo.text =
                        getString(R.string.login_info, data.login, data.password)
                }
            }
        }
    }
}