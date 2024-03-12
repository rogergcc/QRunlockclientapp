package com.rogergcc.qrunlockclientapp.ui.attendance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rogergcc.qrunlockclientapp.data.response.RegisterAttendanceResponse
import com.rogergcc.qrunlockclientapp.domain.attendance.RecordAttendanceUseCase
import com.rogergcc.qrunlockclientapp.ui.helper.TimberAppLogger
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


    fun registerAttendance(userCodeId: String) {

        viewModelScope.launch(viewModelScope.coroutineContext + Dispatchers.IO) {
            _isLoading.postValue(true)
            _error.postValue("loading...")
            try {
                if (userCodeId.isEmpty()) {
                    _isLoading.postValue(false)
                    _error.postValue("Code to register is empty")
                    return@launch
                }

                val result = recordAttendanceUseCase(userCodeId)
                TimberAppLogger.d("registerAttendance() result: $result")
                _registerAttendance.postValue(result)
                _isLoading.postValue(false)
                _error.postValue(result.message?:"")
            } catch (exception: Exception) {
                TimberAppLogger.e("[RegisterAttendanceViewModel] (registerAttendance) ${exception.message}")
                _isLoading.postValue(false)
                _error.postValue(exception.message)

            }
        }

    }
}