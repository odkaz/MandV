package com.example.mandv

import android.graphics.Bitmap
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.MediaController
import android.widget.VideoView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage

import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL

/*
class info
this is the activity which plays video and music


 */
class MainActivity : AppCompatActivity() {


    private var btnonce: Button? = null
    private var btncontinuously: Button? = null
    private var btnstop: Button? = null
    private var vv: VideoView? = null
    private var mediacontroller: MediaController? = null
    private var uri: Uri? = null
    private var isContinuously = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnonce = findViewById(R.id.btnonce) as Button
        btncontinuously = findViewById(R.id.btnconti) as Button
        btnstop = findViewById(R.id.btnstop) as Button
        vv = findViewById(R.id.vv) as VideoView

        mediacontroller = MediaController(this)
        mediacontroller!!.setAnchorView(vv)
        //val uriPath = "android.resource://com.example.mandv/"+R.raw.samplevideo_2_
        //val mRef = FirebaseStorage.getInstance().reference
        //val vidURL = mRef.child(UploadsPATH).child("video:24").downloadUrl

        //uri = Uri.parse(uriPath)
        //Log.d("kotlintest", dwnVideo("video:24"))
        dwnVideo("video:24", {
            Log.d("kotlintest", it)
            uri = Uri.parse(it)
            vv!!.setMediaController(mediacontroller)
            Log.d("kotlintest", uri.toString())
            vv!!.setVideoURI(uri)
            vv!!.requestFocus()
            vv!!.start()
        })

        /*vv!!.setOnPreparedListener{ it: MediaPlayer? ->
            it!!.setLooping(true)
        }*/
/*

vv!!.setOnPreparedListener {
            vv!!.start()
        }

        vv!!.setOnCompletionListener {
            if (isContinuously) {
                vv!!.start()
            }
        }

        btncontinuously!!.setOnClickListener{
            vv!!.stopPlayback()
        }

        btnonce!!.setOnClickListener{
            isContinuously = false
            vv!!.setMediaController(mediacontroller)
            vv!!.setVideoURI(uri)
            //vv!!.setVideoURI(Uri.parse(dwnVideo("video:24")))
            vv!!.requestFocus()
            vv!!.start()
        }

        btncontinuously!!.setOnClickListener{
            isContinuously = true
            vv!!.setMediaController(mediacontroller)
            vv!!.setVideoURI(uri)
            vv!!.requestFocus()
            vv!!.start()

        }
 */
    }

    //this function downloads the video data from fire storage
    private fun dwnVideo(ref: String, complete: (String?)  -> Unit){
        //var vidUrl: String = ""
        val mRef = FirebaseStorage.getInstance().reference
        mRef.child(UploadsPATH).child(ref).downloadUrl.addOnSuccessListener {
            complete(it.toString())

        }.addOnFailureListener{
            //write a code if it failed to download the video
            Log.d("kotlintest", "failed to find the url")
            complete(null)
        }
        val HUNDRED_MEGABYTE: Long = 1024*1024*100
        val vid = mRef.child(UploadsPATH).child(ref).getBytes(HUNDRED_MEGABYTE).addOnSuccessListener {

        }
    }

    //this function downloadss the music data from fire storage
    private fun dwnMusic() {

    }
}
