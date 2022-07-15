package uz.silence.bootcampsqlviewbinding.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import uz.silence.bootcampsqlviewbinding.databinding.ItemSpinnerBinding

class SpinnerAdapter(var list: ArrayList<String>) : BaseAdapter() {
    override fun getCount(): Int {

        return list.size

    }

    override fun getItem(p0: Int): Any {

        return list[p0]

    }

    override fun getItemId(p0: Int): Long {

        return p0.toLong()

    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

        var basicViewHolder: BasicViewHolder

        if (p1 == null) {

            val binding = ItemSpinnerBinding.inflate(LayoutInflater.from(p2!!.context))
            basicViewHolder = BasicViewHolder(binding)

        } else {
            basicViewHolder = BasicViewHolder(ItemSpinnerBinding.bind(p1))
        }

        basicViewHolder.itemSpinnerBinding.textSpinner.text = list[p0]

        return basicViewHolder.itemView

    }

    inner class BasicViewHolder {

        var itemView: View
        var itemSpinnerBinding: ItemSpinnerBinding

        constructor(itemSpinnerBinding: ItemSpinnerBinding) {
            itemView = itemSpinnerBinding.root
            this.itemSpinnerBinding = itemSpinnerBinding
        }
    }

}