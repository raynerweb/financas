package br.com.rayner.portugal.financas.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.com.rayner.portugal.financas.R
import br.com.rayner.portugal.financas.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        binding.fragment = this
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvWelcome.text = getString(
            R.string.simulador_financas_bem_vindo,
            String(
                Character.toChars(PORTUGAL_P),
            ),
            String(
                Character.toChars(PORTUGAL_T),
            )
        )
    }

    fun next(view: View) {
        findNavController().navigate(R.id.action_welcomeFragment_to_taxTableFragment)
    }

    companion object {
        var PORTUGAL_P = 0x1F1F5
        var PORTUGAL_T = 0x1F1F9
    }

}