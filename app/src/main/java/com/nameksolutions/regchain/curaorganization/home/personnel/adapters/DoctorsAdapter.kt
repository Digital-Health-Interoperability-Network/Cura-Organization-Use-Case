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
import com.nameksolutions.regchain.curaorganization.responses.Practitoner

class DoctorsAdapter: ListAdapter<Practitoner, DoctorsAdapter.DoctorsViewHolder>(DiffCallback()){
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

    private fun createOnClickListener(doctor: Practitoner?): View.OnClickListener {
        return View.OnClickListener {
            //navigate to page to show doctor details using navigation directions
        }
    }

    class DoctorsViewHolder(private val binding: PersonnelDoctorsItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(listener: View.OnClickListener, itemData: Practitoner){
            binding.apply {
                doctorsItemClickListener = listener
                doctorsItem = itemData
                executePendingBindings()
            }
        }
    }
    private class DiffCallback: DiffUtil.ItemCallback<Practitoner>(){
        override fun areItemsTheSame(oldItem: Practitoner, newItem: Practitoner): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Practitoner,
            newItem: Practitoner
        ): Boolean {
            return oldItem == newItem
        }

    }

}