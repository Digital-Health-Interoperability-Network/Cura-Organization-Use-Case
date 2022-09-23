package com.nameksolutions.regchain.curaorganization.home.personnel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nameksolutions.regchain.curaorganization.databinding.PersonnelPharmacistsItemLayoutBinding
import com.nameksolutions.regchain.curaorganization.home.personnel.ui.PersonnelFragmentDirections

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

    private fun createOnClickListener(pharmacist: Practitoner): View.OnClickListener {
        return View.OnClickListener {
            //navigate to page to show doctor details using navigation directions
            val direction = PersonnelFragmentDirections.actionPersonnelFragmentToPractitionerItem(pharmacist)
            it.findNavController().navigate(direction)
        }
    }

    class PharmacistsViewHolder(private val binding: PersonnelPharmacistsItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(listener: View.OnClickListener, itemData: Practitoner){
            binding.apply {
                pharmacistsItemClickListener = listener
                pharmacistsItem = itemData
//                val pharmacistNames = mutableListOf<String>(pharmacistsItem.name!!.given!![0].substring(0, 1), pharmacistsItem.name!!.family!!.substring(0, 1))
//                pharmacistIconText.text = "${pharmacistNames[0]}${pharmacistNames[1]}"
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