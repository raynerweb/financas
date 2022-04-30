package br.com.rayner.portugal.financas.viewmodel

import android.app.Application
import android.content.Context
import android.text.TextUtils
import androidx.lifecycle.*
import br.com.rayner.portugal.financas.R
import br.com.rayner.portugal.financas.domain.repository.IRSRepository
import br.com.rayner.portugal.financas.domain.repository.model.Imposto
import br.com.rayner.portugal.financas.ui.model.Tax
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaxTableViewModel @Inject constructor(
    private val application: Application,
    private val irsRepository: IRSRepository
) : ViewModel() {

    protected val context
        get() = application.applicationContext

    val rendaMaxima = MutableLiveData<String>()

    private val _impostos = MutableLiveData<List<Imposto>>()
    private val _taxTable = MutableLiveData<List<Tax>>()

    //    val taxTable: LiveData<List<Tax>> get() = _taxTable
    val taxTable = MediatorLiveData<List<Tax>>()

    init {
        taxTable.addSource(_impostos) {
            prepare(it)
        }
        taxTable.addSource(rendaMaxima) {
            prepare(_impostos.value)
        }
    }

    private fun prepare(
        impostos: List<Imposto>?
    ) {
        val rendaString = rendaMaxima.value ?: "0"
        val renda = if (TextUtils.isEmpty(rendaString)) 0.0 else rendaString.toDouble()

        if (renda > 0.0) {
            taxTable.value =
                impostos?.filter { imposto -> renda <= imposto.rendaLimite }?.map { imposto ->
                    Tax(
                        rendaLimite = context.getString(
                            R.string.renda_ate,
                            imposto.rendaLimite.toString()
                        ),
                        porcentagemImposto = imposto.porcentagemImposto.toString(),
                        qtdeFilhos = context.getString(
                            R.string.qtde_filhos,
                            imposto.qtdeFilhos.toString()
                        ),
                        deficiente = context.getString(
                            R.string.deficiencia,
                            if (imposto.deficiente) "Sim" else "Não"
                        ),
                        casado = context.getString(
                            R.string.casado,
                            if (imposto.casado) "Sim" else "Não"
                        )
                    )
                }
        } else {
            taxTable.value = impostos?.map { imposto ->
                Tax(
                    rendaLimite = context.getString(
                        R.string.renda_ate,
                        imposto.rendaLimite.toString()
                    ),
                    porcentagemImposto = imposto.porcentagemImposto.toString(),
                    qtdeFilhos = context.getString(
                        R.string.qtde_filhos,
                        imposto.qtdeFilhos.toString()
                    ),
                    deficiente = context.getString(
                        R.string.deficiencia,
                        if (imposto.deficiente) "Sim" else "Não"
                    ),
                    casado = context.getString(
                        R.string.casado,
                        if (imposto.casado) "Sim" else "Não"
                    )
                )
            }
        }

//        var orderedList = list.sortedBy { imposto -> imposto.rendaLimite }
//        val filter = _filter.value ?: ""
//        if (filter.isNotBlank()) {
//            orderedList = orderedList.filter { it.name.contains(filter, ignoreCase = true) }
//        }
//        val sortSelect = sort.value ?: SortSelect.ASC
//        if (sortSelect == SortSelect.DESC) {
//            orderedList = orderedList.reversed()
//        }
//        pokemons.value = orderedList
    }

    fun getTaxTable() = viewModelScope.launch {
        _impostos.postValue(irsRepository.getAll())
    }

}