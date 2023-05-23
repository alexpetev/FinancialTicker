package com.alexp.financialsticker.ui.stockgrid.components

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexp.financialsticker.databinding.GridTableViewBinding
import com.alexp.financialsticker.model.CompanyStock
import com.alexp.financialsticker.model.DeltasStock
import com.alexp.financialsticker.ui.stockgrid.adapters.CompanyStockGridRVAdapter
import com.alexp.financialsticker.util.CSVReader
import com.alexp.financialsticker.util.RVCompanyStockItemAnimator

class GridTableView(context: Context, attrs: AttributeSet? = null): LinearLayout(context, attrs) {

    private val binding = GridTableViewBinding.inflate(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
        .also { addView(it.root) }

    private lateinit var companyStockAdapter: CompanyStockGridRVAdapter
    private var companyStocks = mutableListOf<CompanyStock>()

    fun setupTable(stocks: MutableList<CompanyStock>){
        companyStocks = stocks
        companyStockAdapter = CompanyStockGridRVAdapter(companyStocks)

        binding.rvGrid.adapter = companyStockAdapter
        binding.rvGrid.layoutManager = LinearLayoutManager(context)
    }

    fun updateTable(deltaStocks: List<DeltasStock>){
        binding.rvGrid.itemAnimator = RVCompanyStockItemAnimator()

        var companyCount = 0
        var deltaCount = 0

        val handler = Handler()
        val runnable = object :Runnable {
            override fun run() {

                //get current delta
                val delta = deltaStocks[deltaCount]

                //Sleep != 0 when there's only 1 number in csv row, so sleep
                if (delta.sleepTime != 0L) {
                    Toast.makeText(
                        context,
                        "Sleeping for ${delta.sleepTime} ms",
                        Toast.LENGTH_SHORT
                    ).show()
                    Thread.sleep(delta.sleepTime)

                    //companies have been cycled, return to start of loop
                    companyCount = 0
                } else {
                    val companyStockToChange = companyStocks[companyCount]
                    //flag to show if price should be animated green or red
                    var isIncreased = false

                    if (delta.price != null) {
                        if(delta.price>companyStockToChange.price){
                            isIncreased = true
                        }
                        companyStockToChange.price = delta.price
                    }
                    if (delta.change != null)
                        companyStockToChange.change = delta.change
                    if (delta.changePercent != null)
                        companyStockToChange.changePercent = delta.changePercent

                    //propagate changes to recyclerview
                    companyStocks[companyCount] = companyStockToChange
                    if(delta.price!=null)
                        companyStockAdapter.notifyItemChanged(companyCount, isIncreased)

                    //move to next company
                    companyCount++
                }

                //move to next delta. If this is the last delta, move to start
                if (deltaCount == deltaStocks.size - 1) {
                    deltaCount = 0
                } else {
                    deltaCount++
                }
                //Repeat task every second
                handler.postDelayed(this, 1000)
            }
        }
        handler.post(runnable)
    }

}