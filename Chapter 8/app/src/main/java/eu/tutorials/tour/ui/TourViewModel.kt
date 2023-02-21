package eu.tutorials.tour.ui

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eu.tutorials.tour.data.FirebaseService
import eu.tutorials.tour.data.Tour
import eu.tutorials.tour.utils.Resource

class TourViewModel(private val firebaseService: FirebaseService) : ViewModel() {

    private val downloadUrl = MutableLiveData<Resource<String>>()
    val downloadUrlLiveData: LiveData<Resource<String>>
        get() = downloadUrl

    private val tours = MutableLiveData<Resource<List<Tour>>>()
    val tourLiveData: LiveData<Resource<List<Tour>>>
        get() = tours

    private val saveTour = MutableLiveData<Resource<Nothing>>()
    val saveTourLiveData: LiveData<Resource<Nothing>>
        get() = saveTour

    private val updateTour = MutableLiveData<Resource<Nothing>>()
    val updateTourLiveData: LiveData<Resource<Nothing>>
        get() = updateTour

    private val deleteTour = MutableLiveData<Resource<Nothing>>()
    val deleteTourLiveData: LiveData<Resource<Nothing>>
        get() = deleteTour

    fun getAllTour() {
        tours.postValue(Resource.Loading())
        firebaseService.getAllTour(onError = {
            tours.postValue(Resource.Failure(it?.message.toString()))
        }) {
            tours.postValue(Resource.Success(it))
        }
    }

    fun uploadImage(uri: Uri) {
        downloadUrl.postValue(Resource.Loading())
        firebaseService.uploadImageToFirebaseStorage(uri) {
            downloadUrl.postValue(Resource.Success(it,"image uploaded"))
        }
    }

    fun saveTour(tour: Tour) {
        saveTour.postValue(Resource.Loading())
        firebaseService.saveATour(onError = {
            saveTour.postValue(Resource.Failure(it?.message.toString()))
        }, tour) {
            if (it.isSuccessful) {
                saveTour.postValue(Resource.Success(message = "Tour saved successfully"))
            } else {
                saveTour.postValue(Resource.Failure(it.exception.toString()))
            }
        }
    }

    fun updateTour(
        tourId: String,
        tour: Tour
    ) {
        updateTour.postValue(Resource.Loading())
        firebaseService.updateTour(onError = {
            updateTour.postValue(Resource.Failure(it?.message.toString()))
        }, tourId, tour, onUpdateSuccess = {
            updateTour.postValue(Resource.Success(message = "Update successful"))
        })
    }

    fun deleteTour(docId: String) {
        deleteTour.postValue(Resource.Loading())
        firebaseService.deleteATour(onError = {
            deleteTour.postValue(Resource.Failure(it?.message.toString()))
        }, docId) {
            deleteTour.postValue(Resource.Success(message = "Tour has been deleted"))
        }
    }

    class TourViewModelFactory(private val service: FirebaseService) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TourViewModel(service) as T
        }
    }
}