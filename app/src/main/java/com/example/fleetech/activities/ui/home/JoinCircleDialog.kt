package com.nrv.fleetech.activities.ui.home

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.fleetech.R
import com.example.fleetech.util.Prefs
import com.example.fleetech.util.Session

import com.github.drjacky.imagepicker.ImagePicker
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


class JoinCircleDialog : DialogFragment() {
    var listener: InterfaceCommunicator? = null
    private val FINAL_TAKE_PHOTO = 1
    lateinit var photoURI: Uri
    lateinit var mBitmapValueFront: Bitmap
    var imageViewFlag = false
    var currentPhotoPath: String = ""
    val PERMISSIONS_MULTIPLE_REQUEST = 123
    lateinit var session: Session;
    private val imgCamera: Any = ""
    private var mCameraUri: Uri? = null

    interface InterfaceCommunicator {
        fun sendRequest(value: String?, verificationCode: String?)
    }

    companion object {

        const val TAG = "SimpleDialog"
        private const val KEY_TITLE = "KEY_TITLE"
        private var otpTv: String = ""
        private const val KEY_SUBTITLE = "KEY_SUBTITLE"


        fun newInstance(title: String): JoinCircleDialog {
            val args = Bundle()
            args.putString(KEY_TITLE, title)
            val fragment = JoinCircleDialog()
            fragment.arguments = args
            return fragment
        }

    }
//    override fun onAttach(context: Context?) {
//        super.onAttach(context)
//        listener = context as InterfaceCommunicator
//    }

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        listener = targetFragment as InterfaceCommunicator

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.screen_popup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        session = Session(context)
        val mEditText = view.findViewById(R.id.capture_icon) as ImageView
        val frontPodTx = view.findViewById(R.id.txt_front_pod) as TextView
        val txt_capture_frontpod = view.findViewById(R.id.txt_capture_frontpod) as TextView
        if (session.keyPodtype.equals("LRTYPE")) {
            txt_capture_frontpod.setText("Capture LR Image")
            frontPodTx.setText("Take LR Image")
        }
        mEditText.setOnClickListener {
            // checkAndroidVersion()
            //    selectImage()
//            val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            startActivityForResult(takePicture, 0)
            // dispatchTakePictureIntent()
            pickCameraImage()

        }
        setupView(view)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )


    }

    private fun setupView(view: View) {
//        view.capture_icon.setOnClickListener { v: View? ->
////            currentType = "1"
//            selectImage()
////            popupWindowFrontPod.dismiss()
//        }
//        view.otp_view1!!.setOtpCompletionListener(object : OnOtpCompletionListener {
//            override fun onOtpCompleted(otp: String?) {
////                if(otp.toString().trim().isEmpty()){
////                    view.otp_view1!!.error  ="Please check otp!!"
////                }else {
//                otpTv = otp.toString()
//                // }
//            }
//
//        })
    }

//     fun setupClickListeners(view: View) {
//        view.capture_icon.setOnClickListener {
//            selectImage()
//            dismiss()
////            if(!otpTv.isEmpty()&& otpTv!=null){
////                callValidateApi(otpTv)
////
////            }else{
////                Toast.makeText(getActivity(), "Please enter otp!!", Toast.LENGTH_LONG).show();
////            }
//        }
//
//
////        view.cancelIv.setOnClickListener {
////            dismiss()
////        }
//    }

    //    fun callValidateApi(verificationCode: String) {
