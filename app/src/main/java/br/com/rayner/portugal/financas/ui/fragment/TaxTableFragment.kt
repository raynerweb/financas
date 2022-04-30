package br.com.rayner.portugal.financas.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.rayner.portugal.financas.databinding.FragmentTaxTableBinding
import br.com.rayner.portugal.financas.ui.adapter.TaxAdapter
import br.com.rayner.portugal.financas.viewmodel.TaxTableViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaxTableFragment : Fragment() {

    private lateinit var binding: FragmentTaxTableBinding
    private val viewModel: TaxTableViewModel by viewModels()

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
        binding.rvTax.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun subscribe() {
        viewModel.taxTable.observe(viewLifecycleOwner) {
            binding.rvTax.adapter = TaxAdapter(it) { tax ->
                Log.d("TAX", tax.rendaLimite)
            }
        }
    }

}