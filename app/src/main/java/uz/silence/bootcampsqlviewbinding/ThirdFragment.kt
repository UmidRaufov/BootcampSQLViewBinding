package uz.silence.bootcampsqlviewbinding

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import uz.silence.bootcampsqlviewbinding.Adapters.RvAdapters
import uz.silence.bootcampsqlviewbinding.Adapters.SpinnerAdapter
import uz.silence.bootcampsqlviewbinding.CLASS.Bootcamp
import uz.silence.bootcampsqlviewbinding.MB.MyDbHelper
import uz.silence.bootcampsqlviewbinding.R
import uz.silence.bootcampsqlviewbinding.databinding.FragmentFirstBinding
import uz.silence.bootcampsqlviewbinding.databinding.FragmentThirdBinding
import uz.silence.bootcampsqlviewbinding.databinding.ItemCustomDialogBinding

class ThirdFragment : Fragment() {

    private var _binding: FragmentThirdBinding? = null
    private val binding get() = _binding!!

    lateinit var rvAdapters: RvAdapters
    lateinit var spinnerAdapter: SpinnerAdapter
    lateinit var basicList: ArrayList<Bootcamp>
    lateinit var spinnerBasicList: ArrayList<String>
    lateinit var myDbHelper: MyDbHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentThirdBinding.inflate(inflater, container, false)

        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myDbHelper = MyDbHelper(requireContext())

        setHasOptionsMenu(true)

        spinnerBasicList = ArrayList()

        spinnerBasicList.add("Asosiy")
        spinnerBasicList.add("Dunyo")
        spinnerBasicList.add("Ijtimoiy")

        basicList = ArrayList()

        val allBasic = myDbHelper.getAllBasic()

        for (bootcamp in allBasic) {

            if (bootcamp.type == spinnerBasicList[2]) {

                basicList.add(bootcamp)

            }

        }


        rvAdapters = RvAdapters(basicList, object : RvAdapters.OnMyItemOnClickListener {
            override fun onItemCLick(bootcamp: Bootcamp, position: Int) {

                val bundle = Bundle()
                bundle.putInt("firstID", bootcamp.id!!)
                bundle.putSerializable("basic", bootcamp)
                bundle.putInt("pos", position)
                bundle.putInt("position", 2)
                findNavController().navigate(R.id.fourthFragment, bundle)

            }

            override fun onMyItemClick(bootcamp: Bootcamp, position: Int, imageView: ImageView) {

                val popupMenu = PopupMenu(context, imageView)
                popupMenu.inflate(R.menu.pop_menu)
                popupMenu.show()
                popupMenu.setOnMenuItemClickListener {

                    when (it.itemId) {
                        R.id.edit -> {

                            val alertDialog = AlertDialog.Builder(context)
                            val dialog = alertDialog.create()
                            val dialogView =
                                ItemCustomDialogBinding.inflate(LayoutInflater.from(context))
                            dialogView.itemTitleTv.text = "O'zgartirish"
                            spinnerAdapter = SpinnerAdapter(spinnerBasicList)
                            dialogView.spinnerDialog.adapter = spinnerAdapter

                            dialogView.nameEt.setText(bootcamp.name)
                            dialogView.descriptionEt.setText(bootcamp.description)
                            dialogView.spinnerDialog.setSelection(2)

                            dialogView.saveText.setOnClickListener {

                                if (spinnerBasicList[dialogView.spinnerDialog.selectedItemPosition].trim()
                                        .isNotEmpty() && dialogView.nameEt.text.trim()
                                        .isNotEmpty() && dialogView.descriptionEt.text.isNotEmpty()
                                ) {

                                    bootcamp.type =
                                        spinnerBasicList[dialogView.spinnerDialog.selectedItemPosition]
                                    bootcamp.name = dialogView.nameEt.text.toString()
                                    bootcamp.description = dialogView.descriptionEt.text.toString()

                                    myDbHelper.updateBasic(bootcamp)
                                    basicList[position] = bootcamp

                                    basicList.clear()

                                    val allBasicList = myDbHelper.getAllBasic()

                                    for (bootcampList in allBasicList) {
                                        if (bootcampList.type == spinnerBasicList[0]) {
                                            basicList.add(bootcampList)
                                        }
                                    }

                                    binding.thirdRv.adapter = rvAdapters
                                    rvAdapters.notifyItemInserted(basicList.size)
                                    rvAdapters.notifyItemChanged(position)
                                    rvAdapters.notifyItemRangeInserted(position, basicList.size)
                                    rvAdapters.notifyItemRangeChanged(position, basicList.size)

                                    dialog.dismiss()
                                    Toast.makeText(
                                        context,
                                        "Changed",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                } else {

                                    Toast.makeText(context, "Enter correctly", Toast.LENGTH_SHORT)
                                        .show()

                                }

                            }

                            dialogView.notText.setOnClickListener {
                                dialog.dismiss()
                            }
                            dialog.setView(dialogView.root)
                            dialog.show()

                        }

                        R.id.delete -> {

                            val alertDialog = AlertDialog.Builder(requireContext())
                            alertDialog.setMessage("Xabarni o'chirmoqchimisiz ?")
                            alertDialog.setPositiveButton("O'chirish",
                                object : DialogInterface.OnClickListener {
                                    override fun onClick(p0: DialogInterface?, p1: Int) {

                                        myDbHelper.deleteBasic(bootcamp)
                                        basicList.remove(bootcamp)
                                        rvAdapters.notifyItemRemoved(position)
                                        rvAdapters.notifyItemRangeRemoved(position, basicList.size)

                                    }

                                })


                            alertDialog.setNegativeButton("Bekor qilish",
                                object : DialogInterface.OnClickListener {
                                    override fun onClick(p0: DialogInterface?, p1: Int) {


                                    }

                                })

                            alertDialog.show()

                        }


                    }

                    true

                }


            }

        })

        binding.thirdRv.adapter = rvAdapters

        rvAdapters.notifyItemInserted(basicList.size)
        rvAdapters.notifyItemChanged(basicList.size)


    }

}