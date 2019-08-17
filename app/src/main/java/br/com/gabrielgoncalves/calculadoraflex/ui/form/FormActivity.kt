package br.com.gabrielgoncalves.calculadoraflex.ui.form

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.gabrielgoncalves.calculadoraflex.R
import br.com.gabrielgoncalves.calculadoraflex.model.CarData
import br.com.gabrielgoncalves.calculadoraflex.ui.result.ResultActivity
import br.com.gabrielgoncalves.calculadoraflex.watchers.DecimalTextWatcher
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_form.*

class FormActivity : AppCompatActivity() {

    private lateinit var userId: String
    private lateinit var mAuth: FirebaseAuth
    private val firebaseReferenceNode = "CarData"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        mAuth = FirebaseAuth.getInstance()
        userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        listenerFirebaseRealtime()

        etGasPrice.addTextChangedListener(DecimalTextWatcher(etGasPrice, 1))
        etEthanolPrice.addTextChangedListener(DecimalTextWatcher(etEthanolPrice, 1))
        etGasAverage.addTextChangedListener(DecimalTextWatcher(etGasAverage, 1))
        etEthanolAverage.addTextChangedListener(DecimalTextWatcher(etEthanolAverage, 1))

        btCalculate.setOnClickListener {
            saveCarData()
            val proximaTela = Intent(this@FormActivity, ResultActivity::class.java)
            proximaTela.putExtra("GAS_PRICE", etGasPrice.text.toString().toDouble())
            proximaTela.putExtra("ETHANOL_PRICE", etEthanolPrice.text.toString().toDouble())
            proximaTela.putExtra("GAS_AVERAGE", etGasAverage.text.toString().toDouble())
            proximaTela.putExtra("ETHANOL_AVERAGE", etEthanolAverage.text.toString().toDouble())
            startActivity(proximaTela)
        }
    }

    private fun saveCarData() {
        val carData = CarData(
            etGasPrice.text.toString().toDouble(),
            etEthanolPrice.text.toString().toDouble(),
            etGasAverage.text.toString().toDouble(),
            etEthanolAverage.text.toString().toDouble()
        )
        FirebaseDatabase.getInstance().getReference(firebaseReferenceNode)
            .child(userId)
            .setValue(carData)
    }

    private fun listenerFirebaseRealtime() {
        val database = FirebaseDatabase.getInstance()
//Define para usar dados off-line
        database.setPersistenceEnabled(true)
        database
            .getReference(firebaseReferenceNode)
            .child(userId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val carData = dataSnapshot.getValue(CarData::class.java)
                    etGasPrice.setText(carData?.gasPrice.toString())
                    etEthanolPrice.setText(carData?.ethanolPrice.toString())
                    etGasAverage.setText(carData?.gasAverage.toString())
                    etEthanolAverage.setText(carData?.ethanolAverage.toString())
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

}
