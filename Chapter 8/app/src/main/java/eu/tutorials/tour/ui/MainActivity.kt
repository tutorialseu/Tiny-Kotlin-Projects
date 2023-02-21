package eu.tutorials.tour.ui

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import eu.tutorials.tour.TourApp
import eu.tutorials.tour.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    val firebaseServices by lazy {
        (application as TourApp).firebaseServices
    }

    val factory by lazy {
        TourViewModel.TourViewModelFactory(firebaseServices)
    }
    val viewModel by lazy {
        ViewModelProvider(this, factory)[TourViewModel::class.java]
    }
    private lateinit var binding: ActivityMainBinding

    var toolbar: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        toolbar = binding.toolbar
        setSupportActionBar(toolbar)

    }

}