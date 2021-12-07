package com.example.bookfilterapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val author=findViewById<TextInputLayout>(R.id.author).editText?.text
        val country=findViewById<TextInputLayout>(R.id.country).editText?.text
        val button=findViewById<Button>(R.id.button)
        val result=findViewById<TextView>(R.id.result)

        button.setOnClickListener {
            val bookApplication=application as BookApplication
            val booksService=bookApplication.books
            var count = 0
            CoroutineScope(Dispatchers.IO).launch {
                val decodedBookResult = booksService.getBooks()

                withContext(Dispatchers.Main)
                {
                    val myStringBuilder = StringBuilder()

                    for (myData in decodedBookResult) {
                        if (author.toString() == myData.author && country.toString() == myData.country) {
                            if (count < 3) {
                                myStringBuilder.append("Result : " + myData.title)

                                myStringBuilder.append("\n")
                            }
                            count++
                        }
                    }

                    result.text = "Result : " + count + "\n$myStringBuilder"
                }

            }
        }



    }




}