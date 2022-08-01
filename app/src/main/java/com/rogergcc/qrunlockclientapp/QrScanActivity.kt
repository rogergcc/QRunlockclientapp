package com.rogergcc.qrunlockclientapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.huawei.hms.hmsscankit.RemoteView
import com.rogergcc.qrunlockclientapp.databinding.ActivityQrScanBinding

class QrScanActivity : AppCompatActivity() {

    //    private ImageView flashBtn;
    //declare RemoteView instance
    private val remoteView: RemoteView? = null

    var mScreenWidth = 0
    var mScreenHeight = 0

    //scan_view_finder width & height is  300dp
    val SCAN_FRAME_SIZE = 300

    //    LottieAnimationView binding.animationView;
    //private val mSoundPoolPlayer: SoundPoolPlayer? = null
    private val binding: ActivityQrScanBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_scan)
    }

    companion object {
        //declare the key ,used to get the value returned from scankit
        val SCAN_RESULT = "scanResult"
        private val TAG = "QrScanActivity2"
    }
}