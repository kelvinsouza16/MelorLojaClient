package com.example.melorlojacliente.register.view

import android.content.Context
import com.example.melorlojacliente.R
import com.example.melorlojacliente.commom.base.BaseFragment
import com.example.melorlojacliente.commom.base.DependencyInjector
import com.example.melorlojacliente.commom.dialogs.ChooseDateDialog
import com.example.melorlojacliente.commom.extensions.getCurrentDate
import com.example.melorlojacliente.commom.extensions.hideRegisterProgress
import com.example.melorlojacliente.commom.extensions.showErrorDialog
import com.example.melorlojacliente.commom.extensions.showRegisterProgress
import com.example.melorlojacliente.commom.extensions.toDate
import com.example.melorlojacliente.commom.extensions.toast
import com.example.melorlojacliente.commom.utils.TxtWatcher
import com.example.melorlojacliente.databinding.FragmentRegisterBinding
import com.example.melorlojacliente.register.Register
import com.example.melorlojacliente.register.RegisterAttachListener
import com.example.melorlojacliente.register.presentation.RegisterPresenter

class FragmentRegister(private val phone: String) : BaseFragment<FragmentRegisterBinding, Register.Presenter>(
    R.layout.fragment_register,
    FragmentRegisterBinding::bind
), Register.View {

    override lateinit var presenter: Register.Presenter

    private var registerAttachListener: RegisterAttachListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is RegisterAttachListener) {
            registerAttachListener = context
        }
    }

    override fun setupViews() {

        binding?.let {
            with(it) {

                registerEditFullName.addTextChangedListener(watcher)
                registerEditFullName.addTextChangedListener(TxtWatcher {
                    displayNameFailure(null)
                })
                registerEditPhone.setText(phone)
                registerEditPhone.addTextChangedListener(watcher)
                registerEditPhone.addTextChangedListener(TxtWatcher {
                    displayPhoneFailure(null)
                })
                registerEditAddress.addTextChangedListener(watcher)
                registerEditAddress.addTextChangedListener(TxtWatcher {
                    displayAddressFailure(null)
                })

                registerEditBirthday.setText(getCurrentDate())
                registerEditBirthday.setOnClickListener {
                    val dialog = ChooseDateDialog(
                        requireContext(),
                        registerEditBirthday.text.toString().toDate()
                    )
                    dialog.setConfirmClickListener { date ->
                        registerEditBirthday.setText(getCurrentDate(date))
                    }
                    dialog.show()
                }

                registerBtn.setOnClickListener {
                    presenter.register(
                        registerEditFullName.text.toString(), registerEditPhone.text.toString(),
                        registerEditAddress.text.toString(), registerEditBirthday.text.toString()
                    )
                }
            }
        }

    }

    override fun setupPresenter() {
        presenter = RegisterPresenter(this, DependencyInjector.repository())
    }

    override fun onDestroy() {
        presenter.onDestroy()
        binding = null
        super.onDestroy()
    }

    private var watcher = TxtWatcher {
        binding?.registerBtn?.isEnabled =
            binding?.registerEditFullName?.text.toString().isNotEmpty() &&
                    binding?.registerEditPhone?.text.toString().isNotEmpty() &&
                    binding?.registerEditAddress?.text.toString().isNotEmpty()
    }

    override fun progress(enabled: Boolean) {
        if (enabled) showRegisterProgress() else hideRegisterProgress()
    }

    override fun successRegister(message: String) {
        toast(message)
        registerAttachListener?.goToMainScreen()
    }

    override fun failureRegister(message: String?) {
        showErrorDialog(message!!)
    }

    override fun displayNameFailure(nameError: Int?) {
        binding?.registerInputLayoutFullName?.error = nameError?.let { getString(it) }
    }

    override fun displayPhoneFailure(phoneError: Int?) {
        binding?.registerInputLayoutPhone?.error = phoneError?.let { getString(it) }
    }

    override fun displayAddressFailure(addressError: Int?) {
        binding?.registerInputLayoutAddress?.error = addressError?.let { getString(it) }
    }
}