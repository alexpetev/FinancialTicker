package com.alexp.financialsticker.util

import android.content.Context
import com.alexp.financialsticker.R
import com.alexp.financialsticker.model.CompanyStock
import com.alexp.financialsticker.model.DeltasStock
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

object CSVReader{
    fun readSnapshotCSV(context: Context): List<CompanyStock> {
        val inputStreamReader = context.resources.openRawResource(R.raw.snapshot).bufferedReader()

        //remove header
        val header = inputStreamReader.readLine()

        val csvList = inputStreamReader.lineSequence()
            .map{
            val (name,companyName,price,change,changePercent,marketCap) = it.split(',', ignoreCase = false, limit = 6)
                CompanyStock(name,companyName,price.toDouble(),change.toDouble(),
                    changePercent.replace("%","").toDouble(),marketCap)
        }.toList()

        inputStreamReader.close()
        return csvList
    }

    fun readDeltasCSV(context: Context): List<DeltasStock>{
        val inputStreamReader = context.resources.openRawResource(R.raw.deltas).bufferedReader()

        val deltaCSVList = inputStreamReader.lineSequence()
            .map {
                if(it.contains(",")) {
                    val (_, blank, price, change, changePercent) = it.split(',', ignoreCase = false, limit = 5)

                    DeltasStock(0, blank, if (price == "") null else price.toDouble(),
                        if (change == "") null else change.toDouble(),
                        if (changePercent == ",") null else changePercent.replace("%", "")
                            .replace(",", "").toDouble())

                } else {
                    DeltasStock(it.toLong(),"",null,null,null)
                }
            }.toList()
        inputStreamReader.close()
        return deltaCSVList
    }
}

//Extension to allow 6 elements
operator fun <T> List<T>.component6(): T = get(5)
