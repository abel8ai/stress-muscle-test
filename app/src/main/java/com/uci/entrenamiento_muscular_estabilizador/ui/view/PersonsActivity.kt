package com.uci.entrenamiento_muscular_estabilizador.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.PersonEntity
import com.uci.entrenamiento_muscular_estabilizador.databinding.ActivityPersonsBinding
import com.uci.entrenamiento_muscular_estabilizador.ui.view.adapters.PersonAdapter
import com.uci.entrenamiento_muscular_estabilizador.ui.view_model.PersonViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PersonsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPersonsBinding
    private val personViewModel: PersonViewModel by viewModels()
    private lateinit var adapter: PersonAdapter
    private val list = emptyList<PersonEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        personViewModel
        initRecycleView()
        loadData()
        binding.favAddPerson.setOnClickListener {
            addData()
        }
        personViewModel.personModel.observe(this, Observer {
            adapter = PersonAdapter(it)
            binding.rvPersonList.adapter = adapter
        })
    }
    private fun initRecycleView() {
        adapter = PersonAdapter(list)
        binding.rvPersonList.layoutManager = LinearLayoutManager(this)
        binding.rvPersonList.adapter = adapter
    }
    private fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            personViewModel.getAllPersons()
        }
    }

    private fun addData() {
        CoroutineScope(Dispatchers.IO).launch {
            val person =
                PersonEntity(null, "Abel", "M", 31, 183.0, 89.1, "Pinar del Rio", "Pinar del Rio")
            val success = personViewModel.addPerson(person)
            runOnUiThread {
                binding.pbLoading.visibility = View.VISIBLE
                if (!success.equals(null)) {
                    binding.pbLoading.visibility = View.GONE
                    binding.rvPersonList.visibility = View.VISIBLE
                }


            }
        }
    }
}