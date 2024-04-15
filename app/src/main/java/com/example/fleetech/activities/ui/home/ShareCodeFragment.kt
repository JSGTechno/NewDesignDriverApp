package com.example.fleetech.activities.ui.home

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.drawable.ColorDrawable
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
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.fleetech.R
import com.example.fleetech.util.Prefs
import com.example.fleetech.util.Session
import com.github.drjacky.imagepicker.ImagePicker
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class ShareCodeFragment :  DialogFragment() {
    private val FINAL_TAKE_PHOTO = 1
    lateinit var photoURI: Uri
    var currentPhotoPath: String = ""

    lateinit var mBitmapValueFront: Bitmap
    var imageViewFlag = false
    val PERMISSIONS_MULTIPLE_REQUEST = 123
    var listenerShare: ShareCodeFragment.InterfaceCommunicator1? = null
    private var mCameraUri: Uri? = null
    lateinit var session: Session

    companion object {

        const val TAG = "SimpleDialog"

        private const val KEY_TITLE = "KEY_TITLE"
        private const val KEY_SUBTITLE = "KEY_SUBTITLE"

        fun newInstance(title: String): ShareCodeFragment {
            val args = Bundle()
            args.putString(KEY_TITLE, title)
            val fragment = ShareCodeFragment()
            fragment.arguments = args
            return fragment
        }

    }

    interface InterfaceCommunicator1 {
        fun sendRequest(value: String?, verificationCode: String?)
    }

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        listenerShare = targetFragment as ShareCodeFragment.InterfaceCommunicator1

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.screen_popup_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        session = Session(context)
        val mEditText = view.findViewById(R.id.capture_icon_back) as ImageView
        val txt_back_pod = view.findViewById(R.id.txt_back_pod) as TextView
        val txt_capture_backpod = view.findViewById(R.id.txt_capture_backpod) as TextView
        if (session.keyPodtype.equals("LRTYPE")) { // yogesh comment
            txt_capture_backpod.setText("Capture EwayBill Image")
            txt_back_pod.setText("Take EwayBill Image")
        }
        mEditText.setOnClickListener {
//            dispatchTakePictureIntent()
//            val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            startActivityForResult(takePicture, 0)
            pickCameraImage()
            // selectImage()
            // selectImage()
            //checkAndroidVersion()
        }
        setupClickListeners(view)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setupView(view: View) {
//        view.codeTv.text = arguments?.getString(KEY_TITLE)
    }

    private fun setupClickListeners(view: View) {
//        view.shareCodeTv.setOnClickListener {
//            // TODO: Do some task here
//            val intent= Intent()
//            intent.action=Intent.ACTION_SEND
//            intent.putExtra(Intent.EXTRA_TEXT, "Join my PCL Carer Circle! Use my invite code:-${codeTv.text.toString()}")
//            intent.type="text/plain"
//            startActivity(Intent.createChooser(intent,"Share To:"))
//            dismiss()
//        }
//        view.cancelIv.setOnClickListener {
//            // TODO: Do some task here
//            dismiss()
//        }
    }

    private fun selectImage() {
        val options = arrayOf<CharSequence>("Take Photo", "Cancel")
        val builder = AlertDialog.Builder(requireContext())
        if (session.keyPodtype.equals("LRTYPE")) {
            builder.setTitle("UPLOAD Eway Bill")

        } else {
            builder.setTitle("UPLOAD POD")
        }
        builder.setItems(options) { dialog, item ->
            when {
                options[item] == "Take Photo" -> {
//                    dispatchTakePictureIntent()
                }

                options[item] == "Cancel" -> {
                    dialog.dismiss()
                }
            }
        }
        builder.show()
    }

//    private fun dispatchTakePictureIntent() {
//        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
//            // Ensure that there's a camera activity to handle the intent
//            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
//                // Create the File where the photo should go
//                val photoFile: File? = try {
//                    createImageFile()
//                } catch (ex: IOException) {
//                    // Error occurred while creating the File
//                    null
//                }
//                // Continue only if the File was successfully created
//                photoFile?.also {
//                    photoURI = FileProvider.getUriForFile(
//                        requireContext(),
//                        "com.example.fleetech.fileprovider",
//                        it
//                    )
//                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
//                    startActivityForResult(takePictureIntent, FINAL_TAKE_PHOTO)
//                }
//            }
//        }
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != AppCompatActivity.RESULT_CANCELED) {
            when (requestCode) {
                0 -> if (resultCode == AppCompatActivity.RESULT_OK && data != null) {
                    val selectedImage = data.extras!!["data"] as Bitmap?
                    val tempUri: Uri? = getImageUri(
                        Objects.requireNonNull(
                            requireContext()
                        ), selectedImage!!
                    )
                    Prefs.get().address = getRealPathFromURI(tempUri)!!
                    MainPodDialog.newInstance("sss")
                        .show(requireActivity().supportFragmentManager, MainPodDialog.TAG)
                    dismiss()
                }
            }
        }}
        fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
            val bytes = ByteArrayOutputStream()
