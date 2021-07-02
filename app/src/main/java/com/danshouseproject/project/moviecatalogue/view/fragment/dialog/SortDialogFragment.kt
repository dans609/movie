package com.danshouseproject.project.moviecatalogue.view.fragment.dialog

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.DialogFragment
import com.danshouseproject.project.moviecatalogue.R
import com.danshouseproject.project.moviecatalogue.databinding.FragmentSortDialogBinding
import com.danshouseproject.project.moviecatalogue.helper.utils.SortUtils
import java.lang.ClassCastException


class SortDialogFragment : DialogFragment(), View.OnClickListener {
    private var _optionDialog: OnOptionDialogListener? = null
    private val optionDialogListener get() = _optionDialog

    private var _binding: FragmentSortDialogBinding? = null
    private val binding
        get() = _binding

    interface OnOptionDialogListener { fun onOptionChosen(text: String?) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSortDialogBinding.inflate(inflater, container, false)
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            binding?.dialogForm?.also {
                it.btnChoose.setOnClickListener(this)
                it.btnClose.setOnClickListener(this)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            _optionDialog = activity as OnOptionDialogListener?
        } catch (e: ClassCastException) {
            Log.e(TAG, "onAttach: " + e.message)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_choose -> whenRadioChecked()
            R.id.btn_close -> dialog?.cancel()
        }
    }

    private fun whenRadioChecked() {
        binding?.dialogForm?.let {
            val checkedRadioId = it.rgOptions.checkedRadioButtonId
            if (checkedRadioId != -1) {
                val sort: String? = when (checkedRadioId) {
                    R.id.rb_sort_id_asc -> SortUtils.SORT_ID_ASC
                    R.id.rb_sort_id_desc -> SortUtils.SORT_ID_DES
                    R.id.rb_sort_high_score -> SortUtils.HIGH_RANK
                    R.id.rb_sort_low_score -> SortUtils.LOW_RANK
                    R.id.rb_sort_fast_runtime -> SortUtils.FASTEST_RUNTIME
                    R.id.rb_sort_long_runtime -> SortUtils.LONGEST_RUNTIME
                    else -> null
                }
                if (!(sort.isNullOrBlank())) optionDialogListener?.onOptionChosen(sort)
                dialog?.dismiss()
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        _optionDialog = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val TAG = SortDialogFragment::class.java.simpleName
    }
}