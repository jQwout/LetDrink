package io.letdrink.common.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import io.letdrink.R
import io.letdrink.databinding.CommonDialogFragmentBinding

class CommonDialog : DialogFragment() {

    companion object {

        const val TAG = "CommonDialog"

        private const val KEY_TITLE = "KEY_TITLE"
        private const val KEY_SUBTITLE = "KEY_SUBTITLE"
        private const val KEY_BUTTON_TEXT = "KEY_BUTTON_TEXT"
        private const val KEY_BUTTON_2_TEXT = "KEY_BUTTON_2_TEXT"

        fun newInstance(title: String, subTitle: String, buttonText: String): CommonDialog {
            val args = Bundle()
            args.putString(KEY_TITLE, title)
            args.putString(KEY_SUBTITLE, subTitle)
            args.putString(KEY_BUTTON_TEXT, buttonText)

            val fragment = CommonDialog()
            fragment.arguments = args
            return fragment
        }

        fun newInstanceError(subtitle: String?): CommonDialog {
            val args = Bundle()
            val fragment = CommonDialog()
            val resources = fragment.requireContext().resources
            val title = resources.getString(R.string.error_title_text)
            val oktext = resources.getString(R.string.button_ok_text)
            val text = subtitle ?: resources.getString(R.string.error_subtitle_text)

            args.putString(KEY_TITLE, title)
            args.putString(KEY_SUBTITLE, text)
            args.putString(KEY_BUTTON_TEXT, oktext)
            fragment.arguments = args
            return fragment
        }

        fun newInstance(
            title: String,
            subTitle: String,
            buttonText: String,
            button2Text: String
        ): CommonDialog {
            val args = Bundle()
            args.putString(KEY_TITLE, title)
            args.putString(KEY_SUBTITLE, subTitle)
            args.putString(KEY_BUTTON_TEXT, buttonText)
            args.putString(KEY_BUTTON_2_TEXT, button2Text)

            val fragment = CommonDialog()
            fragment.arguments = args
            return fragment
        }
    }

    private var b: CommonDialogFragmentBinding? = null

    var rightButtonClickListener: (() -> Unit)? = null
    var leftButtonClickListener: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.common_dialog_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupClickListeners(view)
    }

    override fun onResume() {
        super.onResume()
        val width = resources.getDimensionPixelSize(R.dimen.dialog_width)
        dialog?.window?.apply {
            setBackgroundDrawableResource(android.R.color.transparent);
            setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT)
        }
    }

    private fun setupView(view: View) {

        val binding = CommonDialogFragmentBinding.bind(view)
        b = binding

        binding.title.text = arguments?.getString(KEY_TITLE)
        binding.content.text = arguments?.getString(KEY_SUBTITLE)
        binding.button.text = arguments?.getString(KEY_BUTTON_TEXT)

        val button2Text = arguments?.getString(KEY_BUTTON_2_TEXT)

        if (button2Text == null) {
            binding.button2.visibility = View.INVISIBLE
        } else {
            binding.button2.text = button2Text
        }

    }

    private fun setupClickListeners(view: View) {
        b?.button?.setOnClickListener {
            rightButtonClickListener?.invoke()
            dismissAllowingStateLoss()
        }

        b?.button2?.setOnClickListener {
            leftButtonClickListener?.invoke()
            dismissAllowingStateLoss()
        }
    }

}