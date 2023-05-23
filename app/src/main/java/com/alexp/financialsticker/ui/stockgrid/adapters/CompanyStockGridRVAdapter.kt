package com.alexp.financialsticker.ui.stockgrid.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexp.financialsticker.databinding.GridRowBinding
import com.alexp.financialsticker.model.CompanyStock

class CompanyStockGridRVAdapter(private var rows: List<CompanyStock>): RecyclerView.Adapter<CompanyStockGridRVAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = GridRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return rows.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val itemRow = rows[position]
        holder.bindRow(itemRow)
    }

    inner class Holder(val binding: GridRowBinding): RecyclerView.ViewHolder(binding.root){
        fun bindRow(companyStock: CompanyStock){
            binding.tvName.text = companyStock.name
            binding.tvCompanyName.text = companyStock.companyName
            binding.tvPrice.text = companyStock.price.toString()
            binding.tvChange.text = companyStock.change.toString()
            binding.tvChangePercent.text = "${companyStock.changePercent}%"
            binding.tvMarketCap.text = companyStock.marketCap
        }
    }
}