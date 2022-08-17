package com.rogergcc.qrunlockclientapp

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.RenderMode
import com.huawei.hms.hmsscankit.RemoteView
import com.huawei.hms.ml.scan.HmsScan
import com.rogergcc.qrunlockclientapp.databinding.ActivityQrScanBinding

class QrScanActivity : AppCompatActivity() {

    //    private ImageView flashBtn;
    //declare RemoteView instance
    private lateinit var remoteView: RemoteView


    private lateinit var binding: ActivityQrScanBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //        setContentView(R.layout.activity_qr_scan);
        binding = ActivityQrScanBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        //        binding.ani.bringToFront();


//        binding.ani.bringToFront();
        binding.animationView.setAnimation("qrcode_scanner.json")
//        binding.animationView.setScale(.7f);
        //        binding.animationView.setScale(.7f);
        binding.animationView.setRenderMode(RenderMode.HARDWARE)
        binding.animationView.buildDrawingCache(true)

        binding.animationView.playAnimation()


        //1.get screen density to caculate viewfinder's rect


        //1.get screen density to caculate viewfinder's rect
        val dm = resources.displayMetrics
        val density = dm.density
        //2.get screen size
        //2.get screen size
        mScreenWidth = resources.displayMetrics.widthPixels
        mScreenHeight = resources.displayMetrics.heightPixels

        val scanFrameSize = (SCAN_FRAME_SIZE * density).toInt()

        //3.caculate viewfinder's rect,it's in the middle of the layout
        //set scanning area(Optional, rect can be null,If not configure,default is in the center of layout)

        //3.caculate viewfinder's rect,it's in the middle of the layout
        //set scanning area(Optional, rect can be null,If not configure,default is in the center of layout)
        val rect = Rect()
        rect.left = mScreenWidth / 2 - scanFrameSize / 2
        rect.right = mScreenWidth / 2 + scanFrameSize / 2
        rect.top = mScreenHeight / 2 - scanFrameSize / 2
        rect.bottom = mScreenHeight / 2 + scanFrameSize / 2

//        mSoundPoolPlayer = new SoundPoolPlayer(this);

        //initialize RemoteView instance, and set calling back for scanning result

//        mSoundPoolPlayer = new SoundPoolPlayer(this);

        //initialize RemoteView instance, and set calling back for scanning result
        remoteView = RemoteView.Builder().setContext(this).setBoundingBox(rect)
            .setFormat(HmsScan.ALL_SCAN_TYPE).build()
        remoteView.onCreate(savedInstanceState)
        remoteView.setOnResultCallback { result -> //judge the result is effective

            //                mSoundPoolPlayer.playShortResource(R.raw.bleep);
            if (result != null && result.isNotEmpty() && result[0] != null &&
                !TextUtils.isEmpty(result[0].getOriginalValue())
            ) {

                Log.e(TAG,"Result Scan ${result[0].originalValue } ")
                Log.e(TAG,"Result Scan data 2 ${result[0].getEmailContent()} ")
                val intent = Intent()
                intent.putExtra(SCAN_RESULT, result[0])
                setResult(RESULT_OK, intent)
                finish()
            }
        }


        //add remoteView to framelayout
        val params = FrameLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        val frameLayout = findViewById<FrameLayout>(R.id.rim)
        frameLayout.addView(remoteView, params)

        //set back button listener

        //set back button listener
        val backBtn = findViewById<ImageView>(R.id.back_img)
        backBtn.setOnClickListener { finish() }

        setFlashOperation()
    }

    private fun setFlashOperation() {
        val hasFlash = this.packageManager
            .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
        if (!hasFlash) {
            binding.flashBtn.visibility = View.INVISIBLE
        } else {
            binding.flashBtn.visibility = View.VISIBLE
        }
        binding.flashBtn.setOnClickListener {
            if (remoteView.lightStatus) {
                remoteView.switchLight()
                binding.flashBtn.setImageResource(R.drawable.scankit_flashlight_layer_off)
            } else {
                remoteView.switchLight()
                binding.flashBtn.setImageResource(R.drawable.scankit_flashlight_layer_on)
            }
        }
    }

    //manage remoteView lifecycle
    override fun onStart() {
        super.onStart()
        remoteView.onStart()
    }

    override fun onResume() {
        super.onResume()
        remoteView.onResume()
    }

    override fun onPause() {
        super.onPause()
        remoteView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        remoteView.onDestroy()
//        if (remoteView != null) {
//            remoteView = null;
//        }

//        if(mSoundPoolPlayer != null){
//            mSoundPoolPlayer.release();
//            mSoundPoolPlayer = null;
//        }
    }

    override fun onStop() {
        super.onStop()
        remoteView.onStop()
    }

    companion object {
        //declare the key ,used to get the value returned from scankit
        const val SCAN_RESULT = "scanResult"
        private const val TAG = "QrScanActivity2"

        //scan_view_finder width & height is  300dp
        const val SCAN_FRAME_SIZE = 300
        var mScreenHeight = 0
        var mScreenWidth = 0
    }
}