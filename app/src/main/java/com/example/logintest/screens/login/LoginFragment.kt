package com.example.logintest.screens.registration.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.children
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.logintest.R
import com.example.logintest.core.ResourceHolder
import com.example.logintest.databinding.FragmentLoginBinding
import com.example.logintest.presentation.BaseStates
import com.example.logintest.screens.login.domain.LoginRepository
import com.example.logintest.ui.delegate.viewBinding
import com.example.logintest.ui.delegate.viewModelCreator
import com.example.logintest.ui.utils.gone
import com.example.logintest.ui.utils.show
import com.example.logintest.ui.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    @Inject
    lateinit var resourceHolder: ResourceHolder

    @Inject
    lateinit var loginRepository: LoginRepository

    //Имплементация черегат
    private val binding by viewBinding(FragmentLoginBinding::bind)

    //Делаю проброс через factory которая в делегате viewModelCreator
    private val viewModel by viewModelCreator {
        LoginViewModel(
            router = findNavController(),
            resourceHolder = resourceHolder,
            repository = loginRepository
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        stateSubscribe()
    }

    private fun initViews() {
        with(binding) {
            skipTextView.setOnClickListener {
                showSnackbar(getString(R.string.skip))
            }

            registrationButton.setOnClickListener {
                viewModel.toRegistration()
            }

            loginButton.setOnClickListener {
                viewModel.login(
                    login = emailInput.text.toString(),
                    password = passwordInput.text.toString()
                )
            }

            emailInput.doAfterTextChanged { checkButtonEnable() }
            passwordInput.doAfterTextChanged { checkButtonEnable() }
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
                        else -> showWorking()
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

    private fun checkButtonEnable() {
        binding.apply {
            loginButton.isEnabled =
                emailInput.text.toString().isNotEmpty()
                && passwordInput.text.toString().isNotEmpty()
        }
    }

    private fun blockAllElements(isBlock: Boolean) =
        binding.root.children.forEach { elements -> elements.isEnabled = !isBlock }

}