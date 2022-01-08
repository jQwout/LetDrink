package io.letdrink.common.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import io.letdrink.R

class CommonProgressDialog : DialogFragment() {

    companion object {

        const val TAG = "CommonProgressDialog"

        fun newInstance(): CommonProgressDialog {
            return CommonProgressDialog()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.progress_dialog_fragment, container, false)
    }


    override fun onResume() {
        super.onResume()
        val size = resources.getDimensionPixelSize(R.dimen.dialog_progress_size)
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.window?.apply {
            setBackgroundDrawableResource(android.R.color.transparent)
            setLayout(size, size)
        }
    }
}

fun showProgressDialog(fm: FragmentManager) {
    CommonProgressDialog().show(fm, CommonProgressDialog.TAG)
}

fun Fragment.showProgressDialog() = showProgressDialog(childFragmentManager)

fun FragmentActivity.showProgressDialog() = showProgressDialog(supportFragmentManager)

fun dismissProgressDialog(fm: FragmentManager) {
    val f = fm.findFragmentByTag(CommonProgressDialog.TAG) as? CommonProgressDialog
    f?.dismiss()
}

fun Fragment.dismissProgressDialog() = dismissProgressDialog(childFragmentManager)

fun FragmentActivity.dismissProgressDialog() = dismissProgressDialog(supportFragmentManager)
