package io.letdrink.common.utils

import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior

class BottomSheetBehaviorCallback(
    private val onStateChange: (Int) -> Unit
) : BottomSheetBehavior.BottomSheetCallback() {
    override fun onStateChanged(bottomSheet: View, newState: Int) {
        onStateChange(newState)
    }

    override fun onSlide(bottomSheet: View, slideOffset: Float) {

    }
}