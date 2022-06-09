package com.nameksolutions.regchain.curaorganization.home.personnel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nameksolutions.regchain.curaorganization.databinding.PersonnelNursesItemLayoutBinding
import com.nameksolutions.regchain.curaorganization.databinding.PersonnelPharmacistsItemLayoutBinding
import com.nameksolutions.regchain.curaorganization.responses.NewPractitioner

class PharmacistsAdapter: ListAdapter<NewPractitioner, PharmacistsAdapter.PharmacistsViewHolder>(PharmacistsAdapter.DiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PharmacistsAdapter.PharmacistsViewHolder {
        return PharmacistsViewHolder(
            PersonnelPharmacistsItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        holder: PharmacistsAdapter.PharmacistsViewHolder,
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

    class PharmacistsViewHolder(private val binding: PersonnelPharmacistsItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(listener: View.OnClickListener, itemData: NewPractitioner){
            binding.apply {
                pharmacistsItemClickListener = listener
                pharmacistsItem = itemData
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