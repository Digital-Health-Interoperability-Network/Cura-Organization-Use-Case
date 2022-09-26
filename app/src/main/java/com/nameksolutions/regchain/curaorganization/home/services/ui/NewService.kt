package com.nameksolutions.regchain.curaorganization.home.services.ui

import android.app.Dialog
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.hbb20.CountryCodePicker
import com.nameksolutions.regchain.curaorganization.R
import com.nameksolutions.regchain.curaorganization.base.BaseFragment
import com.nameksolutions.regchain.curaorganization.databinding.FragmentNewServiceBinding
import com.nameksolutions.regchain.curaorganization.home.services.ServicesApi
import com.nameksolutions.regchain.curaorganization.home.services.ServicesRepo
import com.nameksolutions.regchain.curaorganization.home.services.ServicesViewModel
import com.nameksolutions.regchain.curaorganization.network.Resource
import com.nameksolutions.regchain.curaorganization.requests.*
import com.nameksolutions.regchain.curaorganization.utils.*
import java.util.*

class NewService : BaseFragment<ServicesViewModel,FragmentNewServiceBinding, ServicesRepo>(), CountryCodePicker.OnCountryChangeListener {

    lateinit var newServiceCategory: String
    lateinit var newServiceProviderName: String
    lateinit var newServiceName: String
    lateinit var newServiceComment: String
    lateinit var newServiceSpecialty: String

    //lateinit var newServiceAvailableTime: String
    lateinit var newServiceProvisionCost: String
    lateinit var newServiceContactEmail: String
    lateinit var newServiceContactPhoneNumber: String
    lateinit var newServicePhoneNumberCode: String
    lateinit var newServiceEligibility: String
    lateinit var newServiceProgram: String
    lateinit var newServiceCharacteristics: String
    var newServiceAppointmentRequired: Boolean = false
    lateinit var newServiceReferralMethod: String
    lateinit var newServiceCommunication: String
//
//    private val telco = Telecom()
//    private val availTime = AvailableTime()
//    private val coding = Coding()
//    private val characteristic = Characteristic(listOf(coding))
//    private val eligibility = Eligibility(listOf(coding))
//    private val program = Program(listOf(coding))
//    private var speciality = Speciality(listOf(coding))
//    private val referralMethod = ReferralMethod(listOf(coding))
//    private val serviceProvision = ServiceProvisionCode(listOf(coding))

//
//    private var telecom: MutableList<Telecom> = mutableListOf()
//    private var availableTime: MutableList<AvailableTime> = mutableListOf()
//    private var serviceCategoryList: MutableList<String> = mutableListOf()
//    private var serviceCharacteristicList: MutableList<Characteristic> = mutableListOf()
//    private var serviceCommunicationList: MutableList<String> = mutableListOf()
//    private var serviceEligibilityList: MutableList<Eligibility> = mutableListOf()
//    private var serviceReferralMethodList: MutableList<ReferralMethod> = mutableListOf()
//    private var serviceServiceProvisionCodeList: MutableList<ServiceProvisionCode> = mutableListOf()
//    private var serviceProgramList: MutableList<Program> = mutableListOf()

    lateinit var timePicker: TimePickerHelper
    lateinit var openHour: String
    private var availabilityMon: AvailableTime = AvailableTime()
    private var availabilityTue: AvailableTime = AvailableTime()
    private var availabilityWed: AvailableTime = AvailableTime()
    private var availabilityThurs: AvailableTime = AvailableTime()
    private var availabilityFri: AvailableTime = AvailableTime()
    private var availabilitySat: AvailableTime = AvailableTime()
    private var availabilitySun: AvailableTime = AvailableTime()

