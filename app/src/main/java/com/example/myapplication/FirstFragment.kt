package com.example.myapplication

import android.R.id.input
import android.R.id.list
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentFirstBinding
import java.io.File
import kotlin.math.log
import android.util.Log


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

        var list = lecture()

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
                ecriture(list)
            }catch (e: NumberFormatException) {}

        }

        return binding.root

    }
    private fun ecriture(list:ArrayList<Spent>){
        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val filePath = File(downloadsDir, "nom_du_fichier.csv").absolutePath
        val csvContent = StringBuilder()
        val data: ArrayList<List<String>> = arrayListOf()

        for (e in list) {
            val personData: List<String> = listOf(e.price, e.unity,e.reason)
            data.add(personData)
        }


        // Parcours des lignes de données
        for (row in data) {
            // Parcours des colonnes de chaque ligne
            for (column in row) {
                csvContent.append("\"").append(column.replace("\"", "\"\"")).append("\",")
            }
            csvContent.deleteCharAt(csvContent.length - 1) // Supprimer la virgule finale
            csvContent.append("\n") // Nouvelle ligne
        }

        // Écriture du contenu CSV dans le fichier
        try {
            File(filePath).writeText(csvContent.toString())
        } catch (e: Exception) {
            // Gérer les erreurs de sauvegarde
        }
    }
    private fun lecture() :ArrayList<Spent> {
        var finalval = ArrayList<Spent>()
        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val filePath = File(downloadsDir, "nom_du_fichier.csv").absolutePath

        try {
            val csvContent = File(filePath).readText()
            val rows = csvContent.split("\n")

            for (row in rows) {
                var column = row.split(",").map { it.replace("\"\"", "\"") }
                column = column.map { element ->
                    element.replace("\"", "")
                }
                finalval.add(Spent(column.get(0),column.get(1),column.get(2)))
            }

        } catch (e: Exception) {
            Log.i("test",e.toString());
        }
        return finalval
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}