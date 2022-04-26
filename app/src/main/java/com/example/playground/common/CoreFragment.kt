package com.example.playground.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.playground.extensions.handleOnBackPressed

abstract class CoreFragment<B : ViewDataBinding>(private val inflateMethod: (LayoutInflater, ViewGroup?, Boolean) -> B) :
    Fragment() {

    private var _binding: B? = null

    protected val binding: B
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflateMethod.invoke(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        setupViews()
        setupObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleOnBackPressed {
            onBackPressed()
        }

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

    protected fun onBackPressed() {
        val succeed = onReturnPreviousPage()
        if (!succeed) requireActivity().finish()
    }

    protected open fun onReturnPreviousPage(): Boolean {
        return findNavController().popBackStack()
    }
}
