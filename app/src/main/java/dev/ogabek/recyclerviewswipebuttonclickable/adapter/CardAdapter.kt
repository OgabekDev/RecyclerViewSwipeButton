package dev.ogabek.recyclerviewswipebuttonclickable.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.ogabek.recyclerviewswipebuttonclickable.model.Card
import dev.ogabek.recyclerviewswipebuttonclickable.databinding.CardItemBinding
import java.lang.ref.WeakReference

class CardAdapter: RecyclerView.Adapter<CardAdapter.VH>() {

    private val dif = AsyncListDiffer(this, ITEM_DIFF)
    lateinit var onDeleteClick: () -> Unit

    inner class VH(private val binding: CardItemBinding): RecyclerView.ViewHolder(binding.root) {
        private val view = WeakReference(binding.root)
        fun bind() {

            val card = dif.currentList[adapterPosition]

            view.get()?.let {
                it.setOnClickListener {
                    if (view.get()?.scrollX != 0) {
                        view.get()?.scrollTo(0,0)
                    }
                }
            }

            binding.apply {
                title.text = card.title
                deleteTv.setOnClickListener {
                    onDeleteClick.invoke()
                }
            }

        }
    }

    fun submitList(list: List<Card>) {
        dif.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind()

    override fun getItemCount() = dif.currentList.size

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<Card>() {
            override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean =
                oldItem.title == newItem.title
        }
    }

}