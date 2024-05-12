package com.nrv.fleetech.activities.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.fleetech.R
import com.example.fleetech.activities.MainActivity
import com.example.fleetech.retrofit.Apiuitils
import com.example.fleetech.retrofit.RetrofitClient
import com.example.fleetech.retrofit.response.PODResponse
import com.example.fleetech.util.Prefs
import com.example.fleetech.util.Session

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class MainPodDialog : DialogFragment() {
    lateinit var sessionManager: Session
    lateinit var progressBar_pod_image: ProgressBar

    companion object {

        const val TAG = "SimpleDialog"

        private const val KEY_TITLE = "KEY_TITLE"
        private const val KEY_SUBTITLE = "KEY_SUBTITLE"

        fun newInstance(title: String): MainPodDialog {
            val args = Bundle()
            args.putString(KEY_TITLE, title)
            val fragment = MainPodDialog()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.pod_image_layout, container, false)
//        sessionManager = Session(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        sessionManager = Session(context)

        val img_front = view.findViewById<ImageView>(R.id.img_frontImage)
        val img_back = view.findViewById<ImageView>(R.id.img_frontback)
        val txtFrontPod = view.findViewById<TextView>(R.id.txtFrontPod)
        val txtBackPod = view.findViewById<TextView>(R.id.txtBackPod)
        val submit = view.findViewById<TextView>(R.id.nextPod)
        val back_pod = view.findViewById<TextView>(R.id.back_pod)
        progressBar_pod_image = view.findViewById<ProgressBar>(R.id.progressBar_pod_image)
        if (sessionManager.keyPodtype.equals("LRTYPE")) {
            txtFrontPod.setText("LR")
            txtBackPod.setText("Eway Bill")
        }
        back_pod.setOnClickListener {
            dismiss()
        }
        Glide.with(requireActivity()).load(Prefs.get().authToken)
            .into(img_front)
        Glide.with(requireActivity()).load(Prefs.get().address)
            .into(img_back)

        submit.setOnClickListener {
            if (sessionManager.keyPodtype.equals("PODTYPE")) {
                uploadPOD(Prefs.get().authToken!!, Prefs.get().address!!)
            } else {
                data(Prefs.get().authToken!!, Prefs.get().address!!)
            }

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
    }

    private fun setupClickListeners(view: View) {

    }


    fun uploadPOD(currentPhotoPath: String, currentPhotoPathBack: String) {
        val file = File(currentPhotoPath)
        val fileBack = File(currentPhotoPathBack)
        val requestFile: RequestBody =
            RequestBody.create("image/*".toMediaTypeOrNull(), file)
        val body: MultipartBody.Part =
            MultipartBody.Part.createFormData("DocImageFront", file.name, requestFile)

        val requestFile2: RequestBody =
            RequestBody.create("image/*".toMediaTypeOrNull(), fileBack)
        val body2: MultipartBody.Part =
            MultipartBody.Part.createFormData("DocImageBack", fileBack.name, requestFile2)
        RetrofitClient.retrofit = null
        val userAPI = Apiuitils.testUrl
        val call: Call<PODResponse> = userAPI.updatePODData(
            "Bearer " + sessionManager.keyToken,
            sessionManager.keyOrderId,
            body,
            body2
        )
        call.enqueue(object : Callback<PODResponse> {
            override fun onResponse(
                call: Call<PODResponse>,
                response: Response<PODResponse>) {
                Log.i("TAG","check_data" + response.body())
                Log.i("TAG","check_data" + "trueee")
                if(response.body()!!.jDoc!=null /*&& response.body()!!.jDoc.getString("message")!=null*/){
                    Toast.makeText(requireActivity(), response.body()!!.jDoc , Toast.LENGTH_SHORT).show()
                    Log.i("TAG","check_data" + response.body()!!.jDoc)
                    Log.i("TAG","check_data" + "False")

                }

//                Toast.makeText(requireActivity(), response.body()!!.jDoc , Toast.LENGTH_SHORT).show()
  //              println("Success111 ${response.body()!!.jDoc}")

                val intent = Intent(requireContext(), MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                dismiss()

            }

            override fun onFailure(
                call: Call<PODResponse>,
                t: Throwable
            ) {
                Log.d(
                    "MainActivity",
                    "user image = " + t.cause + "==" + t.localizedMessage + "==" + t.localizedMessage
                )
            }
        })
    }

    fun data(currentPhotoPath: String, currentPhotoPathBack: String) {
        val file = File(currentPhotoPath)
        val fileBack = File(currentPhotoPathBack)
        val requestFile: RequestBody =
            RequestBody.create("image/*".toMediaTypeOrNull(), file)
        val body: MultipartBody.Part =
            MultipartBody.Part.createFormData("DocImageLR", file.name, requestFile)

        val requestFile2: RequestBody =
            RequestBody.create("image/*".toMediaTypeOrNull(), fileBack)
        val body2: MultipartBody.Part =
            MultipartBody.Part.createFormData("DocImageEwayBill", fileBack.name, requestFile2)
        val OrderID: RequestBody = RequestBody.create(
            "text/plain".toMediaTypeOrNull(),
            sessionManager.keyOrderId
        )
        RetrofitClient.retrofit = null
        val userAPI = Apiuitils.testUrl
        val call: Call<PODResponse> = userAPI.updateProfilePhotoProcess(
            "Bearer " + sessionManager.keyToken,
            sessionManager.keyOrderId,
            body,
            body2
        )
        call.enqueue(object : Callback<PODResponse> {
            override fun onResponse(
                call: Call<PODResponse>,
                response: Response<PODResponse>
            ) {

                if(response.body()!!.jDoc!=null /*&& response.body()!!.jDoc.getString("message")!=null*/){
                    Toast.makeText(requireActivity(), response.body()!!.jDoc , Toast.LENGTH_SHORT).show()
                }

              //  Toast.makeText(requireActivity(), response.body()!!.jDoc , Toast.LENGTH_SHORT).show()
                dismiss()
                val intent = Intent(requireActivity(), MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)

            }

            override fun onFailure(
                call: Call<PODResponse>,
                t: Throwable
            ) {
                Log.d(
                    "MainActivity",
                    "user image = " + t.cause + "==" + t.localizedMessage + "==" + t.localizedMessage
                )
            }
        })
    }
}
