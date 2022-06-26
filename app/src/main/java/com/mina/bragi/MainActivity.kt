package com.mina.bragi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.mina.bragi.databinding.ActivityMainBinding
import com.mina.bragi.databinding.FragmentLoginBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    fun showSnackBar(name: String) {
        Snackbar.make(
            binding.root, name,
            Snackbar.LENGTH_SHORT
        ).show()
    }
}