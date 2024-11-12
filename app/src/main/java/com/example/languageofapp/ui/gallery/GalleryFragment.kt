package com.example.languageofapp.ui.gallery

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.languageofapp.R
import com.example.languageofapp.databinding.FragmentGalleryBinding
import java.util.Locale

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    private var suppressListener = false
    private var isRecreating = false

    private val sharedPreferences: SharedPreferences by lazy {
        requireActivity().getSharedPreferences("LanguagePreferences", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel = ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val radioGroups = arrayOf(
            binding.radioGroupJapan,
            binding.radioGroupEngland,
            binding.radioGroupSaudia,
            binding.radioGroupIndia
        )

        radioGroups.forEach { radioGroup ->
            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                if (!suppressListener) {
                    suppressListener = true

                    // Clear selection for other RadioGroups
                    radioGroups.forEach { otherGroup ->
                        if (otherGroup != group) {
                            otherGroup.clearCheck()
                        }
                    }

                    // Change app language based on selection
                    when (group.id) {
                        R.id.radioGroupJapan -> setLanguage("ja") // Japanese
                        R.id.radioGroupEngland -> setLanguage("en") // English
                        R.id.radioGroupSaudia -> setLanguage("ar") // Arabic
                        R.id.radioGroupIndia -> setLanguage("hi") // Hindi
                    }

                    suppressListener = false // Re-enable listeners after clearing
                }
            }
        }

        val savedLanguage = sharedPreferences.getString("selected_language", "en")
        if (!isRecreating) {
            setLanguage(savedLanguage ?: "en")
        }

        return root
    }

    private fun setLanguage(languageCode: String) {

        val currentLanguage = Locale.getDefault().language
        if (languageCode != currentLanguage) {
            // Save the selected language
            with(sharedPreferences.edit()) {
                putString("selected_language", languageCode)
                apply()
            }

            // Update locale
            val locale = Locale(languageCode)
            Locale.setDefault(locale)
            val config = requireContext().resources.configuration
            config.setLocale(locale)
            requireContext().resources.updateConfiguration(config, requireContext().resources.displayMetrics)

            isRecreating = true
            requireActivity().recreate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

