package com.example.ejercicio_indiv_1_m_6


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ejercicio_indiv_1_m_6.databinding.ItemUserBinding


class AdapterUser(var itemsList: List<User>) : RecyclerView.Adapter<AdapterUser.ViewHolder>() {

    private var onItemSelectedListener: ((User) -> Unit)? = null
    private var selectedPosition: Int = RecyclerView.NO_POSITION

    fun setOnItemSelectedListener(listener: (User) -> Unit) {
        onItemSelectedListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterUser.ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterUser.ViewHolder, position: Int) {
        val datos = itemsList[position]
        holder.bind(datos)

        holder.itemView.isActivated = selectedPosition == position

        holder.itemView.setOnClickListener {
            val currentPosition = holder.bindingAdapterPosition

            if (selectedPosition == currentPosition) {
                selectedPosition = RecyclerView.NO_POSITION
            } else {
                selectedPosition = currentPosition
            }
            notifyDataSetChanged()
            onItemSelectedListener?.invoke(datos)

        }
        if (selectedPosition == position) {
            holder.itemView.setBackgroundResource(R.drawable.selected_item_background)
        } else {
            holder.itemView.setBackgroundResource(android.R.color.transparent)
        }
    }


    override fun getItemCount(): Int {
        return itemsList.size
    }

    inner class ViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(datos: User) {
            binding.userAlias.text = datos.alias
            binding.userAge.text = datos.age.toString()
        }
    }
}