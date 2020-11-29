package com.example.flowapidemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var flow: Flow<Int>
    val TAG = "MainActivity.kt"

    lateinit var button : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById<Button>(R.id.button)
        setupFlow()
        setupClicks()
    }
    fun setupFlow(){
        
        flow = flow {
            Log.d(TAG, "Start flow")
            (0..10).forEach {
                // Emit items with 500 milliseconds delay
                delay(500)
                Log.d(TAG, "Emitting $it")
                emit(it)
            }
        }.map {// will make operation while emit to flow
            it * it
        }.flowOn(Dispatchers.Default)
    }
    private fun setupClicks() {
        button.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                flow.collect {
                    Log.d(TAG, it.toString())
                }

            }
        }
    }

//    fun makeFlow() = flow {
//        Log.d(TAG,"sending first value")
//        emit(1)
//        Log.d(TAG,"first value collected, sending another value")
//        emit(2)
//        Log.d(TAG,"second value collected, sending a third value")
//        emit(3)
//        Log.d(TAG,"done")
//    }
//    CoroutineScope(Dispatchers.Main).launch {
//        makeFlow().collect { value ->
//            Log.d(TAG,"got $value")
//        }
//        Log.d(TAG,"flow is completed")
//    }
}