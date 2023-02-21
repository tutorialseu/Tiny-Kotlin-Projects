package eu.tutorials.tour

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import eu.tutorials.tour.data.FirebaseServices
import eu.tutorials.tour.data.Tour
import eu.tutorials.tour.databinding.FragmentTourBinding
import eu.tutorials.tour.ui.ToursAdapter
import eu.tutorials.tour.utils.Constants

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class TourFragment : Fragment() {

    private var _binding: FragmentTourBinding? = null
    private val firebaseServices by lazy {
        FirebaseServices()
    }

        // This property is only valid between onCreateView and
        // onDestroyView.
        private val binding get() = _binding!!

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {

            _binding = FragmentTourBinding.inflate(inflater, container, false)
            return binding.root

        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            (activity as MainActivity).title = getString(R.string.app_name)
            binding.addATourBtn.setOnClickListener {
                findNavController().navigate(R.id.action_TourFragment_to_addEditTour)
            }
            firebaseServices.getAllTour {
                Log.d("tours", "$it")
               initializedAdapter(ArrayList(it))
            }

        }

    private fun itemOnClick(tour: Tour) {
        val args = Bundle()
        args.putParcelable(Constants.TOUR_KEY, tour)
        findNavController().navigate(R.id.action_TourFragment_to_addEditTour, args)
    }

    private fun onDeleteClicked(tour: Tour,toursList: ArrayList<Tour>,toursAdapter: ToursAdapter?){

        firebaseServices.deleteATour(tour.tourId){
            toursList.remove(tour)
            toursAdapter?.notifyDataSetChanged()
            showToast("Tour has been deleted")
        }
    }

    private fun initializedAdapter(toursList: ArrayList<Tour>) {
        // Adapter class is initialized and list is passed in the param.
        var tourAdapter:ToursAdapter? = null
        tourAdapter = ToursAdapter(firebaseServices.user?.uid,toursList, onDeleteClicked = {
            onDeleteClicked(it,toursList,tourAdapter)
        }){
            itemOnClick(it)
        }
        val dividerItemDecoration = DividerItemDecoration(requireActivity(), RecyclerView.VERTICAL)
        if (toursList.isNotEmpty()) {
            binding.apply {
                // adapter instance is set to the recyclerview to inflate the items.
                binding.tourRv.adapter = tourAdapter
                // Set the LayoutManager that this RecyclerView will use.
                binding.tourRv.layoutManager = LinearLayoutManager(requireActivity())
                // Set the addItemDecoration that this RecyclerView will use.
                binding.tourRv.addItemDecoration(dividerItemDecoration)
                //Reverses the position of the items
                toursList.reverse()
                binding.tourRv.visibility = View.VISIBLE
                //emptyTextView.visibility = View.INVISIBLE
            }
        } else {
            binding.apply {
                binding.tourRv.visibility = View.INVISIBLE
               // binding..visibility = View.VISIBLE
            }
        }

    }
        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
}