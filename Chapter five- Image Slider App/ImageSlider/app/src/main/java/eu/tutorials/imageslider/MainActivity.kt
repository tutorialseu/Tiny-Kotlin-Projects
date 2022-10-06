package eu.tutorials.imageslider

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL
import eu.tutorials.imageslider.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var viewPager2:ViewPager2? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Log.d("create","This is the onCreate")
         viewPager2 =  binding.viewpager
        val adapter = PagerAdapter(this, slides)
        viewPager2?.adapter = adapter
        binding.dotsIndicator.setViewPager2(viewPager2!!)
        viewPager2?.clipToPadding = false
        viewPager2?.clipChildren = false
        viewPager2?.offscreenPageLimit = 2
        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin)
        val offsetPx = resources.getDimensionPixelOffset(R.dimen.ofset)
        viewPager2?.setPageTransformer { page, position ->
            val viewPager = page.parent.parent as ViewPager2
            val offset = position * -(2 * offsetPx + pageMarginPx)
            if (viewPager.orientation == ORIENTATION_HORIZONTAL) {
                if (ViewCompat.getLayoutDirection(viewPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                    page.translationX = -offset
                } else {
                    page.translationX = offset
                }
            } else {
                page.translationY = offset
            }
        }

        viewPager2?.registerOnPageChangeCallback(object:ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 0){
                    binding.buttonPrev.isEnabled = false
                }else{
                    binding.buttonPrev.isEnabled = true
                binding.buttonPrev.setOnClickListener {
                    viewPager2?.currentItem?.let {
                        viewPager2?.setCurrentItem(it - 1, false)
                    }
                }
                }
                if(position == slides.size - 1){
                    binding.buttonNext.text = "Finish"
                    binding.buttonNext.setOnClickListener {
                        viewPager2?.currentItem = 0
                    }
                }else{
                    binding.buttonNext.text = "Next"
                    binding.buttonNext.setOnClickListener {
                        viewPager2?.currentItem?.let {
                            viewPager2?.setCurrentItem(it + 1, false)
                        }
                    }
                }
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 2000)

            }
        })
    }
    private val sliderHandler: Handler = Handler(Looper.getMainLooper())
    private val sliderRunnable =
        Runnable {
            viewPager2?.let {
                if (it.currentItem == slides.size-1){
                    it.currentItem = 0

                }else {
                    it.currentItem = it.currentItem.plus(1)
                }

            }
        }


    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(sliderRunnable)
        Log.d("pause","This is the onPause")
    }

    override fun onResume() {
        super.onResume()
        sliderHandler.postDelayed(sliderRunnable, 2000)
        Log.d("resume","This is the onResume")
    }

}