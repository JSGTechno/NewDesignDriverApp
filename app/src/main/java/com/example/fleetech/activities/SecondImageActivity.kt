package com.example.fleetech.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.Activity
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.fleetech.R
import com.example.fleetech.retrofit.Apiuitils
import com.example.fleetech.retrofit.RetrofitClient
import com.example.fleetech.retrofit.response.PODResponse
import com.example.fleetech.util.FileUtil
import com.example.fleetech.util.IntentUtil
import com.github.drjacky.imagepicker.ImagePicker
import com.github.drjacky.imagepicker.constant.ImageProvider
import com.github.drjacky.imagepicker.util.IntentUtils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.*


class SecondImageActivity : AppCompatActivity() {

    companion object {
        private const val GITHUB_REPOSITORY = "https://github.com/drjacky/ImagePicker"
    }

    private val imgCamera: Any = ""
    private var mCameraUri: Uri? = null
    private var mGalleryUri: Uri? = null
    private var mProfileUri: Uri? = null

//    private val profileLauncher =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//            if (it.resultCode == Activity.RESULT_OK) {
//                val uri = it.data?.data!!
//                mProfileUri = uri
//                imgProfile.setLocalImage(uri, true)
//            } else parseError(it)
//        }
//    private val galleryLauncher =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//            if (it.resultCode == Activity.RESULT_OK) {
//                val uri = it.data?.data!!
//                mGalleryUri = uri
//                imgGallery.setLocalImage(uri)
//            } else parseError(it)
//        }

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!
                mCameraUri = uri
                println("VVVVVV" + mCameraUri!!.path)
                data(mCameraUri!!.path!!)
                // imgCamera.setLocalImage(uri, false)
            } else parseError(it)
        }
    fun data(currentPhotoPath: String) {
        val file = File(currentPhotoPath)
        println("BBBBBBBBB" + currentPhotoPath)
        val fileBack = File(currentPhotoPath)
        println("BBBBBBBBB11" + currentPhotoPath)

        val requestFile0: RequestBody = RequestBody.create(
            "image/*".toMediaTypeOrNull(),
            file
        )
        val body0: MultipartBody.Part = MultipartBody.Part.createFormData(
            "DocImageLR",
            "image", requestFile0
        )
        val requestFile: RequestBody =
            RequestBody.create("image/*".toMediaTypeOrNull(), file)

        val body: MultipartBody.Part =
            MultipartBody.Part.createFormData("DocImageLR", file.name, requestFile)

        val requestFile2: RequestBody =
            RequestBody.create("image/*".toMediaTypeOrNull(), fileBack)
        val body2: MultipartBody.Part =
            MultipartBody.Part.createFormData("DocImageEwayBill", fileBack.name, requestFile2)
        RetrofitClient.retrofit = null
        val userAPI = Apiuitils.testUrl
        val call: Call<PODResponse> = userAPI.updateProfilePhotoProcess(
            "Bearer B3Y_vrQSyhQWe3RTbf-KFhj5NdiUVHtT6NPcBIm9D88E83Tzy49qOxzSJRoFRbBN1vZ0RpRddchn_UBpZsFN7Zv6rSMPPAyonC0ADtV-3MDdzdY7a8G0TBCFRxKiZuhEzQOc68-0-D0p6-Itg6LDGWZlE6NwaT8wtz1gzfBGdRdZGtH4UyhBVbdU92GU310bpv5XKLp9A6VInq2ccryqHhVS7SvdgZjJ_Z0NZ_aZJl7vOEsVtLAJ_l5SewdgSDKCaEHc6jpIAj_AjwSypbwBNPA4brTYAEDsbd33feAJXzRbBMqH1v2rWY2-uV0y_nP43M-_JWg47YrXwK08ieNfIQ",
            "40006",
            body,
            body2
        )
        call.enqueue(object : Callback<PODResponse> {
            override fun onResponse(
                call: Call<PODResponse>,
                response: Response<PODResponse>
            ) {
                println("nnnnnn ${call.request().url}")
                Toast.makeText(applicationContext, "" + response.code(), Toast.LENGTH_SHORT).show()
                Toast.makeText(
                    applicationContext,
                    "sss" + response.code() + "==" + response.body()!!,
                    Toast.LENGTH_SHORT
                ).show()
                val TAG: String = response.body()
                    .toString()
                Log.d(
                    "MainActivity",
                    "user image = " + response.errorBody() + "==" +
                            response.message() + "==" + response.body() + response.body()
                )
            }

            override fun onFailure(
                call: Call<PODResponse>,
                t: Throwable
            ) {
                Log.d(
                    "MainActivity",
                    "user image = " + t.cause + "==" + t.localizedMessage + "==" + t.localizedMessage
                )
                println("Error" + t.cause + "==" + t.localizedMessage + "==" + t.localizedMessage)
                Toast.makeText(
                    this@SecondImageActivity,
                    "" + "HomeFragment" + t.cause,
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        })
    }

    fun getRealPathFromURI(uri: Uri?): String? {
        var path = ""
        if (Objects.requireNonNull(applicationContext).contentResolver != null) {
            val cursor = applicationContext.contentResolver.query(
                uri!!, null, null, null, null
            )
            if (cursor != null) {
                cursor.moveToFirst()
                val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                path = cursor.getString(idx)
                cursor.close()
            }
        }
        return path
    }

    private fun parseError(activityResult: ActivityResult) {
        if (activityResult.resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(activityResult.data), Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_image)
        //  imgProfile.setDrawableImage(R.drawable.ic_baseline_person_24, true)
    }


//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.action_github -> {
//                IntentUtil.openURL(this, GITHUB_REPOSITORY)
//                return true
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }//


    fun pickCameraImage(view: View) {
        cameraLauncher.launch(
            ImagePicker.with(this)
                .crop()
                .cameraOnly()
                .maxResultSize(1080, 1920, true)
                .createIntent()
        )
    }

    fun showImage(view: View) {
        val uri = when (view) {

            imgCamera -> mCameraUri

            else -> null
        }

        uri?.let {
            startActivity(IntentUtils.getUriViewIntent(this, uri))
        }
    }

//    fun showImageInfo(view: View) {
//        val uri = when (view) {
//            imgCameraInfo -> mCameraUri
//            else -> null
//        }
//
//        AlertDialog.Builder(this)
//            .setTitle("Image Info")
//            .setMessage(FileUtil.getFileInfo(this, uri))
//            .setPositiveButton("Ok", null)
//            .show()
//    }
}