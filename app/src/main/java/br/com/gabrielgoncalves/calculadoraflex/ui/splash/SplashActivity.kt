package br.com.gabrielgoncalves.calculadoraflex.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import br.com.gabrielgoncalves.calculadoraflex.R
import br.com.gabrielgoncalves.calculadoraflex.ui.form.FormActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    private val TEMPO_AGUARDO_SPLASHSCREEN = 3500L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        carregar()
    }

    private fun carregar() {
        //Carrega animação
        val anim = AnimationUtils.loadAnimation(this, R.anim.animacao_splash)
        anim.reset()
        ivLogo.clearAnimation();
        //Roda animação
        ivLogo.startAnimation(anim)
        //Chama próxima tela após 3.5 segundos definidos na SPLASG_DISPLAY_LENGTH
        Handler().postDelayed({
            val proximoTela = Intent(this@SplashActivity, FormActivity::class.java)
            startActivity(proximoTela)
            finish()
        },TEMPO_AGUARDO_SPLASHSCREEN)
    }
}
