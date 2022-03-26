package com.example.todoapp_kotlin.pages.onboarding.screens

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.todoapp_kotlin.R

class SeconScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_secon_screen, container, false)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        Glide.with(this).load(R.drawable.slide_image_2).into(view.findViewById(R.id.imageView))


        view.findViewById<Button>(R.id.skip_btn).setOnClickListener{
            viewPager?.currentItem =2
        }

        view.findViewById<Button>(R.id.back_btn).setOnClickListener{
            viewPager?.currentItem =0
        }

        val dots = mutableListOf<TextView>(
            view.findViewById(R.id.dot0),
            view.findViewById(R.id.dot1),
            view.findViewById(R.id.dot2)
        )
        for(i in 0..2){
            dots[i].text = Html.fromHtml("&#9679;")
            dots[i].textSize = 25f
            if(i==1)
                dots[2].setTextColor(resources.getColor(R.color.black))
            else
                dots[i].setTextColor(resources.getColor(R.color.grey))
        }

        return view
    }

}