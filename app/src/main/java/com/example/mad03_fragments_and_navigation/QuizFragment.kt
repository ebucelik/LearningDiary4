package com.example.mad03_fragments_and_navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.mad03_fragments_and_navigation.databinding.FragmentQuizBinding
import com.example.mad03_fragments_and_navigation.models.QuestionCatalogue
import com.example.mad03_fragments_and_navigation.viewmodels.QuizViewModel


class QuizFragment : Fragment() {

    private lateinit var binding: FragmentQuizBinding
    private lateinit var viewModel: QuizViewModel
    private var index: Int = 0
    private var score: Int = 0
    private var size: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz, container, false)

        viewModel = ViewModelProvider(this).get(QuizViewModel::class.java)

        viewModel.index.observe(viewLifecycleOwner, Observer { newIndex ->
            binding.index = newIndex + 1
            index = newIndex
        })

        viewModel.score.observe(viewLifecycleOwner, Observer { newScore ->
            score = newScore
        })

        viewModel.size.observe(viewLifecycleOwner, Observer { newSize ->
            binding.questionsCount = newSize
            size = newSize
        })

        viewModel.questions.observe(viewLifecycleOwner, Observer { newQuestion ->
            binding.question = newQuestion
        })

        binding.btnNext.setOnClickListener { btnNextView ->
            nextQuestion(btnNextView)
        }

        return binding.root
    }

    private fun nextQuestion(view: View){
        // get selected answer
        // check if is correct answer
        // update score
        // check if there are any questions left
            // show next question OR
            // navigate to QuizEndFragment
        val checkedRadioBtn = binding.answerBox.checkedRadioButtonId

        if(checkedRadioBtn != -1){
            if(index <= size-1){
                val radio: RadioButton = binding.answerBox.findViewById(checkedRadioBtn)
                viewModel.selectedAnswer = radio.text.toString()

                viewModel.checkSelectedAnswer()

                binding.answerBox.clearCheck()

                if(index >= size){
                    view.findNavController().navigate(QuizFragmentDirections.actionQuizFragmentToQuizEndFragment(score, size))
                }
            }
        }
        else
        {
            Toast.makeText(context, "No Answer selected!", Toast.LENGTH_SHORT).show()
        }
    }
}