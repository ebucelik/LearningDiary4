package com.example.mad03_fragments_and_navigation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mad03_fragments_and_navigation.models.Question
import com.example.mad03_fragments_and_navigation.models.QuestionCatalogue

class QuizViewModel : ViewModel(){

    private var allQuestions = QuestionCatalogue().defaultQuestions

    private var _questions = MutableLiveData<Question>()
    val questions: LiveData<Question>
        get() = _questions

    var selectedAnswer: String = ""

    private var _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    private var _index = MutableLiveData<Int>()
    val index: LiveData<Int>
        get() = _index

    private var _size = MutableLiveData<Int>()
    val size: LiveData<Int>
        get() = _size

    init {
        _score.value = 0
        _index.value = 0
        _questions.value = allQuestions[index.value?:0]
        _size.value = allQuestions.size
    }

    private fun increaseIndex(){
        _index.value = (index.value)?.plus(1)
    }

    private fun increaseScore(){
        _score.value = (score.value)?.plus(1)
    }

    private fun nextQuestion(){
        _questions.value = allQuestions[index.value ?: 0]
    }

    fun checkSelectedAnswer(){
        for (answer in allQuestions[index.value ?: 0].answers){
            if(answer.answerText == selectedAnswer){
                if(answer.isCorrectAnswer){
                    increaseScore()
                    break
                }
            }
        }

        increaseIndex()

        if(index.value?:0 < size.value?:0) nextQuestion()
    }
}