package br.com.raynerweb.portugal.financas.ui.fragment

import android.animation.AnimatorInflater
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.raynerweb.portugal.financas.R
import br.com.raynerweb.portugal.financas.databinding.FragmentTaxTableBinding
import br.com.raynerweb.portugal.financas.databinding.ViewTaxFilterBinding
import br.com.raynerweb.portugal.financas.ui.adapter.TaxAdapter
import br.com.raynerweb.portugal.financas.viewmodel.TaxTableViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaxTableFragment : Fragment() {

    private lateinit var binding: FragmentTaxTableBinding
    private val viewModel: TaxTableViewModel by viewModels()

    private lateinit var dialogFilter: BottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaxTableBinding.inflate(inflater, container, false)
        binding.fragment = this
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        subscribe()

        viewModel.getTaxTable()
    }

    private fun setupViews() {
        setupToolbar()
        setupBottomSheetDialog()

        binding.rvTax.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun setupToolbar() {
        binding.actionBar.title = getString(R.string.tax_table)
        binding.actionBar.setTitleTextColor(Color.WHITE)
    }

    private fun setupBottomSheetDialog() {
        val inflater = LayoutInflater.from(requireContext())
        val dialogBinding = ViewTaxFilterBinding.inflate(inflater)
        dialogBinding.fragment = this
        dialogBinding.viewModel = viewModel
        dialogBinding.lifecycleOwner = this

        dialogFilter = BottomSheetDialog(requireContext())
        dialogFilter.setContentView(dialogBinding.root)
    }

    private fun subscribe() {
        viewModel.taxTable.observe(viewLifecycleOwner) {
            binding.rvTax.adapter = TaxAdapter(it) { tax ->
                Log.d("TAX", tax.rendaLimite)
            }
        }
    }

    fun dismisDialog(view: View) {
        dialogFilter.dismiss()
    }

    fun showFilter(view: View) {
        dialogFilter.show()
    }


}