package com.nameksolutions.regchain.curaorganization.auth.ui.registration

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.hbb20.CountryCodePicker
import com.hbb20.countrypicker.models.CPCountry
import com.nameksolutions.regchain.curaorganization.R
import com.nameksolutions.regchain.curaorganization.auth.AuthApi
import com.nameksolutions.regchain.curaorganization.auth.AuthRepo
import com.nameksolutions.regchain.curaorganization.auth.AuthViewModel
import com.nameksolutions.regchain.curaorganization.base.BaseFragment
import com.nameksolutions.regchain.curaorganization.databinding.FragmentRegAddressBinding
import com.nameksolutions.regchain.curaorganization.network.Resource
import com.nameksolutions.regchain.curaorganization.requests.AddressRequest
import com.nameksolutions.regchain.curaorganization.requests.CreateOrganizationRequest
import com.nameksolutions.regchain.curaorganization.requests.Period
import com.nameksolutions.regchain.curaorganization.utils.Common.regStepCount
import com.nameksolutions.regchain.curaorganization.utils.getDate
import com.nameksolutions.regchain.curaorganization.utils.handleApiError
import com.nameksolutions.regchain.curaorganization.utils.showProgressDialog
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*

class RegAddress : BaseFragment<AuthViewModel, FragmentRegAddressBinding, AuthRepo>() {

    private lateinit var organizationAddressLineOne: String
    private var organizationAddressLineTwo: String? = ""
    private lateinit var organizationAddressCity: String
    private lateinit var organizationAddressLGA: String
    private lateinit var organizationAddressState: String
    private lateinit var organizationAddressPostalCode: String
    private lateinit var organizationAddressCountry: String
    private lateinit var organizationAddressStartDate: String
    private lateinit var organizationAddressCountryPicker: CountryCodePicker
    private lateinit var nextBtn: Button
    private lateinit var prevBtn: Button

    //var addressMap: HashMap<String, String> = hashMapOf()

    private var address: MutableList<AddressRequest> = mutableListOf()


    private val gson = Gson()

    var cal = Calendar.getInstance()
    private var progressDialog: Dialog? = null

    private val TAG = "EQUA"


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        nextBtn = (requireActivity() as RegActivity).btnNext
//        regStepCount = (requireActivity() as RegActivity).currentRegStep

        with(binding) {


            val stateArray = resources.getStringArray(R.array.states)
            val stateArrayAdapter =
                ArrayAdapter(requireContext(), R.layout.drop_down_item, stateArray)
            regAddressState.setAdapter(stateArrayAdapter)

            // create an OnDateSetListener for Organization Start Date
            val startDateSetListener = object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(
                    view: DatePicker, year: Int, monthOfYear: Int,
                    dayOfMonth: Int
                ) {
                    cal.set(Calendar.YEAR, year)
                    cal.set(Calendar.MONTH, monthOfYear)
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    updateStartDateInView()
                }
            }

            regAddressStartDate!!.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?) {
                    DatePickerDialog(
                        requireContext(),
                        startDateSetListener, // set DatePickerDialog to point to today's date when it loads up
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }

            })

//            nextBtn = (requireActivity() as RegActivity).btnNext

            nextBtn.setOnClickListener {


                organizationAddressLineOne = regAddressAddressLine1.text.toString().trim()
                organizationAddressLineTwo = regAddressAddressLine2.text.toString().trim()
                organizationAddressCity = regAddressAddressCity.text.toString().trim()
                organizationAddressLGA = regAddressAddressLga.text.toString().trim()
                organizationAddressPostalCode = regAddressPostalCode.text.toString().trim()
                organizationAddressStartDate = regAddressStartDate!!.text.toString().trim()
                organizationAddressState = regAddressState.text.toString()


                with(binding) {
                    // Modify CPViewConfig if you need. Access cpViewConfig through `cpViewHelper`
                    countryPicker!!.cpViewHelper.cpViewConfig.viewTextGenerator =
                        { cpCountry: CPCountry ->
                            "${cpCountry.name} (${cpCountry.alpha2})"
                            //"${cpCountry.name} (${cpCountry.alpha2})"
                            //organizationAddressCountry = "${cpCountry.name}"
                        }
                    // make sure to refresh view once view configuration is changed
                    countryPicker.cpViewHelper.refreshView()
                    organizationAddressCountry =
                        countryPicker.tvCountryInfo.text.toString()//.cpViewHelper.selectedCountry.toString()
                }

                performValidation()
            }

        }


    }

