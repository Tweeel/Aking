package com.example.todoapp_kotlin.pages.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.todoapp_kotlin.R
import com.example.todoapp_kotlin.pages.onboarding.screens.FirstScreen
import com.example.todoapp_kotlin.pages.onboarding.screens.SeconScreen
import com.example.todoapp_kotlin.pages.onboarding.screens.ThirdScreen


class ViewPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_view_pager, container, false)

        val fragmentlist = arrayListOf<Fragment>(
            FirstScreen(),
            SeconScreen(),
            ThirdScreen()
        )

        val adapter = ViewPagerAdapter(
            fragmentlist,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        view.findViewById<ViewPager2>(R.id.viewPager).adapter = adapter
        return view
    }




}