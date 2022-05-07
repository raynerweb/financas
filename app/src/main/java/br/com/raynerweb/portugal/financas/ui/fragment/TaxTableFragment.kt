package br.com.raynerweb.portugal.financas.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.widget.SwitchCompat
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

        dialogBinding.qtdeFilhos.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                dialogBinding.tvQtdeFilhos.text = progress.toString()
                viewModel.updateChildren(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        dialogFilter = BottomSheetDialog(requireContext())
        dialogFilter.setContentView(dialogBinding.root)

    }

    private fun subscribe() {
        viewModel.taxTable.observe(viewLifecycleOwner) {
            binding.rvTax.adapter = TaxAdapter(it) { tax ->
                Log.d("TAX", tax.rendaLimite)
            }
        }

        viewModel.isCasado.observe(viewLifecycleOwner) { isCasado ->
            dialogFilter.findViewById<SwitchCompat>(R.id.switch_two_holders)?.apply {
                isChecked = false
                isEnabled = isCasado
            }
        }

    }

    fun dismisDialog(view: View) {
        dialogFilter.dismiss()
    }

    fun clearFilter(view: View) {
        viewModel.clearFilter()
        dialogFilter.dismiss()
    }

    fun showFilter(view: View) {
        dialogFilter.show()
    }


}