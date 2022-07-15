package uz.silence.bootcampsqlviewbinding.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import uz.silence.bootcampsqlviewbinding.FirstFragment
import uz.silence.bootcampsqlviewbinding.SecondFragment
import uz.silence.bootcampsqlviewbinding.ThirdFragment

class BootcampPagerAdapter(var list: ArrayList<String>, fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(
        fragmentManager,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Fragment {

        when (position) {

            0 -> {
                return FirstFragment()
            }

            1 -> {
                return SecondFragment()
            }

            2 -> {
                return ThirdFragment()
            }

        }

        return FirstFragment()

    }

    override fun getPageTitle(position: Int): CharSequence? {
        return list[position]
    }

}