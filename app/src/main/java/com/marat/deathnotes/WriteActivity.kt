package com.marat.deathnotes

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.marat.deathnotes.databinding.ActivityWriteBinding

class WriteActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    private lateinit var binding: ActivityWriteBinding
    private lateinit var vm: MainViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vm = ViewModelProvider(this, MainViewModel.MainViewModelFactory(this)).get(MainViewModel::class.java)

        val id = intent.getParcelableExtra<Note>("aaaa")

        findViewById<TextView>(R.id.noteText).apply {
            text = id?.noteText
        }
        findViewById<TextView>(R.id.noteTitle).apply {
            text = id?.noteTitle
        }

        binding.saveButton.setOnClickListener {
            if (id == null) {
                vm.newNote(binding.noteTitle.text.toString(), binding.noteText.text.toString())
            } else {
                id.noteTitle = binding.noteTitle.text.toString()
                id.noteText = binding.noteText.text.toString()
                id.date2 = vm.date2
                vm.updateNote(id)
            }
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
