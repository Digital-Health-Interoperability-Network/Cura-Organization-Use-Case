/*
 * Copyright (c) 2022.
 * Richard Uzor
 * For Namek Solutions Limited,
 * Abuja, FCT.
 * Nigeria
 */

package com.nameksolutions.regchain.curaorganization.home.services.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nameksolutions.regchain.curaorganization.databinding.HealthCareServiceCategoryItemLayoutBinding
import com.nameksolutions.regchain.curaorganization.responses.services.HealthcareService

/**
 * Created by Richard Uzor  on 10/10/2022
 */
class HealthCareServiceCategoryAdapter: ListAdapter<HealthcareService, HealthCareServiceCategoryAdapter.HealthCareServicesCategoryViewHolder>(DiffCallback()){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HealthCareServiceCategoryAdapter.HealthCareServicesCategoryViewHolder {
        return HealthCareServicesCategoryViewHolder(
            HealthCareServiceCategoryItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        holder: HealthCareServiceCategoryAdapter.HealthCareServicesCategoryViewHolder,
        position: Int
    ) {
        val healthCareServiceCategory = getItem(position)
        holder.apply {
            bind(createOnClickListener(healthCareServiceCategory), healthCareServiceCategory)
            itemView.tag = healthCareServiceCategory

        }

    }

    private fun createOnClickListener(healthCareService: HealthcareService): View.OnClickListener {
        return View.OnClickListener {
            //navigate to page to show doctor details using navigation directions
//            val direction = PersonnelFragmentDirections.actionPersonnelFragmentToPractitionerItem2(practitioner.id)
//            it.findNavController().navigate(direction)
        }
    }

    class HealthCareServicesCategoryViewHolder(private val binding: HealthCareServiceCategoryItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(listener: View.OnClickListener, itemData: HealthcareService){
            binding.apply {
                healthCareServiceCategoryItemClickListener = listener
                healthCareServiceCategoryItem = itemData

                executePendingBindings()
            }
        }
    }
    private class DiffCallback: DiffUtil.ItemCallback<HealthcareService>(){
        override fun areItemsTheSame(oldItem: HealthcareService, newItem: HealthcareService): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(
            oldItem: HealthcareService,
            newItem: HealthcareService
        ): Boolean {
            return oldItem == newItem
        }

    }

}