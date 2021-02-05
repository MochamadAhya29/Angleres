package app.mochamadahya.angleres.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import app.mochamadahya.angleres.R
import app.mochamadahya.angleres.activity.auth.ChangePasswordActivity
import app.mochamadahya.angleres.activity.auth.UpdateEmailActivity
import kotlinx.android.synthetic.main.activity_pengaturan.*
import kotlinx.android.synthetic.main.activity_privasi.*

class PrivasiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privasi)

        setTitle("Privasi")

        tv1_privasi.setOnClickListener {
            startActivity(Intent(this, UpdateEmailActivity::class.java))
        }

        tv2_privasi.setOnClickListener {
            startActivity(Intent(this, ChangePasswordActivity::class.java))
        }

        tv3_privasi.setOnClickListener {
            Toast.makeText(this, "Masih tahap pengembangan", Toast.LENGTH_SHORT).show()
        }

    }
}