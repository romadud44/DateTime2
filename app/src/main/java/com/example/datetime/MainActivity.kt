package com.example.datetime

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.datetime.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val c = Calendar.getInstance()
    private val year = c.get(Calendar.YEAR)
    private val month = c.get(Calendar.MONTH)
    private val day = c.get(Calendar.DAY_OF_MONTH)
    private var photoUri: Uri? = null
    private val GALLERY_REQUEST = 777

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.toolbarMain.title = "Карточка данных"
        binding.toolbarMain.subtitle = "версия 1.0"
        setSupportActionBar(binding.toolbarMain)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.enterImageIV.setImageResource(R.drawable.ic_enter_image)
        binding.enterDobET.setOnClickListener {
            val dpd = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
                binding.enterDobET.setText("$dayOfMonth.$monthOfYear.$year")
            }, year, month, day)
            dpd.show()
        }
        binding.enterImageIV.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST)
        }
        binding.saveBTN.setOnClickListener {
            val name = binding.enterNameET.text.toString()
            val lastName = binding.enterLastNameET.text.toString()
            val dob = binding.enterDobET.text.toString()
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("lastName", lastName)
            intent.putExtra("dob", dob)
            intent.putExtra("image", photoUri.toString())
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exitMenuMain -> {
                finishAndRemoveTask()
                finishAffinity()
                finish()
                Toast.makeText(this, "Программа завершена", Toast.LENGTH_LONG).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            GALLERY_REQUEST -> if (resultCode === RESULT_OK) {
                photoUri = data?.data

                binding.enterImageIV.setImageURI(photoUri)
            }
        }

    }
}