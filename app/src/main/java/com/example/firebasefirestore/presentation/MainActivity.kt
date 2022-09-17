package com.example.firebasefirestore.presentation

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.firebasefirestore.R
import com.example.firebasefirestore.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding
    lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private var resultLauncherSave : ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val viewModelFactory = MainActivityFactory(applicationContext)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)

        singFirebase()//вынести

        binding.signInButton.setOnClickListener {
            signIn()
        }
        binding.signOutButton.setOnClickListener {
            signOut()
        }

        binding.editTextTextCatName.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if (event != null) {
                    if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                        viewModel.setCatName(binding.editTextTextCatName.text.toString())
                        return true
                    }
                }
                return false
            }
        })

        viewModel.dataCat.observe(this, Observer {
            binding.textFindName.text = it.name
            binding.textFindAge.text = it.age.toString()
            binding.textFindBreed.text = it.breed
        })

        viewModel.catName.observe(this, Observer {
            viewModel.findCat()
        })

    }

    fun firebaseAuthWithGoogle(idToken: String) { //вынести в репозиторий
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        this.let {
            auth.signInWithCredential(credential)
                .addOnCompleteListener(it) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("MY", "signInWithCredential:success")
                        val user = auth.currentUser
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.d("MY", "signInWithCredential:failed")

                    }
                }
        }
    }

    fun signIn() { //вынести в репозиторий
        val signInIntent = googleSignInClient.signInIntent
        resultLauncherSave?.launch(signInIntent)
    }

    fun signOut() { //вынести в репозиторий
        // Firebase sign out
        auth.signOut()

        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener {
            binding.signInButton.visibility = View.VISIBLE
            binding.signOutButton.visibility = View.GONE
        }
    }

    fun singFirebase(){
        val gso = GoogleSignInOptions//вынести в репозиторий
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        auth = Firebase.auth
        refreshVisibleButton()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        resultLauncherSave = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.e("MY", "result ${result.resultCode}")
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.e("MY", "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                    Log.d("MY", "firebaseAuthWithGoogle:success")
                    binding.signInButton.visibility = View.GONE
                    binding.signOutButton.visibility = View.VISIBLE
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.d("MY", "firebaseAuthWithGoogle:fail")
                    Toast.makeText(this, "Ошибка входа, попробуйте еще раз", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun refreshVisibleButton()
    {
        var uidUser = getUidCurrentUser()
        if (uidUser != null)
        {
            binding.signInButton.visibility = View.GONE
            binding.signOutButton.visibility = View.VISIBLE
        }else
        {
            binding.signInButton.visibility = View.VISIBLE
            binding.signOutButton.visibility = View.GONE
        }
    }

    fun getUidCurrentUser(): String? {
        auth = Firebase.auth
        Log.e("MY","auth ${auth.currentUser}")
        return if (auth.currentUser != null) {
            auth.currentUser?.uid
        } else
            null
    }

}