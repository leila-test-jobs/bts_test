package com.example.bitcointest.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bitcointest.R
import com.example.bitcointest.databinding.NetworkListItemsBinding
import com.example.bitcointest.model.Network
import com.example.bitcointest.viewmodel.MainViewModel

class NetworkAdapter(private val viewModel: MainViewModel, private val listener: OnItemClickListener): ListAdapter<Network, NetworkAdapter.NetworkViewHolder>(NetworkUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NetworkViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NetworkListItemsBinding.inflate(inflater)
        return NetworkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NetworkViewHolder, position: Int) {
        holder.bind(getItem(position), viewModel)
    }

    inner class NetworkViewHolder(
        private val binding: NetworkListItemsBinding
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        private lateinit var net:Network

        init {
            itemView.setOnClickListener(this)
        }


        fun bind(network: Network, viewModel: MainViewModel) {
            binding.network = network
            net = network
            binding.viewModel = viewModel
            binding.networkBtn.setOnClickListener {
                it?.setBackgroundColor(Color.CYAN)
            }
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position, net)
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int, network: Network)
    }

}

class NetworkUtil(): DiffUtil.ItemCallback<Network>(){
    override fun areItemsTheSame(oldItem: Network, newItem: Network): Boolean {
        return oldItem.has_tag == newItem.has_tag
    }

    override fun areContentsTheSame(oldItem: Network, newItem: Network): Boolean {
        return newItem == oldItem
    }

}