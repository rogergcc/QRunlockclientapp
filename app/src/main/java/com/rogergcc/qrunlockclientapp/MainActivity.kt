package com.rogergcc.qrunlockclientapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.huawei.hms.ml.scan.HmsScan
import com.rogergcc.qrunlockclientapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val DEFINED_CODE = 222
    private val REQUEST_CODE_SCAN = 0X01
    //private var mSoundPoolPlayer: SoundPoolPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        //        setContentView(R.layout.activity_main_list_qr_codes);
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        setupUI()
    }

    private fun setupUI() {
       binding.btnScan.setOnClickListener( View.OnClickListener {
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
               requestPermissions(
                   arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE),
                   DEFINED_CODE
               )
           }
       })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //receive result after your activity finished scanning
        super.onActivityResult(requestCode, resultCode, data)
        //AppLogger.e("onActivityResult")
        if (resultCode != RESULT_OK || data == null) {
            return
        }
        if (requestCode == REQUEST_CODE_SCAN) {
            val hmsScan: HmsScan? = data.getParcelableExtra(QrScanActivity.SCAN_RESULT)
            if (hmsScan != null && !TextUtils.isEmpty(hmsScan.getOriginalValue())) {

//
            }
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions!!, grantResults!!)
        if (permissions == null || grantResults == null || grantResults.size < 2 || grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_GRANTED) {
            return
        }
        if (requestCode == DEFINED_CODE) {
            //start your activity for scanning barcode
            //mSoundPoolPlayer = SoundPoolPlayer(this)
            this.startActivityForResult(
                Intent(this, QrScanActivity::class.java),
                REQUEST_CODE_SCAN
            )
        }
    }


}