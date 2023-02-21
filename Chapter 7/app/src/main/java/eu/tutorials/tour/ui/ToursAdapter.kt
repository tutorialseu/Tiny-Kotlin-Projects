package eu.tutorials.tour.ui

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import eu.tutorials.tour.R
import eu.tutorials.tour.data.Tour
import eu.tutorials.tour.databinding.TourItemViewBinding

/**
 * We have the @param [tourItems] to represent the list that populates the adapter
 **/
class ToursAdapter(
    private val currentUserId:String?,
    private val tourItems: ArrayList<Tour>,
    private val onDeleteClicked: (Tour) -> Unit,
    private val onItemClicked: (Tour) -> Unit

) : RecyclerView.Adapter<ToursAdapter.ToursViewHolder>() {
    /**
     * Inflates the item views which is designed in xml layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToursViewHolder {
        val binding = TourItemViewBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ToursViewHolder(binding)
    }

    /**
     * Binds each item in the ArrayList to a view
     *
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ToursViewHolder, position: Int) {
        with(holder) {
            with(tourItems[position]) {
                val context = holder.itemView.context

                binding.placeNameTextView.text = placeName
                binding.descriptionTextView.text = description
                binding.authorNameTextView.text = context.getString(R.string.author, authorName)

                Log.d("TourAdapter Image =|=|=", "===$placeName")

                Glide.with(holder.itemView.context)
                    .load(placeImage)
                    .into(binding.placeImageView)

                if (currentUserId == userId){
                    binding.deleteButton.visibility = View.VISIBLE
                }else{
                    binding.deleteButton.visibility  = View.INVISIBLE
                }
            }
            itemView.setOnClickListener {
                onItemClicked.invoke(tourItems[position])
            }


            binding.deleteButton.setOnClickListener {
                onDeleteClicked.invoke(tourItems[position])
            }
        }
    }

    /**
     * Gets the number of items in the list
     */
    override fun getItemCount() = tourItems.size


    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    inner class ToursViewHolder(val binding: TourItemViewBinding) :
        RecyclerView.ViewHolder(binding.root)

}
