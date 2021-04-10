package com.example.mad03_fragments_and_navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.mad03_fragments_and_navigation.databinding.FragmentQuizBinding
import com.example.mad03_fragments_and_navigation.models.QuestionCatalogue


class QuizFragment : Fragment() {

    private lateinit var binding: FragmentQuizBinding
    private val questions = QuestionCatalogue().defaultQuestions    // get a list of questions for the game
    private var score = 0                                           // save the user's score
    private var index = 0                                           // index for question data to show

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz, container, false)

        binding.index = index + 1
        binding.questionsCount = questions.size
        binding.question = questions[index]

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
            if(index <= questions.size-1){
                val radio: RadioButton = binding.answerBox.findViewById(checkedRadioBtn)

                for (answer in questions[index].answers){
                    if(answer.answerText == radio.text.toString()){
                        if(answer.isCorrectAnswer){
                            score++
                            break
                        }
                    }
                }

                binding.answerBox.clearCheck()
                index++

                if(index >= questions.size){
                    view.findNavController().navigate(QuizFragmentDirections.actionQuizFragmentToQuizEndFragment(score, questions.size))
                }
                else
                {
                    binding.index = index + 1
                    binding.question = questions[index]
                }
            }
        }
        else
        {
            Toast.makeText(context, "No Answer selected!", Toast.LENGTH_SHORT).show()
        }
    }
}