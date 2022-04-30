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

    val rendaMaxima = MutableLiveData<String>()
    val isCasado = MutableLiveData<Boolean>()
    val isDeficiente = MutableLiveData<Boolean>()

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
                    impostosFiltrados.filter { imposto -> imposto.deficiente == casado }
            }

            if (renda > 0.0) {
                impostosFiltrados =
                    mutableListOf(impostosFiltrados.filter { imposto -> renda <= imposto.rendaLimite }
                        .first())
            }

            taxTable.value = impostosFiltrados.map { imposto ->
                Tax(
                    rendaLiquida = if (informouRenda)
                        (renda - (renda * imposto.imposto)).toString() else
                        (imposto.rendaLimite - (imposto.rendaLimite * imposto.imposto)).toString(),
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

}