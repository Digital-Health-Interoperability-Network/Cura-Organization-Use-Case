package com.nameksolutions.regchain.curaorganization.home.personnel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nameksolutions.regchain.curaorganization.databinding.SinglePractitionerAvailableTimeLayoutItemBinding
import com.nameksolutions.regchain.curaorganization.responses.AvailableTime
import com.nameksolutions.regchain.curaorganization.responses.AvailableTimeX

class PractitionerAvailableTImeAdapter : ListAdapter<AvailableTimeX, PractitionerAvailableTImeAdapter.PractitionerAvailableTImeViewHolder>(PractitionerAvailableTImeAdapter.DiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PractitionerAvailableTImeAdapter.PractitionerAvailableTImeViewHolder {
        return PractitionerAvailableTImeViewHolder(
            SinglePractitionerAvailableTimeLayoutItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        holder: PractitionerAvailableTImeAdapter.PractitionerAvailableTImeViewHolder,
        position: Int
    ) {
        val otherPractitioners = getItem(position)
        holder.apply {
            bind(createOnClickListener(otherPractitioners), otherPractitioners)
            itemView.tag = otherPractitioners
        }

    }

    private fun createOnClickListener(otherPractitioners: AvailableTimeX): View.OnClickListener {
        return View.OnClickListener {
            //navigate to page to show doctor details using navigation directions
//            val direction = PersonnelFragmentDirections.actionPersonnelFragmentToPractitionerItem(otherPractitioners)
//            it.findNavController().navigate(direction)
        }
    }

    class PractitionerAvailableTImeViewHolder(private val binding: SinglePractitionerAvailableTimeLayoutItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(listener: View.OnClickListener, itemData: AvailableTimeX){
            binding.apply {
                availableTimeItem = itemData
//                val otherPractitionersNames = mutableListOf<String>(otherPractitionersItem.name!!.given!![0].substring(0, 1), otherPractitionersItem.name!!.family!!.substring(0, 1))
//                otherPractitionersIconText.text = "${otherPractitionersNames[0]}${otherPractitionersNames[1]}"
                executePendingBindings()
            }
        }
    }
    private class DiffCallback: DiffUtil.ItemCallback<AvailableTimeX>(){
        override fun areItemsTheSame(oldItem: AvailableTimeX, newItem: AvailableTimeX): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(
            oldItem: AvailableTimeX,
            newItem: AvailableTimeX
        ): Boolean {
            return oldItem == newItem
        }

    }
}