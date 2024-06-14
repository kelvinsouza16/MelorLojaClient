package com.example.melorlojacliente.register.presentation

import com.example.melorlojacliente.R
import com.example.melorlojacliente.commom.base.RequestCallback
import com.example.melorlojacliente.data.repository.Repository
import com.example.melorlojacliente.register.Register

class RegisterPresenter(private var view: Register.View?, private val repository: Repository) : Register.Presenter {
    override fun register(name: String, phone: String, address: String, birthday: String) {
        val isNameValid = name.length > 3
        val isPhoneValid = phone.length >= 17
        val isAddressValid = address.length > 3

        if (!isNameValid) {
            view?.displayNameFailure(R.string.invalid_name)
        } else {
            view?.displayNameFailure(null)
        }

        if (!isPhoneValid) {
            view?.displayPhoneFailure(R.string.invalid_phone)
        } else {
            view?.displayPhoneFailure(null)
        }

        if (!isAddressValid) {
            view?.displayAddressFailure(R.string.invalid_address)
        }

        if (isNameValid && isPhoneValid && isAddressValid) {
            view?.progress(true)
            repository.register(name, phone, address, birthday, object : RequestCallback<String> {
                override fun onSuccess(data: String) {
                    if (data.isNotEmpty()) {
                        view?.successRegister(data)
                    } else {
                        view?.failureRegister(data)
                    }
                }

                override fun onFailure(message: String?) {
                    view?.failureRegister(message)
                }

                override fun onComplete() {
                    view?.progress(false)
                }

            })
        }
    }

    override fun onDestroy() {
        view = null
    }
}