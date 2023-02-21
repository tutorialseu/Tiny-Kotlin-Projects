package eu.tutorials.tour.ui

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
import eu.tutorials.tour.R
import eu.tutorials.tour.data.Tour
import eu.tutorials.tour.databinding.FragmentTourBinding
import eu.tutorials.tour.utils.Constants
import eu.tutorials.tour.utils.Resource

class TourFragment : Fragment() {

    private var _binding: FragmentTourBinding? = null


                // This property is only valid between onCreateView and
                // onDestroyView.
                private val binding get() = _binding!!
                val activity by lazy {
                    (requireActivity() as MainActivity)
                }
                    override fun onCreateView(
                        inflater: LayoutInflater, container: ViewGroup?,
                        savedInstanceState: Bundle?
                    ): View{

                        _binding = FragmentTourBinding.inflate(inflater, container, false)

                        activity.viewModel.getAllTour()
                        return binding.root

                    }

                    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
                        super.onViewCreated(view, savedInstanceState)
                        (activity).title = getString(R.string.app_name)
                        binding.addATourBtn.setOnClickListener {
                            findNavController().navigate(R.id.action_TourFragment_to_addEditTour)
                        }

                        activity.viewModel.tourLiveData.observe(requireActivity()){ result->
                            when(result){
                                is Resource.Loading ->{
                                    showProgressBar()
                                }
                                is Resource.Success -> {
                                    hideProgressBar()
                                    initializedAdapter(ArrayList(result.data))
                                }

                                is Resource.Failure ->{
                                    hideProgressBar()
                                    showToast(result.message)
                                }
                            }
                        }

                    }

    private fun hideProgressBar() {
        binding.apply {
            binding.tourFeedProgressBar.visibility = View.INVISIBLE
            binding.tourRv.visibility = View.VISIBLE
        }
    }

    private fun showProgressBar() {
        binding.apply {
            binding.tourFeedProgressBar.visibility = View.VISIBLE
            binding.tourRv.visibility = View.INVISIBLE
        }
    }

                    private fun itemOnClick(tour: Tour) {
                        val args = Bundle()
                        args.putParcelable(Constants.TOUR_KEY, tour)
                        findNavController().navigate(R.id.action_TourFragment_to_addEditTour, args)
                    }

                    private fun onDeleteClicked(tour: Tour,toursList: ArrayList<Tour>,toursAdapter: ToursAdapter?){

                        activity.viewModel.deleteTour(tour.tourId)

                        activity.viewModel.deleteTourLiveData.observe(requireActivity()){resources->
                            when(resources){
                                is Resource.Loading ->{
                                    showToast("operation in progress")
                                }
                                is Resource.Success ->{
                                     toursList.remove(tour)
                                    toursAdapter?.notifyDataSetChanged()
                                    showToast(resources.message)
                                }
                                is Resource.Failure->{
                                    showToast(resources.message)
                                }
                            }

                        }
                    }

                    private fun initializedAdapter(toursList: ArrayList<Tour>) {
                        // Adapter class is initialized and list is passed in the param.
                        var tourAdapter:ToursAdapter? = null
                        tourAdapter = ToursAdapter(activity.firebaseServices.user?.uid,toursList, onDeleteClicked = {
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