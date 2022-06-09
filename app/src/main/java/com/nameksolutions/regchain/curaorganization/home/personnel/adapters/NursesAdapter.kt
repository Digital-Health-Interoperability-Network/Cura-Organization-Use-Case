package com.nameksolutions.regchain.curaorganization.home.personnel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nameksolutions.regchain.curaorganization.databinding.PersonnelDoctorsItemLayoutBinding
import com.nameksolutions.regchain.curaorganization.databinding.PersonnelNursesItemLayoutBinding
import com.nameksolutions.regchain.curaorganization.responses.NewPractitioner

class NursesAdapter: ListAdapter<NewPractitioner, NursesAdapter.NursesViewHolder>(NursesAdapter.DiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NursesAdapter.NursesViewHolder {
        return NursesViewHolder(
            PersonnelNursesItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        holder: NursesAdapter.NursesViewHolder,
        position: Int
    ) {
        val nurse = getItem(position)
        holder.apply {
            bind(createOnClickListener(nurse), nurse)
            itemView.tag = nurse
        }

    }

    private fun createOnClickListener(doctor: NewPractitioner?): View.OnClickListener {
        return View.OnClickListener {
            //navigate to page to show doctor details using navigation directions
        }
    }

    class NursesViewHolder(private val binding: PersonnelNursesItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(listener: View.OnClickListener, itemData: NewPractitioner){
            binding.apply {
                nursesItemClickListener = listener
                nursesItem = itemData
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