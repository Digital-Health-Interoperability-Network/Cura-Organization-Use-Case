package com.nameksolutions.regchain.curaorganization.auth.ui.registration

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.nameksolutions.regchain.curaorganization.R
import com.nameksolutions.regchain.curaorganization.databinding.ActivityRegBinding
import com.nameksolutions.regchain.curaorganization.utils.Common
import com.shuhart.stepview.StepView

/*
* This activity hosts all the fragment classes (pages) for the organization registration.
*
* Its major function is to host the 'next' button
*
* The organization registration is divided into 4 information categories
* - Organisation Details
* - Organisation Address
* - Organisation Registry Identifiers
* - Organisation Days of Operation
*
* */

class RegActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegBinding
    var currentRegStep = 0

    val TAG = "EQUA"

//    private val binding get() = _binding!!


    var mIsDualPane = false

    lateinit var btnNext: Button
    lateinit var btnPrev: Button

//    lateinit var navHostFragment: NavHostFragment
//    lateinit var navController: NavController

    fun nextBtn(): View{
        return btnNext
    }
    fun prevBtn(): View{
        return btnPrev
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityRegBinding.inflate(layoutInflater)
       btnNext = binding.btnNext
//       btnPrev = binding.btnPrev

        val totalSteps = resources.getStringArray(R.array.reg_steps).size

//        Common.regStepCount = currentRegStep

        mIsDualPane = binding.stepLayoutLand?.visibility == View.VISIBLE

//        if (mIsDualPane){ // in tablet mode
//
//            Log.d(TAG, "onCreate: We are in tablet mode")
////
////            val stepsBeanList = mutableListOf<StepBean>()
////
////            val stepBean0 = StepBean("Organization Details", 1) as String
////            val stepBean1 = StepBean("Organization Address", 1)
////            val stepBean2 = StepBean("Organization Registry Identifiers", 1)
////            val stepBean3 = StepBean("Organization Days of Operation", -1)
////
////            stepsBeanList.add(stepBean0)
////            stepsBeanList.add(stepBean1)
////            stepsBeanList.add(stepBean2)
////            stepsBeanList.add(stepBean3)
////
////
////            binding.stepViewVertical!!
////                .setStepViewTexts(stepsBeanList) //总步骤
////                .setTextSize(12) //set textSize
////                .setStepsViewIndicatorCompletedLineColor(
////                    ContextCompat.getColor(
////                        this,
////                        android.R.color.white
////                    )
////                ) //设置StepsViewIndicator完成线的颜色
////                .setStepsViewIndicatorUnCompletedLineColor(
////                    ContextCompat.getColor(
////                        this,
////                        R.color.uncompleted_text_color
////                    )
////                ) //设置StepsViewIndicator未完成线的颜色
////                .setStepViewComplectedTextColor(
////                    ContextCompat.getColor(
////                        this,
////                        android.R.color.white
////                    )
////                ) //设置StepsView text完成线的颜色
////                .setStepViewUnComplectedTextColor(
////                    ContextCompat.getColor(
////                        this,
////                        R.color.uncompleted_text_color
////                    )
////                ) //设置StepsView text未完成线的颜色
////                .setStepsViewIndicatorCompleteIcon(
////                    ContextCompat.getDrawable(
////                        this,
////                        R.drawable.ic_check
////                    )
////                ) //设置StepsViewIndicator CompleteIcon
////                .setStepsViewIndicatorDefaultIcon(
////                    ContextCompat.getDrawable(
////                        this,
////                        R.drawable.ic_default_step
////                    )
////                ) //设置StepsViewIndicator DefaultIcon
////                .setStepsViewIndicatorAttentionIcon(
////                    ContextCompat.getDrawable(
////                        this,
////                        R.drawable.ic_attention
////                    )
////                ) //设置StepsViewIndicator AttentionIcon
//
////        }else{ //in smart phone mode
////            binding.stepViewPotrait!!.state
////                .animationType(StepView.ANIMATION_ALL)
////                .animationType(resources.getInteger(android.R.integer.config_shortAnimTime))
////                .stepLineWidth(resources.getDimensionPixelSize(R.dimen.sp8))
////                .commit()
////
////            binding.stepViewPotrait.go(Common.regStepCount, true)
////            if (Common.regStepCount == totalSteps)
////                binding.stepViewPotrait.done(true)
////        }
//
//        navHostFragment =
//        supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
//
//        navController = navHostFragment.navController

        setContentView(binding.root)
    }
}
