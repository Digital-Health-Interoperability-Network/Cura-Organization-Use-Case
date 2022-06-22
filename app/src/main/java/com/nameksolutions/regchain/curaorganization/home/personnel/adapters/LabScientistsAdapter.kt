package com.nameksolutions.regchain.curaorganization.home.personnel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nameksolutions.regchain.curaorganization.databinding.PersonnelLabScientistsItemLayoutBinding
import com.nameksolutions.regchain.curaorganization.databinding.PersonnelPharmacistsItemLayoutBinding
import com.nameksolutions.regchain.curaorganization.home.personnel.ui.PersonnelFragmentDirections
import com.nameksolutions.regchain.curaorganization.responses.NewPractitioner
import com.nameksolutions.regchain.curaorganization.responses.Practitoner

class LabScientistsAdapter : ListAdapter<Practitoner, LabScientistsAdapter.LabScientistsViewHolder>(LabScientistsAdapter.DiffCallback()) {

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
        val labScientist = getItem(position)
        holder.apply {
            bind(createOnClickListener(labScientist), labScientist)
            itemView.tag = labScientist
        }

    }

    private fun createOnClickListener(labScientist: Practitoner): View.OnClickListener {
        return View.OnClickListener {
            //navigate to page to show doctor details using navigation directions
            val direction = PersonnelFragmentDirections.actionPersonnelFragmentToPractitionerItem(labScientist)
            it.findNavController().navigate(direction)
        }
    }

    class LabScientistsViewHolder(private val binding: PersonnelLabScientistsItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(listener: View.OnClickListener, itemData: Practitoner){
            binding.apply {
                labScientistItemClickListener = listener
                labScientistItem = itemData
//                val labScientistsNames = mutableListOf<String>(labScientistItem.name!!.given!![0].substring(0, 1), labScientistItem.name!!.family!!.substring(0, 1))
//                labScientistIconText.text = "${labScientistsNames[0]}${labScientistsNames[1]}"
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