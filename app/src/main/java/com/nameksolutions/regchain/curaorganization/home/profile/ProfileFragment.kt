package com.nameksolutions.regchain.curaorganization.home.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nameksolutions.regchain.curaorganization.R
import com.nameksolutions.regchain.curaorganization.base.BaseFragment
import com.nameksolutions.regchain.curaorganization.databinding.FragmentPersonnelBinding
import com.nameksolutions.regchain.curaorganization.home.personnel.PersonnelRepo

class ProfileFragment : BaseFragment<ProfileViewModel, FragmentPersonnelBinding, ProfileRepo>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

    override fun getViewModel() = ProfileViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPersonnelBinding.inflate(inflater, container, false)

    override fun getFragmentRepo() = ProfileRepo(remoteDataSource.buildApi(ProfileApi::class.java), userprefs)

}