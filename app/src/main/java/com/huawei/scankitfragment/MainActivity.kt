package com.huawei.scankitfragment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private lateinit var scanKitActivityResult: ScanKitActivityResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    interface ScanKitActivityResult{
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        scanKitActivityResult.onActivityResult(requestCode, resultCode, data)
    }


    fun setFragmentListener(fragment: ScanFragment){
        scanKitActivityResult=fragment
    }
}