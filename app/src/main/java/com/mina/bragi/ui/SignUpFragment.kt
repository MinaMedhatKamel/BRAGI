package com.mina.bragi.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mina.bragi.R
import com.mina.bragi.SharedViewModel
import com.mina.bragi.databinding.FragmentSignupBinding
import com.mina.bragi.intent.SharedIntent
import com.mina.bragi.state.ConnectionState
import com.mina.movieslist.effects.SharedEffects
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable

class SignUpFragment : Fragment() {
    private val viewModel: SharedViewModel by activityViewModels()
    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    private var stateDisposable: Disposable? = null
    private val stateConsumer = object : (ConnectionState) -> Unit {
        override fun invoke(it: ConnectionState) {
            showSnackBar(it.newConnectionState.name)
        }
    }

    private var effectsDisposable: Disposable? = null
    private val effectsConsumer = object : (SharedEffects) -> Unit {
        override fun invoke(it: SharedEffects) {
            if (it.equals(SharedEffects.NavigateToForgetPass)) {
                findNavController().navigate(R.id.action_SignUpFragment_to_forgetpass)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        val view = binding.root
        activity?.title = getString(R.string.signup_screen_title)

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