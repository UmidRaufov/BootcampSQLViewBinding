package uz.silence.bootcampsqlviewbinding.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import uz.silence.bootcampsqlviewbinding.CLASS.Bootcamp
import uz.silence.bootcampsqlviewbinding.databinding.ItemRvBinding

class RvAdapters(
    var list: ArrayList<Bootcamp>,
    var onMyItemOnClickListener: OnMyItemOnClickListener
) : RecyclerView.Adapter<RvAdapters.Vh>() {

    inner class Vh(var itemRvBinding: ItemRvBinding) : RecyclerView.ViewHolder(itemRvBinding.root) {

        fun onBind(bootcamp: Bootcamp, position: Int) {

            itemRvBinding.tv1.text = bootcamp.name
            itemRvBinding.tv2.text = list[position].description

            itemRvBinding.img.setOnClickListener {

                onMyItemOnClickListener.onMyItemClick(bootcamp, position, itemRvBinding.img)

            }

            itemRvBinding.root.setOnClickListener {

                onMyItemOnClickListener.onItemCLick(bootcamp, position)

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {

        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: Vh, position: Int) {

        holder.onBind(list[position], position)

    }

    override fun getItemCount(): Int {

        return list.size

    }

    interface OnMyItemOnClickListener {
        fun onItemCLick(bootcamp: Bootcamp, position: Int)
        fun onMyItemClick(bootcamp: Bootcamp, position: Int, imageView: ImageView)
    }

}