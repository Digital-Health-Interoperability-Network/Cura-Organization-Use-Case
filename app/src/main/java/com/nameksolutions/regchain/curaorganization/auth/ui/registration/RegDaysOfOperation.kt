package com.nameksolutions.regchain.curaorganization.auth.ui.registration

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import com.nameksolutions.regchain.curaorganization.R
import com.nameksolutions.regchain.curaorganization.auth.AuthApi
import com.nameksolutions.regchain.curaorganization.auth.AuthRepo
import com.nameksolutions.regchain.curaorganization.auth.AuthViewModel
import com.nameksolutions.regchain.curaorganization.base.BaseFragment
import com.nameksolutions.regchain.curaorganization.databinding.FragmentRegDaysOfOperationBinding
import com.nameksolutions.regchain.curaorganization.home.HomeActivity
import com.nameksolutions.regchain.curaorganization.network.Resource
import com.nameksolutions.regchain.curaorganization.requests.*
import com.nameksolutions.regchain.curaorganization.utils.*
import kotlinx.android.synthetic.main.time_picker_dialog_layout.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.*
import kotlin.collections.HashMap


/*
* This fragment class handles the input and registration of the organization days of operation details
*
* It collects the following organisation information and adds to the table in the database
* The details collected in this class UI include:
* - The Available Day
* - The Available Day Open Time
* - The Available Day Close Time
*
* */

