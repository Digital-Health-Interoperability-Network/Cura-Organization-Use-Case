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

class OtherPractitionersAdapter : ListAdapter<PractitionerResponse, OtherPractitionersAdapter.OtherPractitionersViewHolder>(OtherPractitionersAdapter.DiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OtherPractitionersAdapter.OtherPractitionersViewHolder {
        return OtherPractitionersViewHolder(
            PractitionersItemLayoutBinding.inflate(
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

    private fun createOnClickListener(otherPractitioners: PractitionerResponse): View.OnClickListener {
        return View.OnClickListener {
            //navigate to page to show doctor details using navigation directions
            val direction = PersonnelFragmentDirections.actionPersonnelFragmentToPractitionerItem2(otherPractitioners)
            it.findNavController().navigate(direction)
        }
    }

    class OtherPractitionersViewHolder(private val binding: PractitionersItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(listener: View.OnClickListener, itemData: PractitionerResponse){
            binding.apply {
//                otherPractitionersItemClickListener = listener
//                otherPractitionersItem = itemData
                practitionersItemClickListener = listener
                practitionersItem = itemData
//                val otherPractitionersNames = mutableListOf<String>(otherPractitionersItem.name!!.given!![0].substring(0, 1), otherPractitionersItem.name!!.family!!.substring(0, 1))
//                otherPractitionersIconText.text = "${otherPractitionersNames[0]}${otherPractitionersNames[1]}"
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