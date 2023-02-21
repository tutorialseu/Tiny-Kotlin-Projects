package eu.tutorials.countdowntimer.ui

import android.os.CountDownTimer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun rememberCountDownTimerState() = remember(){
   CountDownTimerState()
}

class CountDownTimerState{
    private var countDownTimer: CountDownTimer? = null

    //Check when the timer is running
    val isRunning: MutableState<Boolean>
        get() = _isRunning
    private val _isRunning = mutableStateOf(false)
    private val _seconds = mutableStateOf("00")
    val seconds: MutableState<String>
        get() = _seconds

    private val _minutes = mutableStateOf("00")
    val minutes: MutableState<String>
        get() = _minutes

    private var _hours = mutableStateOf("00")
    val hours: MutableState<String>
        get() = _hours



    private val _progress = mutableStateOf(1f)
    val progress: MutableState<Float>
        get() = _progress

    var totalTime = 0L

    fun startCountDown() {
        if (countDownTimer != null) {
            cancelTimer()
        }

        /**
         * Gets timer values
         * This is only used if you have want to show the timer progress on a progressBar,
         * also if you want a way to manage the whole timer
         */
        totalTime = (getSeconds() * 1000).toLong()

        /**
         * Handles the whole timer count down
         */
        countDownTimer = object : CountDownTimer(totalTime, 1000) {
            override fun onTick(millisecs: Long) {
                // Seconds
                val secs = (millisecs / MSECS_IN_SEC % SECS_IN_MINUTES).toInt()
                if (secs != seconds.value.toInt()) {
                    _seconds.value = formatTime(secs)
                }
                // Minutes
                val minutes = (millisecs / MSECS_IN_SEC / SECS_IN_MINUTES % SECS_IN_MINUTES).toInt()
                if (minutes != minutes) {
                    _minutes.value = formatTime(minutes)
                }
                // Hours
                val hours = (millisecs / MSECS_IN_SEC / MINUTES_IN_HOUR / SECS_IN_MINUTES).toInt()
                if (hours != hours) {
                    _hours.value = formatTime(hours)
                }

                _progress.value = millisecs.toFloat() / totalTime.toFloat()


                //send timer updates back to view
                _seconds.value  = formatTime(secs)
                _minutes.value = formatTime(minutes)
                _hours.value = formatTime(hours)
            }

            override fun onFinish() {
                _progress.value = 1.0f
                _isRunning.value = false
            }
        }
        countDownTimer?.start()
        _isRunning.value = true
    }

    /**
     * Check Seconds input length from [0..59]
     * Then post value
     */
    fun modifySeconds(secondsValue: String) {
        if (secondsValue.isNotEmpty()) {
            _seconds.value = secondsValue.toInt().coerceIn(0, 59).toString()
        } else {
            _seconds.value  = secondsValue
        }

    }

    /**
     * Check Minutes input length from [0..59]
     * Then post value
     */
    fun modifyMinutes(minutesValue: String) {
        if (minutesValue.isNotEmpty()) {
            _minutes.value = minutesValue.toInt().coerceIn(0, 59).toString()
        } else {
            _minutes.value = minutesValue
        }

    }

    /**
     * Check Hours input length from [0..23]
     * Then post value
     */
    fun modifyHours(hoursValue: String) {
        if (hoursValue.isNotEmpty()) {
            _hours.value = hoursValue.toInt().coerceIn(0, 23).toString()
        } else {
            _hours.value = hoursValue
        }
    }

    /**
     * Format the Count down timer
     */
    private fun formatTime(time: Int) =
        String.format("%02d", time)

    /**
     * Cancel/stop timer
     */
    fun cancelTimer() {
        countDownTimer?.cancel()
        _isRunning.value = false
    }

    /**
     * Handles counting down in seconds
     */
    private fun getSeconds() =
        (hours.value.toInt() * MINUTES_IN_HOUR * SECS_IN_MINUTES) + (minutes.value.toInt() * SECS_IN_MINUTES) + seconds.value.toInt()

    companion object {
        const val MINUTES_IN_HOUR = 60
        const val SECS_IN_MINUTES = 60
        const val MSECS_IN_SEC = 1000
    }
}