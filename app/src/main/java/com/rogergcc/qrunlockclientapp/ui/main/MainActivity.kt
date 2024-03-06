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
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.huawei.hms.ml.scan.HmsScan
import com.rogergcc.qrunlockclientapp.BuildConfig
import com.rogergcc.qrunlockclientapp.ui.scanner.QrScanActivity
import com.rogergcc.qrunlockclientapp.R
import com.rogergcc.qrunlockclientapp.databinding.ActivityMainBinding
import com.rogergcc.qrunlockclientapp.ui.extensions.snackbar
import com.rogergcc.qrunlockclientapp.ui.helper.SoundPoolPlayer
import com.rogergcc.qrunlockclientapp.ui.helper.TimberAppLogger
import dagger.hilt.android.AndroidEntryPoint
import ui.attendance.RegisterAttendanceViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var mSoundPoolPlayer: SoundPoolPlayer? = null
    private val viewModel: RegisterAttendanceViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        setupUI()
        setupViewModelObservers()
    }

    private fun setupViewModelObservers() {
        viewModel.registerAttendance.observe(this) { response->

            Toast.makeText(this, "${response.attendance}", Toast.LENGTH_SHORT).show()

        }
    }

    private fun setupUI() {

        binding.constraintParent.backgroundTintList = ColorStateList.valueOf(Color.parseColor(BuildConfig.BACKGROUND_COLOR))
        binding.btnScan.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                    arrayOf(Manifest.permission.CAMERA),
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
                Toast.makeText(this, "Attendance=> ${hmsScan?.showResult}", Toast.LENGTH_SHORT).show()

                printDetails("getScanType: ${hmsScan?.getScanType().toString()} ")

                printDetails("scanType: ${hmsScan?.scanType.toString()} ")
                printDetails("OriginalValue: ${hmsScan?.originalValue} ")
                printDetails("showResult: ${hmsScan?.showResult} ")
                snackbar( "Attendance=> ${hmsScan?.showResult}")
//                viewModel.registerAttendance(hmsScan?.showResult)
//                viewModel.onRegisterAttendance()

                val scanValue = hmsScan?.originalValue?:""
                viewModel.registerAttendance(scanValue)
            }
        }
    }

    private fun printDetails(hmsScan: String) {
        TimberAppLogger.e("ScanResult: $hmsScan")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED
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