    private var telecomRank = 0
    private var progressDialog: Dialog? = null


//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        with(binding) {
//
//            val categories = resources.getStringArray(R.array.new_service_categories)
//            val categoriesAdapter =
//                ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, categories)
//            newServiceCategoryAutoCompleteView.setAdapter(categoriesAdapter)
//
//            val languages = resources.getStringArray(R.array.id_communication)
//            val languagesAdapter =
//                ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, languages)
//            newServiceCategoryAutoCompleteView.setAdapter(languagesAdapter)
//
//            val specialties = resources.getStringArray(R.array.new_service_specialties)
//            val specialtiesAdapter =
//                ArrayAdapter(requireContext(), R.layout.drop_down_item, specialties)
//            newServiceSpecialtyView.setAdapter(specialtiesAdapter)
//
//            val programs = resources.getStringArray(R.array.new_service_programs)
//            val programsAdapter =
//                ArrayAdapter(requireContext(), R.layout.drop_down_item, programs)
//            newServiceProgramView.setAdapter(programsAdapter)
//
//            newServiceProvisionCostRadioGroup.setOnCheckedChangeListener(
//                RadioGroup.OnCheckedChangeListener { group, checkedId ->
//                    val radio = checkedId
//
//                }
//            )
//
//            newServiceAvailableTimesButton.setOnClickListener {
//                // If the CardView is already expanded, set its visibility
//                //  to gone and change the expand less icon to expand more.
//                if (newServiceAvailableTimesHideAbleView.visibility == View.VISIBLE) {
//                    TransitionManager.beginDelayedTransition(
//                        baseCardViewNewServiceAvailableTimes,
//                        AutoTransition()
//                    )
//
//                    newServiceAvailableTimesHideAbleView.visibility = View.GONE
//                    newServiceAvailableTimesButton.setImageResource(R.drawable.angle_down)
//                } else {
//                    // If the CardView is not expanded, set its visibility
//                    // to visible and change the expand more icon to expand less.
//                    TransitionManager.beginDelayedTransition(
//                        baseCardViewNewServiceAvailableTimes,
//                        AutoTransition()
//                    )
//                    newServiceAvailableTimesHideAbleView.visibility = View.VISIBLE
//                    newServiceAvailableTimesButton.setImageResource(R.drawable.angle_up)
//
//
//                }
//
//                layoutNewServiceMonday.enable(false)
//                layoutNewServiceTuesday.enable(false)
//                layoutNewServiceWednesday.enable(false)
//                layoutNewServiceThursday.enable(false)
//                layoutNewServiceFriday.enable(false)
//                layoutNewServiceSaturday.enable(false)
//                layoutNewServiceSunday.enable(false)
//
//                checkBoxNewServiceMonday.setOnCheckedChangeListener { buttonView, isChecked ->
//                    if (isChecked) {
//                        checkBoxNewServiceMonday.isChecked = true
//                        layoutNewServiceMonday.enable(true)
//                        //fetch Monday times in a function and return the values
//                        newServiceMondayOpen.setOnClickListener {
//                            val monOpenTimeView = it as TextView
//                            showTimePickerDialog(monOpenTimeView)
//
//                            Log.d(
//                                Common.TAG, "onActivityCreated: ${
//                                    monOpenTimeView.text.toString()
//                                }"
//                            )
//                            Log.d(
//                                Common.TAG,
//                                "onActivityCreated: ${showTimePickerDialog(monOpenTimeView)}"
//                            )
//                        }
//
//                        Log.d(Common.TAG, "onActivityCreatedPost: ${newServiceMondayOpen.text}")
//
//
//                        newServiceMondayClose.setOnClickListener {
//
//                            val closeMonTV = it as TextView
//                            showTimePickerDialog(closeMonTV)
//
//                        }
//
//                    } else {
//                        checkBoxNewServiceMonday.isChecked = false
//                        layoutNewServiceMonday.enable(false)
//
//                    }
//
//                }
//                checkBoxNewServiceTuesday.setOnCheckedChangeListener { buttonView, isChecked ->
//                    if (isChecked) {
//                        checkBoxNewServiceTuesday.isChecked = true
//                        layoutNewServiceTuesday.enable(true)
//                        //fetch Tuesday times in a function and return the values
//
//                        newServiceTuesdayOpen.setOnClickListener {
//
//                            val tueOpenTV = it as TextView
//                            showTimePickerDialog(tueOpenTV)
//
//                        }
//                        newServiceTuesdayClose.setOnClickListener {
//                            val tueCloseTV = it as TextView
//                            showTimePickerDialog(tueCloseTV)
//
//                        }
//
//
//                    } else {
//                        checkBoxNewServiceTuesday.isChecked = false
//                        layoutNewServiceTuesday.enable(false)
//
//                    }
//
//                }
//                checkBoxNewServiceWednesday.setOnCheckedChangeListener { buttonView, isChecked ->
//                    if (isChecked) { //if it is not checked
//                        checkBoxNewServiceWednesday.isChecked = true
//                        layoutNewServiceWednesday.enable(true)
//                        //fetch Wednesday times in a function and return the values
//                        newServiceWednesdayOpen.setOnClickListener {
//                            val wedOpenTV = it as TextView
//                            showTimePickerDialog(wedOpenTV)
//                        }
//                        newServiceWednesdayClose.setOnClickListener {
//                            val wedCloseTV = it as TextView
//                            showTimePickerDialog(wedCloseTV)
//                        }
//                    } else {
//                        checkBoxNewServiceWednesday.isChecked = false
//                        layoutNewServiceWednesday.enable(false)
//                    }
//
//
//                    //availableTime.add(availabilityWed)
//                }
//                checkBoxNewServiceThursday.setOnCheckedChangeListener { buttonView, isChecked ->
//                    if (isChecked) { //if it is not checked
//                        checkBoxNewServiceThursday.isChecked = true
//                        layoutNewServiceThursday.enable(true)
//                        //fetch Thursday times in a function and return the values
//                        newServiceThursdayOpen.setOnClickListener {
//                            val thursOpenTV = it as TextView
//                            showTimePickerDialog(thursOpenTV)
//                        }
//                        newServiceThursdayClose.setOnClickListener {
//                            val thursCloseTV = it as TextView
//                            showTimePickerDialog(thursCloseTV)
//                        }
//                    } else {
//                        checkBoxNewServiceThursday.isChecked = false
//                        layoutNewServiceThursday.enable(false)
//                    }
//
//
//                }
//                checkBoxNewServiceFriday.setOnCheckedChangeListener { buttonView, isChecked ->
//                    if (isChecked) { //if it is not checked
//                        checkBoxNewServiceFriday.isChecked = true
//                        layoutNewServiceFriday.enable(true)
//                        //fetch Friday times in a function and return the values
//                        newServiceFridayOpen.setOnClickListener {
//                            val friOpenTV = it as TextView
//                            showTimePickerDialog(friOpenTV)
//                        }
//                        newServiceFridayClose.setOnClickListener {
//                            val friCloseTV = it as TextView
//                            showTimePickerDialog(friCloseTV)
//                        }
//                    } else {
//                        checkBoxNewServiceFriday.isChecked = false
//                        layoutNewServiceFriday.enable(false)
//                    }
//
//                }
//                checkBoxNewServiceSaturday.setOnCheckedChangeListener { buttonView, isChecked ->
//                    if (isChecked) { //if it is not checked
//                        checkBoxNewServiceSaturday.isChecked = true
//                        layoutNewServiceSaturday.enable(true)
//                        //fetch Saturday times in a function and return the values
//                        newServiceSaturdayOpen.setOnClickListener {
//                            val satOpenTV = it as TextView
//                            showTimePickerDialog(satOpenTV)
//                        }
//                        newServiceSaturdayClose.setOnClickListener {
//                            val satCloseTV = it as TextView
//                            showTimePickerDialog(satCloseTV)
//                        }
//                    } else {
//                        checkBoxNewServiceSaturday.isChecked = false
//                        layoutNewServiceSaturday.enable(false)
//                    }
//
//                }
//                checkBoxNewServiceSunday.setOnCheckedChangeListener { buttonView, isChecked ->
//                    if (isChecked) { //if it is not checked
//                        checkBoxNewServiceSunday.isChecked = true
//                        layoutNewServiceSunday.enable(true)
//                        //fetch Sunday times in a function and return the values
//                        newServiceSundayOpen.setOnClickListener {
//                            val sunOpenTV = it as TextView
//                            showTimePickerDialog(sunOpenTV)
//                        }
//                        newServiceSundayClose.setOnClickListener {
//                            val sunCloseTV = it as TextView
//                            showTimePickerDialog(sunCloseTV)
//                        }
//                    } else {
//                        checkBoxNewServiceSunday.isChecked = false
//                        layoutNewServiceSunday.enable(false)
//                    }
//
//                }
//            }
//
//            checkBoxNewServiceAppointmentRequired.setOnCheckedChangeListener { buttonView, isChecked ->
//                if (isChecked) {
//                    checkBoxNewServiceAppointmentRequired.isChecked = true
//                    newServiceAppointmentRequired = true
//                }
//            }
//
//
//            btnNewServiceCancel.setOnClickListener {
//                findNavController().navigate(R.id.action_newService_to_servicesFragment)
//            }
//
//
//
//
//
//            newServiceCountryCodePicker.registerCarrierNumberEditText(
//                newServiceContactPhoneNumberView
//            )
//
//
//            btnNewServiceRegister.setOnClickListener {
//
//                newServiceCategory = newServiceCategoryAutoCompleteView.text.toString().trim()
//                newServiceProviderName = newServiceProvidedBy.text.toString().trim()
//                newServiceName = newServiceNameView.text.toString().trim()
//                newServiceComment = newServiceCommentView.text.toString().trim()
//                newServiceSpecialty = newServiceSpecialtyView.text.toString().trim()
//                newServiceContactEmail = newServiceContactEmailView.text.toString().trim()
//                newServiceContactPhoneNumber =
//                    newServiceContactPhoneNumberView.text.toString().trim()
//
//                newServiceCountryCodePicker.setOnCountryChangeListener(this@NewService)
//                newServiceContactPhoneNumber = newServiceCountryCodePicker.fullNumberWithPlus
//
//                newServiceEligibility = newServiceEligibilityView.text.toString().trim()
//                newServiceProgram = newServiceProgramView.text.toString().trim()
//                newServiceCharacteristics = newServiceCharacteristicsView.text.toString().trim()
//                //is appointment required checkbox
//                newServiceReferralMethod = newServiceReferralMethodView.text.toString().trim()
//                newServiceCommunication =
//                    newServiceCommunicationAutoCompleteView.text.toString().trim()
//
//
//                if (checkBoxNewServiceMonday.isChecked){
//                    availabilityMon = availTime.copy(
//                        availableStartTime = newServiceMondayOpen.text.toString().trim(),
//                        availableEndTime = newServiceMondayClose.text.toString().trim(),
//                        daysOfWeek = "Mon"
//                    )
//                    availableTime.add(availabilityMon)
//                }
//                if (checkBoxNewServiceTuesday.isChecked){
//                    availabilityTue = availTime.copy(
//                        availableStartTime = newServiceTuesdayOpen.text.toString().trim(),
//                        availableEndTime = newServiceTuesdayClose.text.toString().trim(),
//                        daysOfWeek = "Tue"
//                    )
//                    availableTime.add(availabilityTue)
//                }
//                if (checkBoxNewServiceWednesday.isChecked){
//                    availabilityWed = availTime.copy(
//                        availableStartTime = newServiceWednesdayOpen.text.toString().trim(),
//                        availableEndTime = newServiceWednesdayClose.text.toString().trim(),
//                        daysOfWeek = "Wed"
//                    )
//                    availableTime.add(availabilityWed)
//                }
//                if (checkBoxNewServiceThursday.isChecked){
//                    availabilityThurs = availTime.copy(
//                        availableStartTime = newServiceThursdayOpen.text.toString().trim(),
//                        availableEndTime = newServiceThursdayClose.text.toString().trim(),
//                        daysOfWeek = "Thurs"
//                    )
//                    availableTime.add(availabilityThurs)
//                }
//                if (checkBoxNewServiceFriday.isChecked){
//                    availabilityFri = availTime.copy(
//                        availableStartTime = newServiceFridayOpen.text.toString().trim(),
//                        availableEndTime = newServiceFridayClose.text.toString().trim(),
//                        daysOfWeek = "Fri"
//                    )
//                    availableTime.add(availabilityFri)
//                }
//                if (checkBoxNewServiceSaturday.isChecked){
//                    availabilitySat = availTime.copy(
//                        availableStartTime = newServiceSaturdayOpen.text.toString().trim(),
//                        availableEndTime = newServiceSaturdayClose.text.toString().trim(),
//                        daysOfWeek = "Sat"
//                    )
//                    availableTime.add(availabilitySat)
//                }
//                if (checkBoxNewServiceSunday.isChecked){
//                    availabilitySun = availTime.copy(
//                        availableStartTime = newServiceSundayOpen.text.toString().trim(),
//                        availableEndTime = newServiceSundayClose.text.toString().trim(),
//                        daysOfWeek = "Sun"
//                    )
//                    availableTime.add(availabilitySun)
//                }
//
//                val characteristicCoding = coding.copy(
//                    display = newServiceCharacteristics
//                )
//                val eligibilityCoding = coding.copy(
//                    display = newServiceEligibility
//                )
//                val programCoding = coding.copy(
//                    display = newServiceEligibility
//                )
//
//                val specialityCoding = coding.copy(
//                    display = newServiceSpecialty
//                )
//
//                val referralMethodCoding = coding.copy(
//                    display = newServiceReferralMethod
//                )
//
//                val serviceProvisionCoding = coding.copy(
//                    display = newServiceProvisionCost
//                )
//
//
//                val characteristic = characteristic.copy(
//                    coding = listOf(characteristicCoding)
//                )
//
//                val eligibility = eligibility.copy(
//                    coding = listOf(eligibilityCoding)
//                )
//
//                val program = program.copy(
//                    coding = listOf(programCoding)
//                )
//
//                val referralMethod = referralMethod.copy(
//                    coding = listOf(referralMethodCoding)
//                )
//
//                val serviceProvisionCode = serviceProvision.copy(
//                    coding = listOf(serviceProvisionCoding)
//                )
//
//                serviceCategoryList.add(newServiceCategory)
//                serviceCharacteristicList.add(characteristic)
//                serviceCommunicationList.add(newServiceCommunication)
//                serviceEligibilityList.add(eligibility)
//                serviceProgramList.add(program)
//                speciality = Speciality(listOf(specialityCoding))
//                serviceReferralMethodList.add(referralMethod)
//                serviceServiceProvisionCodeList.add(serviceProvisionCode)
//
//                var id = newServiceProvisionCostRadioGroup.checkedRadioButtonId
//                if (id != -1) {
//                    val radio = id
//                } else {
//                    //show validation error
//                }
//                performValidation()
//
//            }
//
//        }
//
//    }
//
//    private fun performValidation() {
//
//        with(binding){
//            when {
//                newServiceCategory.isEmpty() -> {
//                    newServiceCategoryAutoCompleteView.error = "Field cannot be empty"
//                    newServiceCategoryAutoCompleteView.requestFocus()
//                }
//                newServiceProviderName.isEmpty() -> {
//                    textInputLayoutNewServiceProvidedBy.error = "Field cannot be empty"
//                    newServiceProvidedBy.requestFocus()
//                }
//                newServiceName.isEmpty() -> {
//                    textInputLayoutNewServiceName.error = "Field cannot be empty"
//                    newServiceNameView.requestFocus()
//                }
//                newServiceComment.isEmpty() -> {
//                    textInputLayoutNewServiceComment.error = "Field cannot be empty"
//                    newServiceCommentView.requestFocus()
//                }
//                newServiceSpecialty.isEmpty() -> {
//                    textInputLayoutNewServiceSpecialty.error = "Field cannot be empty"
//                    newServiceSpecialtyView.requestFocus()
//                }
//                newServiceContactEmail.isEmpty() -> {
//                    textInputLayoutNewServiceContactEmail.error = "Field cannot be empty"
//                    newServiceContactEmailView.requestFocus()
//                }
//                newServiceContactPhoneNumber.isEmpty() -> {
//                    textInputLayoutNewServiceContactPhoneNumber.error = "Field cannot be empty"
//                    newServiceContactPhoneNumberView.requestFocus()
//                }
//                newServiceEligibility.isEmpty() -> {
//                    textInputLayoutNewServiceEligibility.error = "Field cannot be empty"
//                    newServiceEligibilityView.requestFocus()
//                }
//                newServiceProgram.isEmpty() -> {
//                    newServiceProgramView.error = "Field cannot be empty"
//                    newServiceProgramView.requestFocus()
//                }
//                newServiceCharacteristics.isEmpty() -> {
//                    textInputLayoutNewServiceCharacteristics.error = "Field cannot be empty"
//                    newServiceCharacteristicsView.requestFocus()
//                }
//                newServiceReferralMethod.isEmpty() -> {
//                    textInputLayoutNewServiceReferralMethod.error = "Field cannot be empty"
//                    newServiceReferralMethodView.requestFocus()
//                }
//                newServiceCommunication.isEmpty() -> {
//                    newServiceCommunicationAutoCompleteView.error = "Field cannot be empty"
//                    newServiceCommunicationAutoCompleteView.requestFocus()
//                }
//                else -> {
//
//                    //all requirements are satisfied
//                    val email = telco.copy(
//                        system = "email",
//                        rank = telecomRank++,
//                        value = newServiceContactEmail,
//                        use = "official"
//                    )
//
//                    val phone = telco.copy(
//                        system = "phone",
//                        rank = telecomRank++,
//                        value = newServiceContactPhoneNumber,
//                        use = "official"
//                    )
//
//                    telecom.add(email)
//                    telecom.add(phone)
//
//
//
//                    val newServiceRequest = NewServiceRequest(
//                        active = true,
//                        appointmentRequired = newServiceAppointmentRequired,
//                        availabilityExceptions = "",
//                        availableTime = availableTime,
//                        category = serviceCategoryList,
//                        characteristics = serviceCharacteristicList,
//                        comment = newServiceComment,
//                        communication = serviceCommunicationList,
//                        eligibility = serviceEligibilityList,
//                        extraDetails = "",
//                        name = newServiceName,
//                        notAvailable = null,
//                        photo = "",
//                        program = serviceProgramList,
//                        providedBy = newServiceProviderName,
//                        referralMethod = serviceReferralMethodList,
//                        serviceProvisionCode = serviceServiceProvisionCodeList,
//                        speciality = speciality,
//                        telecom = telecom
//                    )
//
//                    createNewService(newServiceRequest)
//                }
//            }
//
//        }
//    }
//
//    private fun createNewService(newServiceRequest: NewServiceRequest) {
//        //todo: call api to create new service
//        viewModel.createHealthCareService(newServiceRequest)
//        viewModel.healthcareServiceCreationResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
//            when(it){
//                is Resource.Success -> {
//                    hideProgress()
//                    requireView().snackbar("Service Created Successfully")
//                    findNavController().navigate(R.id.action_newService_to_servicesFragment)
//
//                }
//                is Resource.Failure -> {
//                    hideProgress()
//                    handleApiError(it) {createNewService(newServiceRequest)}
//                }
//                is Resource.Loading -> {
//                    showProgress()
//                }
//            }
//
//        })
//    }

