package com.nameksolutions.regchain.curaorganization.auth.ui

import android.app.Dialog
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mukesh.OnOtpCompletionListener
import com.nameksolutions.regchain.curaorganization.R
import com.nameksolutions.regchain.curaorganization.auth.AuthApi
import com.nameksolutions.regchain.curaorganization.auth.AuthRepo
import com.nameksolutions.regchain.curaorganization.auth.AuthViewModel
import com.nameksolutions.regchain.curaorganization.auth.ui.registration.RegActivity
import com.nameksolutions.regchain.curaorganization.base.BaseFragment
import com.nameksolutions.regchain.curaorganization.databinding.FragmentPersonalEmailInputBinding
import com.nameksolutions.regchain.curaorganization.network.Resource
import com.nameksolutions.regchain.curaorganization.utils.enable
import com.nameksolutions.regchain.curaorganization.utils.handleApiError
import com.nameksolutions.regchain.curaorganization.utils.showProgressDialog
import com.nameksolutions.regchain.curaorganization.utils.startNewActivity
import kotlinx.android.synthetic.main.reg_otp_layout.view.*
import java.util.*

class PersonalEmailInput :
    BaseFragment<AuthViewModel, FragmentPersonalEmailInputBinding, AuthRepo>() {

    private lateinit var personalEmail: String
    private var progressDialog: Dialog? = null
    private val COUNTDOWN_IN_MILLIS: Long = 180000
    val TAG = "EQUA"


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //disable the button until email has been entered
        binding.btnGetOtp.enable(false)


        //watch the email input field for when a character has been put
        binding.signUpPersonalEmail.addTextChangedListener {
            personalEmail = binding.signUpPersonalEmail.text.toString().trim()

            //checks if the entered email is valid
            if (!Patterns.EMAIL_ADDRESS.matcher(personalEmail).matches())
                binding.textInputLayoutSignUpPersonalEmail.error = "Input Valid Email"

            //if email is entered, enable button
            binding.btnGetOtp.enable(personalEmail.isNotEmpty())
        }

        binding.btnGetOtp.setOnClickListener {
            sendEmailForOtp(personalEmail)
        }

    }

    //function to make network call to request otp for the 1st time
    private fun sendEmailForOtp(personalEmail: String) {
        viewModel.generateOtp(personalEmail)
        Log.d(TAG, "sendEmailForOtp: $personalEmail")

        //observe the response gotten from the network call
        viewModel.otpResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it) {
                is Resource.Success -> {
                    hideProgress()
                    launchBottomSheet()
                }
                is Resource.Failure -> {
                    hideProgress()
                    handleApiError(it) { resendEmailForOtp(personalEmail) }
                }
                is Resource.Loading -> {
                    showProgress()
                }
            }
        })
    }

    //function to make network to request for a new otp
    private fun resendEmailForOtp(personalEmail: String) {
        viewModel.resendOtp(personalEmail)

        //observe the response gotten from the network call
        viewModel.otpResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            when (it) {
                is Resource.Success -> {
                    hideProgress()
                    launchBottomSheet()
                }
                is Resource.Failure -> {
                    hideProgress()
                    handleApiError(it) { resendEmailForOtp(personalEmail) }
                }
                is Resource.Loading -> {
                    showProgress()
                }
            }
        })
    }

    //function to make network call to verify entered otp
    private fun verifyOtp(email: String, otp: String) {
        viewModel.verifyOtp(email, otp)

        Log.d(TAG, "verifyOtp: called")

        //observe the response gotten from the network call
        viewModel.otpVerification.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            when (it) {
                is Resource.Success -> {
                    hideProgress()
                    requireActivity().startNewActivity(RegActivity::class.java)
                    //navigate to registration page
                }
                is Resource.Failure -> {
                    hideProgress()
                    handleApiError(it) { verifyOtp(email, otp) }
                }
                is Resource.Loading -> {
                    showProgress()
                }
            }
        })
    }


    private fun launchBottomSheet() {
        //show the otp page as a bottom sheet
        val dialog = BottomSheetDialog(requireContext())

        val view = layoutInflater.inflate(R.layout.reg_otp_layout, null)

        val otpView = view.otp_view
        val otpCountDownView = view.sign_up_otp_count_down
        otpCountDownView.setTextColor(resources.getColor(R.color.custom_color))

        otpCountDownView.enable(false)
        dialog.setCancelable(false)
        dialog.setContentView(view)
        dialog.show()

        var timeLeftInMillis = COUNTDOWN_IN_MILLIS

        val timer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                val minutes = (millisUntilFinished / 1000) / 60

                val seconds = (millisUntilFinished / 1000) % 60

                //display timer in minutes and seconds
                val timeFormatted =
                    String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
                otpCountDownView.text = timeFormatted
            }

            override fun onFinish() {
                //enable view to request for new otp after 3 minutes
                timeLeftInMillis = 0
                otpCountDownView.enable(true)
            }
        }
        timer.start()

        otpView!!.setOtpCompletionListener(object :OnOtpCompletionListener
        {
            override fun onOtpCompleted(otp: String?) {
                Log.d(TAG, "onOtpCompleted: ${otp.toString()}")
                timer.cancel()
                dialog.dismiss()
                verifyOtp(personalEmail, otp.toString())


            }

        })

        otpCountDownView.setOnClickListener {
            //enable request another otp when timer elapses
            resendEmailForOtp(personalEmail)
        }


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
    ) = FragmentPersonalEmailInputBinding.inflate(inflater, container, false)

    override fun getFragmentRepo() = AuthRepo(remoteDataSource.buildApi(AuthApi::class.java), userprefs)

}