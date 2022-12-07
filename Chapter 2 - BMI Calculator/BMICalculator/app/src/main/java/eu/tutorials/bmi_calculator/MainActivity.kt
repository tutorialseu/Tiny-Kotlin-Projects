package eu.tutorials.bmi_calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import eu.tutorials.bmi_calculator.databinding.ActivityMainBinding
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var heightValue: Double = 0.0
    private var weightValue: Double = 0.0
    private var bmi:Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val weightEt = binding.weightEt
        val heightEt = binding.heightEt

        binding.heightPlusBtn.setOnClickListener {
            if (heightEt.text.toString().isNotEmpty()) {
                heightValue = heightEt.text.toString().toDouble()
            }
            heightValue++
            heightEt.setText(heightValue.toString())
        }
        binding.heightMinusBtn.setOnClickListener {
            if (heightEt.text.toString().isNotEmpty()) {
                heightValue = heightEt.text.toString().toDouble()
            }
            heightValue--
            heightEt.setText(heightValue.toString())
        }
        binding.weightPlusBtn.setOnClickListener {
            if (weightEt.text.toString().isNotEmpty()){
                weightValue = weightEt.text.toString().toDouble()
            }
            weightValue++
            weightEt.setText(weightValue.toString())
        }
        binding.weightMinusBtn.setOnClickListener {
            if (weightEt.text.toString().isNotEmpty()){
                weightValue = weightEt.text.toString().toDouble()
            }
            weightValue--
            weightEt.setText(weightValue.toString())
       }

        binding.calculateBtn.setOnClickListener {
            val height = heightEt.text.toString().toDouble()
            val weight = weightEt.text.toString().toDouble()
            bmi =(weight/height.times(height))
            Toast.makeText(this,"$bmi",Toast.LENGTH_LONG).show()
            displayBMIResult(bmi)
        }

    }

    private fun displayBMIResult(bmi: Double) {

        var bmiLabel =""
        var bmiDescription  = ""

        when {
            bmi < 18.5 -> {
                bmiLabel = getString(R.string.under_weight_label)
                bmiDescription = "You should take better care of yourself! Eat more!"
            }
            bmi in 18.5..24.9 -> {
                bmiLabel = "Normal"
                bmiDescription = "Congratulations! You are in a good shape!"
            }
            bmi in 25.0..29.9 -> {
                bmiLabel = "Overweight"
                bmiDescription = "You really need to take care of yourself! Workout maybe!"
            }
            bmi >= 30.0 -> {
                bmiLabel = "Obese Class"
                bmiDescription = "You might be in a dangerous condition! Act now!"
            }
        }

        binding.resultTv.text = resources.getString(R.string.bmi_result,bmi,bmiLabel,bmiDescription)
    }
}