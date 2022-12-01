package com.pannawat.weatherforecast.base

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.pannawat.weatherforecast.R
import com.pannawat.weatherforecast.extension.getErrorResponse
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseFragment(@LayoutRes contentLayoutId: Int) :
    Fragment(contentLayoutId) {
    companion object {
        @JvmStatic
        private val TAG = BaseFragment::class.java.simpleName
    }

    val context: Context
        @JvmName("getNonNullContext")
        get() = requireContext()

    val activity: FragmentActivity
        @JvmName("getNonNullActivity")
        get() = requireActivity()

    open val viewModelList: List<ViewModel>
        get() = mutableListOf()

    private lateinit var dialog: Dialog
    protected var subscriptions = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        subscriptions = CompositeDisposable()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDialog()
        viewModelList.forEach { viewModel ->
            when (viewModel) {
                is BaseViewModel -> {
                    viewModel.loadingLiveData
                        .observe(viewLifecycleOwner) { isLoading ->
                            if (isLoading) {
                                showProgressDialog()
                            } else {
                                hideProgressDialog()
                            }
                        }

                    viewModel.errorMessageLiveData
                        .observe(viewLifecycleOwner) { throwable ->
                            showDialog(message = throwable.getErrorResponse().message)
                        }
                }
            }
        }
    }

    private fun initDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setView(R.layout.view_progress_dialog)
        dialog = builder.create()
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onDestroyView() {
        subscriptions.clear()
        super.onDestroyView()
    }

    private fun showProgressDialog() {
        try {
            dialog.show()
        } catch (_: WindowManager.BadTokenException) {

        }
    }

    private fun hideProgressDialog() {
        dialog.dismiss()
    }

    private fun showDialog(
        message: String = ""
    ) {
        AlertDialog.Builder(context)
            .setMessage(message)
            .setPositiveButton(R.string.dialog_close) { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .show()
    }

    protected fun hideSoftKeyboard() {
        activity.currentFocus?.let {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}
