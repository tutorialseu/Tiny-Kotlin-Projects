package eu.tutorials.tour

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import eu.tutorials.tour.data.FirebaseServiceImpl
import eu.tutorials.tour.ui.TourViewModel

class TourApp:Application() {

     val firebaseServices by lazy{
        FirebaseServiceImpl()
    }

}