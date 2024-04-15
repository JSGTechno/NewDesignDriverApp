package com.example.fleetech.util

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import com.example.fleetech.R

@SuppressLint("AppCompatCustomView")
class SlideToConfirm : SeekBar, SeekBar.OnSeekBarChangeListener {

    private lateinit var listener: SlideButtonListener

    // To prevent the thumb to going out of track
    private val maxProgress = 91
    private val minProgress = 9

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    fun init() {
        setDrawables()
        setProperties()
        setOnSeekBarChangeListener(this)
    }

    private fun setDrawables() {
        thumb = ContextCompat.getDrawable(context, R.drawable.slider_thumb)
        progressDrawable = ContextCompat.getDrawable(context, R.drawable.slider_progress_drawable)
    }

    private fun setProperties() {
        isClickable = false
        splitTrack = false
        setPadding(0, 0, 0, 0)
        progress = minProgress
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (progress < minProgress) {
            this.progress = minProgress
        }
        if (progress > maxProgress) {
            this.progress = maxProgress
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

    override fun onStopTrackingTouch(seekBar: SeekBar?) {}

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event!!.action == MotionEvent.ACTION_DOWN) {
            if (thumb.bounds.contains(event.x.toInt(), event.y.toInt())) {
                super.onTouchEvent(event)
            } else
                return false
        } else if (event.action == MotionEvent.ACTION_UP) {
            if (progress > 70) {
                handleSlide()
                progress = maxProgress
            } else {
                progress = minProgress
            }
        } else {
            super.onTouchEvent(event)
        }
        return true
    }

    fun setOnSlideListener(listener: SlideButtonListener) {
        this.listener = listener
    }


//    fun setSlideButtonListener(listener: SlideButtonListener?) {
//        this.listener = listener!!
//    }
    private fun handleSlide() {
        if (::listener.isInitialized) {
            listener.handleSlide()
        }
    }


    interface SlideButtonListener {
        fun handleSlide()
    }
}