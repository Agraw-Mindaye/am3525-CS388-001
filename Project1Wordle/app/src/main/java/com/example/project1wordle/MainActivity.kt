package com.example.project1wordle

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    var target = ""
    var count = 0
    var maxGuess = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val guessText = findViewById<EditText>(R.id.guessText)
        val resultText = findViewById<TextView>(R.id.guessResult)
        val submitButton = findViewById<Button>(R.id.submitButton)
        val resetButton = findViewById<Button>(R.id.resetButton)

        // get a random word
        target = FourLetterWordList.getRandomFourLetterWord()

        // show initial status of game
        resultText.text = "Guesses: $count/$maxGuess"

        submitButton.setOnClickListener(){

            // get user's guess
            val currentGuess = guessText.text.toString().uppercase()
            count++

            val result = checkGuess(currentGuess) // check if user guessed correctly
            resultText.text = "Guess $count: $result" // show user the current status of their guess

            if (result == "OOOO") {
                resultText.text = "Congratulations, you guessed correctly! The correct word was: $target"
                resetButton.visibility = View.VISIBLE
                submitButton.isEnabled = false
            } else if (count >= maxGuess) {
                submitButton.isEnabled = false
                resetButton.visibility = View.VISIBLE
                resultText.text = "You ran out of guesses :(  Correct word: $target"
            }
        }

        resetButton.setOnClickListener {
            target = FourLetterWordList.getRandomFourLetterWord()
            count = 0
            resultText.text = ""
            guessText.text.clear()

            // hide and disable reset button once game resets
            submitButton.isEnabled = true
            resetButton.visibility = View.GONE
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    object FourLetterWordList {
        // List of most common 4 letter words from: https://7esl.com/4-letter-words/
        val fourLetterWords =
            "Area,Army,Baby,Back,Ball,Band,Bank,Base,Bill,Body,Book,Call,Card,Care,Case,Cash,City,Club,Cost,Date,Deal,Door,Duty,East,Edge,Face,Fact,Farm,Fear,File,Film,Fire,Firm,Fish,Food,Foot,Form,Fund,Game,Girl,Goal,Gold,Hair,Half,Hall,Hand,Head,Help,Hill,Home,Hope,Hour,Idea,Jack,John,Kind,King,Lack,Lady,Land,Life,Line,List,Look,Lord,Loss,Love,Mark,Mary,Mind,Miss,Move,Name,Need,News,Note,Page,Pain,Pair,Park,Part,Past,Path,Paul,Plan,Play,Post,Race,Rain,Rate,Rest,Rise,Risk,Road,Rock,Role,Room,Rule,Sale,Seat,Shop,Show,Side,Sign,Site,Size,Skin,Sort,Star,Step,Task,Team,Term,Test,Text,Time,Tour,Town,Tree,Turn,Type,Unit,User,View,Wall,Week,West,Wife,Will,Wind,Wine,Wood,Word,Work,Year,Bear,Beat,Blow,Burn,Call,Care,Cast,Come,Cook,Cope,Cost,Dare,Deal,Deny,Draw,Drop,Earn,Face,Fail,Fall,Fear,Feel,Fill,Find,Form,Gain,Give,Grow,Hang,Hate,Have,Head,Hear,Help,Hide,Hold,Hope,Hurt,Join,Jump,Keep,Kill,Know,Land,Last,Lead,Lend,Lift,Like,Link,Live,Look,Lose,Love,Make,Mark,Meet,Mind,Miss,Move,Must,Name,Need,Note,Open,Pass,Pick,Plan,Play,Pray,Pull,Push,Read,Rely,Rest,Ride,Ring,Rise,Risk,Roll,Rule,Save,Seek,Seem,Sell,Send,Shed,Show,Shut,Sign,Sing,Slip,Sort,Stay,Step,Stop,Suit,Take,Talk,Tell,Tend,Test,Turn,Vary,View,Vote,Wait,Wake,Walk,Want,Warn,Wash,Wear,Will,Wish,Work,Able,Back,Bare,Bass,Blue,Bold,Busy,Calm,Cold,Cool,Damp,Dark,Dead,Deaf,Dear,Deep,Dual,Dull,Dumb,Easy,Evil,Fair,Fast,Fine,Firm,Flat,Fond,Foul,Free,Full,Glad,Good,Grey,Grim,Half,Hard,Head,High,Holy,Huge,Just,Keen,Kind,Last,Late,Lazy,Like,Live,Lone,Long,Loud,Main,Male,Mass,Mean,Mere,Mild,Nazi,Near,Neat,Next,Nice,Okay,Only,Open,Oral,Pale,Past,Pink,Poor,Pure,Rare,Real,Rear,Rich,Rude,Safe,Same,Sick,Slim,Slow,Soft,Sole,Sore,Sure,Tall,Then,Thin,Tidy,Tiny,Tory,Ugly,Vain,Vast,Very,Vice,Warm,Wary,Weak,Wide,Wild,Wise,Zero,Ably,Afar,Anew,Away,Back,Dead,Deep,Down,Duly,Easy,Else,Even,Ever,Fair,Fast,Flat,Full,Good,Half,Hard,Here,High,Home,Idly,Just,Late,Like,Live,Long,Loud,Much,Near,Nice,Okay,Once,Only,Over,Part,Past,Real,Slow,Solo,Soon,Sure,That,Then,This,Thus,Very,When,Wide"

        // Returns a list of four letter words as a list
        fun getAllFourLetterWords(): List<String> {
            return fourLetterWords.split(",")
        }

        // Returns a random four letter word from the list in all caps
        fun getRandomFourLetterWord(): String {
            val allWords = getAllFourLetterWords()
            val randomNumber = (0..allWords.size).shuffled().last()
            return allWords[randomNumber].uppercase()
        }
    }

    /**
     * Parameters / Fields:
     *   wordToGuess : String - the target word the user is trying to guess
     *   guess : String - what the user entered as their guess
     *
     * Returns a String of 'O', '+', and 'X', where:
     *   'O' represents the right letter in the right place
     *   '+' represents the right letter in the wrong place
     *   'X' represents a letter not in the target word
     */
    private fun checkGuess(guess: String) : String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == target[i]) {
                result += "O"
            }
            else if (guess[i] in target) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }
}