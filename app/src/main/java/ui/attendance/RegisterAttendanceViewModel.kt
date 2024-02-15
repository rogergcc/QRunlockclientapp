package ui.attendance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rogergcc.qrunlockclientapp.domain.model.AttendanceDomain
import com.rogergcc.qrunlockclientapp.domain.VerifyUserRegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterAttendanceViewModel @Inject constructor(
    private val verifyUserRegisterUseCase: VerifyUserRegisterUseCase
) : ViewModel() {


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _attendanceData = MutableLiveData<List<AttendanceDomain>>()
    val registerAttendanceResult: LiveData<List<AttendanceDomain>> get() = _attendanceData


    fun onRegisterAttendance(){
        viewModelScope.launch {
//            viewModelScope.launch(viewModelScope.coroutineContext + Dispatchers.IO) {
            _isLoading.postValue(true)
            val result = verifyUserRegisterUseCase()
            if(result.isNotEmpty()){
                _attendanceData.postValue(result)
                _isLoading.postValue(false)
            }
        }
    }

    fun registerAttendance(showResult: String?) {
        TODO("Not yet implemented")
    }


}