package com.example.near.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.near.R
import com.example.near.databinding.FragmentBottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetDialogFragment(val itemClick: (Int) -> Unit) : BottomSheetDialogFragment() {
    lateinit var binding : FragmentBottomSheetDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_sheet_dialog, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEvents()
        setValues()
    }
    fun setupEvents() {
        binding.CartBtn.setOnClickListener {
            itemClick(0)
            dialog?.dismiss()
        }
        binding.buyBtn.setOnClickListener {
            itemClick(1)
            dialog?.dismiss()
        }
    }

    fun setValues() {

    }
}