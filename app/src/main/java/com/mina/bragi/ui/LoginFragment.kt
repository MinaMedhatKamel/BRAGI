package com.mina.bragi.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mina.bragi.R
import com.mina.bragi.SharedViewModel
import com.mina.bragi.databinding.FragmentLoginBinding
import com.mina.bragi.intent.SharedIntent
import com.mina.bragi.state.ConnectionState
import com.mina.movieslist.effects.SharedEffects
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

class LoginFragment : Fragment() {

    private val viewModel: SharedViewModel by activityViewModels()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private var stateDisposable: Disposable? = null
    private val stateConsumer = object : (ConnectionState) -> Unit {
        override fun invoke(it: ConnectionState) {
            Log.d("snackbar State", it.newConnectionState.name)
            showSnackBar(it.newConnectionState.name)
        }
    }

    private var effectsDisposable: Disposable? = null
    private val effectsConsumer = object : (SharedEffects) -> Unit {
        override fun invoke(it: SharedEffects) {
            if (it.equals(SharedEffects.NavigateToSignUp)) {
                findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        activity?.title = getString(R.string.login_screen_title)
        stateDisposable =
            viewModel.state.observeOn(AndroidSchedulers.mainThread()).subscribe(stateConsumer)
        effectsDisposable =
            viewModel.effects.observeOn(AndroidSchedulers.mainThread()).subscribe(effectsConsumer)
        binding.btnCheckConnection.setOnClickListener {
            viewModel.sendAction(SharedIntent.ApplyEstablishedConnectionFilter)
        }

        binding.btnNext.setOnClickListener {
            viewModel.sendAction(SharedIntent.NavigateToSignUp)
        }

        binding.btnSendCommands.setOnClickListener {
            viewModel.sendAction(SharedIntent.SendCommands)
        }

        return view
    }

    private fun showSnackBar(name: String) {
        Snackbar.make(
            binding.root, name,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        stateDisposable?.dispose()
        effectsDisposable?.dispose()
    }
}