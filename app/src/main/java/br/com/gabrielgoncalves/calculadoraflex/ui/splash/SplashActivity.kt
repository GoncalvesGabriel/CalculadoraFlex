package br.com.gabrielgoncalves.calculadoraflex.ui.splash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.core.content.edit
import br.com.gabrielgoncalves.calculadoraflex.R
import br.com.gabrielgoncalves.calculadoraflex.ui.form.FormActivity
import br.com.gabrielgoncalves.calculadoraflex.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    private val TEMPO_AGUARDO_SPLASHSCREEN = 3500L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val preferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE);
        val isFirstOpen =  preferences.getBoolean("open_first", true);
        if (isFirstOpen) {
            markAppRealdyOpen(preferences)
            showSplash()
        } else {
            openLogin()
        }

    }

    private fun markAppRealdyOpen(preferences: SharedPreferences) {
        val editor = preferences.edit();
        editor.putBoolean("open_first", false)
        editor.apply();
    }

    private fun openLogin() {
        val login = Intent(this@SplashActivity, LoginActivity::class.java)
        startActivity(login)
        finish()
    }

    private fun showSplash() {
        //Carrega animação
        val anim = AnimationUtils.loadAnimation(this, R.anim.animacao_splash)
        anim.reset()
        ivLogo.clearAnimation();
        //Roda animação
        ivLogo.startAnimation(anim)
        //Chama próxima tela após 3.5 segundos definidos na SPLASG_DISPLAY_LENGTH
        Handler().postDelayed({
            val proximoTela = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(proximoTela)
            finish()
        },TEMPO_AGUARDO_SPLASHSCREEN)
    }
}
