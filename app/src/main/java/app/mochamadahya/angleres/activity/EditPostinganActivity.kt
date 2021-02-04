package app.mochamadahya.angleres.activity

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.room.Database
import app.mochamadahya.angleres.MainActivity
import app.mochamadahya.angleres.R
import app.mochamadahya.angleres.model.Post
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_edit_postingan.*
import kotlinx.android.synthetic.main.activity_upload_video.*

class EditPostinganActivity : AppCompatActivity() {



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

//        val postid: String = intent.getStringExtra("postid")
        when {
            TextUtils.isEmpty(jdl_edit.text.toString()) -> Toast.makeText(this, "Tulis Judulnya", Toast.LENGTH_SHORT).show()
            TextUtils.isEmpty(desk_edit.text.toString()) -> Toast.makeText(this, "Tulis Deskripsinya", Toast.LENGTH_SHORT).show()

            else -> {
                val postid: String = intent.getStringExtra("postid").toString()
                val database = FirebaseDatabase.getInstance()
                val postRef = database.getReference("Posts")
                val post = postRef.child(postid)

                post.child("judul").setValue(jdl_edit.text.toString())
                post.child("description").setValue(desk_edit.text.toString())


                finish()

//                val intent = Intent(this, DetailActivity::class.java)
//                startActivity(intent)

            }
        }
    }
}