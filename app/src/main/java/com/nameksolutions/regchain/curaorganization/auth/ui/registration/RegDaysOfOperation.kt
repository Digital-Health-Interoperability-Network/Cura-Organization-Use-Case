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
import com.google.gson.Gson
import com.nameksolutions.regchain.curaorganization.R
import com.nameksolutions.regchain.curaorganization.auth.AuthApi
import com.nameksolutions.regchain.curaorganization.auth.AuthRepo
import com.nameksolutions.regchain.curaorganization.auth.AuthViewModel
import com.nameksolutions.regchain.curaorganization.base.BaseFragment
import com.nameksolutions.regchain.curaorganization.databinding.FragmentRegDaysOfOperationBinding
import com.nameksolutions.regchain.curaorganization.home.HomeActivity
import com.nameksolutions.regchain.curaorganization.network.Resource
import com.nameksolutions.regchain.curaorganization.requests.CreateOrganizationRequest
import com.nameksolutions.regchain.curaorganization.requests.HoursOfOperation
import com.nameksolutions.regchain.curaorganization.requests._RegIdentifiers
import com.nameksolutions.regchain.curaorganization.utils.*
import kotlinx.android.synthetic.main.time_picker_dialog_layout.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.*
import kotlin.collections.HashMap

class RegDaysOfOperation :
    BaseFragment<AuthViewModel, FragmentRegDaysOfOperationBinding, AuthRepo>() {//,
    // View.OnClickListener {

    var hoursOfOperation: HoursOfOperation = HoursOfOperation()
    var daysOfOperation = mutableListOf<String>()

    lateinit var timePicker: TimePickerHelper

    lateinit var mondayHours: String
    lateinit var tuesdayHours: String
    lateinit var wednesdayHours: String
    lateinit var thursdayHours: String
    lateinit var fridayHours: String
    lateinit var saturdayHours: String
    lateinit var sundayHours: String

    lateinit var openHour: String

    private lateinit var nextBtn: Button


    var hoursDaysOfOperation: HashMap<String, String> = hashMapOf()

    private var progressDialog: Dialog? = null
    val gson = Gson()

    private val TAG = "EQUA"


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//
//        regStepCount = 3
//        (requireActivity() as RegActivity).currentRegStep = 3

        timePicker = TimePickerHelper(requireContext(), true, false)
        with(binding) {


            layoutMonday!!.enable(false)
            layoutTuesday!!.enable(false)
            layoutWednesday!!.enable(false)
            layoutThursday!!.enable(false)
            layoutFriday!!.enable(false)
            layoutSaturday!!.enable(false)
            layoutSunday!!.enable(false)

            lateinit var mondayOpenTime: String
            lateinit var mondayCloseTime: String
            lateinit var tuesdayOpenTime: String
            lateinit var tuesdayCloseTime: String
            lateinit var wednesdayOpenTime: String
            lateinit var wednesdayCloseTime: String
            lateinit var thursdayOpenTime: String
            lateinit var thursdayCloseTime: String
            lateinit var fridayOpenTime: String
            lateinit var fridayCloseTime: String
            lateinit var saturdayOpenTime: String
            lateinit var saturdayCloseTime: String
            lateinit var sundayOpenTime: String
            lateinit var sundayCloseTime: String


            checkBoxMonday.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    checkBoxMonday.isChecked = true
                    layoutMonday!!.enable(true)
                    daysOfOperation.add("Monday")
                    //fetch Monday times in a function and return the values
                    regDooMondayOpen!!.setOnClickListener {
                        val monOpenTimeView = it as TextView
                        showTimePickerDialog(monOpenTimeView)

//                            mondayOpenTime =
//                            getOpenTime(it)
                        Log.d(
                            TAG, "onActivityCreated: ${
                                monOpenTimeView.text.toString()
                            }"
                        )
                        Log.d(TAG, "onActivityCreated: ${showTimePickerDialog(monOpenTimeView)}")
//
                        //getOpenTime(it)
                        //mondayOpenTime = monOpenTimeView.text.toString()

//                            requireContext().toast("monday open time = $mondayOpenTime")

                    }

                    Log.d(TAG, "onActivityCreatedPost: ${regDooMondayOpen.text}")


                    regDooMondayClose!!.setOnClickListener {
                        //mondayCloseTime =
                        //getCloseTime(it)
                        val closeMonTV = it as TextView
                        showTimePickerDialog(closeMonTV)
                    }

//                    Log.d(TAG, "onActivityCreated: $mondayOpenTime")

//                    }

                    //add the returned values to the list

                } else {
                    checkBoxMonday.isChecked = false
                    layoutMonday!!.enable(false)
                    //remove times from list
//                    hoursDaysOfOperation.remove("Monday")
                }

