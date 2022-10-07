package com.nameksolutions.regchain.curaorganization.home.personnel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nameksolutions.regchain.curaorganization.databinding.PractitionersItemLayoutBinding
import com.nameksolutions.regchain.curaorganization.home.personnel.ui.PersonnelFragmentDirections
import com.nameksolutions.regchain.curaorganization.responses.PractitionerResponse

class LabScientistsAdapter : ListAdapter<PractitionerResponse, LabScientistsAdapter.LabScientistsViewHolder>(LabScientistsAdapter.DiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LabScientistsAdapter.LabScientistsViewHolder {
        return LabScientistsViewHolder(
            PractitionersItemLayoutBinding.inflate(
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

    private fun createOnClickListener(labScientist: PractitionerResponse): View.OnClickListener {
        return View.OnClickListener {
            //navigate to page to show doctor details using navigation directions
            val direction = PersonnelFragmentDirections.actionPersonnelFragmentToPractitionerItem2(labScientist.id)
            it.findNavController().navigate(direction)
        }
    }

    class LabScientistsViewHolder(private val binding: PractitionersItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(listener: View.OnClickListener, itemData: PractitionerResponse){
            binding.apply {
                practitionersItemClickListener = listener
                practitionersItem = itemData
//                val labScientistsNames = mutableListOf<String>(labScientistItem.name!!.given!![0].substring(0, 1), labScientistItem.name!!.family!!.substring(0, 1))
//                labScientistIconText.text = "${labScientistsNames[0]}${labScientistsNames[1]}"
                executePendingBindings()
            }
        }
    }
    private class DiffCallback: DiffUtil.ItemCallback<PractitionerResponse>(){
        override fun areItemsTheSame(oldItem: PractitionerResponse, newItem: PractitionerResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PractitionerResponse,
            newItem: PractitionerResponse
        ): Boolean {
            return oldItem == newItem
        }

    }
}