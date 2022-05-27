package com.uci.entrenamiento_muscular_estabilizador.ui.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.uci.entrenamiento_muscular_estabilizador.R
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.PracticantEntity
import com.uci.entrenamiento_muscular_estabilizador.ui.view_model.PracticantViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PracticantAdapter(private val practicants: MutableList<PracticantEntity>,
                        private val practicantViewModel: PracticantViewModel):RecyclerView.Adapter<PracticantViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PracticantViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PracticantViewHolder(layoutInflater.inflate(R.layout.item_document, parent, false))
    }

    override fun onBindViewHolder(holder: PracticantViewHolder, position: Int) {
        val item = practicants[position]
        holder.bind(item)
        val context = holder.binding.cvElement.context
        holder.binding.cvElement.setOnLongClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.dialog_delete_practicant)
            builder.setCancelable(false)
            builder.setMessage(R.string.confirm_delete)
            builder.create()
            builder.setPositiveButton(R.string.button_accept) { dialog, which ->
                CoroutineScope(Dispatchers.IO).launch {
                    practicantViewModel.deletePracticant(item)
                }
                practicants.remove(item)
                notifyItemRemoved(position)
            }
            builder.setNegativeButton(R.string.button_cancel) { dialog, which ->
                dialog.dismiss()
            }
            builder.show()
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int = practicants.size
}