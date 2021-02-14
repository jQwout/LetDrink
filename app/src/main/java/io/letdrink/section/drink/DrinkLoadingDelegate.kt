package io.letdrink.section.drink

import android.widget.ViewSwitcher
import io.letdrink.R

class DrinkLoadingDelegate(private val drinkCardBottomSheet: ViewSwitcher) {

    private val contentId = 1
    private val loadingId = 0

    fun bind() {
        drinkCardBottomSheet.displayedChild = loadingId
    }

    fun showContentIfNeed() {
        drinkCardBottomSheet.displayedChild = contentId
    }
}