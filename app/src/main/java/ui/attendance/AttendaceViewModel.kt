package ui.attendance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rogergcc.qrunlockclientapp.domain.model.AttendanceDomain
import com.rogergcc.qrunlockclientapp.domain.GetVerifyUserRegister
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AttendaceViewModel @Inject constructor(
    private val getVerifyUserRegister: GetVerifyUserRegister
) : ViewModel() {


    private val isLoading = MutableLiveData<Boolean>()

    val attendaceData = MutableLiveData<List<AttendanceDomain>>()

    fun onCreate(){
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getVerifyUserRegister()
            if(!result.isNullOrEmpty()){
                attendaceData.postValue(result)
                isLoading.postValue(false)
            }
        }
    }




}