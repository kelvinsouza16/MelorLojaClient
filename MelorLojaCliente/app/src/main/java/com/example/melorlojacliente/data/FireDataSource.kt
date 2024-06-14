package com.example.melorlojacliente.data

import com.example.melorlojacliente.commom.base.RequestCallback
import com.example.melorlojacliente.commom.models.Category
import com.example.melorlojacliente.commom.models.Product
import com.example.melorlojacliente.commom.models.User
import com.example.melorlojacliente.login.view.LoginCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.firestore.FirebaseFirestore

class FireDataSource : DataSource {
    override fun signInWithPhoneAuthCredential(
        credential: PhoneAuthCredential,
        sms: String,
        callback: LoginCallback
    ) {
        FirebaseAuth.getInstance()
            .signInWithCredential(credential)
            .addOnSuccessListener { res ->
                if (res.user != null) {
                    callback.onSuccess()
                } else {
                    callback.onFailure("Usuário não encontrado")
                }
            }
            .addOnFailureListener { exception ->
                callback.onFailure(exception.message ?: "Erro interno no servidor")
            }
            .addOnCompleteListener {
                callback.onComplete()
            }
    }

    override fun fetchUserRecord(phone: String, callback: RequestCallback<Boolean>) {
        FirebaseFirestore.getInstance()
            .collection("/clients")
            .whereEqualTo("phoneNumber", phone)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    callback.onSuccess(false)
                } else {
                    callback.onSuccess(true)
                }
            }
            .addOnFailureListener { exception ->
                callback.onFailure(exception.message ?: "Erro interno no servidor")
            }
            .addOnCompleteListener {
                callback.onComplete()
            }
    }

    override fun register(
        name: String,
        phone: String,
        address: String,
        birthday: String,
        callback: RequestCallback<String>
    ) {

        val userRef = FirebaseFirestore.getInstance()
            .collection("/clients")
            .document()

        val user = User(
            uuid = userRef.id,
            fullName = name,
            phoneNumber = phone,
            password = "123456",
            fullAddress = address,
            birthday = birthday
        )

        userRef.set(user)
            .addOnSuccessListener {
                callback.onSuccess("Usuário cadastrado com sucesso")
            }
            .addOnFailureListener { exception ->
                callback.onFailure(exception.message ?: "Falha ao adicionar usuário")
            }
            .addOnCompleteListener {
                callback.onComplete()
            }
    }

    override fun fetchAllProducts(callback: RequestCallback<List<Product>>) {
        FirebaseFirestore.getInstance()
            .collection("/products")
            .orderBy("name")
            .get()
            .addOnSuccessListener { res ->
                val documents = res.documents
                val products = mutableListOf<Product>()
                for (document in documents) {
                    val product = document.toObject(Product::class.java)
                    if (product != null) {
                        products.add(product)
                    }
                }
                callback.onSuccess(products)
            }
            .addOnFailureListener { exception ->
                callback.onFailure(exception.message ?: "Falha ao buscar produtos")
            }
            .addOnCompleteListener {
                callback.onComplete()
            }
    }

    override fun fetchAllCategories(callback: RequestCallback<List<Category>>) {
        FirebaseFirestore.getInstance()
            .collection("/categories")
            .orderBy("name")
            .get()
            .addOnSuccessListener { res ->
                val documents = res.documents
                val categories = mutableListOf<Category>()
                for (document in documents) {
                    val category = document.toObject(Category::class.java)
                    if (category != null) {
                        categories.add(category)
                    }
                }
                callback.onSuccess(categories)
            }
            .addOnFailureListener { exception ->
                callback.onFailure(exception.message ?: "Falha ao buscar categorias")
            }
            .addOnCompleteListener {
                callback.onComplete()
            }
    }
}