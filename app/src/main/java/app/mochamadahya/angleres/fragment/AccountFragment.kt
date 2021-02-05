package app.mochamadahya.angleres.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.mochamadahya.angleres.R
import app.mochamadahya.angleres.activity.PengaturanActivity
import app.mochamadahya.angleres.activity.ProfileActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_account.*

class AccountFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_account, container, false)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        val user  = auth.currentUser
        Glide.with(view.context)
                .load(user?.photoUrl)
                .into(img_profil)

        txtUserProfil.setText(user?.displayName)
        txtEmailProfil.setText(user?.email)


//        linearLayoutProfile.setOnClickListener {
//            startActivity(Intent(context, ProfileActivity::class.java))
//        }

        settings.setOnClickListener {
            startActivity(Intent(context, PengaturanActivity::class.java))
        }

//        txt_ubah_email.setOnClickListener {
//            startActivity(Intent(context, UpdateEmailActivity::class.java))
//        }
//        txt_ubah_password.setOnClickListener {
//            startActivity(Intent(context, ResetPasswordActivity::class.java))
//        }

    }

}