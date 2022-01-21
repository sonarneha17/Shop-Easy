package com.example.shopeasy

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var fAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =inflater.inflate(R.layout.fragment_login, container, false)

        username=view.findViewById(R.id.log_username)
        password=view.findViewById(R.id.log_password)
        fAuth=Firebase.auth

        view.findViewById<Button>(R.id.btn_register).setOnClickListener{
            var navRegister=activity as FragmentNavigation
            navRegister.navigateFrag(RegisterFragment(),false)
        }
        view.findViewById<Button>(R.id.btn_login).setOnClickListener{
            validateForm()
        }
        return view
    }
    private fun firebaseSignIn()
    {
        fAuth.signInWithEmailAndPassword(username.text.toString(),
        password.text.toString()).addOnCompleteListener{
            task->
            if(task.isSuccessful){
                Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()
                var navHome=activity as FragmentNavigation
                navHome.navigateFrag(HomeFragment(),true)
            }else{
                Toast.makeText(context, task.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateForm() {
        val icon = AppCompatResources.getDrawable(
            requireContext(),
            R.drawable.warning
        )

        icon?.setBounds(0, 0, icon.intrinsicWidth, icon.intrinsicHeight)
        when {
            TextUtils.isEmpty(username.text.toString().trim()) -> {
                username.setError("Please Enter Username!", icon)
            }
            TextUtils.isEmpty(password.text.toString().trim()) -> {
                password.setError("Please Enter Password!", icon)
            }


            username.text.toString().isNotEmpty() && password.text.toString().isNotEmpty() -> {
                if (username.text.toString()
                        .matches(Regex("[a-zA-Z0-9.!#\$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)"))
                ) {
                    firebaseSignIn()

                } else {
                    username.setError("Please Enter Valid Email Id!", icon)
                }
            }
        }
    }
}






