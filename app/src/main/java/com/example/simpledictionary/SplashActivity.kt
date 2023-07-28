package com.example.simpledictionary

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.simpledictionary.databinding.ActivitySplashBinding
import com.example.simpledictionary.ui.main_list.MainActivity


class SplashActivity : AppCompatActivity() {

    lateinit var binding : ActivitySplashBinding
    lateinit var myAnim: Animation
    lateinit var myAnim2: Animation
    lateinit var upDownCont: Animation
    lateinit var leftRight: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding = ActivitySplashBinding.inflate(layoutInflater)
//        setContentView(R.layout.activity_splash)
        setContentView(binding.root)
        defineAnim()
        setupUI()
    }

    private fun defineAnim(){
        myAnim = AnimationUtils.loadAnimation(this, R.anim.my_anim)
        myAnim2 = AnimationUtils.loadAnimation(this, R.anim.my_anim_2)
        upDownCont = AnimationUtils.loadAnimation(this, R.anim.up_down_cont)
        leftRight = AnimationUtils.loadAnimation(this, R.anim.left_to_right)
    }

    private fun setupUI(){
        binding.ivSplash.startAnimation(myAnim)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(
                Intent(this@SplashActivity, MainActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            )
            overridePendingTransition(R.anim.slide_right, R.anim.slide_left)
        },3000)
    }
}