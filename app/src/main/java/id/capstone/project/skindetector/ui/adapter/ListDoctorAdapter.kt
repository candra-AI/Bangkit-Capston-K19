package id.capstone.project.skindetector.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import id.capstone.project.skindetector.data.model.DoctorEntity
import id.capstone.project.skindetector.databinding.ItemListDoctorBinding

class ListDoctorAdapter : RecyclerView.Adapter<ListDoctorAdapter.DoctorViewHolder>() {
    private val listDoctor = ArrayList<DoctorEntity>()

    fun setDoctors(doctors: List<DoctorEntity>?) {
        doctors ?: return
        listDoctor.clear()
        listDoctor.addAll(doctors)
    }

    class DoctorViewHolder(private val binding: ItemListDoctorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(doctor: DoctorEntity) {
            with(binding) {
                ivDoctor.load(doctor.urlProfile) {
                    transformations(CircleCropTransformation())
                }
                tvDoctor.text = doctor.name
                tvSpecialist.text = doctor.specialist
                tvLocation.text = doctor.location
                btnAppoinment.setOnClickListener {
                    Toast.makeText(binding.root.context, "Not yet implemented!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val view = ItemListDoctorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DoctorViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        holder.bind(listDoctor[position])
    }

    override fun getItemCount(): Int = listDoctor.size
}