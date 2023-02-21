package eu.tutorials.tour.data

import android.net.Uri
import android.util.Log
import android.util.Log.DEBUG
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import eu.tutorials.tourguideapp.utils.Resource
import java.util.*

class FirebaseServices {
    private val firebaseAuth:FirebaseAuth = FirebaseAuth.getInstance()
    private val storageRef = Firebase.storage.reference
    private val database = Firebase.firestore
    private val databseReference = database.collection("tours")

    fun registerUser(
        userEmail: String,
        userPassword: String,
        onUserCreated:(Task<AuthResult>)->Unit
    ){
            firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener {
                    onUserCreated(it)
                }
    }


     val user = firebaseAuth.currentUser
    val hasUser = user != null

    fun SignInUser(
        userEmail: String,
        userPassword: String,
        onUserSignIn:(Task<AuthResult>)->Unit
    ){
        firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener {
                onUserSignIn(it)
            }
        Log.d("name", firebaseAuth.currentUser?.displayName.toString())

    }

     fun uploadImageToFirebaseStorageAndAddTour(
        selectedImageUri: Uri,
        onUploadSuccess:(String)->Unit
    ){
            val uploadTask = storageRef.child("tourImages/${Calendar.getInstance().time.time}")
            uploadTask.putFile(selectedImageUri).addOnCompleteListener {
                if (it.isSuccessful){
                    uploadTask.downloadUrl.addOnSuccessListener { uri ->
                        if (uri.toString().isNotEmpty()) {

                            Log.d(
                                "AddTourFragment",
                                "TourImage link getDownloadImageUrl: $uri"
                            )
                            //  addTour(tour, uri, result = {})
                       onUploadSuccess(uri.toString())
                        } else {
                            Log.d("AddTourFragment", "Image Url is null")
                        }
                        Log.d("AddTourFragment", "Image File Location:// $uri")
                    }
                }
            }


        }

    fun saveATour(tour:Tour,onTourSaved:(Task<Void>)->Unit){
       databseReference.document().set(tour).
               addOnCompleteListener {
                   if(it.isSuccessful){
                       onTourSaved(it)
                   }else{
          Log.d("Firebase Services","${it.exception}")
                   }
               }
    }

    fun getAllTour(onSuccess:(tours:List<Tour>)->Unit){
        val toursList =  ArrayList<Tour>()
       database.collection("tours").get().addOnCompleteListener {
            // Here we get the list of users in the form of documents.
            if(it.isSuccessful){
            for (document in it.result.documents) {
                val tour = document.toObject(Tour::class.java)
                val newTour  =  tour?.copy(tourId = document.id, authorName = user?.displayName.toString())
                if (newTour != null) {
                    toursList.add(newTour )
                }
                onSuccess(toursList)
                Log.d(
                    "TourFragment",
                    "All Tours Info:|:|:|:${document.id} => ${document.data}")
            }
            }}

        }

     fun updateTour(tourId:String,tour: Tour,onUpdateSuccess:()->Unit){
            databseReference.document(tourId).update(tour.toMap()).addOnCompleteListener {
                if (it.isSuccessful){
                    onUpdateSuccess()
                }
            }
            Log.d(
                "AddTourFragment",
                "Tour DocumentSnapshot updated with ID: ${tour.tourId}"
            )
        }

    fun deleteATour(docId: String, onSuccess: () -> Unit){
            Log.d("TourDetailsFragment", "DocumentSnapshot id::: $docId")

            databseReference.document(docId).delete().addOnCompleteListener {
                onSuccess()
                Log.d("TourDetailsFragment", "Tour deleted successfully")
            }


        }
}