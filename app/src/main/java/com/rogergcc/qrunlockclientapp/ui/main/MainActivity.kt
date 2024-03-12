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
import com.google.gson.Gson
import com.huawei.hms.ml.scan.HmsScan
import com.rogergcc.qrunlockclientapp.BuildConfig
import com.rogergcc.qrunlockclientapp.R
import com.rogergcc.qrunlockclientapp.databinding.ActivityMainBinding
import com.rogergcc.qrunlockclientapp.ui.attendance.RegisterAttendanceViewModel
import com.rogergcc.qrunlockclientapp.ui.extensions.fromJson
import com.rogergcc.qrunlockclientapp.ui.extensions.snackbar
import com.rogergcc.qrunlockclientapp.ui.helper.SoundPoolPlayer
import com.rogergcc.qrunlockclientapp.ui.helper.TimberAppLogger
import com.rogergcc.qrunlockclientapp.ui.scanner.QrScanActivity
import com.rogergcc.qrunlockclientapp.ui.scanner.QrScanImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var mSoundPoolPlayer: SoundPoolPlayer? = null
    private val viewModel: RegisterAttendanceViewModel by viewModels()

    //    private val viewModel by viewModels<RegisterAttendanceViewModel>()
//    private val viewModel:RegisterAttendanceViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        setupUI()
        setupViewModelObservers()
    }

    private fun setupViewModelObservers() {

        viewModel.error.observe(this) { error ->
            if (error.isNotEmpty()) {
                binding.tvError.text = error
            }
        }

        viewModel.registerAttendance.observe(this) { response ->
            try {
                TimberAppLogger.e("response: $response")
                if (response.status == 0) {
                    Toast.makeText(this, "Error: ${response.message}", Toast.LENGTH_SHORT).show()
                    return@observe
                }
            } catch (e: Exception) {
                TimberAppLogger.e("setupViewModelObservers: ${e.message}")
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun setupUI() {

        binding.constraintParent.backgroundTintList =
            ColorStateList.valueOf(Color.parseColor(BuildConfig.BACKGROUND_COLOR))
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
                printDetails(hmsScan)

                val scanValue = hmsScan?.originalValue ?: ""
                val scanCode: QrScanImage = Gson().fromJson(scanValue)
                val userId = scanCode.userId
                viewModel.registerAttendance(userId?:"")
            }
        }
    }

    private fun printDetails(hmsScan: HmsScan?) {
        TimberAppLogger.e("ScanResult: ${hmsScan?.getScanType().toString()}")
        TimberAppLogger.e("ScanType: ${hmsScan?.scanType.toString()}")
        TimberAppLogger.e("OriginalValue: ${hmsScan?.originalValue}")
        TimberAppLogger.e("showResult: ${hmsScan?.showResult}")
        snackbar("Attendance=> ${hmsScan?.showResult}")

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