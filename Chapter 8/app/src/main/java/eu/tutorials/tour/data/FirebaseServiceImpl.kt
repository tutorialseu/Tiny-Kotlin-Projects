package eu.tutorials.tour.data

import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.*

class FirebaseServiceImpl: FirebaseService {
    private val firebaseAuth:FirebaseAuth = FirebaseAuth.getInstance()
    private val storageRef = Firebase.storage.reference
    private val database = Firebase.firestore
    private val databseReference = database.collection("tours")

    override fun registerUser(
        userEmail: String,
        userPassword: String,
        onUserCreated:(Task<AuthResult>)->Unit
    ){
            firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener {
                    onUserCreated(it)
                } }

    val user = firebaseAuth.currentUser
     val hasUser = user != null

    override fun SignInUser(
        userEmail: String,
        userPassword: String,
        onUserSignIn:(Task<AuthResult>)->Unit
    ){
        firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener {
                onUserSignIn(it) } }

     override fun uploadImageToFirebaseStorage(
        selectedImageUri: Uri,
        onUploadSuccess:(String)->Unit
    ){
            val uploadTask = storageRef.child("tourImages/${Calendar.getInstance().time.time}")
            uploadTask.putFile(selectedImageUri).addOnCompleteListener {
                if (it.isSuccessful){
                    uploadTask.downloadUrl.addOnSuccessListener { uri ->
                        if (uri.toString().isNotEmpty()) {
                       onUploadSuccess(uri.toString())
                        }
                    }
                }
            }


        }

    override fun saveATour(onError:(Throwable?)->Unit,tour:Tour, onTourSaved:(Task<Void>)->Unit){
       databseReference.document().set(tour).
               addOnCompleteListener {
                   if(it.isSuccessful){
                       onTourSaved(it)
                   }else{
                       onError(it.exception?.fillInStackTrace())
                   } } }

    override fun getAllTour(
    onError:(Throwable?)->Unit,onSuccess:(List<Tour>)->Unit){
        val toursList =  ArrayList<Tour>()
       database.collection("tours").get().addOnCompleteListener {
           if(it.isSuccessful){
            for (document in it.result.documents) {
                val tour = document.toObject(Tour::class.java)
                val newTour  =  tour?.copy(tourId = document.id, authorName = user?.displayName.toString())
                if (newTour != null) {
                    toursList.add(newTour )
                }
                onSuccess(toursList)
            }
            }else{
                onError(it.exception?.fillInStackTrace())
            }}

        }

     override fun updateTour(onError:(Throwable?)->Unit,tourId:String, tour: Tour, onUpdateSuccess:()->Unit){
            databseReference.document(tourId).update(tour.toMap()).addOnCompleteListener {
                if (it.isSuccessful){
                    onUpdateSuccess()
                }else{
                    onError(it.exception?.fillInStackTrace())
                }
            }
        }

    override fun deleteATour(onError:(Throwable?)->Unit,docId: String, onSuccess: () -> Unit){
            Log.d("TourDetailsFragment", "DocumentSnapshot id::: $docId")
            databseReference.document(docId).delete().addOnCompleteListener {
                if (it.isSuccessful) {
                    onSuccess()
                }else{
                    onError(it.exception?.fillInStackTrace())
                }
            } }
}