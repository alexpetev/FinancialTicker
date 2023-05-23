package com.alexp.financialsticker.ui.stockgrid

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexp.financialsticker.databinding.FragmentGridBinding
import com.alexp.financialsticker.ui.stockgrid.adapters.CompanyStockGridRVAdapter
import com.alexp.financialsticker.util.CSVReader
import com.alexp.financialsticker.util.RVCompanyStockItemAnimator

class GridFragment : Fragment() {

    private lateinit var viewModel: GridViewModel
    private lateinit var binding: FragmentGridBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGridBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[GridViewModel::class.java]

        val companyStocks = CSVReader.readSnapshotCSV(requireContext()).toMutableList()
        val deltasStock = CSVReader.readDeltasCSV(requireContext())

        binding.gridTable.setupTable(companyStocks)
        binding.gridTable.updateTable(deltasStock)
    }
}