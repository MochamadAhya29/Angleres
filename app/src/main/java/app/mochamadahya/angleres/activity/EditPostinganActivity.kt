package app.mochamadahya.angleres.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.mochamadahya.angleres.R
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_edit_postingan.*
import kotlinx.android.synthetic.main.activity_upload_video.*

class EditPostinganActivity : AppCompatActivity() {

    private var firebaseUser: FirebaseUser? = null
    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference: DatabaseReference = firebaseDatabase.reference
    private val childReference: DatabaseReference = databaseReference.child("pos")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_postingan)

        jdl_edit.setText(intent.getStringExtra("judul"))
        desk_edit.setText(intent.getStringExtra("description"))

        btnSaveEdit.setOnClickListener {
            updatePost()
        }

    }

    private fun updatePost() {

//        database.child("Posts").child(userid).child
//                .addOnSuccessListener {
//
//                }
//                .addOnFailureListener {
//                    // Write failed
//                    // ...
//                }
    }
}