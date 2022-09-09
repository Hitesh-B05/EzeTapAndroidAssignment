package com.ezetap.assignment.view

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ezetap.assignment.R
import com.ezetap.assignment.databinding.ActivityMainBinding
import com.ezetap.assignment.presenter.IPresenter
import com.ezetap.assignment.presenter.MainActivityPresenter


class MainActivity : AppCompatActivity(), IView, View.OnClickListener {

    /**
     * View binding instance.
     */
    private lateinit var mViewBinding: ActivityMainBinding
    /**
     * IPresenter instance.
     */
    private lateinit var mMainPresenter : IPresenter
    /**
     * Heading text string.
     */
    private lateinit var mHeadingText: String
    /**
     * Image url string.
     */
    private lateinit var mImageUrl: Bitmap
    /**
     * Index of child additional child classes.
     */
    private var mViewIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mViewBinding.root)

        initResources()
    }

    /**
     * Initialize resources.
     */
    private fun initResources() {
        mMainPresenter = MainActivityPresenter(this)
    }

    /**
     * Method to initiate data fetch.
     */
    fun fetchData(view: View) {
        if(view.id == R.id.btn_fetch_data) {
            mViewBinding.layoutContainer.removeAllViews()
            mViewIndex = 0
            mMainPresenter.fetchData()
        }
    }

    override fun addLabel(id: String, text: String) {
        val textView: TextView = TextView(this)
        textView.text = text
        textView.setTextColor(Color.BLACK)
        textView.textSize = 20.0f
        textView.id = when (id) {
            "label_name" -> R.id.label_name
            "label_phone" -> R.id.label_phone
            "label_city" -> R.id.label_city
            else -> R.id.unknown
        }
        this.runOnUiThread(Runnable {
            mViewBinding.layoutContainer.addView(textView, mViewIndex++)
        })
    }

    override fun addEditText(id: String, hint: String) {
        val editText: EditText = EditText(this)
        editText.hint = hint
        when (id) {
            "text_name" -> {
                editText.id = R.id.text_name
                editText.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
            }
            "text_phone" -> {
                editText.id = R.id.text_phone
                editText.inputType = InputType.TYPE_CLASS_PHONE
                editText.filters = arrayOf<InputFilter>(LengthFilter(10))
            }
            "text_city" -> {
                editText.id = R.id.text_city
                editText.inputType = InputType.TYPE_CLASS_TEXT
            }
            else -> editText.id = R.id.unknown
        }
        this.runOnUiThread(Runnable {
            mViewBinding.layoutContainer.addView(editText, mViewIndex++)
        })
    }

    override fun addButton(text: String) {
        val button: Button = Button(this)
        button.text = text
        button.id = R.id.submit_button
        button.setOnClickListener(this)
        this.runOnUiThread(Runnable {
            mViewBinding.layoutContainer.addView(button, mViewIndex++)
        })
    }

    override fun setHeadingText(headingText: String) {
        mHeadingText = headingText
    }

    override fun setImageBitmap(imageBitmap: Bitmap) {
        mImageUrl = imageBitmap
    }

    override fun fetchDataFail() {
        this.runOnUiThread(Runnable {
            Toast.makeText(this, "Data fetch error", Toast.LENGTH_SHORT).show()
        })
    }

    override fun onClick(v: View?) {
        if (v != null && v is Button) {
            val bundle: Bundle = Bundle()
            bundle.putString("Header", mHeadingText)
            bundle.putParcelable("Image", mImageUrl)
            bundle.putString(
                "Name",
                (mViewBinding.layoutContainer.getChildAt(1) as EditText).text.toString()
            )
            bundle.putString(
                "Phone",
                (mViewBinding.layoutContainer.getChildAt(3) as EditText).text.toString()
            )
            bundle.putString(
                "City",
                (mViewBinding.layoutContainer.getChildAt(5) as EditText).text.toString()
            )
            val intent: Intent = Intent(applicationContext, SecondActivity::class.java)
            intent.putExtra("Bundle", bundle)
            startActivity(intent)
        }
    }
}