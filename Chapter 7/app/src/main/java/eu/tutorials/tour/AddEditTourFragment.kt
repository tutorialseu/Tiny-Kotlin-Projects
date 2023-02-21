package eu.tutorials.tour

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import eu.tutorials.tour.data.FirebaseServices
import eu.tutorials.tour.data.Tour
import eu.tutorials.tour.databinding.FragmentAddEditTourBinding
import eu.tutorials.tour.utils.Constants.TOUR_KEY
import java.io.IOException
import kotlin.properties.Delegates

fun Fragment.showToast(message: String) {
    Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

class AddEditTour : Fragment() {

    private val TAG = AddEditTour::class.java.simpleName
    private val binding  by lazy {
        FragmentAddEditTourBinding.inflate(layoutInflater)
    }
    private lateinit var startActivityLaunch: ActivityResultLauncher<Intent>
    private lateinit var requestPermissionLauncher:ActivityResultLauncher<String>
    private val firebaseServices by lazy{
        FirebaseServices()
    }

    private var galleryIntent:Intent? = null
    //private var tour: Tour? = null
    private var tourId: String = ""
    private var placeName: String = ""
    private var placeImageView: ImageView? = null
    private var placeDescription: String = ""
    private var documentId: String? = ""
    private var selectedImageFileUri: Uri? = null
    private var isUploadNewImageForEdit by Delegates.notNull<Boolean>()


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            // Inflate the layout for this fragment
            return binding.root
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


         requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                    showToast("Storage permission granted")
                    // Launches the selected image intent
                    startActivityLaunch.launch(galleryIntent)
                } else {
                    //Displaying another toast if permission is not granted
                    showToast("Storage permission denied. You can also allow it from settings.")
                }

            }

        //Checks for READ_EXTERNAL_STORAGE permission
         startActivityLaunch =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                //Get the data returned by intent
                val resultCode = it.resultCode
                val data = it.data

                if (resultCode == AppCompatActivity.RESULT_OK && data != null) {
                    try {

                        // TODO Step : Initialize the global variable for URI and set the image to the ImageView.
                        // START
                        // The uri of selected image from phone storage.
                        selectedImageFileUri = data.data
                        selectedImageFileUri?.let { it1 ->
                            firebaseServices.uploadImageToFirebaseStorageAndAddTour(it1){
                                Log.d("image link",it)
                            }
                        }
                        //Show imageView if Image Uri is null
                        binding.previewImageView.visibility = View.VISIBLE
                        //isUploadNewImageForEdit = true

                        binding.previewImageView.setImageURI(selectedImageFileUri)

                        Log.d(TAG, "Selected Image Uri file ||| === ${selectedImageFileUri}")
                        Log.d(TAG, "Showing image Uri $selectedImageFileUri")
                        // END
                    } catch (e: IOException) {
                        e.printStackTrace()
                        showToast("Image selection Failed! ${e.message}")
                    }
                }else{
                    requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
         galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        placeName = binding.placeName.text.toString().trim()
        placeImageView = binding.previewImageView

        val tour = arguments?.getParcelable<Tour>(TOUR_KEY)
        tour?.let {
            onTourSelected(it)
        }

        binding.chooseImageBtn.setOnClickListener {
            checkPermissions(requireContext())
        }

        binding.uploadBtn.setOnClickListener {
            saveAndUpdateTour(tour)
        }

    }

    private fun saveAndUpdateTour(tour: Tour?) {
        val userId = firebaseServices.user?.uid.toString()
        val name = binding.placeName.text.toString()
        val image = selectedImageFileUri.toString()
        val desc = binding.placeDescription.text.toString()
        if (userId.isNullOrEmpty() || name.isNullOrEmpty() ||
            selectedImageFileUri.toString().isNullOrBlank() || desc.isNullOrEmpty()
        ) {
            showToast("Field cannot be empty")
        } else {
            if (tour != null) {
                firebaseServices.updateTour(
                    tourId =tour.tourId,
                    tour.copy(
                        placeName = name,
                        description = desc, userId = userId, placeImage = image
                    )
                ){
                    showToast("Tour successfully updated")
                    findNavController().popBackStack()
                }
            } else {
                firebaseServices.saveATour(
                    Tour(
                        placeName = name,
                        description = desc, userId = userId, placeImage = image
                    )
                ) {
                    if (it.isSuccessful) {
                        showToast("Tour successfully uploaded")
                        findNavController().popBackStack()
                    } else {
                        showToast(it.exception?.message.toString())
                    }
                }
            }

        }
    }

    private fun onTourSelected(tour: Tour){

        binding.apply {

            Log.d(
                TAG,
                "Argument =>:|: DocumentId ${tour.tourId} ${tour.placeImage} || ${tour.description}"
            )
            placeName.setText(tour.placeName)
            placeDescription.setText(tour.description)
            placeImageView?.let {
                Glide.with(requireContext())
                    .load(tour.placeImage)
                    .placeholder(R.drawable.water_villa)
                    .into(it)
            }

            if (tour.userId != firebaseServices.user?.uid) {
                placeName.isEnabled = false
                placeDescription.isEnabled = false
                uploadBtn.isEnabled = false
                chooseImageBtn.isEnabled  = false

            }else{
                placeName.isEnabled = true
                placeDescription.isEnabled = true
                uploadBtn.isEnabled = true
                chooseImageBtn.isEnabled  = true
            }
            }
    }
    private fun checkPermissions(context: Context) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            startActivityLaunch.launch(galleryIntent)
            // END
        }else{
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

    }
    }