//    private fun setupCountryPickerView(): String {
////        val countryPicker = findViewById<CountryPickerView>(R.id.countryPicker)
//
//        with(binding){
//            lateinit var country: String
//            // Modify CPViewConfig if you need. Access cpViewConfig through `cpViewHelper`
//            countryPicker!!.cpViewHelper.cpViewConfig.viewTextGenerator = { cpCountry: CPCountry ->
//                "${cpCountry.name} (${cpCountry.alpha2})"
//            }
//            // make sure to refresh view once view configuration is changed
//            countryPicker.cpViewHelper.refreshView()
//
//            // Modify CPDialogConfig if you need. Access cpDialogConfig through `countryPicker.cpViewHelper`
//            // countryPicker.cpViewHelper.cpDialogConfig.
//
//            // Modify CPListConfig if you need. Access cpListConfig through `countryPicker.cpViewHelper`
//            // countryPicker.cpViewHelper.cpListConfig.
//
//            // Modify CPRowConfig if you need. Access cpRowConfig through `countryPicker.cpViewHelper`
//            // countryPicker.cpViewHelper.cpRowConfig.
//        }
//
//        return
//
//
//    }

    private fun performValidation() {
        with(binding) {
            //if organization address line 1 field is empty
            if (organizationAddressLineOne.isEmpty()) {
                textInputLayoutRegAddressLine1.error = "Address Line Required"
                regAddressAddressLine1.requestFocus()
                return
            }
            //if organization address city field is empty
            if (organizationAddressCity.isEmpty()) {
                textInputLayoutRegAddressCity.error = "Organization City Required"
                regAddressAddressCity.requestFocus()
                return
            }

            //if organization address LGA field is empty
            if (organizationAddressLGA.isEmpty()) {
                textInputLayoutRegAddressLga.error = "Organization LGA required"
                regAddressAddressLga.requestFocus()
                return
            }
            //if organization address postal code is not selected
            if (organizationAddressPostalCode.isEmpty()) {
                textInputLayoutRegPostalCode.error = "Organization Postal Code Required"
                regAddressPostalCode.requestFocus()
                return
            }
            //if organization address country field is empty
            if (organizationAddressCountry.isEmpty()) {
                textInputLayoutRegAddressCountry.error = "Organization Country Required"
                return
            } //if organization address country field is empty
            if (organizationAddressState.isEmpty()) {
                txtInputLayoutState.error = "Organization State Required"
                return
            }
            //if organization address start date field is empty
            if (organizationAddressStartDate.isEmpty()) {
                textInputLayoutRegAddressStartDate!!.error =
                    "Organization Address Start Date Required"
                return
            } else {

                val nowTime = System.currentTimeMillis()
                val timeEdited = getDate(nowTime, "dd/MM/yyyy")
                val period = Period(timeEdited!!, organizationAddressStartDate)
//                val addressHapMap = hashMapOf("use" to  )
                //val addressRequest = AddressRequest()
//                val addressMap = hashMapOf("use" to "official", "city" to organizationAddressCity, "type" to "both", "district" to organizationAddressLGA, "state" to organizationAddressState, "country" to organizationAddressCountry, "period" to period)
                val addressInstance = AddressRequest(
                    organizationAddressCity,
                    organizationAddressCountry,
                    organizationAddressLGA,
                    period,
                    organizationAddressState,
                    "both",
                    "official"
                )


//                val addressGson = gson.toJson(addressInstance).toString()
//                val addressGson = gson.toJson(addressMap).toString()
                address.add(addressInstance)

                val newOrganizationAddress: CreateOrganizationRequest = CreateOrganizationRequest(
                    address = address
                )

                addOrganizationAddress(newOrganizationAddress)

            }
        }

    }

    private fun addOrganizationAddress(newOrganizationAddress: CreateOrganizationRequest) {
        viewModel.addOrganizationDetails(newOrganizationAddress)
        Log.d(TAG, "addOrganizationAddress: $address")

        viewModel.organizationDetailsUpdate.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    hideProgress()
                    Log.d(TAG, "addOrganizationAddress: Registered Address")
                    //navigate to identifiers registration
                    regStepCount++
                    Log.d(TAG, "registerOrganizationRegStepCount: $regStepCount")
                    findNavController().navigate(R.id.action_regAddress_to_regRegistryIdentifiers)
                }
                is Resource.Failure -> {
                    hideProgress()
                    handleApiError(it) { addOrganizationAddress(newOrganizationAddress) }
                }
                is Resource.Loading -> {
                    showProgress()
                }
            }
        })
    }

    private fun updateStartDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.regAddressStartDate!!.setText(sdf.format(cal.getTime()).toString())
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
    ) = FragmentRegAddressBinding.inflate(inflater, container, false)

    override fun getFragmentRepo(): AuthRepo {
        val token = runBlocking { userprefs.authToken.first() }
        val api = remoteDataSource.buildApi(AuthApi::class.java, token)
        return AuthRepo(api, userprefs)
    }


}