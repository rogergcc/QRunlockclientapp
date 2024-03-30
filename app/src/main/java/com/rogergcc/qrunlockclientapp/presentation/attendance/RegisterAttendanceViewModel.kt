package com.rogergcc.qrunlockclientapp.presentation.attendance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.rogergcc.qrunlockclientapp.data.network.response.RegisterAttendanceResponse
import com.rogergcc.qrunlockclientapp.domain.usecases.RecordAttendanceUseCase
import com.rogergcc.qrunlockclientapp.presentation.extensions.fromJson
import com.rogergcc.qrunlockclientapp.presentation.helper.TimberAppLogger
import com.rogergcc.qrunlockclientapp.presentation.scanner.QrScanImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterAttendanceViewModel @Inject constructor(
    private val recordAttendanceUseCase: RecordAttendanceUseCase,
) : ViewModel() {


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _registerAttendance = MutableLiveData<RegisterAttendanceResponse>()
    val registerAttendance: LiveData<RegisterAttendanceResponse> get() = _registerAttendance


    fun registerAttendance(scanValue: String) {

        viewModelScope.launch(viewModelScope.coroutineContext + Dispatchers.IO) {
            _isLoading.postValue(true)
            _error.postValue("loading...")
            try {
                val scanCode: QrScanImage? = parseScanCode(scanValue)
                if (scanCode == null) return@launch

                val userId = scanCode.userId
                val result = recordAttendanceUseCase(userId)

                _registerAttendance.postValue(result)
//                _isLoading.postValue(false)
                _error.postValue(result.message ?: "")
            } catch (exception: Exception) {
                val errorMessage = "Error registering attendance: ${exception.message}"
                TimberAppLogger.e("[RegisterAttendanceViewModel] (registerAttendance) $errorMessage")
                _error.postValue(errorMessage)

            } finally {
                _isLoading.postValue(false)
            }
        }

    }
    private fun parseScanCode(scanValue: String): QrScanImage? {
        if (scanValue.isEmpty()) {
            _error.postValue("Scan Value is Empty")
            return null
        }
        return try {
            Gson().fromJson(scanValue, QrScanImage::class.java)
        } catch (e: JsonSyntaxException) {
            _error.postValue("Invalid QR code format for Event Register")
            null
        }
    }

    private fun verifyQrStandardFormat(scanValue: String): Pair<String, Boolean> {
        return try {
            val scanCode: QrScanImage = Gson().fromJson(scanValue)
            val userId = scanCode.userId
            Pair(userId, true)

        } catch (ex: Exception) {
            Pair("", false)

        }
    }
}