package com.nameksolutions.regchain.curaorganization.auth.ui.registration

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.hbb20.CountryCodePicker
import com.nameksolutions.regchain.curaorganization.R
import com.nameksolutions.regchain.curaorganization.auth.AuthApi
import com.nameksolutions.regchain.curaorganization.auth.AuthRepo
import com.nameksolutions.regchain.curaorganization.auth.AuthViewModel
import com.nameksolutions.regchain.curaorganization.base.BaseFragment
import com.nameksolutions.regchain.curaorganization.databinding.FragmentRegDetailsBinding
import com.nameksolutions.regchain.curaorganization.network.Resource
import com.nameksolutions.regchain.curaorganization.requests.CreateOrganizationRequest
import com.nameksolutions.regchain.curaorganization.requests.IdentifierRequest
import com.nameksolutions.regchain.curaorganization.requests.TelecomRequest
import com.nameksolutions.regchain.curaorganization.utils.*
import com.nameksolutions.regchain.curaorganization.utils.Common.regStepCount
import kotlinx.coroutines.launch

/*
* This fragment class handles the input and registration of the organization details
*
* It collects the following organisation information and creates a table in the database
* The details collected in this class UI include:
* - Organisation Name
* - Organisation Alias Name
* - Organisation National Identifier Document Type
* - Organisation National Identifier Value
* - Organisation Email
* - Organisation Password
* - Organisation Phone Number
*
* From the information, an account is created for the organisation using the email and password
*
*
* */

