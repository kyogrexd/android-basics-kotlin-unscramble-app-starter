package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {

    //分數
    private var _score = 0
    val score: Int
        get() = _score
    //答題數
    private var _currentWordCount = 0
    val currentWordCount: Int
        get() = _currentWordCount
    //當前打亂的單字
    private lateinit var _currentScrambledWord: String
    val currentScrambledWord: String
        get() = _currentScrambledWord
    private var _count = 0
    val count: Int
        get() = _count
    //使用過的單字
    private var wordsList: MutableList<String> = mutableListOf()
    //當前單字
    private lateinit var currentWord: String

    init {
        Log.d("GameViewModel", "GameViewModel created!")
        //一開始取得打亂單字
        getNextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("GameViewModel", "GameViewModel destroyed!")
    }

    private fun increaseScore() {
        _score += SCORE_INCREASE
    }

    /*
    * Re-initializes the game data to restart the game.
    */
    fun reinitializeData() {
        _score = 0
        _currentWordCount = 0
        wordsList.clear()
        getNextWord()
    }

    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(currentWord, true)) {
            increaseScore()
            return true
        }
        return false
    }

    private fun getNextWord() {
        //隨機取得單字
        currentWord = allWordsList.random()
        val tempWord = currentWord.toCharArray()
        //打亂單字順序
        tempWord.shuffle()

        //打亂後順序沒變再執行
        while (String(tempWord).equals(currentWord, false)) {
            tempWord.shuffle()
        }

        //判斷此單字是否已用過
        if (wordsList.contains(currentWord)) {
            getNextWord()
        } else {
            _currentScrambledWord = String(tempWord)
            ++_currentWordCount
            wordsList.add(currentWord)
        }
    }

    fun nextWord(): Boolean {
        return if (currentWordCount < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }
}