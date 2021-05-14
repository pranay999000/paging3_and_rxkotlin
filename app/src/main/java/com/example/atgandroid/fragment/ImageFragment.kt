package com.example.atgandroid.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.atgandroid.R
import com.example.atgandroid.databinding.FragmentImageBinding
import java.lang.Float.max
import java.lang.Float.min

class ImageFragment : Fragment() {
    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private var scaleFactor: Float = 1.0f
    private lateinit var binding: FragmentImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentImageBinding.inflate(inflater, container, false)

        var args = ImageFragmentArgs.fromBundle(requireArguments())
        context?.let { Glide.with(it).load(args.image).into(binding.image) }

        scaleGestureDetector = ScaleGestureDetector(context, ScaleListener())
        binding.root.setOnTouchListener { _, event ->
            if (event?.action == MotionEvent.ACTION_MOVE) {
                scaleGestureDetector.onTouchEvent(event)
            }
            true
        }

        return binding.root
    }

    private inner class ScaleListener: SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector?): Boolean {
            scaleFactor *= scaleGestureDetector.scaleFactor
            scaleFactor = max(0.1f, min(scaleFactor, 10.0f))
            binding.image.scaleX = scaleFactor
            binding.image.scaleY = scaleFactor
            return true
        }
    }
}