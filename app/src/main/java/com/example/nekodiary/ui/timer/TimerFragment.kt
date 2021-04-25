package com.example.nekodiary.ui.timer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nekodiary.R
import com.example.nekodiary.databinding.FragmentTimerBinding
import com.example.nekodiary.ui.BaseFragment


/**
 * A simple [Fragment] subclass.
 * Use the [TimerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TimerFragment : BaseFragment() {

    enum class State{
        Running, Stopped, Paused
    }

    private lateinit var binding: FragmentTimerBinding
    private var timerLength = 0L
    private var timeRemaining = 0L
    private var timerState = State.Paused

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTimerBinding.bind(inflater.inflate(R.layout.fragment_timer, container, false))

        binding.start.setOnClickListener{
            startTimer()
        }
        binding.pause.setOnClickListener {
            pauseTimer()
        }
        binding.stop.setOnClickListener {
            stopTimer()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        // TODO: remove background timer, hide notification
        initTimer()
    }

    override fun onPause() {
        super.onPause()
        if(timerState == State.Running){
            //TODO: start background timer, show notification

        }else if(timerState == State.Paused) {
            //TODO: show notification
        }

        getPrefs().setTimerLengthSeconds(this.timeRemaining)
        getPrefs().setTimerState(timerState)
    }

    private fun initTimer(){
        timerState = getPrefs().getTimerState()

        if(timerState == State.Stopped)
            setNewTimerLength()
        else
            setPreviewsTimerLength()

        timeRemaining = if(timerState == State.Paused || timerState == State.Running)
            getPrefs().getTimerLengthSeconds()
        else
            timerLength

    }

    fun startTimer(){

    }

    fun stopTimer(){

    }
    fun pauseTimer(){

    }




}