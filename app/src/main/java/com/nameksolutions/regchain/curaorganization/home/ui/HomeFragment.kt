package com.nameksolutions.regchain.curaorganization.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nameksolutions.regchain.curaorganization.R
import com.nameksolutions.regchain.curaorganization.auth.AuthApi
import com.nameksolutions.regchain.curaorganization.auth.AuthRepo
import com.nameksolutions.regchain.curaorganization.auth.AuthViewModel
import com.nameksolutions.regchain.curaorganization.base.BaseFragment
import com.nameksolutions.regchain.curaorganization.base.BaseRepo
import com.nameksolutions.regchain.curaorganization.base.BaseViewModel
import com.nameksolutions.regchain.curaorganization.databinding.FragmentHomeBinding
import com.nameksolutions.regchain.curaorganization.databinding.FragmentPersonalEmailInputBinding
import com.nameksolutions.regchain.curaorganization.home.HomeApi
import com.nameksolutions.regchain.curaorganization.home.HomeRepo
import com.nameksolutions.regchain.curaorganization.home.HomeViewModel
import com.nameksolutions.regchain.curaorganization.utils.Common
import com.nameksolutions.regchain.curaorganization.utils.snackbar
import kotlinx.coroutines.flow.first

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding, HomeRepo>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.saveIsFirstTime(false)

        Common.organizationName = userprefs.organisationName.toString()
        with(binding){
            customToolBar.title = Common.organizationName
            buttonPersonnel.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_personnelFragment)
            }

            buttonServices.setOnClickListener {
                requireView().snackbar("Services Clicked!")
            }

            buttonProfile.setOnClickListener {
                requireView().snackbar("Profile Clicked!")
            }

            buttonToken.setOnClickListener {
                requireView().snackbar("Token Clicked!")
            }
        }

    }

    override fun getViewModel() = HomeViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)

    override fun getFragmentRepo() = HomeRepo(remoteDataSource.buildApi(HomeApi::class.java), userprefs)
}


