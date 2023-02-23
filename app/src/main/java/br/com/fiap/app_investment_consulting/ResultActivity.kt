package br.com.fiap.app_investment_consulting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_product.*
import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.android.synthetic.main.card_product.*
import kotlin.math.pow
import java.text.DecimalFormat


class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        txtNameResult.text = intent.getStringExtra("name")
        txtEmailResult.text = intent.getStringExtra("email")
        txtPhoneResult.text = intent.getStringExtra("phone")
        var calc:Resultados = calcComp(
            intent.getStringExtra("starValue").toString().toDouble(),
            intent.getStringExtra("fee").toString().toDouble(),
            intent.getStringExtra("monthly").toString().toDouble(),
            intent.getStringExtra("yield").toString().toDouble()
        )
        println(calc)
        intent.getStringExtra("fee")

        var product = intent.getStringExtra("product")
        var risk = intent.getStringExtra("risk")
        txtTotalValor.text = calc.totalValor
        totalFeeValor.text = calc.totalFeeValor
        txtTotalYield.text = calc.totalYield
        txtTotalFee.text = calc.totalFee
        txtTotalexpoMonthly.text = calc.totalexpoMonthly

    }

    private fun calcComp(
        valor: Double,
        fee: Double,
        monthly: Double,
        yield: Double
    ): Resultados{
        var qnt = 12
        val result: Resultados = Resultados()
        var expoFee:Double = fee/100
        var expoMonthly:Double = 0.0
        var perc:Double = valor/100
        var total:Double = valor
        while (qnt > 0) {
            total = ((total * expoFee) + monthly)+total
            expoMonthly = expoMonthly + monthly
            qnt--
        }
        // value total
        result.totalValor = (total).format()
        //value Fee
        result.totalYield = ((total-(expoMonthly+valor))*(yield/100)).format()
        result.totalFeeValor = ((total-(expoMonthly+valor)- result.totalYield.toDouble())).format()
        result.totalFee = (result.totalFeeValor.toDouble() / perc).format()
        // value Yield
        result.totalexpoMonthly = expoMonthly.format()

        return result
    }
    // Formata número double para duas casas
    private fun Double.format(): String {
        val df = DecimalFormat("#.00")
        return df.format(this)
    }
    data class Resultados(
        var totalValor: String = "",
        var totalFeeValor: String = "",
        var totalFee: String = "",
        var totalexpoMonthly: String = "",
        var totalYield: String = "",
    )
}