package com.example.logintest.screens.registration


import android.os.Bundle
import android.view.View
import androidx.core.view.children
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.logintest.R
import com.example.logintest.databinding.FragmentRegistrationBinding
import com.example.logintest.model.RegistrationInfo
import com.example.logintest.presentation.BaseStates
import com.example.logintest.ui.delegate.viewBinding
import com.example.logintest.ui.utils.gone
import com.example.logintest.ui.utils.show
import com.example.logintest.ui.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegistrationFragment : Fragment(R.layout.fragment_registration) {

    private val binding by viewBinding(FragmentRegistrationBinding::bind)

    //имплементация идет через Hilt
    private val viewModel: RegistrationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        stateSubscribe()
    }

    private fun initViews() {
        binding.apply {
            backTextView.setOnClickListener { findNavController().popBackStack() }

            registrationButton.setOnClickListener {
                viewModel.registration(
                    RegistrationInfo(
                        emailInput.text.toString(),
                        passwordInput.text.toString(),
                        password2Input.text.toString()
                    )
                )
            }

            emailInput.doAfterTextChanged { checkButtonEnable() }
            passwordInput.doAfterTextChanged { checkButtonEnable() }
            password2Input.doAfterTextChanged { checkButtonEnable() }
        }
    }

    private fun stateSubscribe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { state ->
                    when (state) {
                        is BaseStates.Error -> showError(state.message)
                        is BaseStates.Loading -> showLoading()
                        is BaseStates.Working -> showWorking()
                        is BaseStates.Success -> successResult()
                    }
                }
            }
        }
    }

    private fun showError(message: String) {
        showSnackbar(message)
        blockAllElements(false)
    }

    private fun showWorking() {
        binding.progressBar.gone()
        blockAllElements(false)
    }

    private fun showLoading() {
        binding.progressBar.show()
        blockAllElements(true)
    }

    private fun successResult() {
        showLoading()
        showSnackbar(getString(R.string.account_created_successfully))
    }

    private fun blockAllElements(isBlock: Boolean) =
        binding.root.children.forEach { elements -> elements.isEnabled = !isBlock }

    private fun checkButtonEnable() {
        binding.apply {
            registrationButton.isEnabled = emailInput.text.toString().isNotBlank()
                    && passwordInput.text.toString().isNotBlank()
                    && password2Input.text.toString().isNotBlank()
        }
    }
}