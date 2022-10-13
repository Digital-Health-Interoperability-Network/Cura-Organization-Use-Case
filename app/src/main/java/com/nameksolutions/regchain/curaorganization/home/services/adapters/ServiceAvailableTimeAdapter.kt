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
import com.nameksolutions.regchain.curaorganization.databinding.SingleHealthCareAvailableTimeLayoutBinding
import com.nameksolutions.regchain.curaorganization.responses.services.AvailableTime

/**
 * Created by Richard Uzor  on 11/10/2022
 */
class ServiceAvailableTimeAdapter : ListAdapter<AvailableTime, ServiceAvailableTimeAdapter.ServiceAvailableTImeViewHolder>(
    ServiceAvailableTimeAdapter.DiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ServiceAvailableTimeAdapter.ServiceAvailableTImeViewHolder {
        return ServiceAvailableTImeViewHolder(
            SingleHealthCareAvailableTimeLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        holder: ServiceAvailableTimeAdapter.ServiceAvailableTImeViewHolder,
        position: Int
    ) {
        val availableTime = getItem(position)
        holder.apply {
            bind(createOnClickListener(availableTime), availableTime)
            itemView.tag = availableTime
        }

    }

    private fun createOnClickListener(otherPractitioners: AvailableTime): View.OnClickListener {
        return View.OnClickListener {
            //navigate to page to show doctor details using navigation directions
//            val direction = PersonnelFragmentDirections.actionPersonnelFragmentToPractitionerItem(otherPractitioners)
//            it.findNavController().navigate(direction)
        }
    }

    class ServiceAvailableTImeViewHolder(private val binding: SingleHealthCareAvailableTimeLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(listener: View.OnClickListener, itemData: AvailableTime){
            binding.apply {
                healthCareServiceAvailableTimeItem = itemData
//                val otherPractitionersNames = mutableListOf<String>(otherPractitionersItem.name!!.given!![0].substring(0, 1), otherPractitionersItem.name!!.family!!.substring(0, 1))
//                otherPractitionersIconText.text = "${otherPractitionersNames[0]}${otherPractitionersNames[1]}"
                executePendingBindings()
            }
        }
    }
    private class DiffCallback: DiffUtil.ItemCallback<AvailableTime>(){
        override fun areItemsTheSame(oldItem: AvailableTime, newItem: AvailableTime): Boolean {
            return oldItem.dayOfWeek == newItem.dayOfWeek
        }

        override fun areContentsTheSame(
            oldItem: AvailableTime,
            newItem: AvailableTime
        ): Boolean {
            return oldItem == newItem
        }

    }
}