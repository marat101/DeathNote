package com.marat.deathnotes

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.marat.deathnotes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var vm: MainViewModel
    private lateinit var observer: Observer<List<Note>>

    @RequiresApi(Build.VERSION_CODES.O)
    private val myAdapter = NoteAdapter(
        onRemoveItem = { removeNote(it) },
        onClick = { clickOnItem(it) })

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vm = ViewModelProvider(
            this,
            MainViewModel.MainViewModelFactory(this)
        ).get(MainViewModel::class.java)

        binding.sort2.text = vm.sortinName()
        binding.rcView.adapter = myAdapter

        observer = Observer {
            myAdapter.submitList(vm.selectedSort(it, vm.sorting(vm.selectedSort)))
            binding.counter.text = "${it.size} ВСЕГО"
        }

        vm.allNotes.observe(this, observer)

        binding.addBtn.setOnClickListener {
            startActivity(Intent(this, WriteActivity::class.java))
        }

        binding.sort.setOnClickListener {
            binding.updown.setImageResource(R.drawable.ic_up)
            showPopup(binding.sort)
            vm.allNotes.value
        }
    }

    private fun clickOnItem(item: Note) {
        val outp = Intent(this, WriteActivity::class.java)
        outp.putExtra("aaaa", item)
        startActivity(outp)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun removeNote(item: Note) {
        vm.deleteNote(item)
    }

    private fun showPopup(view: View) {
        val popup = PopupMenu(view.context, view)
        popup.inflate(R.menu.sorting)
        popup.setOnMenuItemClickListener { item: MenuItem? ->
            when (item?.itemId) {
                R.id.item1 -> {
                    vm.preferenceStore.saveSort(Sorting.SORTINGBYDATE)
                    binding.sort2.text = vm.sortinName()
                    binding.updown.setImageResource(R.drawable.ic_down)
                }
                R.id.item2 -> {
                    vm.preferenceStore.saveSort(Sorting.SORTINGBYTITLE)
                    binding.sort2.text = vm.sortinName()
                    binding.updown.setImageResource(R.drawable.ic_down)
                }
                R.id.item3 -> {
                    vm.preferenceStore.saveSort(Sorting.SORTINGBYDATE2)
                    binding.sort2.text = vm.sortinName()
                    binding.updown.setImageResource(R.drawable.ic_down)
                }
            }
            true
        }
        popup.setOnDismissListener {
            binding.updown.setImageResource(R.drawable.ic_down)
        }
        popup.show()
    }

}

