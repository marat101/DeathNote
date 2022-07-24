package com.marat.deathnotes

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.marat.deathnotes.databinding.ItemNoteBinding

class NoteAdapter(
    private val onRemoveItem: (item: Note) -> Unit,
    private val onClick: (item: Note) -> Unit,
) : ListAdapter<Note, NoteAdapter.NoteHolder>(DiffUtils()) {

    class NoteHolder(
        private val binding: ItemNoteBinding,
        private val onClick: (item: Note) -> Unit,
        private val onRemoveItem: (item: Note) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Note) = with(binding) {
            titleNote.text = item.noteTitle
            textNote.text = item.noteText
            date.text = item.date
            cardView.setOnClickListener {
                onClick(item)
            }
            removeBtn.setOnClickListener { showPopup(removeBtn, item) }

        }

        private fun showPopup(view: View, item2: Note) {
            val popup = PopupMenu(view.context, view)
            popup.inflate(R.menu.menu_delete)

            popup.setOnMenuItemClickListener { item: MenuItem? ->
                when (item?.itemId) {
                    R.id.delete -> {
                        onRemoveItem(item2)
                    }
                }
                true
            }
            popup.show()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val inflater = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteHolder(inflater, onClick, onRemoveItem)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.bind(currentList[position])
    }
}

class DiffUtils : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }

}
