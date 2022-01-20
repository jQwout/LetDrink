package io.letdrink.common.views

import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import io.letdrink.R


fun ChipGroup.addChip(text: String, isSelected: Boolean = false, isSelectable: Boolean = false) {
    val c = Chip(context)
    c.checkedIcon = null
    c.isChecked = isSelected
    c.isCloseIconVisible = isSelectable

    if (isSelected) {
        c.setChipBackgroundColorResource(R.color.purple_500)
    }

    c.text = text
}

fun ChipGroup.addChips(list: List<Pair<String, Boolean>>, isSelectable: Boolean = false) {
    list.sortedBy {
        it.second
    }.forEach {
        addChip(it.first, it.second, isSelectable)
    }
}