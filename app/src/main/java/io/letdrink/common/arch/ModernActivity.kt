package io.letdrink.common.arch

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import io.letdrink.common.viewmodel.ModernViewModel

abstract class ModernActivity : AppCompatActivity() {

    abstract val viewModel: ModernViewModel

    abstract val consumer: VmConsumer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        consumer.subscribe(viewModel)
    }

}

abstract class ModernFragment : Fragment() {

    abstract val viewModel: ModernViewModel

    abstract val consumer: VmConsumer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        consumer.subscribe(viewModel)
    }
}