//            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
            val path =
                MediaStore.Images.Media.insertImage(
                    inContext.contentResolver,
                    inImage,
                    "Title",
                    null
                )
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
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (resultCode == Activity.RESULT_OK) {
//
//            if (currentPhotoPath != null && currentPhotoPath != "") {
//                mBitmapValueFront = BitmapFactory.decodeFile(currentPhotoPath)
//                mBitmapValueFront = rotateImageIfRequired(
//                    requireContext(),
//                    mBitmapValueFront, photoURI
//                )
//                Prefs.get().address = currentPhotoPath
//
//                imageViewFlag = true
//            }
////            val myIntent = Intent(activity, MainPODActivity::class.java)
////            myIntent.putExtra("bitmap",mBitmapValueFront )
////            startActivity(myIntent)
//            MainPodDialog.newInstance("sss11")
//                .show(requireActivity().supportFragmentManager, MainPodDialog.TAG)
//            dismiss()
//        }
//        super.onActivityResult(requestCode, resultCode, data)
//
//    }

//        /**
//         * Rotate an image if required.
//         *
//         * @param img           The image bitmap
//         * @param selectedImage Image URI
//         * @return The resulted Bitmap after manipulation
//         */
//        @Throws(IOException::class)
//        private fun rotateImageIfRequired(
//            context: Context,
//            img: Bitmap,
//            selectedImage: Uri
//        ): Bitmap {
//            val input = context.contentResolver.openInputStream(selectedImage)
//            val ei: ExifInterface
//            if (Build.VERSION.SDK_INT > 23) ei = ExifInterface(input!!) else ei =
//                ExifInterface(selectedImage.path!!)
//            val orientation: Int =
//                ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
//            return when (orientation) {
//                ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(img, 90f)
//                ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(img, 180f)
//                ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(img, 270f)
//                else -> img
//            }
//        }
//
//        private fun rotateImage(
//            img: Bitmap,
//            degree: Float
//        ): Bitmap {
//            val matrix = Matrix()
//            matrix.postRotate(degree)
//            val rotatedImg = Bitmap.createBitmap(
//                img,
//                0,
//                0,
//                img.width,
//                img.height,
//                matrix,
//                true
//            )
//            img.recycle()
//            return rotatedImg
//        }
//
//        // Method to compress a bitmap
//        private fun compressBitmap(bitmap: Bitmap, quality: Int): Bitmap {
//            // Initialize a new ByteArrayStream
//            val stream = ByteArrayOutputStream()
//
//            // Compress the bitmap with JPEG format and quality 50%
//            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)
//
//            val byteArray = stream.toByteArray()
//
//            // Finally, return the compressed bitmap
//            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
//        }
//
//        private fun getBitmap(context: Context, imageUri: Uri): Bitmap? {
//
//            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//
//                ImageDecoder.decodeBitmap(
//                    ImageDecoder.createSource(
//                        context.contentResolver,
//                        imageUri
//                    )
//                )
//            } else {
//
//                context.contentResolver
//                    .openInputStream(imageUri)?.use { inputStream ->
//                        BitmapFactory.decodeStream(inputStream)
//                    }
//            }
//        }
//
//        @Throws(IOException::class)
//        private fun createImageFile(): File {
//            // Create an image file name
//            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
//            val storageDir: File? =
//                context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//            return File.createTempFile(
//                "PNG_${timeStamp}_", /* prefix */
//                ".png", /* suffix */
//                storageDir /* directory */
//            ).apply {
//                // Save a file: path for use with ACTION_VIEW intents
//                currentPhotoPath = absolutePath
//
//            }
//        }
//
//        private fun checkAndroidVersion() {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                checkPermission()
//            } else {
//                // write your logic here
//                //selectImage()
//                dispatchTakePictureIntent()
//
//            }
//        }
//
//        private fun checkPermission() {
//            if (ContextCompat.checkSelfPermission(
//                    requireContext(),
//                    Manifest.permission.READ_EXTERNAL_STORAGE
//                ) +
//                ContextCompat.checkSelfPermission(
//                    requireContext(),
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE
//                ) +
//                ContextCompat.checkSelfPermission(
//                    requireContext(),
//                    Manifest.permission.CAMERA
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//                if (ActivityCompat.shouldShowRequestPermissionRationale(
//                        requireActivity(),
//                        Manifest.permission.READ_EXTERNAL_STORAGE
//                    ) ||
//                    ActivityCompat.shouldShowRequestPermissionRationale(
//                        requireActivity(),
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE
//                    ) ||
//                    ActivityCompat.shouldShowRequestPermissionRationale(
//                        requireActivity(),
//                        Manifest.permission.CAMERA
//                    )
//                ) {
//
//
//                    requestPermissions(
//                        arrayOf(
//                            Manifest.permission.READ_EXTERNAL_STORAGE,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                            Manifest.permission.CAMERA
//                        ),
//                        PERMISSIONS_MULTIPLE_REQUEST
//                    )
//
//                } else {
//                    requestPermissions(
//                        arrayOf(
//                            Manifest.permission.READ_EXTERNAL_STORAGE,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                            Manifest.permission.CAMERA
//                        ),
//                        PERMISSIONS_MULTIPLE_REQUEST
//                    )
//                }
//            } else {
//                // write your logic code if permission already granted
//                selectImage()
//            }
//        }
//
//        protected open fun onBackPressed() {
//            requireActivity().onBackPressed()
//            dismiss()
//        }
//    }

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!
                mCameraUri = uri
                println("VVVVVV" + mCameraUri!!.path)
                Prefs.get().address = mCameraUri!!.path!!
                MainPodDialog.newInstance("sss")
                    .show(requireActivity().supportFragmentManager, MainPodDialog.TAG)
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
