package com.example.todoapp_kotlin.pages.mainPage.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.todoapp_kotlin.R

class CategiriesFragment : Fragment() {

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

}