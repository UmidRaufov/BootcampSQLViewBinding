package uz.silence.bootcampsqlviewbinding.Fragments

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import uz.silence.bootcampsqlviewbinding.Adapters.BootcampPagerAdapter
import uz.silence.bootcampsqlviewbinding.Adapters.RvAdapters
import uz.silence.bootcampsqlviewbinding.Adapters.SpinnerAdapter
import uz.silence.bootcampsqlviewbinding.CLASS.Bootcamp
import uz.silence.bootcampsqlviewbinding.MB.MyDbHelper
import uz.silence.bootcampsqlviewbinding.R
import uz.silence.bootcampsqlviewbinding.databinding.FragmentHomeBinding
import uz.silence.bootcampsqlviewbinding.databinding.ItemCustomDialogBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    lateinit var spinnerAdapter: SpinnerAdapter
    lateinit var list: ArrayList<String>
    lateinit var bootcampPagerAdapter: BootcampPagerAdapter

    lateinit var rvAdapters: RvAdapters

    lateinit var myDbHelper: MyDbHelper

    var positionViewPager = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentHomeBinding.inflate(inflater)

        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myDbHelper = MyDbHelper(requireContext())

        setHasOptionsMenu(true)

        list = ArrayList()

        list.add("Asosiy")
        list.add("Dunyo")
        list.add("Ijtimoiy")


        bootcampPagerAdapter = BootcampPagerAdapter(list, childFragmentManager)
        binding.viewPager.adapter = bootcampPagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)

        binding.tabLayout.setTabTextColors(R.color.white, Color.WHITE)

        bootcampPagerAdapter.notifyDataSetChanged()

        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                positionViewPager = position
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        Toast.makeText(context, "MENUUUU", Toast.LENGTH_SHORT).show()

        when (item.itemId) {

            R.id.add -> {

                val alertDialog = AlertDialog.Builder(context, R.style.NewDialog)
                val dialog = alertDialog.create()
                val dialogView = ItemCustomDialogBinding.inflate(LayoutInflater.from(context))

                spinnerAdapter = SpinnerAdapter(list)
                dialogView.spinnerDialog.adapter = spinnerAdapter

                dialogView.saveText.setOnClickListener {

                    if (list[dialogView.spinnerDialog.selectedItemPosition].trim()
                            .isNotEmpty() && dialogView.nameEt.text.trim()
                            .isNotEmpty() && dialogView.descriptionEt.text.isNotEmpty()
                    ) {

                        val bootcamp = Bootcamp(
                            list[dialogView.spinnerDialog.selectedItemPosition],
                            dialogView.nameEt.text.toString(),
                            dialogView.descriptionEt.text.toString()
                        )

                        Toast.makeText(requireContext(), "${dialogView.nameEt.text}", Toast.LENGTH_SHORT).show()

                        myDbHelper.addBasic(bootcamp)

                        dialogView.nameEt.setText("")
                        dialogView.descriptionEt.setText("")

                        bootcampPagerAdapter = BootcampPagerAdapter(list, childFragmentManager)
                        binding.viewPager.adapter = bootcampPagerAdapter
                        binding.tabLayout.setupWithViewPager(binding.viewPager)
                        binding.viewPager.currentItem = positionViewPager



                        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()

                    } else {
                        Toast.makeText(context, "Enter correctly", Toast.LENGTH_SHORT).show()
                    }

                }

                dialogView.notText.setOnClickListener {

                    dialog.dismiss()

                }

                dialog.setView(dialogView.root)
                dialog.show()

            }

        }

        return super.onOptionsItemSelected(item)
    }

}