package ui.attendance

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rogergcc.qrunlockclientapp.domain.model.AttendanceDomain
import com.rogergcc.qrunlockclientapp.domain.GetVerifyUserRegister
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getVerifyUserRegister: GetVerifyUserRegister
) : ViewModel() {


    val isLoading = MutableLiveData<Boolean>()
    val popularMovies = MutableLiveData<List<AttendanceDomain>>()

    fun onCreate(){
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getVerifyUserRegister()
            if(!result.isNullOrEmpty()){
                popularMovies.postValue(result)
                isLoading.postValue(false)
            }
        }
    }




}