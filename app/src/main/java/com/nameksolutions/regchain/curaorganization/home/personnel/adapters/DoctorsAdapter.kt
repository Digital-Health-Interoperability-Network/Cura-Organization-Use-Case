package com.nameksolutions.regchain.curaorganization.home.personnel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nameksolutions.regchain.curaorganization.databinding.ActivityHomeBinding
import com.nameksolutions.regchain.curaorganization.databinding.ActivityHomeBinding.bind
import com.nameksolutions.regchain.curaorganization.databinding.PersonnelDoctorsItemLayoutBinding
import com.nameksolutions.regchain.curaorganization.responses.DataAllPractitioner
import com.nameksolutions.regchain.curaorganization.responses.NewPractitioner

class DoctorsAdapter: ListAdapter<NewPractitioner, DoctorsAdapter.DoctorsViewHolder>(DiffCallback()){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DoctorsAdapter.DoctorsViewHolder {
        return DoctorsViewHolder(
            PersonnelDoctorsItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        holder: DoctorsAdapter.DoctorsViewHolder,
        position: Int
    ) {
        val doctor = getItem(position)
        holder.apply {
            bind(createOnClickListener(doctor), doctor)
            itemView.tag = doctor
        }

    }

    private fun createOnClickListener(doctor: NewPractitioner?): View.OnClickListener {
        return View.OnClickListener {
            //navigate to page to show doctor details using navigation directions
        }
    }

    class DoctorsViewHolder(private val binding: PersonnelDoctorsItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(listener: View.OnClickListener, itemData: NewPractitioner){
            binding.apply {
                doctorsItemClickListener = listener
                doctorsItem = itemData
                executePendingBindings()
            }
        }
    }
    private class DiffCallback: DiffUtil.ItemCallback<NewPractitioner>(){
        override fun areItemsTheSame(oldItem: NewPractitioner, newItem: NewPractitioner): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: NewPractitioner,
            newItem: NewPractitioner
        ): Boolean {
            return oldItem == newItem
        }

    }

}