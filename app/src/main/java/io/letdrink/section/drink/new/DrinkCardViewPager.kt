package io.letdrink.section.drink.new

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import io.letdrink.R
import io.letdrink.section.drink.DrinkCardFragment

class DrinkCardViewPager : Fragment(R.layout.fragment_item_page) {


}

class ViewPagerFragmentStateAdapter(private val pager: DrinkCardViewPager) :
    FragmentStateAdapter(pager) {

    override fun createFragment(position: Int): Fragment {
        return DrinkCardFragment()
    }

    override fun getItemCount(): Int = 3
}