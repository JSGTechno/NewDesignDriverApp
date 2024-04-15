package com.example.fleetech.activities

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.fleetech.R
import com.example.fleetech.retrofit.Apiuitils
import com.example.fleetech.retrofit.RetrofitClient
import com.example.fleetech.retrofit.response.PODResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class ImageActivity : AppCompatActivity() {
    private val PERMISSION_REQUEST_CODE: Int =23
    private var rotatedBitmap1: Bitmap?=null
    private var bitmapfront: Bitmap? = null
     private lateinit var bitmap1: Bitmap
    var mBitmapValueFront: Bitmap? = null
    private val FINAL_TAKE_PHOTO = 1
    lateinit var photoURI: Uri
    var currentPhotoPath: String = ""
    var imageType: Int = 0
    var outPutfileUri1: Uri? = null
    var d1: Drawable? = null
    lateinit var file1: File
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        val my_avatar_imageview = findViewById<ImageView>(R.id.my_avatar_imageview)
        my_avatar_imageview.setOnClickListener {
//            if(checkAndRequestPermissions(this@ImageActivity)){
//            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
////            file1 = File(
////                Objects.requireNonNull(applicationContext)
////                    .getExternalFilesDir(Environment.DIRECTORY_PICTURES)
////            "MyPhoto1.jpg"
////            )
//
//            val mediaStorageDir: File = File(
//                Objects.requireNonNull(applicationContext)
//                    .getExternalFilesDir(Environment.DIRECTORY_PICTURES),
//                "INVision"
//            )
//            // Create the storage directory if it does not exist
//            // Create the storage directory if it does not exist
//            if (!mediaStorageDir.exists()) {
//                if (!mediaStorageDir.mkdirs()) {
//                    Log.e("Directory create fail", "Oops! Failed create directory")
//                }
//            }
//             file1 = File(
//                mediaStorageDir.path + File.separator
//                        + "IMG_" + 1 + System.currentTimeMillis() + ".jpg"
//            )
//
////            outPutfileUri1 = Uri.fromFile(file1)
//           outPutfileUri1 = FileProvider.getUriForFile(
//                this@ImageActivity,
//                "com.example.fleetech.fileprovider",
//                file1
//            )
//
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutfileUri1)
//            startActivityForResult(intent, 0)
          chooseImage(this@ImageActivity);
            // }
        }

    }

    // function to let's the user to choose image from camera or gallery
    private fun chooseImage(context: Context) {
        val optionsMenu = arrayOf<CharSequence>(
            "Take Photo",
            "Choose from Gallery",
            "Exit"
        ) // create a menuOption Array
        // create a dialog for showing the optionsMenu
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        // set the items in builder
        builder.setItems(optionsMenu,
            DialogInterface.OnClickListener { dialogInterface, i ->
                if (optionsMenu[i] == "Take Photo") {
                    // Open the camera and get the photo
//                    val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                    startActivityForResult(takePicture, 0)
//                    //                   dispatchTakePictureIntent()
                    if(checkPermissions()){
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            file1 = File(
//                Objects.requireNonNull(applicationContext)
//                    .getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//            "MyPhoto1.jpg"
//            )

                        val mediaStorageDir: File = File(
                            Objects.requireNonNull(applicationContext)
                                .getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                            "INVision"
                        )
                        // Create the storage directory if it does not exist
                        // Create the storage directory if it does not exist
                        if (!mediaStorageDir.exists()) {
                            if (!mediaStorageDir.mkdirs()) {
                                Log.e("Directory create fail", "Oops! Failed create directory")
                            }
                        }
                        file1 = File(
                            mediaStorageDir.path + File.separator
                                    + "IMG_" + 1 + System.currentTimeMillis() + ".jpg"
                        )

//            outPutfileUri1 = Uri.fromFile(file1)
                        outPutfileUri1 = FileProvider.getUriForFile(
                            this@ImageActivity,
                            "com.example.fleetech.fileprovider",
                            file1
                        )

                        intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutfileUri1)
                        startActivityForResult(intent, 0)
                    }

                } else if (optionsMenu[i] == "Choose from Gallery") {
                    // choose from  external storage
                    val pickPhoto =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(pickPhoto, 1)
                } else if (optionsMenu[i] == "Exit") {
                    dialogInterface.dismiss()
                }
            })
        builder.show()
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(applicationContext.packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    photoURI = FileProvider.getUriForFile(
                        applicationContext,
                        "com.example.fleetech.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, FINAL_TAKE_PHOTO)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                /* bitmapfront = (Bitmap) data.getExtras().get("data");
               System.out.print("Getting_front" + bitmapfront);
                System.out.println("Fileonesize" +bitmapfront.getByteCount());*/
                try {
                    bitmap1 =
                        MediaStore.Images.Media.getBitmap(this.contentResolver, outPutfileUri1)
                    println("Getting_uri_data_bitmap_front$bitmap1")
                     d1 = BitmapDrawable (resources, bitmap1)
                    bitmapfront = getResizedBitmap(bitmap1, 600)
                    println("Fileonesize_one" + bitmapfront!!.getByteCount())
                    val bounds = BitmapFactory.Options()
                    bounds.inJustDecodeBounds = true
                    bounds.inSampleSize = 2
                    BitmapFactory.decodeFile(file1.toString(), bounds)
                    val opts = BitmapFactory.Options()
                    val bm = BitmapFactory.decodeFile(file1.toString().toString(), opts)
                    val exif = ExifInterface(file1.toString().toString())
                    val orientString: String = exif.getAttribute(ExifInterface.TAG_ORIENTATION).toString()
                    val orientation = orientString?.toInt() ?: ExifInterface.ORIENTATION_NORMAL
                    var rotationAngle = 0
                    if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90
                    if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180
                    if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270
                    val matrix = Matrix()
                    matrix.setRotate(
                        rotationAngle.toFloat(), bm.width.toFloat() / 2, bm.height
                            .toFloat() / 2
                    )
                    rotatedBitmap1 = Bitmap.createBitmap(
                        bm,
                        0,
                        0,
                        bounds.outWidth,
                        bounds.outHeight,
                        matrix,
                        true
                    )

                    println("fimage $rotatedBitmap1")
                    val tempUri: Uri? = getImageUri(
                        Objects.requireNonNull(
                            applicationContext
                        ), bitmapfront!!
                    )
                    data(getRealPathFromURI(tempUri)!!)

                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }
    fun getFileDataFromDrawable(bitmap: Bitmap): ByteArray? {
        try {
            val byteArrayOutputStream = ByteArrayOutputStream()
            //         bitmap.compress(Bitmap.CompressFormat.PNG, 101, byteArrayOutputStream);
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream)
            return byteArrayOutputStream.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ByteArray(0)
    }
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode != RESULT_CANCELED) {
//            when (requestCode) {
//                0 -> if (resultCode == RESULT_OK && data != null) {
//                    val selectedImage = data.extras!!["data"] as Bitmap?
////                    val newBitmap = Bitmap.createBitmap(
////                        selectedImage!!.width,
////                        selectedImage.height,
////                        selectedImage.config
////                    )
////                    val canvas = Canvas(newBitmap)
////                    canvas.drawColor(Color.WHITE)
////                    canvas.drawBitmap(selectedImage, 0f, 0f, null)
////                    val outputStream = ByteArrayOutputStream()
////                    newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
//                    val tempUri: Uri? = getImageUri(
//                        Objects.requireNonNull(
//                            applicationContext
//                        ), selectedImage!!
//                    )
//                    data(getRealPathFromURI(tempUri)!!)
//                }
//                1 -> if (resultCode == RESULT_OK && data != null) {
//                    val selectedImage = data.data
//                    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
//                    if (selectedImage != null) {
//                        val cursor: Cursor? =
//                            contentResolver.query(selectedImage, filePathColumn, null, null, null)
//                        if (cursor != null) {
//                            cursor.moveToFirst()
//                            val columnIndex: Int = cursor.getColumnIndex(filePathColumn[0])
//                            val picturePath: String = cursor.getString(columnIndex)
//                            data(picturePath)
////                            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath))
//                            cursor.close()
//                        }
//                    }
//                }
//            }
//        }
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (resultCode == Activity.RESULT_OK) {
//
//            if (currentPhotoPath != null && currentPhotoPath != "") {
//                mBitmapValueFront = BitmapFactory.decodeFile(currentPhotoPath)
////                mBitmapValueFront = rotateImageIfRequired(
////                    applicationContext,
////                    mBitmapValueFront, photoURI
////                )
//                println("Path---" + photoURI.path)
//                data(photoURI.path!!)
////                Prefs.get().address = currentPhotoPath
//
//            }
////            val myIntent = Intent(activity, MainPODActivity::class.java)
////            myIntent.putExtra("bitmap",mBitmapValueFront )
////            startActivity(myIntent)
////            MainPodDialog.newInstance("sss11")
////                .show(requireActivity().supportFragmentManager, MainPodDialog.TAG)
////            dismiss()
//        }
//        super.onActivityResult(requestCode, resultCode, data)
//
//    }

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
            "40004",
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
                    this@ImageActivity,
                    "" + "HomeFragment" + t.cause,
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        })
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        if (storageDir != null) {
            storageDir.mkdirs()
        };
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
            println("VVVVVVVVVVV" + currentPhotoPath)
        }
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
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


    private fun getResizedBitmap(bitmap1: Bitmap, i: Int): Bitmap? {
        var width = bitmap1.width
        var height = bitmap1.height
        val bitmapRatio = width.toFloat() / height.toFloat()
        if (bitmapRatio > 1) {
            width = i
            height = (width / bitmapRatio).toInt()
        } else {
            height = i
            width = (height * bitmapRatio).toInt()
        }
        return Bitmap.createScaledBitmap(bitmap1, width, height, true)
    }
    var permissions = arrayOf(
        Manifest.permission.INTERNET,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.VIBRATE,
        Manifest.permission.RECORD_AUDIO
    )

    private fun checkPermissions(): Boolean {
        var result: Int
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        for (p in permissions) {
            result = ContextCompat.checkSelfPermission(this, p)
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p)
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toTypedArray(), 100)
            return false
        }
        return true
    }
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            if (grantResults.size > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                // do something
                chooseImage(this@ImageActivity)
            }
            return
        }
    }
}