package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity(){

    private var tvResult: TextView? = null
    private var isLastDot = false
    private var isLastNumeric = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvResult = findViewById(R.id.tvResult)

    }

    fun onNumber(v: View){
        tvResult?.append((v as Button).text)
        isLastDot = false
        isLastNumeric = true
    }

    fun onClear(v: View){
        when(v.id){
            R.id.clear -> {
                try{
                    tvResult?.text = tvResult?.text?.subSequence(0, tvResult?.text?.length!!.minus(1))

                    if(tvResult?.text?.last() == '.'){
                        isLastDot = true
                        isLastNumeric = false
                    }else if(tvResult?.text?.last() == '+'
                        || tvResult?.text?.last() == '-'
                        || tvResult?.text?.last() == '*'
                        || tvResult?.text?.last() == '/'){
                        isLastDot = false
                        isLastNumeric = false
                    }else{
                        isLastDot = false
                        isLastNumeric = true
                    }
                }catch(e : Exception){
                    e.printStackTrace()
                }

            }

            R.id.allClear -> {
                tvResult?.text = ""
            }
        }
    }

    fun onPoint(v: View){
        if(isLastNumeric && !isLastDot && !tvResult?.text?.contains(".")!!){
            tvResult?.append(".")
            isLastDot = true
            isLastNumeric = false
        }
    }

    fun onOperator(v: View){

        tvResult?.text?.let{
            if(isLastNumeric && !isOperatorAdded(it.toString())){
                tvResult?.append((v as Button).text)
                isLastNumeric = false
                isLastDot= false
            }
        }

    }

    fun isOperatorAdded(value: String) : Boolean{
        return if(value.startsWith("-")){
            false
        }else{
            value.contains("+") ||
                    value.contains("-") ||
                    value.contains("*") ||
                    value.contains("/")
        }
    }

    fun removeZero(result: String): String{
        var value = result
        if(result.contains(".0")){
            value = result.substring(0,result.length.minus(2))
        }
        return value
    }

    fun onEqual(v: View){
        if(isLastNumeric){
            var tv = tvResult?.text.toString()
            var prefix = ""
            try {

                if(tv.startsWith("-")){
                    prefix = "-"
                    tv = tv.substring(1)
                }

                if(tv.contains("-")){
                    val value = tv.split("-")
                    var first = value[0]
                    var second = value[1]
                    if(prefix.isNotEmpty()){
                        first = prefix + first
                    }
                    tvResult?.text = removeZero((first.toBigDecimal() - second.toBigDecimal()).toString())
                }else if(tv.contains("+")){
                    val value = tv.split("+")
                    var first = value[0]
                    var second = value[1]
                    if(prefix.isNotEmpty()){
                        first = prefix + first
                    }
                    tvResult?.text = removeZero((first.toBigDecimal() + second.toBigDecimal()).toString())
                }else if(tv.contains("*")){
                    val value = tv.split("*")
                    var first = value[0]
                    var second = value[1]
                    if(prefix.isNotEmpty()){
                        first = prefix + first
                    }
                    tvResult?.text = removeZero((first.toBigDecimal() * second.toBigDecimal()).toString())
                }else if(tv.contains("/")){
                    val value = tv.split("/")
                    var first = value[0]
                    var second = value[1]
                    if(prefix.isNotEmpty()){
                        first = prefix + first
                    }
                    tvResult?.text = removeZero((first.toBigDecimal() / second.toBigDecimal()).toString())
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }






}