package com.example.todoapp_kotlin.pages.mainPage.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp_kotlin.R
import com.example.todoapp_kotlin.adapters.CategoryAdapter
import com.example.todoapp_kotlin.database.entities.Caterogy
import com.example.todoapp_kotlin.viewmodels.MyViewModel

class CategiriesFragment : Fragment(), CategoryAdapter.CategoryClickInterface {

    private lateinit var viewModel: MyViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_categiries, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.recyclerview)
        // on below line we are setting layout
        // manager to our recycler view.
        recyclerView.layoutManager = GridLayoutManager(activity,2)
        // on below line we are initializing our adapter class.
        val noteRVAdapter = CategoryAdapter(requireActivity(),this)

        // on below line we are setting
        // adapter to our recycler view.
        recyclerView.adapter = noteRVAdapter
        recyclerView.setHasFixedSize(true)

        // on below line we are
        // initializing our view modal.
        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[MyViewModel::class.java]

        // on below line we are calling all notes method
        // from our view modal class to observer the changes on list.
        viewModel.allCategories.observe(requireActivity()) { list ->
            list?.let {
                // on below line we are updating our list.
                noteRVAdapter.updateList(it)
            }
        }
    }

    override fun oneCategoryClick(caterogy: Caterogy) {
        TODO("Not yet implemented")
    }
}