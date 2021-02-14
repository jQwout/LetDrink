package io.letdrink.section.splash

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import io.letDrink.localbar.db.LocalBarServiceLocator
import io.letdrink.features.main.MainActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            viewModel.uiState.collect {
                if (!it.loading) {
                    startActivity(
                        MainActivity.getIntent(this@SplashActivity)
                    )
                }
            }
        }
        viewModel.start()
    }
}


class SplashViewModel @ViewModelInject constructor() : ViewModel() {

    val uiState: MutableStateFlow<SplashState> = MutableStateFlow(SplashState())

    fun start() {
        viewModelScope.launch {
            uiState.emit(SplashState(false))
        }
    }
}

class SplashState(val loading: Boolean = true)