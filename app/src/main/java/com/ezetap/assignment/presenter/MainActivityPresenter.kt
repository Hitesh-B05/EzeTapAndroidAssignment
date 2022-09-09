package com.ezetap.assignment.presenter

import android.graphics.Bitmap
import android.util.Log
import com.ezetap.assignment.view.IView
import com.ezetap.networklibrary.ICallback
import com.ezetap.networklibrary.INetworkApi
import com.ezetap.networklibrary.NetworkApi
import org.json.JSONArray
import org.json.JSONObject

class MainActivityPresenter(private val mMainView: IView): IPresenter, ICallback {

    /**
     * Tag for logging purpose.
     */
    private val TAG: String = javaClass.simpleName
    /**
     * Network Api instance.
     */
    private val mNetworkApi: NetworkApi
    /**
     * Url string for the data location.
     */
    private val mDataUrlString: String

    init {
        mNetworkApi = NetworkApi(this)
        mDataUrlString = "https://demo.ezetap.com/mobileapps/android_assignment.json"
    }

    override fun fetchData() {
        mNetworkApi.fetchCustomUi(mDataUrlString)
    }

    override fun getJsonResponse(jsonObject: JSONObject) {
        Log.d(TAG, "UI Json data : ${jsonObject.toString()}")
        val uiDataArray: JSONArray = jsonObject.getJSONArray("uidata")
        for (index in 0 until uiDataArray.length()) {
            val uiData: JSONObject = uiDataArray.get(index) as JSONObject
            if (uiData.has("uitype")) {
                val uiType: String = uiData.getString("uitype")
                when (uiType) {
                    "label" -> {
                        Log.d(TAG, "UI type : Label")
                        mMainView.addLabel(uiData.getString("key"), uiData.getString("value"))
                    }
                    "edittext" -> {
                        Log.d(TAG, "UI type : EditText")
                        mMainView.addEditText(uiData.getString("key"), uiData.getString("hint"))
                    }
                    "button" -> {
                        Log.d(TAG, "UI type : Button")
                        mMainView.addButton(uiData.getString("value"))
                    }
                    else -> {
                        Log.d(TAG, "Unknown UI type found.")
                    }
                }
            }

        }
        mMainView.setHeadingText(jsonObject.getString("heading-text"))
        mNetworkApi.fetchImage(jsonObject.getString("logo-url"))
    }

    override fun getImageResponse(image: Bitmap) {
        mMainView.setImageBitmap(image)
    }

    override fun responseFetchFail() {
        Log.d(TAG, "Ui data fetch failed")
        mMainView.fetchDataFail()
    }
}