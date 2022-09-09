package com.ezetap.assignment.view

import android.graphics.Bitmap

/**
 * Interface class for view.
 */
interface IView {
    /**
     * Method to indicate error in data fetching.
     */
    fun fetchDataFail()
    /**
     * Adds label to the UI.
     */
    fun addLabel(id: String, text: String)
    /**
     * Adds edit text to the UI.
     */
    fun addEditText(id: String, hint: String)
    /**
     * Adds button to the UI.
     */
    fun addButton(text: String)
    /**
     * Sets the heading text.
     */
    fun setHeadingText(headingText: String)

    /**
     * Sets the image bitmap.
     */
    fun setImageBitmap(imageBitmap: Bitmap)
}