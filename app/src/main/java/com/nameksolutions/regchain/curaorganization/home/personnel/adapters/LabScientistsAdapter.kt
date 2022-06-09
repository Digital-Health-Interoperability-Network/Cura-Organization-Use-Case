package com.nameksolutions.regchain.curaorganization.home.personnel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nameksolutions.regchain.curaorganization.databinding.PersonnelLabScientistsItemLayoutBinding
import com.nameksolutions.regchain.curaorganization.databinding.PersonnelPharmacistsItemLayoutBinding
import com.nameksolutions.regchain.curaorganization.responses.NewPractitioner

class LabScientistsAdapter : ListAdapter<NewPractitioner, LabScientistsAdapter.LabScientistsViewHolder>(LabScientistsAdapter.DiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LabScientistsAdapter.LabScientistsViewHolder {
        return LabScientistsViewHolder(
            PersonnelLabScientistsItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        holder: LabScientistsAdapter.LabScientistsViewHolder,
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

    class LabScientistsViewHolder(private val binding: PersonnelLabScientistsItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(listener: View.OnClickListener, itemData: NewPractitioner){
            binding.apply {
                labScientistItemClickListener = listener
                labScientistItem = itemData
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