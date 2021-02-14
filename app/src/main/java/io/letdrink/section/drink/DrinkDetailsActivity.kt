package io.letdrink.section.drink

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.letdrink.R
import io.letdrink.common.const.Constants
import io.letdrink.common.long.collectOnly
import com.example.thecocktaildb.network.models.Drink
import io.letdrink.common.state.SectionState
import io.letdrink.common.utils.intent
import io.letdrink.features.random.NavBarUi
import io.letdrink.features.random.RandomDrinkState
import kotlinx.android.synthetic.main.activity_drink_details.*
import kotlinx.android.synthetic.main.activity_drink_details.backFab
import kotlinx.android.synthetic.main.activity_drink_details.forwardFab
import kotlinx.android.synthetic.main.fragment_random.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun Activity.getDrinkDetailsActivityIntent(drink: Drink): Intent {
    return intent(DrinkDetailsActivity::class.java) {
        putExtra(Constants.EXTRA.DRINK, drink)
    }
}

@AndroidEntryPoint
@Suppress("UNCHECKED_CAST")
class DrinkDetailsActivity : AppCompatActivity(R.layout.activity_drink_details) {

    private val viewModel: DrinkDetailsViewModel by viewModels()

    private val fragment: DrinkCardFragment by lazy {
        supportFragmentManager.findFragmentByTag("card") as DrinkCardFragment
    }
    private val drink: Drink by lazy {
        intent.getSerializableExtra(Constants.EXTRA.DRINK) as Drink
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        returnFab.setOnClickListener {
            finish()
        }
    }

    override fun onStart() {
        super.onStart()

        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                state.drink?.let { setNew(it) }
                renderDrinkHistoryBar(state.navBarUi)
            }
        }
        viewModel.load(drink)
    }

    private fun setNew(drink: Drink) {
        fragment.setContent(SectionState(content = drink)) {
            viewModel.onSimiliarClick(it)
        }
    }

    private fun renderDrinkHistoryBar(ui: NavBarUi) {

        if (ui.hasNext) {
            forwardFab.show()
            forwardFab.setOnClickListener {
                viewModel.onClickNextDrink()
            }
        } else {
            forwardFab.hide()
        }

        if (ui.hasPrev) {
            backFab.show()
            backFab.setOnClickListener {
                viewModel.onClickPrevDrink()
            }
        } else {
            backFab.hide()
        }

    }
}

fun interface SelectSimiliarDrinkListener {
    fun select(drink: Drink)
}