package com.codepath.sadab.parstagram

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.codepath.sadab.parstagram.Post.Companion.KEY_USER
import com.codepath.sadab.parstagram.fragments.ComposeFragment
import com.codepath.sadab.parstagram.fragments.FeedFragment
import com.codepath.sadab.parstagram.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.parse.*
import java.io.File
import android.text.Spannable

import android.text.style.TypefaceSpan

import android.text.SpannableString
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#000000")))

        setContentView(R.layout.activity_main)

        val fragmentManager: FragmentManager =supportFragmentManager

        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener {
            item ->
            var fragmentToShow: Fragment?=null
            when(item.itemId){
                R.id.action_home->{
                    fragmentToShow=FeedFragment()
                }
                R.id.action_compose->{
                    fragmentToShow=ComposeFragment()
                }
                R.id.action_profile->{
                    fragmentToShow=ProfileFragment()
                }
            }
            if(fragmentToShow!=null){
                fragmentManager.beginTransaction().replace(R.id.flContainer,fragmentToShow).commit()
            }

            true
        }
        findViewById<BottomNavigationView>(R.id.bottom_navigation).selectedItemId=R.id.action_home
        //queryPosts()
    }





    companion object{
        var TAG="MainActivity"
    }
}