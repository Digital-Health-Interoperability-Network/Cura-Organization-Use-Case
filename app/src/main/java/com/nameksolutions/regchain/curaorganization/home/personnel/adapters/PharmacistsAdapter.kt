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
import com.nameksolutions.regchain.curaorganization.responses.Practitoner

class PharmacistsAdapter: ListAdapter<Practitoner, PharmacistsAdapter.PharmacistsViewHolder>(PharmacistsAdapter.DiffCallback()) {

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
        val pharmacist = getItem(position)
        holder.apply {
            bind(createOnClickListener(pharmacist), pharmacist)
            itemView.tag = pharmacist
        }

    }

    private fun createOnClickListener(pharmacist: Practitoner?): View.OnClickListener {
        return View.OnClickListener {
            //navigate to page to show doctor details using navigation directions
        }
    }

    class PharmacistsViewHolder(private val binding: PersonnelPharmacistsItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(listener: View.OnClickListener, itemData: Practitoner){
            binding.apply {
                pharmacistsItemClickListener = listener
                pharmacistsItem = itemData
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