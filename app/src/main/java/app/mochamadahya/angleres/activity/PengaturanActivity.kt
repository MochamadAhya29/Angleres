package app.mochamadahya.angleres.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import app.mochamadahya.angleres.R
import app.mochamadahya.angleres.activity.auth.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_pengaturan.*

class PengaturanActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengaturan)

        setTitle("Pengaturan")

        auth = FirebaseAuth.getInstance()
        val user  = auth.currentUser

        tv1.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        tv2.setOnClickListener {
            Toast.makeText(this, "Masih tahap pengembangan", Toast.LENGTH_SHORT).show()
        }
        tv3.setOnClickListener {
            startActivity(Intent(this, PrivasiActivity::class.java))
        }
        tv4.setOnClickListener {
            Toast.makeText(this, "Masih tahap pengembangan", Toast.LENGTH_SHORT).show()
        }

        tv_keluar.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Angleres")
            builder.setMessage("Apakah anda yakin ingin keluar?")

            builder.setPositiveButton("Ya") { dialog, which ->
                auth.signOut()
                Intent(this, LoginActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                    Toast.makeText(applicationContext,
                        "Anda sudah keluar", Toast.LENGTH_SHORT).show()
                }
            }

            builder.setNegativeButton("Tidak") { dialog, which ->
                Toast.makeText(applicationContext,
                    "Batal", Toast.LENGTH_SHORT).show()
            }

            builder.show()
        }



    }
}