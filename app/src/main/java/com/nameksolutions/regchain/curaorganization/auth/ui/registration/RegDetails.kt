package com.nameksolutions.regchain.curaorganization.auth.ui.registration

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
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
import com.nameksolutions.regchain.curaorganization.requests.Identifiers
import com.nameksolutions.regchain.curaorganization.requests.Telecom
import com.nameksolutions.regchain.curaorganization.utils.*
import com.nameksolutions.regchain.curaorganization.utils.Common.regStepCount
import kotlinx.coroutines.launch

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


    private var identifiers: MutableList<Identifiers> = mutableListOf()
    private var telecom: MutableList<Telecom> = mutableListOf()

    //    private var telecom:  MutableList<Any> = mutableListOf()
//    private var telecom:  MutableList<HashMap<String, String>> = mutableListOf()
//    private var telecom:  MutableList<Map<String, Any>> = mutableListOf()
    private var aliasName: MutableList<String> = mutableListOf()
    private val telco = Telecom()

    private var telecomRank = 1
    private var progressDialog: Dialog? = null

    private val TAG = "EQUA"
//    val identifiers: Identifiers()


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


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
                    Identifiers(
                        "Official",
                        organizationNationalIdentificationType,
                        organizationNationalIdentificationValue
                    )
                )

//                val emailGson = gson.toJson(email)//.toString()
                val email = telco.copy(
                    system = "email",
                    rank = "${telecomRank++}",
                    value = organizationEmail,
                    use = "official"
                )//Telecom("email",  "${telecomRank++}", organizationEmail, "official" ) //as Objects//arrayListOf<String>("email","${telecomRank++}",organizationEmail,"Official")
                val emailMap = mapOf(
                    "system" to "email",
                    "rank" to "${telecomRank++}",
                    "value" to organizationEmail,
                    "use" to "official"
                )//arrayListOf<String>("email","${telecomRank++}",organizationEmail,"Official")
                val phone = telco.copy(
                    system = "mobile",
                    rank = "${telecomRank++}",
                    value = organizationFullPhoneNumber,
                    use = "official"
                )//Telecom("mobile", "${telecomRank++}",organizationFullPhoneNumber,"official") //as Objects


                val emailGson = gson.toJsonTree(email) //as JSONObject
                val phoneGson = gson.toJson(phone) //as JSONObject

//                val json = getJson()
//                val phoneGson = Json.enc//JSONObject(phone)//Gson().fromJson(phone, Telecom::class.java)//.toString()

                Log.d(TAG, "performValidation: $email")


                val phoneMap = mapOf(
                    "system" to "mobile",
                    "rank" to "${telecomRank++}",
                    "value" to organizationFullPhoneNumber,
                    "use" to "Official"
                )

                val emailAsMap: Map<String, Any> = email.serializeToMap()
                val phoneAsMap: Map<String, Any> = phone.serializeToMap()

//                val phoneObject: Any = object {
//                    val system = "mobile"
//                    val rank = "${telecomRank++}"
//                    val value = organizationFullPhoneNumber
//                    val use = "Official"
//
//                }
//                val emailObject: Any = object {
//                    val system = "email"
//                    val rank = "${telecomRank++}"
//                    val value = organizationEmail
//                    val use = "official"
//
//                }

//                telecom.add(emailGson)
                telecom.add(email)
                Log.d(TAG, "performValidation: $phone")

//                telecom.add(phoneGson)
                telecom.add(phone)

                aliasName.add(organizationAliasName)

                val newOrganization: CreateOrganizationRequest = CreateOrganizationRequest(
                    active = true,
                    identifier = identifiers,
                    name = organizationName,
                    password = organizationPassword,
                    telecom = telecom,
                    type = null,
                    address = null
                )

//                val newOrg = newOrganization.copy(
//
//                )

//                val telcoEmail = TelecomMap.ModelMapper.from(email).serializeToMap()
//                val telcoPhone = TelecomMap.ModelMapper.from(phone).serializeToMap()
//                registerOrganization(organizationName, aliasName, organizationPassword, telecom, identifiers)
                registerOrganization(newOrganization)
            }
        }

    }

    private fun registerOrganization(newOrganization: CreateOrganizationRequest) {

//        organizationName: String, aliasName: MutableList<String>, organizationPassword: String,
//        telecom: MutableList<Telecom>,
//        identifiers: MutableList<Identifiers>
//    )
        //    {
        Log.d(TAG, "registerOrganization: $telecom")
//         viewModel.createOrganization(organizationName, aliasName, organizationPassword, identifiers, telecom, true )
        viewModel.createOrganization(newOrganization)
        Log.d(TAG, "registerOrganization: here")

        viewModel.organizationCreationResponse.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    Log.d(TAG, "registerOrganization: Success")
                    hideProgress()
                    requireContext().toast("Registration Success!!")
                    lifecycleScope.launch {
                        viewModel.saveAuthToken(it.value.token)
                        regStepCount++
                        Log.d(TAG, "registerOrganizationRegStepCount: $regStepCount")
                        findNavController().navigate(R.id.action_regDetails_to_regAddress)
                    }
//                    navController.navigate(R.id.action_regDetails_to_regAddress)
                }
                is Resource.Failure -> {
                    Log.d(TAG, "registerOrganization: Failed")
                    hideProgress()
//                    handleApiError(it) {registerOrganization(organizationName, aliasName, organizationPassword, telecom, identifiers )}
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