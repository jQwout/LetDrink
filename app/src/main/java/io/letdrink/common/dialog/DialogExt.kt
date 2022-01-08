package io.letdrink.common.dialog

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import io.letdrink.R


fun FragmentManager.showError(
    exception: Throwable? = null,
    message: String?,
    onButtonRightClick: (() -> Unit)? = null
) {
    CommonDialog.newInstanceError(message).apply {
        this.rightButtonClickListener = onButtonRightClick
    }
        .showNow(this, CommonDialog.TAG)
}

fun Fragment.showError(
    exception: Throwable? = null,
    message: String?,
    onButtonRightClick: (() -> Unit)? = null
) {
    val title = resources.getString(R.string.error_title_text)
    val oktext = resources.getString(R.string.button_ok_text)
    childFragmentManager.showError(
        title,
        message ?: resources.getString(R.string.error_subtitle_text),
        oktext,
        exception,
        onButtonRightClick
    )
}

fun AppCompatActivity.showError(
    exception: Throwable? = null,
    message: String?,
    onButtonRightClick: (() -> Unit)? = null
) {
    val title = resources.getString(R.string.error_title_text)
    val oktext = resources.getString(R.string.button_ok_text)
    supportFragmentManager.showError(
        title,
        message ?: resources.getString(R.string.error_subtitle_text),
        oktext,
        exception,
        onButtonRightClick
    )
}

fun FragmentManager.showError(
    title: String,
    subTitle: String,
    buttonText: String,
    exception: Throwable? = null,
    onButtonRightClick: (() -> Unit)? = null
) {
    CommonDialog.newInstance(title, subTitle, buttonText).apply {
        this.rightButtonClickListener = onButtonRightClick
    }
        .showNow(this, CommonDialog.TAG)
}