//                mondayHours = "$mondayOpenTime - $mondayCloseTime"
//
//                hoursDaysOfOperation["Monday"] = mondayHours


            }

            checkBoxTuesday.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    checkBoxTuesday.isChecked = true
                    layoutTuesday!!.enable(true)
                    daysOfOperation.add("Tuesday")
                    //fetch Tuesday times in a function and return the values

                    rooTuesdayOpen!!.setOnClickListener {
                        //tuesdayOpenTime =
//                        getOpenTime(regDayOfOperationTuesdayOpen)

                        val tueOpenTV = it as TextView
                        showTimePickerDialog(tueOpenTV)

                    }
                    rooTuesdayClose!!.setOnClickListener {
                        val tueCloseTV = it as TextView
                        showTimePickerDialog(tueCloseTV)
//                        tuesdayCloseTime = getCloseTime(regDayOfOperationTuesdayClose)
                    }
//                    tuesdayHours = "$tuesdayOpenTime - $tuesdayCloseTime"
//                    hoursDaysOfOperation.put("Tuesday", tuesdayHours)


                    //add the returned values to the list

                } else {
                    checkBoxTuesday.isChecked = false
                    layoutTuesday!!.enable(false)
                    //remove times from list
//                    hoursDaysOfOperation.remove("Tuesday")
                }

            }

            checkBoxWednesday.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) { //if it is not checked
                    checkBoxWednesday.isChecked = true
                    layoutWednesday!!.enable(true)
                    daysOfOperation.add("Wednesday")
                    //fetch Wednesday times in a function and return the values
                    rooWednesdayOpen!!.setOnClickListener {
                        val wedOpenTV = it as TextView
                        showTimePickerDialog(wedOpenTV)
//                        wednesdayOpenTime = getOpenTime(regDayOfOperationWednesdayOpen)
                    }
                    rooWednesdayClose!!.setOnClickListener {
                        val wedCloseTV = it as TextView
                        showTimePickerDialog(wedCloseTV)
//                        wednesdayCloseTime = getCloseTime(regDayOfOperationWednesdayClose)
                    }
//                    wednesdayHours = "$wednesdayOpenTime - $wednesdayCloseTime"
//                    hoursDaysOfOperation.put("Wednesday", wednesdayHours)


                    //add the returned values to the list

                } else {
                    checkBoxWednesday.isChecked = false
                    layoutWednesday!!.enable(false)
                    //remove times from list
//                    hoursDaysOfOperation.remove("Wednesday")
                }
            }
            checkBoxThursday.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) { //if it is not checked
                    checkBoxThursday.isChecked = true
                    layoutThursday!!.enable(true)
                    daysOfOperation.add("Thursday")
                    //fetch Thursday times in a function and return the values
                    rooThursdayOpen!!.setOnClickListener {
                        val thursOpenTV = it as TextView
                        showTimePickerDialog(thursOpenTV)
//                        thursdayOpenTime = getOpenTime(regDayOfOperationThursdayOpen)
                    }
                    rooThursdayClose!!.setOnClickListener {
                        val thursCloseTV = it as TextView
                        showTimePickerDialog(thursCloseTV)
//                        thursdayCloseTime = getCloseTime(regDayOfOperationThursdayClose)
                    }
//                    thursdayHours = "$thursdayOpenTime - $thursdayCloseTime"
//                    hoursDaysOfOperation.put("Thursday", thursdayHours)


                    //add the returned values to the list

                } else {
                    checkBoxThursday.isChecked = false
                    layoutThursday!!.enable(false)
                    //remove times from list
//                    hoursDaysOfOperation.remove("Thursday")


                }


                checkBoxFriday.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked) { //if it is not checked
                        checkBoxFriday.isChecked = true
                        layoutFriday!!.enable(true)
                        daysOfOperation.add("Friday")
                        //fetch Friday times in a function and return the values
                        rooFridayOpen!!.setOnClickListener {
                            val friOpenTV = it as TextView
                            showTimePickerDialog(friOpenTV)
//                        fridayOpenTime = getOpenTime(regDayOfOperationFridayOpen)
                        }
                        rooFridayClose!!.setOnClickListener {
                            val friCloseTV = it as TextView
                            showTimePickerDialog(friCloseTV)
//                        fridayCloseTime = getCloseTime(regDayOfOperationFridayClose)
                        }
//                    fridayHours = "$fridayOpenTime - $fridayCloseTime"
//                    hoursDaysOfOperation.put("Friday", fridayHours)


                        //add the returned values to the list

                    } else {
                        checkBoxFriday.isChecked = false
                        layoutFriday!!.enable(false)
                        //remove times from list
//                    hoursDaysOfOperation.remove("Friday")

                    }
                }
                checkBoxSaturday.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked) { //if it is not checked
                        checkBoxSaturday.isChecked = true
                        layoutSaturday!!.enable(true)
                        daysOfOperation.add("Saturday")
                        //fetch Saturday times in a function and return the values
                        rooSaturdayOpen!!.setOnClickListener {
                            val satOpenTV = it as TextView
                            showTimePickerDialog(satOpenTV)
//                        saturdayOpenTime = getOpenTime(regDayOfOperationSaturdayOpen)
                        }
                        rooSaturdayClose!!.setOnClickListener {
                            val satCloseTV = it as TextView
                            showTimePickerDialog(satCloseTV)
//                        saturdayCloseTime = getCloseTime(regDayOfOperationSaturdayClose)
                        }
//                    saturdayHours = "$saturdayOpenTime - $saturdayCloseTime"
//                    hoursDaysOfOperation.put("Saturday", saturdayHours)

                        //add the returned values to the list

                    } else {
                        checkBoxSaturday.isChecked = false
                        layoutSaturday!!.enable(false)
                        //remove times from list
//                    hoursDaysOfOperation.remove("Saturday")

                    }
                }

                checkBoxSunday.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked) { //if it is not checked
                        checkBoxSunday.isChecked = true
                        layoutSunday!!.enable(true)
                        daysOfOperation.add("Sunday")
                        //fetch Sunday times in a function and return the values
                        rooSundayOpen!!.setOnClickListener {
                            val sunOpenTV = it as TextView
                            showTimePickerDialog(sunOpenTV)
//                        sundayOpenTime = getOpenTime(regDayOfOperationSundayOpen)
                        }
                        rooSundayClose!!.setOnClickListener {
                            val sunCloseTV = it as TextView
                            showTimePickerDialog(sunCloseTV)
//                        sundayCloseTime = getCloseTime(regDayOfOperationSundayClose)
                        }
//                    sundayHours = "$sundayOpenTime - $sundayCloseTime"
//                    hoursDaysOfOperation.put("Sunday", sundayHours)

                        //add the returned values to the list

                    } else {
                        checkBoxSunday.isChecked = false
                        layoutSunday!!.enable(false)
                        //remove times from list
//                    hoursDaysOfOperation.remove("Sunday")

                    }
                }


                nextBtn = (requireActivity() as RegActivity).btnNext

                nextBtn.setOnClickListener {

                    hoursDaysOfOperation["Monday"] =
                        "${regDooMondayOpen!!.text.toString()} - ${regDooMondayClose!!.text.toString()}"
                    hoursDaysOfOperation["Tuesday"] =
                        "${rooTuesdayOpen!!.text.toString()} - ${rooTuesdayClose!!.text.toString()}"
                    hoursDaysOfOperation["Wednesday"] =
                        "${rooWednesdayOpen!!.text.toString()} - ${rooWednesdayClose!!.text.toString()}"
                    hoursDaysOfOperation["Thursday"] =
                        "${rooThursdayOpen!!.text.toString()} - ${rooThursdayClose!!.text.toString()}"
                    hoursDaysOfOperation["Friday"] =
                        "${rooFridayOpen!!.text.toString()} - ${rooFridayClose!!.text.toString()}"
                    hoursDaysOfOperation["Saturday"] =
                        "${rooSaturdayOpen!!.text.toString()} - ${rooSaturdayClose!!.text.toString()}"
                    hoursDaysOfOperation["Sunday"] =
                        "${rooSundayOpen!!.text.toString()} - ${rooSundayClose!!.text.toString()}"

                    Log.d(TAG, "onActivityCreated: $hoursDaysOfOperation")
//                hoursDaysOfOperation["Monday"] = "${regDooMondayOpen!!.text.toString()}-${regDooMondayClose!!.text.toString()}"
                    if (hoursDaysOfOperation.isNullOrEmpty()) {
                        requireContext().toast("At least one day required")
                    } else {
//                    val daysOfOperation = hoursDaysOfOperation.keys.toString()
                        Log.d(TAG, "onActivityCreated: $daysOfOperation")
                        val regID = _RegIdentifiers(
                            daysOfOperation = daysOfOperation, hoursOfOperation =
                            hoursDaysOfOperation

                        )
                        val hoursDaysOfOperationGson = gson.toJson(hoursDaysOfOperation)

                        val newOrganizationHoursDaysOfOperation =
                            CreateOrganizationRequest(_registryIdentifier = regID)

                        sendHoursOfOperation(newOrganizationHoursDaysOfOperation)
                    }
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
                    Log.d(TAG, "sendHoursOfOperation: Days of operation added")
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

