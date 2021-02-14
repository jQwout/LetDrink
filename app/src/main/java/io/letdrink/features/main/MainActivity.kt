package io.letdrink.features.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commitNow
import dagger.hilt.android.AndroidEntryPoint
import io.letdrink.R
import io.letdrink.features.favorites.FavoritesFragment
import io.letdrink.features.random.RandomFragment2
import io.letdrink.section.search.featured.FeaturedFragment
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    companion object {
        fun getIntent(context: Context) = Intent(context, MainActivity::class.java)
    }

    private val randomFragment: RandomFragment2 by lazy {
        RandomFragment2()
    }

    private val favoritesFragment: FavoritesFragment by lazy {
        FavoritesFragment()
    }

    private val searchFragment: FeaturedFragment by lazy {
        FeaturedFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.commitNow {
            add(R.id.mainContainer, favoritesFragment)
            add(R.id.mainContainer, randomFragment)
            add(R.id.mainContainer, searchFragment)
        }

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_random -> {
                    supportFragmentManager.commitNow {
                        show(randomFragment)
                        hide(favoritesFragment)
                        hide(searchFragment)
                    }
                    true
                }
                R.id.action_favorites -> {
                    supportFragmentManager.commitNow {
                        show(favoritesFragment)
                        hide(randomFragment)
                        hide(searchFragment)
                    }
                    true
                }
                R.id.action_search -> {
                    supportFragmentManager.commitNow {
                        hide(favoritesFragment)
                        hide(randomFragment)
                        show(searchFragment)
                    }
                    true
                }
                else -> true
            }
        }

        bottom_navigation.selectedItemId = R.id.action_random
    }

}