class RegDetails : BaseFragment<AuthViewModel, FragmentRegDetailsBinding, AuthRepo>(),
    CountryCodePicker.OnCountryChangeListener {


    private lateinit var organizationName: String
    private lateinit var organizationAliasName: String
    private lateinit var organizationNationalIdentificationType: String
    private lateinit var organizationNationalIdentificationValue: String
    private lateinit var organizationEmail: String
    private lateinit var organizationPassword: String
    private lateinit var organizationConfirmPassword: String
    private lateinit var organizationFullPhoneNumber: String
    private lateinit var organizationCountryCodePicker: CountryCodePicker
    private lateinit var nextBtn: Button
    private lateinit var prevBtn: Button

    //    lateinit var navController: NavController
    private lateinit var phoneNumberCode: String

    val gson = Gson()


    private var identifiers: MutableList<IdentifierRequest> = mutableListOf()
    private var telecom: MutableList<TelecomRequest> = mutableListOf()

    private var aliasName: MutableList<String> = mutableListOf()
    private val telco = TelecomRequest()

    private var telecomRank = 1
    private var progressDialog: Dialog? = null

    private val TAG = "EQUA"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            val nationalIDTypeArray = resources.getStringArray(R.array.id_types)
            val nationalIDTypeArrayAdapter =
                ArrayAdapter(requireContext(), R.layout.drop_down_item, nationalIDTypeArray)
            regIdType.setAdapter(nationalIDTypeArrayAdapter)
            organizationCountryCodePicker = signUpCountryCodePicker



            nextBtn = (requireActivity() as RegActivity).btnNext
//            regStepCount = (requireActivity() as RegActivity).currentRegStep
//            navController = (requireActivity() as RegActivity).navController

            nextBtn.setOnClickListener {

                organizationCountryCodePicker.registerCarrierNumberEditText(organizationPhoneNumber)

                organizationName = regOrganizationName.text.toString().trim()
                organizationAliasName = regOrganizationAliasName.text.toString().trim()
                organizationNationalIdentificationValue =
                    regOrganizationIdentifierValue?.text.toString().trim()
                organizationEmail = regOrganizationEmail.text.toString().trim()
                organizationPassword = regOrganizationPassword.text.toString().trim()
                organizationConfirmPassword = regOrganizationConfirmPassword.text.toString().trim()
                organizationNationalIdentificationType = regIdType.text.toString()
                organizationCountryCodePicker.setOnCountryChangeListener(this@RegDetails)
                organizationFullPhoneNumber = organizationCountryCodePicker.fullNumberWithPlus

                performValidation()
            }


        }

    }

    private fun performValidation() {

        with(binding) {

            // TODO: replicate the validation in sql app by making the error go away if satisfied
            //if organization name field is empty
            if (organizationName.isEmpty()) {
                textInputLayoutRegOrganizationName.error = "Organization Name Required"
                regOrganizationName.requestFocus()
                return
            }
            //if organization email field is empty
            if (organizationEmail.isEmpty()) {
                textInputLayoutRegOrganizationEmail.error = "Organization Email Required"
                regOrganizationEmail.requestFocus()
                return
            }
            //checks if the entered organization email is valid
            if (!Patterns.EMAIL_ADDRESS.matcher(organizationEmail).matches()) {
                binding.textInputLayoutRegOrganizationEmail.error = "Input Valid Email"
                regOrganizationEmail.requestFocus()
            }
            //if organization password field is empty
            if (organizationPassword.isEmpty() || organizationPassword.length < 8) {
                textInputLayoutRegOrganizationPassword.error =
                    "Password Must Be At Least 8 Characters"
                regOrganizationPassword.requestFocus()
                return
            }
            //if confirm password field is empty or less than 8 characters
            if (organizationConfirmPassword != organizationPassword) {
                textInputLayoutRegOrganizationConfirmPassword.error = "Password Does Not Match"
                return
            }

            //if organization phone number field is empty
            if (organizationFullPhoneNumber.isEmpty()) {
                textInputLayoutOrganizationPhoneNumber.error = "Phone number required"
                organizationPhoneNumber.requestFocus()
                return
            }
            //if organization national identifier type is not selected
            if (organizationNationalIdentificationType.isEmpty()) {
                txtInputLayoutIdType.error = "Select One"
                return
            }
            //if organization national identifier field is empty
            if (organizationNationalIdentificationValue.isEmpty()) {
                textInputLayoutRegOrganizationIdentifier.error = "National Identifier Required"
                regOrganizationIdentifierValue?.requestFocus()
                return
            } else {
//                identifiers = mutableListOf<Identifiers>(getOldIdentifiers())

                identifiers.add(
                    IdentifierRequest(
                        type = organizationNationalIdentificationType,
                        value = organizationNationalIdentificationValue
                    )
                )

                val email = telco.copy(
                    system = "email",
                    rank = telecomRank++,
                    value = organizationEmail,
                    use = "official"
                )

                val phone = telco.copy(
                    system = "mobile",
                    rank = telecomRank++,
                    value = organizationFullPhoneNumber,
                    use = "official"
                )

                telecom.add(email)

                telecom.add(phone)

                aliasName.add(organizationAliasName)

                val newOrganization = CreateOrganizationRequest(
                    alias = aliasName,
                    identifier = identifiers,
                    name = organizationName,
                    password = organizationPassword,
                    telecom = telecom
                )
                registerOrganization(newOrganization)
            }
        }

    }

    /*
    * this function registers the organisation
    * It creates a new table for the organisation using the entered information
    * It creates a new user account for the organisation with the email and password
    * */
    private fun registerOrganization(newOrganization: CreateOrganizationRequest) {

        Log.d(TAG, "registerOrganization: $telecom")
        viewModel.createOrganization(newOrganization)

        viewModel.organizationCreationResponse.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    Log.d(TAG, "registerOrganization: Success")
                    hideProgress()
                    requireContext().toast("Registration Success!!")
                    lifecycleScope.launch {
                        viewModel.saveAuthToken(it.value.token)
                        Log.d(TAG, "registerOrganization: ${it.value.token}")
                        regStepCount++
                        Log.d(TAG, "registerOrganizationRegStepCount: $regStepCount")
                        findNavController().navigate(R.id.action_regDetails_to_regAddress)
                    }
                }
                is Resource.Failure -> {
                    Log.d(TAG, "registerOrganization: Failed")
                    hideProgress()
                    handleApiError(it) { registerOrganization(newOrganization) }
                }
                is Resource.Loading -> {
                    Log.d(TAG, "registerOrganization: Loading")
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

    override fun onCountrySelected() {
        phoneNumberCode = organizationCountryCodePicker.selectedCountryCode
    }


    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentRegDetailsBinding.inflate(inflater, container, false)

    override fun getFragmentRepo() =
        AuthRepo(remoteDataSource.buildApi(AuthApi::class.java), userprefs)

}