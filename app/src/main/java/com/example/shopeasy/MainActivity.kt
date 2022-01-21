package com.example.shopeasy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), FragmentNavigation{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var fAuth=Firebase.auth
        val currentUser=fAuth.currentUser
        if(currentUser!=null)
        {
            supportFragmentManager.beginTransaction()
                .add(R.id.container,HomeFragment()).addToBackStack(null)
                .commit()
        }

        supportFragmentManager.beginTransaction()
            .add(R.id.container,LoginFragment())
            .commit()
    }

    override fun navigateFrag(fragment: Fragment, addToStack: Boolean) {
        val transaction=supportFragmentManager
            .beginTransaction()
            .replace(R.id.container,fragment)

        if(addToStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }
}