//        RetrofitClient.retrofit = null
//        val userAPI = ApiUtils.testUrl
//        val call = userAPI.ValidateVerificationCode(verificationCode)
//        if (call != null) {
//            call.enqueue(object : Callback<MasterResponse<ValidateVerifyCodeResponse>> {
//                override fun onResponse(
//                    call: Call<MasterResponse<ValidateVerifyCodeResponse>>,
//                    response: Response<MasterResponse<ValidateVerifyCodeResponse>>
//                ) {
//                    if (response.code() == 200) {
//                        println("data check" + response.body())
//                        if(response.body()!!.data!=null) {
//                            val myIntent = Intent(activity, JoinCircleRelationActivity::class.java)
//                            myIntent.putExtra("userId", response.body()!!.data!!.userId)
//                            myIntent.putExtra(
//                                "verificationCode",
//                                response.body()!!.data!!.verificationCode
//                            )
//                            activity!!.startActivity(myIntent)
//                        }else{
//                            Toast.makeText(activity!!,response.body()!!.message, Toast.LENGTH_SHORT).show()
//                        }
//                        dialog!!.dismiss()
//                    }else{
//                        Toast.makeText(activity!!,response.body()!!.message, Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//                override fun onFailure(
//                    call: Call<MasterResponse<ValidateVerifyCodeResponse>>,
//                    t: Throwable
//                ) {
//
//                }
//            })
//        }
//    }
    private fun selectImage() {
        val options = arrayOf<CharSequence>("Take Photo", "Cancel")
        val builder = AlertDialog.Builder(requireContext())
        if (session.keyPodtype.equals("LRTYPE")) {
            builder.setTitle("UPLOAD LR")
        } else {
            builder.setTitle("UPLOAD POD")
        }
        builder.setItems(options) { dialog, item ->
            when {
                options[item] == "Take Photo" -> {
                    dispatchTakePictureIntent()
                }

                options[item] == "Cancel" -> {
                    dialog.dismiss()
                }
            }
        }
        builder.show()
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
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
                        requireContext(),
                        "com.nrv.fleetech.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, FINAL_TAKE_PHOTO)
                }
            }
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (resultCode == Activity.RESULT_OK) {
//
//            if (currentPhotoPath != null && currentPhotoPath != "") {
//                mBitmapValueFront = BitmapFactory.decodeFile(currentPhotoPath)
//                mBitmapValueFront = rotateImageIfRequired(
//                    requireContext(),
//                    mBitmapValueFront, photoURI
//                )
//                Prefs.get().authToken = currentPhotoPath
//                imageViewFlag = true
//            }
//
//            ShareCodeFragment.newInstance("sss")
//                .show(requireActivity().supportFragmentManager, ShareCodeFragment.TAG)
//            dismiss()
//
//        }
//        super.onActivityResult(requestCode, resultCode, data)
//
//    }

    /**
     * Rotate an image if required.
     *
     * @param img           The image bitmap
     * @param selectedImage Image URI
     * @return The resulted Bitmap after manipulation
     */
    @Throws(IOException::class)
    private fun rotateImageIfRequired(
        context: Context,
        img: Bitmap,
        selectedImage: Uri
    ): Bitmap {
        val input = context.contentResolver.openInputStream(selectedImage)
        val ei: ExifInterface
        if (Build.VERSION.SDK_INT > 23) ei = ExifInterface(input!!) else ei =
            ExifInterface(selectedImage.path!!)
        val orientation: Int =
            ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(img, 90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(img, 180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(img, 270f)
            else -> img
        }
    }

    private fun rotateImage(
        img: Bitmap,
        degree: Float
    ): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree)
        val rotatedImg = Bitmap.createBitmap(
            img,
            0,
            0,
            img.width,
            img.height,
            matrix,
            true
        )
        img.recycle()
        return rotatedImg
    }

    // Method to compress a bitmap
    private fun compressBitmap(bitmap: Bitmap, quality: Int): Bitmap {
        // Initialize a new ByteArrayStream
        val stream = ByteArrayOutputStream()

        // Compress the bitmap with JPEG format and quality 50%
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)

        val byteArray = stream.toByteArray()

        // Finally, return the compressed bitmap
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    private fun getBitmap(context: Context, imageUri: Uri): Bitmap? {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {

            ImageDecoder.decodeBitmap(
                ImageDecoder.createSource(
                    context.contentResolver,
                    imageUri
                )
            )
        } else {

            context.contentResolver
                .openInputStream(imageUri)?.use { inputStream ->
                    BitmapFactory.decodeStream(inputStream)
                }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? =
            context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "PNG_${timeStamp}_", /* prefix */
            ".png", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath

        }
    }

    private fun checkAndroidVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission()
        } else {
            // write your logic here
            selectImage()
        }
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) +
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) +
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) ||
                ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) ||
                ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.CAMERA
                )
            ) {


                requestPermissions(
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                    ),
                    PERMISSIONS_MULTIPLE_REQUEST
                )

            } else {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                    ),
                    PERMISSIONS_MULTIPLE_REQUEST
                )
            }
        } else {
            // write your logic code if permission already granted
            selectImage()
        }
    }

    protected open fun onBackPressed() {
        requireActivity().onBackPressed()
        dismiss()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != AppCompatActivity.RESULT_CANCELED) {
            when (requestCode) {
                0 -> if (resultCode == AppCompatActivity.RESULT_OK && data != null) {
                    val selectedImage = data.extras!!["data"] as Bitmap?
//                    val newBitmap = Bitmap.createBitmap(
//                        selectedImage!!.width,
//                        selectedImage.height,
//                        selectedImage.config
//                    )
//                    val canvas = Canvas(newBitmap)
//                    canvas.drawColor(Color.WHITE)
//                    canvas.drawBitmap(selectedImage, 0f, 0f, null)
//                    val outputStream = ByteArrayOutputStream()
//                    newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)

//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) saveImageInQ(selectedImage)
//                    else saveImageInLegacy(selectedImage)
                    val tempUri: Uri? = getImageUri(
                        Objects.requireNonNull(
                            requireContext()
                        ), selectedImage!!
                    )
                    Prefs.get().authToken = getRealPathFromURI(tempUri)!!
                    ShareCodeFragment.newInstance("sss")
                        .show(requireActivity().supportFragmentManager, ShareCodeFragment.TAG)
                    dismiss()
//                    data(getRealPathFromURI(tempUri)!!)
                }
            }
        }
    }
