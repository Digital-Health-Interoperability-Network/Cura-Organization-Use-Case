package com.nameksolutions.regchain.curaorganization.home.profile.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nameksolutions.regchain.curaorganization.databinding.ProfileDaysOfOperationItemBinding
import com.nameksolutions.regchain.curaorganization.responses.profile.AvailableTime
import com.nameksolutions.regchain.curaorganization.responses.profile.Telecom
import com.nameksolutions.regchain.curaorganization.utils.snackbar

class AvailableTimeAdapter :
    ListAdapter<AvailableTime, AvailableTimeAdapter.AvailableTimeViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AvailableTimeAdapter.AvailableTimeViewHolder {
        return AvailableTimeViewHolder(
            ProfileDaysOfOperationItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        holder: AvailableTimeAdapter.AvailableTimeViewHolder,
        position: Int
    ) {
        val availableTime = getItem(position)
        holder.apply {
            bind(createOnClickListener(availableTime), availableTime)
            itemView.tag = availableTime
        }

    }

    private fun createOnClickListener(availableTime: AvailableTime): View.OnClickListener {
        return View.OnClickListener {
            //navigate to page to show doctor details using navigation directions
//            val direction =
//                PersonnelFragmentDirections.actionPersonnelFragmentToPractitionerItem2(telecom.id)
//            it.findNavController().navigate(direction)
            it.snackbar("${availableTime.daysOfWeek[0]} clicked!")
        }
    }

    class AvailableTimeViewHolder(private val binding: ProfileDaysOfOperationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: View.OnClickListener, itemData: AvailableTime) {
            binding.apply {
                profileDaysOfOperationItemClickListener = listener
                daysOfOperationItem = itemData
                executePendingBindings()
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<AvailableTime>() {
        override fun areItemsTheSame(
            oldItem: AvailableTime,
            newItem: AvailableTime
        ): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(
            oldItem: AvailableTime,
            newItem: AvailableTime
        ): Boolean {
            return oldItem == newItem
        }

    }

}