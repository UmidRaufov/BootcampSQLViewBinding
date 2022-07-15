package uz.silence.bootcampsqlviewbinding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import uz.silence.bootcampsqlviewbinding.Adapters.SpinnerAdapter
import uz.silence.bootcampsqlviewbinding.CLASS.Bootcamp
import uz.silence.bootcampsqlviewbinding.MB.MyDbHelper
import uz.silence.bootcampsqlviewbinding.databinding.FragmentFirstBinding
import uz.silence.bootcampsqlviewbinding.databinding.FragmentFourthBinding
import uz.silence.bootcampsqlviewbinding.databinding.ItemCustomDialogBinding

class FourthFragment : Fragment() {

    private var _binding: FragmentFourthBinding? = null
    private val binding get() = _binding!!

    lateinit var myDbHelper: MyDbHelper

    lateinit var spinnerAdapter: SpinnerAdapter
    lateinit var list: ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFourthBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myDbHelper = MyDbHelper(requireContext())


        val ID = arguments?.getInt("firstID", -1)

        val bootcamp = arguments?.getSerializable("basic") as Bootcamp

        val position = arguments?.getInt("pos")

        val pos = arguments?.getInt("position")

        list = ArrayList()

        list.add("Asosiy")
        list.add("Dunyo")
        list.add("Ijtimoiy")

        val byId = myDbHelper.getBasicById(ID!!)

        binding.tv1.text = byId.name
        binding.tv2.text = byId.description


        val allBasic = myDbHelper.getAllBasic()


        binding.back.setOnClickListener {

            findNavController().popBackStack()

        }

        binding.floatingId.setOnClickListener {

            val alertDialog = AlertDialog.Builder(requireContext())
            val dialog = alertDialog.create()
            val dialogView =
                ItemCustomDialogBinding.inflate(LayoutInflater.from(requireContext()), null, false)

            dialogView.itemTitleTv.text = "O'zgartirish"

            spinnerAdapter = SpinnerAdapter(list)
            dialogView.spinnerDialog.adapter = spinnerAdapter

            dialogView.nameEt.setText(bootcamp.name)
            dialogView.descriptionEt.setText(bootcamp.description)
            dialogView.spinnerDialog.setSelection(pos!!)


            dialogView.saveText.setOnClickListener {

                val type = list[dialogView.spinnerDialog.selectedItemPosition]
                val name = dialogView.nameEt.text.toString().trim()
                val descriptions = dialogView.descriptionEt.text.toString().trim()

                if (name.isNotEmpty() && type.isNotEmpty() && descriptions.isNotEmpty()){


                    bootcamp.type=list[dialogView.spinnerDialog.selectedItemPosition]
                    bootcamp.name=dialogView.nameEt.text.toString().trim()
                    bootcamp.description=dialogView.descriptionEt.text.toString().trim()

                    myDbHelper.updateBasic(bootcamp)
                    allBasic[position!!] = bootcamp

                    binding.tv1.text = "${bootcamp.name}"
                    binding.tv2.text = "${bootcamp.description}"

                    dialog.dismiss()
                    Toast.makeText(requireContext(), "Changed", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireContext(), "Enter correctly", Toast.LENGTH_SHORT).show()
                }
            }


            dialogView.notText.setOnClickListener {
                dialog.dismiss()
            }
            dialog.setView(dialogView.root)
            dialog.show()

        }


    }


    override fun onResume() {
        super.onResume()

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

    }

    override fun onStop() {
        super.onStop()

        (activity as AppCompatActivity?)!!.supportActionBar!!.show()

    }

}