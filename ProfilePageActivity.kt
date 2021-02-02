package com.example.feelingsdiary


import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_profile2.*
import java.io.IOException


private var mAuth: FirebaseAuth? = null
private var textViewemailname: TextView? = null
private var mDatabaseReference: DatabaseReference? = null
private var mProfileFirstName: TextView? = null
private var mProfileLastName: TextView? = null
private var mProfileBday: TextView? = null
private var mProfileEmail: TextView? = null
private var profileImageView: ImageView? = null
private var FNBtn: Button? = null
private var LNBtn: Button? = null
private var BDAYBtn: Button? = null
private const val PICK_IMAGE = 123
var imagePath: Uri? = null

private lateinit var mDatabase: FirebaseDatabase


class ProfilePageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile2)

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase.getReference("Users")

        mProfileFirstName = findViewById(R.id.profile_first_name);
        mProfileLastName = findViewById(R.id.profile_last_name);
        mProfileEmail = findViewById(R.id.profile_email);

        mProfileBday = findViewById(R.id.profile_bday);
        FNBtn = findViewById(R.id.buttonEditFirstName);
        LNBtn = findViewById(R.id.buttonEditLastName);
        BDAYBtn = findViewById(R.id.buttonEditBday);

        profileImageView = findViewById(R.id.profile_pic_imageView);

        //Clickable profile image, Google Photos chooser
        profileImageView?.setOnClickListener{

                val profileIntent = Intent()
                profileIntent.type = "image/*"
                profileIntent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(
                    Intent.createChooser(profileIntent, "Select Image."),
                    PICK_IMAGE
                )

        }

        val intent = intent

        mProfileEmail?.text = intent.getStringExtra("user_email").toString()
        //Source: https://codinginflow.com/tutorials/android/bottomnavigationview
        val bottomNav =
            findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

    }

    //Sets chosen image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode === PICK_IMAGE && resultCode === Activity.RESULT_OK && data?.getData() != null) {
            imagePath = data?.getData()
            try {
                val bitmap =
                    MediaStore.Images.Media.getBitmap(contentResolver, imagePath)
                profileImageView!!.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    //Code referenced from: https://medium.com/@bariskarapinar/firebase-authentication-android-app-sign-in-sign-up-create-profile-c20d185bbbaf
    fun EditFirstName(view: View?) {
        val inflater = layoutInflater
        val alertLayout: View =
            inflater.inflate(R.layout.layout_custom_dialog_edit_name, null)
        val etUsername = alertLayout.findViewById<EditText>(R.id.et_username)
        val alert: AlertDialog.Builder = AlertDialog.Builder(this)
        alert.setTitle("Edit First Name")

        alert.setView(alertLayout)

        alert.setCancelable(false)
        alert.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialog, which -> })
        alert.setPositiveButton("OK",
            DialogInterface.OnClickListener { dialog, which ->
                val name = etUsername.text.toString()
                val lastname: String = profile_last_name.getText().toString()
                val bday: String = profile_bday.getText().toString()
                mProfileFirstName?.text = etUsername.text.toString()

                etUsername.onEditorAction(EditorInfo.IME_ACTION_DONE)
            })
        val dialog: AlertDialog = alert.create()
        dialog.show()
    }

    //Code referenced from: https://medium.com/@bariskarapinar/firebase-authentication-android-app-sign-in-sign-up-create-profile-c20d185bbbaf
    fun EditLastName(view: View?) {
        val inflater = layoutInflater
        val alertLayout: View =
            inflater.inflate(R.layout.layout_custom_dialog_edit_name, null)
        val etUsername = alertLayout.findViewById<EditText>(R.id.et_username)
        val alert: AlertDialog.Builder = AlertDialog.Builder(this)
        alert.setTitle("Edit Last Name")

        alert.setView(alertLayout)

        alert.setCancelable(false)
        alert.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialog, which -> })
        alert.setPositiveButton("OK",
            DialogInterface.OnClickListener { dialog, which ->
                val name = etUsername.text.toString()
                val lastname: String = profile_last_name.getText().toString()
                val bday: String = profile_bday.getText().toString()
                mProfileLastName?.text = etUsername.text.toString()

                etUsername.onEditorAction(EditorInfo.IME_ACTION_DONE)
            })
        val dialog: AlertDialog = alert.create()
        dialog.show()
    }

    //Code referenced from: https://medium.com/@bariskarapinar/firebase-authentication-android-app-sign-in-sign-up-create-profile-c20d185bbbaf
    fun EditBday(view: View?) {
        val inflater = layoutInflater
        val alertLayout: View =
            inflater.inflate(R.layout.layout_custom_dialog_edit_name, null)
        val etUsername = alertLayout.findViewById<EditText>(R.id.et_username)
        val alert: AlertDialog.Builder = AlertDialog.Builder(this)
        alert.setTitle("Edit Birthday")

        alert.setView(alertLayout)

        alert.setCancelable(false)
        alert.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialog, which -> })
        alert.setPositiveButton("OK",
            DialogInterface.OnClickListener { dialog, which ->
                val name = etUsername.text.toString()
                val lastname: String = profile_last_name.getText().toString()
                val bday: String = profile_bday.getText().toString()
                mProfileBday?.text = etUsername.text.toString()

                etUsername.onEditorAction(EditorInfo.IME_ACTION_DONE)
            })
        val dialog: AlertDialog = alert.create()
        dialog.show()
    }

    //Log Out returns to homescreen
    fun LogOut(v: View?) {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


    //Bottom Navivation Bar
    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->

            loadFragment(item.itemId)
            true
        }

    private fun loadFragment(itemId: Int) {
        val tag = itemId.toString()
        var fragment = supportFragmentManager.findFragmentByTag(tag) ?: when (itemId) {
            R.id.profile -> {
                val intent = Intent(this, ProfilePageActivity::class.java)
                startActivity(intent)
                Toast.makeText(getApplicationContext(), "Profile", Toast.LENGTH_SHORT).show();
            }
            R.id.calendar -> {
                val intent = Intent(this, CalendarActivity::class.java)
                startActivity(intent)
                Toast.makeText(getApplicationContext(), "Calendar", Toast.LENGTH_SHORT).show();
            }
            R.id.diary -> {
                val intent = Intent(this,DashboardActivity::class.java )
                startActivity(intent)
                Toast.makeText(getApplicationContext(), "Diary", Toast.LENGTH_SHORT).show();
            }
            else -> {
                null
            }
        }


    }
}