//    //Make sure to call this function on a worker thread, else it will block main thread
//    fun saveImageInQ(bitmap: Bitmap): Uri? {
//        val filename = "IMG_${System.currentTimeMillis()}.jpg"
//        var fos: OutputStream? = null
//        var imageUri: Uri? = null
//        val contentValues = ContentValues().apply {
//            put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
//            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
//            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
//            put(MediaStore.Video.Media.IS_PENDING, 1)
//        }
//
//        //use application context to get contentResolver
//        val contentResolver = requireActivity().contentResolver
//
//        contentResolver.also { resolver ->
//            imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
//            fos = imageUri?.let { resolver.openOutputStream(it) }
//        }
//
//        fos?.use { bitmap.compress(Bitmap.CompressFormat.JPEG, 70, it) }
//
//        contentValues.clear()
//        contentValues.put(MediaStore.Video.Media.IS_PENDING, 0)
//        resolver.update(imageUri, contentValues, null, null)
//
//        return imageUri
//    }

    //Make sure to call this function on a worker thread, else it will block main thread
//    fun saveTheImageLegacyStyle(bitmap:Bitmap){
//        val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
//        val image = File(imagesDir, filename)
//        fos = FileOutputStream(image)
//        fos?.use {bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)}
//    }
    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    fun getRealPathFromURI(uri: Uri?): String? {
        var path = ""
        if (Objects.requireNonNull(requireActivity()).contentResolver != null) {
            val cursor = requireActivity().contentResolver.query(
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

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!
                mCameraUri = uri
                println("VVVVVV" + mCameraUri!!.path)
                Prefs.get().authToken = mCameraUri!!.path!!
                ShareCodeFragment.newInstance("sss")
                    .show(requireActivity().supportFragmentManager, ShareCodeFragment.TAG)
                dismiss()
//                data(mCameraUri!!.path!!)
                // imgCamera.setLocalImage(uri, false)
            } else parseError(it)
        }

    fun pickCameraImage() {
        cameraLauncher.launch(
            ImagePicker.with(requireActivity())
                .crop()
                .cameraOnly()
                .maxResultSize(1080, 1920, true)
                .createIntent()
        )
    }

    private fun parseError(activityResult: ActivityResult) {
        if (activityResult.resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(
                requireContext(),
                ImagePicker.getError(activityResult.data),
                Toast.LENGTH_SHORT
            )
                .show()
        } else {
            Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

}


