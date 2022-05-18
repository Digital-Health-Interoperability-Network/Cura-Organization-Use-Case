package com.nameksolutions.regchain.curaorganization.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.baoyachi.stepview.VerticalStepView
import com.nameksolutions.regchain.curaorganization.R
import com.nameksolutions.regchain.curaorganization.databinding.FragmentStepViewBinding


class StepView : Fragment() {

    lateinit var binding: FragmentStepViewBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentStepViewBinding.inflate(layoutInflater)

//        mSetpview0 = findViewById(R.id.step_view0) as VerticalStepView

//        with(binding){
//            val list0: MutableList<String> = ArrayList()
//            list0.add("Details")
//            list0.add("Registry Identifiers")
//            list0.add("Address")
//            list0.add("Days of Operation")
//            stepView.setStepsViewIndicatorComplectingPosition(list0.size - 2) //设置完成的步数
//                .reverseDraw(false) //default is true
//                .setStepViewTexts(list0) //总步骤
//                .setLinePaddingProportion(0.85f) //设置indicator线与线间距的比例系数
//                .setStepsViewIndicatorCompletedLineColor(
//                    ContextCompat.getColor(
//                        requireContext(),
//                        android.R.color.white
//                    )
//                ) //设置StepsViewIndicator完成线的颜色
//                .setStepsViewIndicatorUnCompletedLineColor(
//                    ContextCompat.getColor(
//                        requireContext(),
//                        R.color.uncompleted_text_color
//                    )
//                ) //设置StepsViewIndicator未完成线的颜色
//                .setStepViewComplectedTextColor(
//                    ContextCompat.getColor(
//                        requireContext(),
//                        android.R.color.white
//                    )
//                ) //设置StepsView text完成线的颜色
//                .setStepViewUnComplectedTextColor(
//                    ContextCompat.getColor(
//                        requireContext(),
//                        R.color.uncompleted_text_color
//                    )
//                ) //设置StepsView text未完成线的颜色
//                .setStepsViewIndicatorCompleteIcon(
//                    ContextCompat.getDrawable(
//                        requireContext(),
//                        R.drawable.ic_check
//                    )
//                ) //设置StepsViewIndicator CompleteIcon
//                .setStepsViewIndicatorDefaultIcon(
//                    ContextCompat.getDrawable(
//                        requireContext(),
//                        R.drawable.ic_default_step
//                    )
//                ) //设置StepsViewIndicator DefaultIcon
//                .setStepsViewIndicatorAttentionIcon(
//                    ContextCompat.getDrawable(
//                        requireContext(),
//                        R.drawable.ic_attention
//                    )
//                ) //设置StepsViewIndicator AttentionIcon
//        }


        return binding.root
    }


}