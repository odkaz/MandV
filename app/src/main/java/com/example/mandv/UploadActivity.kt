package com.example.mandv

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.lang.Exception
/*
this class allows users to upload video and music files to firestorage

it eventually need to compress the file before uploading it
 */

class UploadActivity : AppCompatActivity() {
    val VIDEO : Int = 0
    val MUSIC : Int = 1
    private lateinit var mStroage : StorageReference
    private lateinit var uri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        val videoBtn = findViewById<View>(R.id.videoBtn) as Button
        val musicBtn = findViewById<View>(R.id.musicBtn) as Button

        mStroage = FirebaseStorage.getInstance().getReference(UploadsPATH)

        videoBtn.setOnClickListener {view: View? ->
            val intent = Intent()
            intent.setType("video/*")
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Select Video"), VIDEO)
        }

        musicBtn.setOnClickListener {view: View? ->
            val intent = Intent()
            intent.setType("music/*")
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Select Music"), MUSIC)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //val uriTxt = findViewById<>()
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == VIDEO){
                uri = data!!.data!!
                upload()
            } else if (requestCode == MUSIC) {
                uri = data!!.data!!
                upload()
            }

        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    private fun upload() {
        var mReference = mStroage.child(uri.lastPathSegment!!)

        try {
            mReference.putFile(uri).continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                return@Continuation mReference.downloadUrl
            }).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                } else {
                    //handle failures
                }
            }

                /*addOnSuccessListener {taskSnapshot : UploadTask.TaskSnapshot? ->
                var url = taskSnapshot!!.result
                //val dwntxt.text = url.toString()
                val view = findViewById<View>(android.R.id.content)
                Snackbar.make(view, "Successfully Uploaded", Snackbar.LENGTH_LONG).show()
                */

        } catch (e : Exception) {
            val view = findViewById<View>(android.R.id.content)
            Snackbar.make(view, e.toString(), Snackbar.LENGTH_LONG).show()

        }
    }
}
