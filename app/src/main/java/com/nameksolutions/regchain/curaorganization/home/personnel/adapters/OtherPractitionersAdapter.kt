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
import com.nameksolutions.regchain.curaorganization.responses.PractitionerRoleX
import com.nameksolutions.regchain.curaorganization.responses.Practitoner

class OtherPractitionersAdapter : ListAdapter<Practitoner, OtherPractitionersAdapter.OtherPractitionersViewHolder>(OtherPractitionersAdapter.DiffCallback()) {

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
        val otherPractitioners = getItem(position)
        holder.apply {
            bind(createOnClickListener(otherPractitioners), otherPractitioners)
            itemView.tag = otherPractitioners
        }

    }

    private fun createOnClickListener(otherPractitioners: Practitoner?): View.OnClickListener {
        return View.OnClickListener {
            //navigate to page to show doctor details using navigation directions
        }
    }

    class OtherPractitionersViewHolder(private val binding: PersonnelOtherPractitionersItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(listener: View.OnClickListener, itemData: Practitoner){
            binding.apply {
                otherPractitionersItemClickListener = listener
                otherPractitionersItem = itemData
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