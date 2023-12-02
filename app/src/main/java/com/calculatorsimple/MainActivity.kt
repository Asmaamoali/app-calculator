package com.calculatorsimple

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.calculatorsimple.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {


    private lateinit var binding:ActivityMainBinding
    var  lastNumeric = false
    var statError = false
    var lastDot = false


    private lateinit var expression: Expression


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onEqualClick(view: View) {

        onEqual()
        binding.dataTv.text = binding.resultTv.text.toString().drop(1)

    }
    fun onDigitClick(view: View) {

        if(statError){

            binding.dataTv.text = (view as Button).text

            statError = false
        }else{
            binding.dataTv.append((view as Button).text)
        }

        lastNumeric = true
        onEqual()

    }
    fun onAllclearClick(view: View) {

        binding.dataTv.text = ""
        binding.resultTv.text = ""
        statError = false
        lastDot = false
        lastNumeric = false
        binding.resultTv.visibility = View.GONE


    }
    fun onBackClick(view: View){
        binding.dataTv.text = binding.dataTv.text.toString().dropLast(1)

        try {
            val lastChar = binding.dataTv.text.toString().last()

            if(lastChar.isDigit()){
                onEqual()
            }



        }catch (e : Exception){

            binding.resultTv.text = ""
            binding.resultTv.visibility = View.GONE
            Long.e("last char error " , e.toString())


        }


    }
    fun onClearClick(view: View){

        binding.dataTv.text = ""
        lastNumeric = false





    }
    fun onOperatorClick(view: View){

        if(!statError && lastNumeric){

            binding.dataTv.append((view as Button).text)
            lastDot = false
            lastNumeric = false
            onEqual()


        }



    }


    fun onEqual (){

        if (lastNumeric && !statError){
            val txt = binding.dataTv.text.toString()

            expression = ExpressionBuilder(txt).build()

            try{

                val result = expression.evaluate()

                binding.resultTv.visibility = View.VISIBLE
                binding.resultTv.text = "=" + result.toString()

            }catch (ex:ArithmeticException){
                Long.e("evaluate error " , ex.toString())

                binding.resultTv.text = "Error "

                statError = true

                lastNumeric = false
            }
        }
    }
}

private fun Long.Companion.e(s: String, toString: String) {

}
