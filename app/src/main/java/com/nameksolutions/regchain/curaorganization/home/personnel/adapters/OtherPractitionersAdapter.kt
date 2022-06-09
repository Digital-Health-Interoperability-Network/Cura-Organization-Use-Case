package com.nameksolutions.regchain.curaorganization.home.personnel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nameksolutions.regchain.curaorganization.databinding.PersonnelLabScientistsItemLayoutBinding
import com.nameksolutions.regchain.curaorganization.databinding.PersonnelOtherPractitionersItemLayoutBinding
import com.nameksolutions.regchain.curaorganization.responses.NewPractitioner

class OtherPractitionersAdapter : ListAdapter<NewPractitioner, OtherPractitionersAdapter.OtherPractitionersViewHolder>(OtherPractitionersAdapter.DiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OtherPractitionersAdapter.OtherPractitionersViewHolder {
        return OtherPractitionersViewHolder(
            PersonnelOtherPractitionersItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        holder: OtherPractitionersAdapter.OtherPractitionersViewHolder,
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

    class OtherPractitionersViewHolder(private val binding: PersonnelOtherPractitionersItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(listener: View.OnClickListener, itemData: NewPractitioner){
            binding.apply {
                otherPractitionersItemClickListener = listener
                otherPractitionersItem = itemData
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