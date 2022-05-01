package br.com.raynerweb.portugal.financas.viewmodel

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.*
import br.com.raynerweb.portugal.financas.R
import br.com.raynerweb.portugal.financas.domain.repository.IRSRepository
import br.com.raynerweb.portugal.financas.domain.repository.model.Imposto
import br.com.raynerweb.portugal.financas.ui.model.Tax
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

    val rendaMaxima = MutableLiveData<String?>()
    val isCasado = MutableLiveData<Boolean?>()
    val isDeficiente = MutableLiveData<Boolean?>()
    val possuiDoisTitulares = MutableLiveData<Boolean?>()
    val qtdeFilhos = MutableLiveData<Int?>()

    private val _impostos = MutableLiveData<List<Imposto>>()
    val taxTable = MediatorLiveData<List<Tax>>()

    init {
        taxTable.addSource(_impostos) {
            prepare(it)
        }
        taxTable.addSource(rendaMaxima) {
            prepare(_impostos.value)
        }
        taxTable.addSource(isCasado) {
            prepare(_impostos.value)
        }
        taxTable.addSource(isDeficiente) {
            prepare(_impostos.value)
        }
        taxTable.addSource(possuiDoisTitulares) {
            prepare(_impostos.value)
        }
        taxTable.addSource(qtdeFilhos) {
            prepare(_impostos.value)
        }
    }

    private fun prepare(
        impostos: List<Imposto>? = emptyList()
    ) {

        val rendaString = rendaMaxima.value ?: "0"
        val renda = if (TextUtils.isEmpty(rendaString)) 0.0 else rendaString.toDouble()
        val informouRenda = renda > 0.0

        impostos?.let {
            var impostosFiltrados = it

            isDeficiente.value?.let { deficiente ->
                impostosFiltrados =
                    impostosFiltrados.filter { imposto -> imposto.deficiente == deficiente }
            }

            isCasado.value?.let { casado ->
                impostosFiltrados =
                    impostosFiltrados.filter { imposto -> imposto.casado == casado }
            }

            possuiDoisTitulares.value?.let { doisTitulares ->
                impostosFiltrados =
                    impostosFiltrados.filter { imposto -> imposto.unicoTitular != doisTitulares }
            }

            qtdeFilhos.value?.let { qtdeFilhos ->
                impostosFiltrados =
                    impostosFiltrados.filter { imposto -> imposto.qtdeFilhos >= qtdeFilhos }
            }

            if (renda > 0.0) {
                impostosFiltrados =
                    mutableListOf(impostosFiltrados.filter { imposto -> renda <= imposto.rendaLimite }
                        .first())
            }

            taxTable.value = impostosFiltrados.map { imposto ->
                val rendaLiquidaInformada = context.getString(
                    R.string.renda_liquida,
                    (renda - (renda * imposto.imposto)).toString()
                )
                val rendaLiquidaPadrao = context.getString(
                    R.string.renda_liquida,
                    (imposto.rendaLimite - (imposto.rendaLimite * imposto.imposto)).toString()
                )
                Tax(
                    doisTitulares = context.getString(
                        R.string.dois_titulares,
                        if (imposto.unicoTitular) "Não" else "Sim"
                    ),
                    rendaLiquida = if (informouRenda)
                        rendaLiquidaInformada else
                        rendaLiquidaPadrao,
                    rendaLimite = context.getString(
                        R.string.renda_ate,
                        imposto.rendaLimite.toString()
                    ),
                    porcentagemImposto = context.getString(
                        R.string.imposto,
                        imposto.porcentagemImposto
                    ),
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

    }

    fun getTaxTable() = viewModelScope.launch {
        _impostos.postValue(irsRepository.getAll())
    }

    fun clearFilter() {
        rendaMaxima.postValue("")
        isCasado.postValue(null)
        isDeficiente.postValue(null)
        possuiDoisTitulares.postValue(null)
        qtdeFilhos.postValue(null)
    }

    fun updateChildren(progress: Int) {
        qtdeFilhos.postValue(progress)
    }

}