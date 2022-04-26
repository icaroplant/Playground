package com.example.playground.common

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.example.playground.extensions.adjustResize
import com.example.playground.extensions.hideKeyboard
import com.example.playground.extensions.setStateExpanded
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class CoreBottomSheet<B : ViewDataBinding>(private val inflateMethod: (LayoutInflater, ViewGroup?, Boolean) -> B) :
    BottomSheetDialogFragment() {

    private var _binding: B? = null

    protected val binding: B
        get() = _binding!!

    @Deprecated("Use instead @{setupObservers} and @{setupViews}")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflateMethod.invoke(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        setupObservers()
        setupViews()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        afterSetup()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    open fun setupObservers() {
        // ignore
    }

    open fun setupViews() {
        // ignore
    }

    open fun afterSetup() {
        // ignore
    }

    override fun onStart() {
        super.onStart()

        adjustResize()
        setStateExpanded()
    }

    override fun onDismiss(dialog: DialogInterface) {
        view?.hideKeyboard()

        super.onDismiss(dialog)
    }
}
