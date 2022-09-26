package com.nameksolutions.regchain.curaorganization.home.personnel.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nameksolutions.regchain.curaorganization.databinding.PersonnelDoctorsItemLayoutBinding

//class DoctorsAdapter: ListAdapter<Practitoner, DoctorsAdapter.DoctorsViewHolder>(DiffCallback()){
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): DoctorsAdapter.DoctorsViewHolder {
//        return DoctorsViewHolder(
//            PersonnelDoctorsItemLayoutBinding.inflate(
//                LayoutInflater.from(parent.context), parent, false
//            )
//        )
//    }
//
//    override fun onBindViewHolder(
//        holder: DoctorsAdapter.DoctorsViewHolder,
//        position: Int
//    ) {
//        val doctor = getItem(position)
//        holder.apply {
//            bind(createOnClickListener(doctor), doctor)
//            itemView.tag = doctor
//        }
//
//    }
//
//    private fun createOnClickListener(doctor: Practitoner): View.OnClickListener {
//        return View.OnClickListener {
//            //navigate to page to show doctor details using navigation directions
//            val direction = PersonnelFragmentDirections.actionPersonnelFragmentToPractitionerItem(doctor)
//            it.findNavController().navigate(direction)
//        }
//    }
//
//    class DoctorsViewHolder(private val binding: PersonnelDoctorsItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
//        fun bind(listener: View.OnClickListener, itemData: Practitoner){
//            binding.apply {
//                doctorsItemClickListener = listener
//                doctorsItem = itemData
////                val doctorNames = doctorsItem.name!!.given!![0].substring(0, 1)
////                if (doctorNames != null){
////                    doctorIconText.text = doctorNames[0].toString()
////                }
//                executePendingBindings()
//            }
//        }
//    }
//    private class DiffCallback: DiffUtil.ItemCallback<Practitoner>(){
//        override fun areItemsTheSame(oldItem: Practitoner, newItem: Practitoner): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        override fun areContentsTheSame(
//            oldItem: Practitoner,
//            newItem: Practitoner
//        ): Boolean {
//            return oldItem == newItem
//        }
//
//    }
//
//}