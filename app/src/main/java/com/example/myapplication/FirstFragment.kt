package com.example.myapplication

import android.R.id.input
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentFirstBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var taux=11.81
    private var tauxno=0.085

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        binding.inputno.addTextChangedListener {
            var lint = binding.inputno.text.toString();
            try {
                var value = (lint.toDouble())*tauxno
                binding.textViewno.text= String.format("%.2f", value).toString()+"EUR";
            } catch (e: NumberFormatException) {
                binding.textViewno.text= "OEUR";
            }

        }
        binding.inputeu.addTextChangedListener {
            var lint = binding.inputeu.text.toString();
            if(!lint.isEmpty())
                binding.textVieweu.text= ((lint.toInt())*taux).toString()+"NOK";
            else
                binding.textVieweu.text= "0NOK";
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}