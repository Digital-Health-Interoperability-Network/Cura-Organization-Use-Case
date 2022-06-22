package com.nameksolutions.regchain.curaorganization.home.personnel.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nameksolutions.regchain.curaorganization.R
import com.nameksolutions.regchain.curaorganization.base.BaseFragment
import com.nameksolutions.regchain.curaorganization.databinding.FragmentPractitionerItemBinding
import com.nameksolutions.regchain.curaorganization.home.personnel.PersonnelApi
import com.nameksolutions.regchain.curaorganization.home.personnel.PersonnelRepo
import com.nameksolutions.regchain.curaorganization.home.personnel.PersonnelViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class PractitionerItem : BaseFragment<PersonnelViewModel, FragmentPractitionerItemBinding, PersonnelRepo>() {

    val args by navArgs<PractitionerItemArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val practitionerRoles = args.currentPractitioner.practitionerRole
        val telecoms = args.currentPractitioner.telecom


        with(binding){

            mockBackBtn.setOnClickListener {
                findNavController().navigate(R.id.action_practitionerItem_to_personnelFragment)
            }

            var practitionerNames = mutableListOf<String>(args.currentPractitioner.name!!.given!![0].substring(0, 1), args.currentPractitioner.name!!.family!!.substring(0, 1))
            practitionerIconText.text = "${practitionerNames[0]}${practitionerNames[1]}"
            txtPractitionerName.text = args.currentPractitioner.name!!.text
            for (role in practitionerRoles){
                txtPractitionerRoles.append(role.code.toString())
            }
//            txtPractitionerRoles.text = args.currentPractitioner.practitionerRole.toString()//find a way to fetch the lists and append
            singlePractitionerGender.text = args.currentPractitioner.gender
            for (telecom in telecoms!!){
                if (telecom.system == "email"){
                    singlePractitionerEmail.text = telecom.value
                }
            }
            for (telecom in telecoms){
                if (telecom.system == "phone" || telecom.system == "mobile"){
                    singlePractitionerPhoneNumber.text = telecom.value
                }
            }
//            singlePractitionerEmail.text = args.currentPractitioner.telecom.toString() //find a way to fetch the 1st telecom element as email
//            singlePractitionerPhoneNumber.text = args.currentPractitioner.telecom.toString()// find a way to fetch the 2nd telecom element as phone number
        }

    }


    override fun getViewModel() = PersonnelViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPractitionerItemBinding.inflate(inflater, container, false)

    override fun getFragmentRepo(): PersonnelRepo {
        val token = runBlocking { userprefs.authToken.first() }
        val api = remoteDataSource.buildApi(PersonnelApi::class.java, token)
        return PersonnelRepo(api, userprefs)
    }

}