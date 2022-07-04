package com.nameksolutions.regchain.curaorganization.home.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
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
import kotlinx.coroutines.runBlocking

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding, HomeRepo>() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(binding.customToolBar)

        viewModel.saveIsFirstTime(false)

        runBlocking { Common.organizationName = userprefs.organisationName.first().toString() }
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_logout -> {
                logout()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getViewModel() = HomeViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)

    override fun getFragmentRepo() = HomeRepo(remoteDataSource.buildApi(HomeApi::class.java), userprefs)
}


