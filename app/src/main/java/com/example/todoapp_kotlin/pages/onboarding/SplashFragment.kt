package com.example.todoapp_kotlin.pages.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.todoapp_kotlin.R
import com.example.todoapp_kotlin.pages.mainPage.MainActivity

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_splash_screen, container, false)

        val  animation : Animation= AnimationUtils.loadAnimation(activity,R.anim.splash_animation)
        val logo : ImageView = view.findViewById(R.id.logo)
        Glide.with(this).load(R.drawable.logo).into(logo)

        logo.animation = animation

        if(onBoardingFinished()){

            Handler().postDelayed({
                val intent = Intent(activity, MainActivity::class.java)
                activity?.startActivity(intent)
                activity?.finish()
            },1000)
        }else{
            Handler().postDelayed({
                findNavController().navigate(R.id.action_splashScreen_to_viewPagerFragment)
            },2000)
        }

        return  view
    }

    private fun onBoardingFinished() : Boolean{
        val sharedPref = activity?.getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref?.getBoolean("Finished",false) ?: true
    }




}