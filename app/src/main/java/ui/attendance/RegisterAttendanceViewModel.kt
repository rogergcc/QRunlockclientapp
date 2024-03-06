package ui.attendance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rogergcc.qrunlockclientapp.data.response.RegisterAttendanceResponse
import com.rogergcc.qrunlockclientapp.domain.VerifyUserRegisterUseCase
import com.rogergcc.qrunlockclientapp.domain.attendance.RegisterAttendanceUseCase
import com.rogergcc.qrunlockclientapp.domain.model.AttendanceDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterAttendanceViewModel @Inject constructor(
    private val verifyUserRegisterUseCase: VerifyUserRegisterUseCase,
    private val registerAttendanceUseCase: RegisterAttendanceUseCase,
) : ViewModel() {


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _attendanceData = MutableLiveData<List<AttendanceDomain>>()
    val registerAttendanceResult: LiveData<List<AttendanceDomain>> get() = _attendanceData


    private val _registerAttendance = MutableLiveData<RegisterAttendanceResponse>()
    val registerAttendance: LiveData<RegisterAttendanceResponse> get() = _registerAttendance

    fun onRegisterAttendance() {
        viewModelScope.launch {
//            viewModelScope.launch(viewModelScope.coroutineContext + Dispatchers.IO) {
            _isLoading.postValue(true)
            val result = verifyUserRegisterUseCase()
            if (result.isNotEmpty()) {
                _attendanceData.postValue(result)
                _isLoading.postValue(false)
            }
        }
    }

    fun registerAttendance(showResult: String) {
        try {
            _isLoading.postValue(true)
            if (showResult.isEmpty()){
                _isLoading.postValue(false)

                return
            }
            viewModelScope.launch {

                val result = registerAttendanceUseCase(showResult)
                _registerAttendance.postValue(result)
                _isLoading.postValue(false)
            }
        } catch (exception: Exception) {
            _isLoading.postValue(false)

        }

    }


}