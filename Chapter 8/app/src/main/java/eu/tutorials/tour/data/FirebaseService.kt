package eu.tutorials.tour.data

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface FirebaseService {
    fun registerUser(userEmail: String,
                     userPassword: String,
                     onUserCreated:(Task<AuthResult>)->Unit)
    fun SignInUser(
        userEmail: String,
        userPassword: String,
        onUserSignIn:(Task<AuthResult>)->Unit
    )

    fun uploadImageToFirebaseStorage(
        selectedImageUri: Uri,
        onUploadSuccess:(String)->Unit
    )

    fun saveATour(onError:(Throwable?)->Unit,tour:Tour,onTourSaved:(Task<Void>)->Unit)
    fun getAllTour(onError:(Throwable?)->Unit,onSuccess:(List<Tour>)->Unit)

    fun updateTour(onError:(Throwable?)->Unit,tourId:String,tour: Tour,onUpdateSuccess:()->Unit)
    fun deleteATour(onError:(Throwable?)->Unit,docId: String, onSuccess: () -> Unit)
}