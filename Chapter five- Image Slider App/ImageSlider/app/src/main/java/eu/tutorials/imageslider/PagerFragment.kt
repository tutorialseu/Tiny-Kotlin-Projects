package eu.tutorials.imageslider

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import eu.tutorials.imageslider.databinding.FragmentPagerBinding

class PagerFragment : Fragment() {


    private val binding by lazy {
        FragmentPagerBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val position = requireArguments().getInt(slide)
            binding.image.setImageResource(slides[position])


    }

    companion object {
         const val slide = "SLIDES_ARG"
        @JvmStatic
        fun newInstance(position:Int) =
            PagerFragment().apply {
              arguments =  Bundle().apply {
                    putInt(slide,position)
                }
            }
    }
}