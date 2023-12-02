package com.example.imt.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.imt.databinding.ActivityRegisterBinding
import com.example.imt.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            auth = FirebaseAuth.getInstance()

            btnRgDaftar.setOnClickListener {
                val email = edtRgEmail.text.toString()
                val password = edtRgPassword.text.toString()
                val confirmPassword = edtRgConfirmPassword.text.toString()
                val name = edtRgName.text.toString()

                if (email.isEmpty()) {
                    edtRgEmail.error = "Email Harus Di Isi"
                    edtRgEmail.requestFocus()
                    return@setOnClickListener
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    edtRgEmail.error = "Email Tidak Valid"
                    edtRgEmail.requestFocus()
                    return@setOnClickListener
                }

                if (password.isEmpty()) {
                    edtRgPassword.error = "Password Tidak Boleh Kosong"
                    edtRgPassword.requestFocus()
                    return@setOnClickListener
                }

                if (password.length < 8) {
                    edtRgPassword.error = "Password Tidak Boleh Kurang Dari 8"
                    edtRgPassword.requestFocus()
                    return@setOnClickListener
                }

                if (password != confirmPassword) {
                    edtRgPassword.error = "Password Tidak Sama"
                    edtRgPassword.requestFocus()
                    return@setOnClickListener
                }

                RegisterFirebase(email, password, name)
            }
        }
    }

    private fun RegisterFirebase(email: String, password: String, name: String) {
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if (it.isSuccessful) {
                    val user: FirebaseUser? = auth.currentUser
                    val profileUpdate = UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build()

                    user?.updateProfile(profileUpdate)

                    Toast.makeText(this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }


}