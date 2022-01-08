package io.letdrink.common.arch

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleCoroutineScope
import io.letdrink.common.dialog.dismissProgressDialog
import io.letdrink.common.dialog.showError
import io.letdrink.common.dialog.showProgressDialog
import io.letdrink.common.viewmodel.ModernViewModel
import io.letdrink.common.viewmodel.VmEvents
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class VmConsumer(
    val fragmentManager: FragmentManager,
    val scope: LifecycleCoroutineScope,
    val onNavEventCallback: (VmEvents) -> Unit,
    val renderers: List<StateRenderer>
) {

    fun subscribe(modernViewModel: ModernViewModel) {
        scope.launchWhenCreated {

            launch {
                modernViewModel.dialog.filterNotNull().collect {
                    if (it is VmEvents.Loading) {
                        if (it.isLoading) {
                            showProgressDialog(fragmentManager)
                        } else {
                            dismissProgressDialog(fragmentManager)
                        }
                    } else {
                        dismissProgressDialog(fragmentManager)
                    }


                    if (it is VmEvents.Fail) {
                        fragmentManager.showError(it.t, null)
                    }
                }
            }

            launch {
                modernViewModel.navigate.filterNotNull().collect {
                    onNavEventCallback(it)
                }
            }
        }

        renderers.forEach {
            it.subscribe(modernViewModel)
        }
    }
}

abstract class StateRenderer(
    val scope: LifecycleCoroutineScope
) {
    abstract fun subscribe(viewModel: ModernViewModel)
}