package me.xh.wp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import me.xh.wp.databinding.RvFooterBinding

class RvFooterAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<RvFooterAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvFooterBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, retry)
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    class ViewHolder(
        private val binding: RvFooterBinding, private val retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            (itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams).apply {
                isFullSpan = true
            }
            binding.btnRetry.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                btnRetry.isVisible = loadState is LoadState.Error
                pb.isVisible = loadState is LoadState.Loading
            }
        }
    }
}