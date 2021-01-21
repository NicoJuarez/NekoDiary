package com.example.nekodiary.ui.battery

import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModelProvider
import com.example.nekodiary.R
import com.example.nekodiary.ui.BaseFragment
import com.example.nekodiary.user.Preferences

class BatteryFragment : BaseFragment() {

    private lateinit var slideshowViewModel: SlideshowViewModel
    private var batteryStatus: Intent? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_battery, container, false)
//        val textView: TextView = root.findViewById(R.id.percentage)
//        slideshowViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        batteryStatus = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
            context?.registerReceiver(null, ifilter)
        }

        initializeComponents(view)

    }

    private fun initializeComponents(view: View) {
        view.findViewById<TextView>(R.id.percentage).text =
            getString(R.string.battery_percentage, calculatePercentage())

        view.findViewById<ImageView>(R.id.image_battery).setImageResource(
            if (isCharging())
                R.drawable.ic_battery_charging_24
            else
                R.drawable.ic_battery_24
        )

        val switch = view.findViewById<SwitchCompat>(R.id.notification_switch)
        getPrefs()?.let {
            val notif = it.getValue(Preferences.BATTERY_NOTIFICATION)
            switch.isChecked =
                (notif != Preferences.NONE && notif != "false")
        }

        switch.setOnCheckedChangeListener { _, isChecked ->

            getPrefs()?.setValue(
                Preferences.BATTERY_NOTIFICATION,
                if (isChecked) "true" else "false"
            )

        }
    }

    private fun calculatePercentage(): Float {
        val level: Int? = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        val scale: Int? = batteryStatus?.getIntExtra(BatteryManager.EXTRA_SCALE, -1)


        return when {
            level == null -> -1f
            scale == null -> -1f
            else -> {
                (level * 100) / scale.toFloat()
            }
        }
    }

    private fun isCharging(): Boolean {
        val chargePlug: Int = batteryStatus?.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1) ?: -1

        return chargePlug == BatteryManager.BATTERY_PLUGGED_USB || chargePlug == BatteryManager.BATTERY_PLUGGED_AC
    }
}