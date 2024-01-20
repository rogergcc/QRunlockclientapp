package com.rogergcc.qrunlockclientapp.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.huawei.hms.ml.scan.HmsScan
import com.rogergcc.qrunlockclientapp.BuildConfig
import com.rogergcc.qrunlockclientapp.ui.scanner.QrScanActivity
import com.rogergcc.qrunlockclientapp.R
import com.rogergcc.qrunlockclientapp.databinding.ActivityMainBinding
import com.rogergcc.qrunlockclientapp.ui.helper.SoundPoolPlayer
import com.rogergcc.qrunlockclientapp.ui.helper.TimberAppLogger

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var mSoundPoolPlayer: SoundPoolPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        setupUI()
    }

    private fun setupUI() {

        binding.constraintParent.backgroundTintList = ColorStateList.valueOf(Color.parseColor(BuildConfig.BACKGROUND_COLOR))
        binding.btnScan.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                    arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE),
                    DEFINED_CODE
                )
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //receive result after your activity finished scanning
        super.onActivityResult(requestCode, resultCode, data)
        TimberAppLogger.e("onActivityResult")
        if (resultCode != RESULT_OK || data == null) {
            return
        }
        if (requestCode == REQUEST_CODE_SCAN) {
            val hmsScan: HmsScan? = data.getParcelableExtra(QrScanActivity.SCAN_RESULT)
            if (!TextUtils.isEmpty(hmsScan?.getOriginalValue())) {
                mSoundPoolPlayer?.playShortResource(R.raw.bleep)
                Toast.makeText(this, "Data=> ${hmsScan?.showResult}", Toast.LENGTH_SHORT).show()

                TimberAppLogger.e("ScanResult ${hmsScan?.getScanType()}")
                TimberAppLogger.e("ScanResult ${hmsScan?.scanType}")
                TimberAppLogger.e("OriginalValue ${hmsScan?.originalValue} ")
                TimberAppLogger.e("showResult ${hmsScan?.showResult} ")
                TimberAppLogger.e("emailContent ${hmsScan?.emailContent} ")


            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.size < 2 ||
            grantResults[0] != PackageManager.PERMISSION_GRANTED
            || grantResults[1] != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        if (requestCode == DEFINED_CODE) {
            //start your activity for scanning barcode
            mSoundPoolPlayer = SoundPoolPlayer(this)
            this.startActivityForResult(
                Intent(this, QrScanActivity::class.java),
                REQUEST_CODE_SCAN
            )
        }
    }

    companion object {
        private const val DEFINED_CODE = 222
        private const val REQUEST_CODE_SCAN = 0X01
    }


}