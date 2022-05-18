package com.nameksolutions.regchain.curaorganization.auth

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.nameksolutions.regchain.curaorganization.R
import com.nameksolutions.regchain.curaorganization.base.BaseFragment
import com.nameksolutions.regchain.curaorganization.databinding.FragmentRegDaysOfOperationBinding
import com.nameksolutions.regchain.curaorganization.databinding.FragmentSignInBinding
import com.nameksolutions.regchain.curaorganization.home.HomeActivity
import com.nameksolutions.regchain.curaorganization.network.Resource
import com.nameksolutions.regchain.curaorganization.utils.*
import kotlinx.coroutines.launch

class SignIn : BaseFragment<AuthViewModel, FragmentSignInBinding, AuthRepo>() {

    private var progressDialog: Dialog? = null

    val TAG = "EQUA"



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //implement sign in

        binding.btnSignIn.enable(false)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    hideProgress()
                    //viewModel.saveUserProfile(it.value) used to save values in database
                    requireContext().toast("Log in Success")
                    lifecycleScope.launch {
                        viewModel.saveAuthToken(it.value.token)
                        Log.d(TAG, "onActivityCreated: ${it.value.token}")
                        requireActivity().startNewActivity(HomeActivity::class.java)
                    }
                }
                is Resource.Failure -> {
                    hideProgress()
                    handleApiError(it) { login() }
                } //snack bar action to retry login
                is Resource.Loading -> showProgress()

            }
        })

        binding.signInPassword.addTextChangedListener {
            val email = binding.signInEmail.text.toString().trim()

            binding.btnSignIn.enable(email.isNotEmpty() && it.toString().trim().isNotEmpty())
        }

        binding.btnSignIn.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val email = binding.signInEmail.text.toString().trim()
        val password = binding.signInPassword.text.toString().trim()
        viewModel.login(email, password)

    }

    private fun showProgress() {
        hideProgress()
        progressDialog = requireActivity().showProgressDialog()
    }

    private fun hideProgress() {
        progressDialog?.let { if (it.isShowing) it.cancel() }
    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSignInBinding.inflate(inflater, container, false)

    override fun getFragmentRepo() =
        AuthRepo(remoteDataSource.buildApi(AuthApi::class.java), userprefs)


}