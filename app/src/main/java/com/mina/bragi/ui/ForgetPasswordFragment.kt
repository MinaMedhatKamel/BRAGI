package com.mina.bragi.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.mina.bragi.MainActivity
import com.mina.bragi.R
import com.mina.bragi.SharedViewModel
import com.mina.bragi.databinding.FragmentForgetpasswordBinding
import com.mina.bragi.intent.SharedIntent
import com.mina.movieslist.effects.SharedEffects
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable

class ForgetPasswordFragment : Fragment() {
    private val viewModel: SharedViewModel by activityViewModels()
    private var _binding: FragmentForgetpasswordBinding? = null
    private val binding get() = _binding!!

    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForgetpasswordBinding.inflate(inflater, container, false)
        val view = binding.root
        activity?.title = getString(R.string.forget_password_title)

        compositeDisposable.add(
            viewModel.state.observeOn(AndroidSchedulers.mainThread()).subscribe {
                Log.d(" State", it.newConnectionState.name)
            })

        compositeDisposable.add(
            viewModel.effects.observeOn(AndroidSchedulers.mainThread()).subscribe {
                when (it) {
                    is SharedEffects.ShowPop -> (activity as MainActivity).showSnackBar(it.message)
                }
            })
        //buttons actions
        binding.btnCheckConnection.setOnClickListener {
            viewModel.sendAction(SharedIntent.ConnectionCheckClick)
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}