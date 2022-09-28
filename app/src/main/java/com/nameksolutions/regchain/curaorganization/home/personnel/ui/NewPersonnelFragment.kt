/*
 * Copyright (c) 2022.
 * Richard Uzor
 */

package com.nameksolutions.regchain.curaorganization.home.personnel.ui

import android.app.Dialog
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.MultiAutoCompleteTextView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.hbb20.CountryCodePicker
import com.nameksolutions.regchain.curaorganization.R
import com.nameksolutions.regchain.curaorganization.base.BaseFragment
import com.nameksolutions.regchain.curaorganization.databinding.FragmentNewPersonnelBinding
import com.nameksolutions.regchain.curaorganization.home.personnel.PersonnelApi
import com.nameksolutions.regchain.curaorganization.home.personnel.PersonnelRepo
import com.nameksolutions.regchain.curaorganization.home.personnel.PersonnelViewModel
import com.nameksolutions.regchain.curaorganization.network.Resource
import com.nameksolutions.regchain.curaorganization.requests.*
import com.nameksolutions.regchain.curaorganization.utils.*
import com.nameksolutions.regchain.curaorganization.utils.Common.TAG
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.*

//
class NewPersonnelFragment :
    BaseFragment<PersonnelViewModel, FragmentNewPersonnelBinding, PersonnelRepo>(),
    CountryCodePicker.OnCountryChangeListener {


    private lateinit var newPractitionerSurName: String
    private lateinit var newPractitionerOtherNames: String
    private lateinit var newPractitionerNamePrefix: String
    private lateinit var newPractitionerGender: String
    private lateinit var newPractitionerRoleCommunication: String
    private lateinit var newPractitionerPhoneNumber: String
    private lateinit var newPractitionerEmail: String
    private lateinit var newPractitionerCountryCodePicker: CountryCodePicker
    private lateinit var newPractitionerIdentifierValue: String
    private lateinit var newPractitionerIdentifierType: String


    lateinit var practitionerPhoneNumberCode: String

    private val telco = TelecomRequest()
    private val idfier = IdentifierRequest()

    private var identifiers: MutableList<IdentifierRequest> = mutableListOf()
    private var telecom: MutableList<TelecomRequest> = mutableListOf()
    private var practitionerRoleCommunication = mutableListOf<String>()

    //    private var practitionerRoles: MutableList<PractitionerRole> = mutableListOf()
    private var practitionerNamePrefix = mutableListOf<String>()
    private var practitionerOtherNames = mutableListOf<String>()

    var hoursDaysOfOperation: HashMap<String, String> = hashMapOf()


    private var telecomRank = 0
    private var progressDialog: Dialog? = null


    val roleDelimiter = ","

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //fetch practitioner roles from back end and add to list in common

        //todo fetch the list of communication


        with(binding) {


            val practitionerTitleArray = resources.getStringArray(R.array.name_prefixes)
            val practitionerTitleArrayAdapter =
                ArrayAdapter(requireContext(), R.layout.drop_down_item, practitionerTitleArray)
            regPractitionerNamePrefix.setAdapter(practitionerTitleArrayAdapter)

            val practitionerGenderArray = resources.getStringArray(R.array.gender)
            val practitionerGenderArrayAdapter =
                ArrayAdapter(requireContext(), R.layout.drop_down_item, practitionerGenderArray)
            regPractitionerGender.setAdapter(practitionerGenderArrayAdapter)

            val practitionerIdentifierArray = resources.getStringArray(R.array.id_types)
            val practitionerIdentifierArrayAdapter =
                ArrayAdapter(requireContext(), R.layout.drop_down_item, practitionerIdentifierArray)
            regPractitionerIdType.setAdapter(practitionerIdentifierArrayAdapter)

            val practitionerRoleCommunicationArray = resources.getStringArray(R.array.id_types)
            val practitionerRoleCommunicationArrayAdapter =
                ArrayAdapter(
                    requireContext(),
                    R.layout.drop_down_item,
                    practitionerRoleCommunicationArray
                )
            regPractitionerCommunication.setAdapter(practitionerRoleCommunicationArrayAdapter)
            regPractitionerCommunication.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())



            btnPractitionerCancel.setOnClickListener {
                findNavController().navigate(R.id.action_newPersonnelFragment_to_personnelFragment)
            }

            btnPractitionerRegister.setOnClickListener {
                newPractitionerCountryCodePicker.registerCarrierNumberEditText(
                    practitionerPhoneNumber
                )


                newPractitionerNamePrefix = regPractitionerNamePrefix.text.toString().trim()
                newPractitionerSurName = regPractitionerSurname.text.toString().trim()
                newPractitionerOtherNames = regPractitionerOtherNames.text.toString().trim()
                newPractitionerGender = regPractitionerGender.text.toString().trim()
                newPractitionerIdentifierType = regPractitionerIdType.text.toString().trim()
                newPractitionerIdentifierValue =
                    regPractitionerIdentifierValue.text.toString().trim()
                newPractitionerEmail = regPractitionerEmail.text.toString().trim()
                newPractitionerCountryCodePicker.setOnCountryChangeListener(this@NewPersonnelFragment)
                newPractitionerPhoneNumber = newPractitionerCountryCodePicker.fullNumberWithPlus

                newPractitionerRoleCommunication =
                    regPractitionerCommunication.text.toString().trim()


                performValidation()

            }

        }

    }


    private fun performValidation() {
        with(binding) {
            //if new practitioner name prefix field is empty
            if (newPractitionerNamePrefix.isEmpty()) {
                textInputLayoutNamePrefix.error = "Practitioner Title Required"
                regPractitionerNamePrefix.requestFocus()
                return
            }
            //if new practitioner surname field is empty
            if (newPractitionerSurName.isEmpty()) {
                textInputLayoutRegPractitionerSurname.error = "Practitioner Surname Required"
                regPractitionerSurname.requestFocus()
                return
            }
            //if new practitioner other name field is empty
            if (newPractitionerOtherNames.isEmpty()) {
                textInputLayoutRegPractitionerOtherNames.error = "Practitioner Other Name Required"
                regPractitionerOtherNames.requestFocus()
                return
            }
            //if new practitioner gender field is empty
            if (newPractitionerGender.isEmpty()) {
                textInputLayoutPractitionerGender.error = "Practitioner Gender Required"
                regPractitionerGender.requestFocus()
                return
            }
            //if new practitioner ID Type field is empty
            if (newPractitionerIdentifierType.isEmpty()) {
                textInputLayoutPractitionerIdType.error = "Practitioner ID Type Required"
                regPractitionerIdType.requestFocus()
                return
            }
            //if new practitioner ID Number field is empty
            if (newPractitionerIdentifierValue.isEmpty()) {
                textInputLayoutRegPractitionerIdentifier.error = "Practitioner ID Number Required"
                regPractitionerIdentifierValue.requestFocus()
                return
            }
            //if new practitioner email field is empty
            if (newPractitionerEmail.isEmpty()) {
                textInputLayoutRegPractitionerEmail.error = "Practitioner Email Required"
                regPractitionerEmail.requestFocus()
                return
            }
            //checks if the entered organization email is valid
            if (!Patterns.EMAIL_ADDRESS.matcher(newPractitionerEmail).matches()) {
                binding.textInputLayoutRegPractitionerEmail.error = "Input Valid Email"
                regPractitionerEmail.requestFocus()
            }
            //if new practitioner name prefix field is empty
            if (newPractitionerPhoneNumber.isEmpty()) {
                textInputLayoutNewPractitionerPhoneNumber.error =
                    "Practitioner Phone Number Required"
                return
            }
            //if new practitioner role communication field is empty
            if (newPractitionerRoleCommunication.isEmpty()) {
                textInputLayoutPractitionerCommunication.error =
                    "At leLeast One Language Required"
                return
            } else {

                //all requirements are satisfied
                val email = telco.copy(
                    system = "email",
                    rank = telecomRank++,
                    value = newPractitionerEmail,
                    use = "official"
                )

                val phone = telco.copy(
                    system = "phone",
                    rank = telecomRank++,
                    value = newPractitionerPhoneNumber,
                    use = "official"
                )

                val identity = idfier.copy(
                    use = "official",
                    system = newPractitionerIdentifierType,
                    value = newPractitionerIdentifierValue
                )

                telecom.add(email)
                telecom.add(phone)
                identifiers.add(identity)
                practitionerNamePrefix.add(newPractitionerNamePrefix)
                practitionerOtherNames.add(newPractitionerOtherNames)


                val name = NameRequest(
                    family = newPractitionerSurName,
                    given = practitionerOtherNames,
                    prefix = practitionerNamePrefix,
                    suffix = listOf(),
                    use = "Official"
                )

                val newPractitionerCommunicationArray =
                    newPractitionerRoleCommunication.split("\\s*,\\s*")


                val newPractitioner = CreatePractitionerRequest(
                    gender = newPractitionerGender,
                    identifier = identifiers,
                    name = name,
                    telecom = telecom,
                    communication = newPractitionerCommunicationArray
                )



                createNewPractitioner(newPractitioner)

                Log.d(TAG, "performValidation: $newPractitioner")

            }
        }
    }


    private fun createNewPractitioner(newPractitioner: CreatePractitionerRequest) {

        viewModel.createPractitioner(newPractitioner)
        viewModel.practitionerCreationResponse.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgress()
                    requireView().snackbar("Practitioner Details Entered")
                    val navToRoleCreation =
                        NewPersonnelFragmentDirections.actionNewPersonnelFragmentToNewPractitionerRoleFragment(
                            response.value.practitioner.id,
                            response.value.practitioner.name.given[0],
                            response.value.practitioner.name.family
                        )
                    findNavController().navigate(navToRoleCreation)
                }
                is Resource.Failure -> {
                    hideProgress()
                    Log.d(TAG, "createNewPractitioner: $response")
                    handleApiError(response) { createNewPractitioner(newPractitioner) }
                }
                is Resource.Loading -> {
                    showProgress()
                }
            }

        })

    }


    private fun showProgress() {
        hideProgress()
        progressDialog = requireActivity().showProgressDialog()
    }

    private fun hideProgress() {
        progressDialog?.let { if (it.isShowing) it.cancel() }
    }

    override fun getViewModel() = PersonnelViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNewPersonnelBinding.inflate(inflater, container, false)

    override fun getFragmentRepo(): PersonnelRepo {
        val token = runBlocking { userprefs.authToken.first() }
        val api = remoteDataSource.buildApi(PersonnelApi::class.java, token)
        return PersonnelRepo(api, userprefs)
    }

    //
//
    override fun onCountrySelected() {
        practitionerPhoneNumberCode = binding.newPractitionerCountryCodePicker.selectedCountryCode
    }

}