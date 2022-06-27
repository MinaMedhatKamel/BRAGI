package com.mina.bragi.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mina.bragi.MainActivity
import com.mina.bragi.R
import com.mina.bragi.SharedViewModel
import com.mina.bragi.databinding.FragmentLoginBinding
import com.mina.bragi.intent.SharedIntent
import com.mina.movieslist.effects.SharedEffects
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable

class LoginFragment : Fragment() {

    private val viewModel: SharedViewModel by activityViewModels()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        activity?.title = getString(R.string.login_screen_title)

        compositeDisposable.add(
            viewModel.state.observeOn(AndroidSchedulers.mainThread()).subscribe {
                Log.d(" State", it.newConnectionState.name)
            })

        compositeDisposable.add(
            viewModel.effects.observeOn(AndroidSchedulers.mainThread()).subscribe {
                when (it) {
                    is SharedEffects.ShowPop -> (activity as MainActivity).showSnackBar(it.message)
                    SharedEffects.NavigateToSignUp -> findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
                }
            })
        //buttons actions
        //for check connection button it is not clear for me if you want to stop emitting other values of connection options
        //or you want to show one more pop with message sent. but I made the 2 options in other branch we can discuss that on the interview.
        binding.btnCheckConnection.setOnClickListener {
            viewModel.sendAction(SharedIntent.ConnectionCheckClick)
        }

        binding.btnNext.setOnClickListener {
            viewModel.sendAction(SharedIntent.NavigateToSignUp)
        }
        //step2: send command I used a command design pattern with suspending functions of kotlin coroutines
        //and I made the solution with rxjava in the main function of @CommandProcessor file.
        binding.btnSendCommands.setOnClickListener {
            viewModel.sendAction(SharedIntent.SendCommands)
        }

        return view
    }

    //it is not clear for me in the requirements if you want each screen to have its own cycle of the connection flow.
    // if that so end the subscription at onPause and start new one on the onresume of each screen .
    override fun onPause() {
        super.onPause()
        //compositeDisposable.dispose()
        //viewModel.sendAction(SharedIntent.stopConnectionObserving)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

}