class RegDaysOfOperation :
    BaseFragment<AuthViewModel, FragmentRegDaysOfOperationBinding, AuthRepo>() {

    var daysOfOperation = mutableListOf<String>()

    lateinit var timePicker: TimePickerHelper

    lateinit var openHour: String
    private val availTime = AvailableTimeRequest()
    private val _regIdentifiers = _RegIdentifiersRequest()
    private var availableTime: MutableList<AvailableTimeRequest> = mutableListOf()
    private lateinit var nextBtn: Button

    private var availabilityMon: AvailableTimeRequest = AvailableTimeRequest()
    private var availabilityTue: AvailableTimeRequest = AvailableTimeRequest()
    private var availabilityWed: AvailableTimeRequest = AvailableTimeRequest()
    private var availabilityThurs: AvailableTimeRequest = AvailableTimeRequest()
    private var availabilityFri: AvailableTimeRequest = AvailableTimeRequest()
    private var availabilitySat: AvailableTimeRequest = AvailableTimeRequest()
    private var availabilitySun: AvailableTimeRequest = AvailableTimeRequest()

    var hoursDaysOfOperation: HashMap<String, String> = hashMapOf()

    private var progressDialog: Dialog? = null

    private val TAG = "EQUA"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timePicker = TimePickerHelper(requireContext(), true, false)
        with(binding) {


            layoutMonday!!.enable(false)
            layoutTuesday!!.enable(false)
            layoutWednesday!!.enable(false)
            layoutThursday!!.enable(false)
            layoutFriday!!.enable(false)
            layoutSaturday!!.enable(false)
            layoutSunday!!.enable(false)



            checkBoxMonday.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    checkBoxMonday.isChecked = true
                    layoutMonday!!.enable(true)
                    //fetch Monday times in a function and return the values
                    regDooMondayOpen!!.setOnClickListener {
                        val monOpenTimeView = it as TextView
                        showTimePickerDialog(monOpenTimeView)

                        Log.d(
                            TAG, "onActivityCreated: ${
                                monOpenTimeView.text.toString()
                            }"
                        )
                        Log.d(TAG, "onActivityCreated: ${showTimePickerDialog(monOpenTimeView)}")

                    }

                    Log.d(TAG, "onActivityCreatedPost: ${regDooMondayOpen.text}")


                    regDooMondayClose!!.setOnClickListener {

                        val closeMonTV = it as TextView
                        showTimePickerDialog(closeMonTV)
                    }

                } else {
                    checkBoxMonday.isChecked = false
                    layoutMonday.enable(false)
                }


            }
            checkBoxTuesday.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    checkBoxTuesday.isChecked = true
                    layoutTuesday!!.enable(true)
                    //fetch Tuesday times in a function and return the values

                    rooTuesdayOpen!!.setOnClickListener {

                        val tueOpenTV = it as TextView
                        showTimePickerDialog(tueOpenTV)

                    }
                    rooTuesdayClose!!.setOnClickListener {
                        val tueCloseTV = it as TextView
                        showTimePickerDialog(tueCloseTV)
                    }
                } else {
                    checkBoxTuesday.isChecked = false
                    layoutTuesday.enable(false)

                }

            }
            checkBoxWednesday.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) { //if it is not checked
                    checkBoxWednesday.isChecked = true
                    layoutWednesday.enable(true)

                    //fetch Wednesday times in a function and return the values
                    rooWednesdayOpen!!.setOnClickListener {
                        val wedOpenTV = it as TextView
                        showTimePickerDialog(wedOpenTV)
                    }

                    rooWednesdayClose!!.setOnClickListener {
                        val wedCloseTV = it as TextView
                        showTimePickerDialog(wedCloseTV)
                    }
                } else {
                    checkBoxWednesday.isChecked = false
                    layoutWednesday.enable(false)
                }
            }
            checkBoxThursday.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) { //if it is not checked
                    checkBoxThursday.isChecked = true
                    layoutThursday.enable(true)

                    //fetch Thursday times in a function and return the values
                    rooThursdayOpen!!.setOnClickListener {
                        val thursOpenTV = it as TextView
                        showTimePickerDialog(thursOpenTV)

                    }
                    rooThursdayClose!!.setOnClickListener {
                        val thursCloseTV = it as TextView
                        showTimePickerDialog(thursCloseTV)
                    }
                } else {
                    checkBoxThursday.isChecked = false
                    layoutThursday.enable(false)

                }
            }
            checkBoxFriday.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) { //if it is not checked
                    checkBoxFriday.isChecked = true
                    layoutFriday.enable(true)

                    //fetch Friday times in a function and return the values
                    rooFridayOpen!!.setOnClickListener {
                        val friOpenTV = it as TextView
                        showTimePickerDialog(friOpenTV)
                    }
                    rooFridayClose!!.setOnClickListener {
                        val friCloseTV = it as TextView
                        showTimePickerDialog(friCloseTV)
                    }
                } else {
                    checkBoxFriday.isChecked = false
                    layoutFriday.enable(false)

                }
            }
            checkBoxSaturday.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) { //if it is not checked
                    checkBoxSaturday.isChecked = true
                    layoutSaturday.enable(true)

                    //fetch Saturday times in a function and return the values
                    rooSaturdayOpen!!.setOnClickListener {
                        val satOpenTV = it as TextView
                        showTimePickerDialog(satOpenTV)
                    }
                    rooSaturdayClose!!.setOnClickListener {
                        val satCloseTV = it as TextView
                        showTimePickerDialog(satCloseTV)
                    }
                } else {
                    checkBoxSaturday.isChecked = false
                    layoutSaturday.enable(false)

                }
            }
            checkBoxSunday.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) { //if it is not checked
                    checkBoxSunday.isChecked = true
                    layoutSunday.enable(true)

                    //fetch Sunday times in a function and return the values
                    rooSundayOpen!!.setOnClickListener {
                        val sunOpenTV = it as TextView
                        showTimePickerDialog(sunOpenTV)
                    }

                    rooSundayClose!!.setOnClickListener {
                        val sunCloseTV = it as TextView
                        showTimePickerDialog(sunCloseTV)
                    }
                } else {
                    checkBoxSunday.isChecked = false
                    layoutSunday.enable(false)

                }
            }


            nextBtn = (requireActivity() as RegActivity).btnNext

            nextBtn.setOnClickListener {

                if (checkBoxMonday.isChecked){
                    availabilityMon = availTime.copy(
                        availableStartTime = regDooMondayOpen!!.text.toString().trim(),
                        availableEndTime = regDooMondayClose!!.text.toString().trim(),
                        daysOfWeek = listOf("Mon")
                    )
                    availableTime.add(availabilityMon)
                }
                if (checkBoxTuesday.isChecked){
                    availabilityTue = availTime.copy(
                        availableStartTime = rooTuesdayOpen!!.text.toString().trim(),
                        availableEndTime = rooTuesdayClose!!.text.toString().trim(),
                        daysOfWeek = listOf("Tue")
                    )
                    availableTime.add(availabilityTue)
                }
                if (checkBoxWednesday.isChecked){
                    availabilityWed = availTime.copy(
                        availableStartTime = rooWednesdayOpen!!.text.toString().trim(),
                        availableEndTime = rooWednesdayClose!!.text.toString().trim(),
                        daysOfWeek = listOf("Wed")
                    )

                    availableTime.add(availabilityWed)
                }
                if (checkBoxThursday.isChecked){
                    availabilityThurs = availTime.copy(
                        availableStartTime = rooThursdayOpen!!.text.toString().trim(),
                        availableEndTime = rooThursdayClose!!.text.toString().trim(),
                        daysOfWeek = listOf("Thurs")
                    )
                    availableTime.add(availabilityThurs)
                }
                if (checkBoxFriday.isChecked){
                    availabilityFri = availTime.copy(
                        availableStartTime = rooFridayOpen!!.text.toString().trim(),
                        availableEndTime = rooFridayClose!!.text.toString().trim(),
                        daysOfWeek = listOf("Fri")
                    )
                    availableTime.add(availabilityFri)
                }
                if (checkBoxSaturday.isChecked){
                    availabilitySat = availTime.copy(
                        availableStartTime = rooSaturdayOpen!!.text.toString().trim(),
                        availableEndTime = rooSaturdayClose!!.text.toString().trim(),
                        daysOfWeek = listOf("Sat")
                    )
                    availableTime.add(availabilitySat)
                }
                if (checkBoxSunday.isChecked){
                    availabilitySun = availTime.copy(
                        availableStartTime = rooSundayOpen!!.text.toString().trim(),
                        availableEndTime = rooSundayClose!!.text.toString().trim(),
                        daysOfWeek = listOf("Sun")
                    )
                    availableTime.add(availabilitySun)
                }



                Log.d(TAG, "onActivityCreated: $hoursDaysOfOperation")
//                hoursDaysOfOperation["Monday"] = "${regDooMondayOpen!!.text.toString()}-${regDooMondayClose!!.text.toString()}"

                if (availableTime.isEmpty()) {
                    requireView().snackbar("At least one day required")
                } else {

                    val registryIdentifiers = _regIdentifiers.copy(
                        daysOfOperation = daysOfOperation,
                        availableTime = availableTime
                    )


                    val newOrganizationHoursDaysOfOperation =
                        CreateOrganizationRequest(_registryIdentifier = registryIdentifiers)

                    sendHoursOfOperation(newOrganizationHoursDaysOfOperation)

                }
            }

        }


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

    private fun sendHoursOfOperation(newOrganizationHoursDaysOfOperation: CreateOrganizationRequest) {

        viewModel.addOrganizationDetails(newOrganizationHoursDaysOfOperation)
        viewModel.organizationDetailsUpdate.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    hideProgress()
                    Common.organizationName = it.value.organization.name
                    Log.d(TAG, "sendHoursOfOperation: ${Common.organizationName}")
                    requireActivity().startNewActivity(HomeActivity::class.java)
                }
                is Resource.Failure -> {
                    hideProgress()
                    handleApiError(it) { sendHoursOfOperation(newOrganizationHoursDaysOfOperation) }
                }
                is Resource.Loading -> {
                    showProgress()
                }
            }
        })
    }


    private fun getOpenTime(openTimeView: View): String {

        var openTime: String = ""
        var hourString = ""
        var minString = ""
        var am_pm = ""

        val dialog = Dialog(requireActivity())
        dialog.setTitle(R.string.open_time_picker_title)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.time_picker_dialog_layout)

        val okBtn = dialog.time_picker_ok_btn
        val cancelBtn = dialog.time_picker_cancel_btn

        val timePicker = dialog.time_picker
        timePicker.setOnTimeChangedListener { _, hour, minute ->
            var hour = hour

            //AM_PM decider logic
            when {
                hour == 0 -> {
                    hour += 12
                    am_pm = "AM"
                }
                hour == 12 -> am_pm = "PM"
                hour > 12 -> {
                    hour -= 12
                    am_pm = "PM"
                }
                else -> am_pm = "AM"
            }
            if (openTimeView != null) {
                val hour = if (hour < 10) "0$hour" else hour //if the hour value is single digit
                val min =
                    if (minute < 10) "0$minute" else minute //if the minute value is single digit


                hourString = hour.toString()
                minString = min.toString()

            }
        }

        okBtn.setOnClickListener {
            openTime = "$hourString : $minString $am_pm"
            val openTimeTV = openTimeView as TextView
            openTimeTV.text = openTime
            dialog.dismiss()
        }

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

        return openTime

    }

    private fun getCloseTime(closeTimeView: View): String {

        var closeTime = ""
        var hourString = ""
        var minString = ""
        var am_pm = ""

        val dialog = Dialog(requireActivity())
        dialog.setTitle(R.string.close_time_picker_title)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.time_picker_dialog_layout)

        val okBtn = dialog.time_picker_ok_btn
        val cancelBtn = dialog.time_picker_cancel_btn

        val timePicker = dialog.time_picker
        timePicker.setOnTimeChangedListener { _, hour, minute ->
            var hour = hour

            //AM_PM decider logic
            when {
                hour == 0 -> {
                    hour += 12
                    am_pm = "AM"
                }
                hour == 12 -> am_pm = "PM"
                hour > 12 -> {
                    hour -= 12
                    am_pm = "PM"
                }
                else -> am_pm = "AM"
            }
            if (closeTimeView != null) {
                val hour = if (hour < 10) "0$hour" else hour //if the hour value is single digit
                val min =
                    if (minute < 10) "0$minute" else minute //if the minute value is single digit


                hourString = hour.toString()
                minString = min.toString()
            }
        }


        dialog.show()


        okBtn.setOnClickListener {
            closeTime = "$hourString : $minString $am_pm"
            val closeTimeTV = closeTimeView as TextView
            closeTimeTV.text = closeTime
            dialog.dismiss()
        }

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }


        return closeTime

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
    ) = FragmentRegDaysOfOperationBinding.inflate(inflater, container, false)

    override fun getFragmentRepo(): AuthRepo {
        val token = runBlocking { userprefs.authToken.first() }
        val api = remoteDataSource.buildApi(AuthApi::class.java, token)
        return AuthRepo(api, userprefs)
    }

}

