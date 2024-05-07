package com.glucode.about_you.about

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.glucode.about_you.about.views.QuestionCardView
import com.glucode.about_you.databinding.FragmentAboutBinding
import com.glucode.about_you.databinding.ItemEngineerCustomBinding
import com.glucode.about_you.mockdata.MockData

class AboutFragment: Fragment() {
    private lateinit var binding: FragmentAboutBinding
    private lateinit var customBinding: ItemEngineerCustomBinding

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            data?.data?.let { selectedImageUri ->
                Log.d("YourFragment", "Selected Image URI: $selectedImageUri")
                try {
                    customBinding.profileImage.imageTintMode = null
                    customBinding.profileImage.setImageURI(selectedImageUri)
                } catch (e: Exception) {
                    Log.e("YourFragment", "Error loading image: ${e.message}")
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        customBinding = ItemEngineerCustomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpQuestions()
    }

    private fun setProfileImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(intent)
    }

    private fun setUpQuestions() {
        val engineerName = arguments?.getString("name")
        val engineer = MockData.engineers.first { it.name == engineerName }

        customBinding.name.text = engineer.name
        customBinding.role.text = engineer.role
        customBinding.years.text = engineer.quickStats.years.toString()
        customBinding.coffees.text = engineer.quickStats.coffees.toString()
        customBinding.bugs.text = engineer.quickStats.bugs.toString()

        customBinding.profileImage.setOnClickListener{setProfileImage()}

        binding.container.addView(customBinding.root)

        engineer.questions.forEach { question ->
            val questionView = QuestionCardView(requireContext())
            questionView.title = question.questionText
            questionView.answers = question.answerOptions
            questionView.selection = question.answer.index

            binding.container.addView(questionView)
        }
    }
}