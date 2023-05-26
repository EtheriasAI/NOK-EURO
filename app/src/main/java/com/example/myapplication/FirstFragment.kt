package com.example.myapplication

import android.R.id.input
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
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
    private var noeu = tauxno;
    private var end="EUR";
    private var text="0EUR";
    private var isEur=false;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        //load data

        //

        var list = ArrayList<Spent>()
        list.add(Spent("44","NOK","resto"))

        val adapter = CustomAdapter(list)
        binding.rc.layoutManager = LinearLayoutManager(requireContext())
        binding.rc.adapter = adapter
        binding.inputno.addTextChangedListener {
            var lint = binding.inputno.text.toString();
            try {
                var value = (lint.toDouble())*noeu
                binding.textViewno.text= String.format("%.2f", value).toString()+end;
            } catch (e: NumberFormatException) {
                binding.textViewno.text= text;
            }

        }
        binding.switch1.setOnCheckedChangeListener{ buttonView, checked ->
            if(checked) {
                isEur=true;
                noeu = taux
                end="NOK"
                text="0NOK"
                binding.inputno.hint="EUR"
                binding.inputno.setText("")
            }else if(!checked){
                isEur=false;
                noeu = tauxno
                end="EUR"
                text="0EUR"
                binding.inputno.hint="NOK"
                binding.inputno.setText("")
            }

        }
        binding.button.setOnClickListener{
            try {
                if(isEur)
                    list.add(Spent(String.format("%.2f", binding.inputno.text.toString().toDouble()),"EUR","see later",))
                else if(!isEur)
                    list.add(Spent(String.format("%.2f", binding.inputno.text.toString().toDouble()),"NOK","see later"))

                binding.rc.adapter=CustomAdapter(list)
            }catch (e: NumberFormatException) {}

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