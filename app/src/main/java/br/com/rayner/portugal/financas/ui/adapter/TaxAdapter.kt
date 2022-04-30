package br.com.rayner.portugal.financas.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.rayner.portugal.financas.databinding.ViewTaxBinding
import br.com.rayner.portugal.financas.ui.model.Tax

class TaxAdapter(
    val taxes: List<Tax>,
    val clickListener: (Tax) -> Unit
) : RecyclerView.Adapter<TaxAdapter.TaxViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaxViewHolder {
        val binding = ViewTaxBinding.inflate(LayoutInflater.from(parent.context))
        return TaxViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaxViewHolder, position: Int) {
        holder.bind(taxes[position])
    }

    override fun getItemCount() = taxes.size

    inner class TaxViewHolder(val binding: ViewTaxBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tax: Tax) {
            binding.root.setOnClickListener {
                clickListener.invoke(tax)
            }
            binding.tax = tax
            binding.executePendingBindings()
        }
    }
}