package com.ezetap.assignment.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.ezetap.assignment.R

class SecondActivity : AppCompatActivity() {

    lateinit var mImageView: ImageView
    lateinit var mHeaderText: TextView
    lateinit var mNameText: TextView
    lateinit var mPhoneText: TextView
    lateinit var mCityText: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        initResources()

    }

    private fun initResources() {
        mImageView = findViewById<ImageView>(R.id.logo)
        mHeaderText = findViewById<TextView>(R.id.header_text)
        mNameText = findViewById<TextView>(R.id.name_value)
        mPhoneText = findViewById<TextView>(R.id.phone_value)
        mCityText = findViewById<TextView>(R.id.city_value)

        val bundle: Bundle = intent.extras.getBundle("Bundle")

        mImageView.setImageBitmap(bundle.getParcelable("Image"))
        mHeaderText.text = bundle.getString("Header")
        mNameText.text = bundle.getString("Name")
        mPhoneText.text = bundle.getString("Phone")
        mCityText.text = bundle.getString("City")
    }
}