    // Get the selected radio button text using radio button on click listener
    fun radio_button_click(view: View){
        // Get the clicked radio button instance
        val radio= binding.newServiceProvisionCostRadioGroup.checkedRadioButtonId
        newServiceProvisionCost = radio.toString()

    }

    private fun showTimePickerDialog(timeView: TextView) {
        val cal = Calendar.getInstance()
        val h = cal.get(Calendar.HOUR_OF_DAY)
        val m = cal.get(Calendar.MINUTE)


        var hourTime = ""
        var minTime = ""
        var am_pm = ""


        val testVar = timePicker.showDialog(h, m, object : TimePickerHelper.Callback {
            override fun onTimeSelected(hourOfDay: Int, minute: Int) {
                var hourOfDay = hourOfDay
                //AM_PM decider logic
                when {
                    hourOfDay == 0 -> {
                        hourOfDay += 12
                        am_pm = "AM"
                    }
                    hourOfDay == 12 -> am_pm = "PM"
                    hourOfDay > 12 -> {
                        hourOfDay -= 12
                        am_pm = "PM"
                    }
                    else -> am_pm = "AM"
                }
                val hourStr = if (hourOfDay < 10)
                    "0${hourOfDay}" else "${hourOfDay}"
                val minuteStr = if (minute < 10)
                    "0${minute}" else "${minute}"

                hourTime = hourStr
                minTime = minuteStr

                timeView.text = "${hourStr}:${minuteStr}$am_pm"

                openHour = "${hourStr}:${minuteStr}"

            }

        })
        return testVar
    }



    private fun showProgress() {
        hideProgress()
        progressDialog = requireActivity().showProgressDialog()
    }

    private fun hideProgress() {
        progressDialog?.let { if (it.isShowing) it.cancel() }
    }



    override fun getViewModel() = ServicesViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNewServiceBinding.inflate(inflater, container, false)

    override fun getFragmentRepo()= ServicesRepo(remoteDataSource.buildApi(ServicesApi::class.java), userprefs)
    override fun onCountrySelected() {

    }

}