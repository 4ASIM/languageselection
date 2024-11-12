package com.example.languageofapp
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.languageofapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val savedLanguage = LocaleHelper.getSavedLanguage(this)
        LocaleHelper.setLocale(this, savedLanguage)



        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.customButton.setOnClickListener {

            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
            finish()
        }
    }
}
