package com.example.ejercicio_indiv_1_m_6

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.ejercicio_indiv_1_m_6.databinding.FragmentFirstBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdapterUser
    private var selectedUser: User? = null
    private var isListVisible = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.rvUser
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = AdapterUser(emptyList())
        recyclerView.adapter = adapter


        adapter.setOnItemSelectedListener { task ->
            selectedUser = task
        }

        binding.btnAdd.setOnClickListener {
            if (validateFields()) {
                val database = Room.databaseBuilder(
                    requireContext().applicationContext,
                    UserDataBase::class.java,
                    "user_table"
                ).build()
                val newUser = User(
                    alias = binding.etAlias.text.toString(),
                    name = binding.etName.text.toString(),
                    age = binding.etAge.text.toString().toInt()
                )
                GlobalScope.launch(Dispatchers.IO) {
                    database.getTaskDao().insertTask(newUser)
                    val updatedTaskList = database.getTaskDao().getAllTask()
                    launch(Dispatchers.Main) {
                        updateAdapterData(updatedTaskList)
                    }
                }
                binding.etAlias.text.clear()
                binding.etName.text.clear()
                binding.etAge.text.clear()
            } else {
                Toast.makeText(requireContext(), "Ingresar datos en todos los campos", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnDelete.setOnClickListener {
            selectedUser?.let { task ->
                val database = Room.databaseBuilder(
                    requireContext().applicationContext,
                    UserDataBase::class.java,
                    "user_table"
                ).build()

                GlobalScope.launch(Dispatchers.IO) {
                    database.getTaskDao().deleteOneTask(task)
                    val updatedTaskList = database.getTaskDao().getAllTask1()
                    launch(Dispatchers.Main) {
                        updateAdapterData(updatedTaskList)
                    }
                }
                selectedUser = null
            }
        }

        binding.btnUser.setOnClickListener {
            if (isListVisible) {
                recyclerView.visibility = View.GONE
                isListVisible = false
            } else {
                recyclerView.visibility = View.VISIBLE
                isListVisible = true

                val database = Room.databaseBuilder(
                    requireContext().applicationContext,
                    UserDataBase::class.java,
                    "user_table"
                ).build()

                GlobalScope.launch(Dispatchers.IO) {
                    val allTasks = database.getTaskDao().getAllTask1()
                    launch(Dispatchers.Main) {
                        updateAdapterData(allTasks)
                    }
                }
            }
        }
    }

    private fun validateFields(): Boolean {
        val alias = binding.etAlias.text.toString().trim()
        val name = binding.etName.text.toString().trim()
        val age = binding.etAge.text.toString().trim()
        return alias.isNotEmpty() && name.isNotEmpty() && age.isNotEmpty()
    }

    private fun updateAdapterData(userList: List<User>) {
        adapter.itemsList = userList
        adapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}