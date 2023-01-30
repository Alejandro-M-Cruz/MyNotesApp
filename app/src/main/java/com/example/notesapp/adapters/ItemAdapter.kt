package com.example.notesapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.notesapp.R
import com.example.notesapp.fragments.NotesViewModel
import com.example.notesapp.model.Note

class ItemAdapter(
    private val context: Context,
    private val items: List<Note>,
    private val notesViewModel: NotesViewModel
) : Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View) : ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.item_text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.textView.text = items[position].title
        holder.textView.setOnClickListener { notesViewModel.editNote(items[position].noteId) }
    }

    override fun getItemCount(): Int = items.size
}