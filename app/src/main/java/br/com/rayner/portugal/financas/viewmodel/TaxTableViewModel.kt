package br.com.rayner.portugal.financas.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import br.com.rayner.portugal.financas.R
import br.com.rayner.portugal.financas.domain.repository.IRSRepository
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
    private val _taxTable = MutableLiveData<List<Tax>>()
    val taxTable: LiveData<List<Tax>> get() = _taxTable

    fun getTaxTable() = viewModelScope.launch {
        _taxTable.postValue(
            irsRepository.getAll().map { imposto ->
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
        )
    }

}