package app.mochamadahya.angleres.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.mochamadahya.angleres.R
import app.mochamadahya.angleres.adapter.CommentAdapter
import app.mochamadahya.angleres.model.Comment
import app.mochamadahya.angleres.model.Post
import app.mochamadahya.angleres.model.User
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_comment.*
import kotlinx.android.synthetic.main.activity_edit_postingan.*
import kotlinx.android.synthetic.main.comment_item_layout.*
import java.util.ArrayList

class CommentActivity : AppCompatActivity() {

    private var post : Post? = null
    private var postId = ""
    private lateinit var auth: FirebaseAuth
    private var publisherId = ""
    private var postImage : String? = null
    private var firebaseUser: FirebaseUser? = null
    private var commentAdapter: CommentAdapter? = null
    private var commentList: MutableList<Comment>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)


        val intent = intent
        postId = intent.getStringExtra("postId")!!

        setTitle("Komentar")
        firebaseUser = FirebaseAuth.getInstance().currentUser

        val userRef = FirebaseDatabase.getInstance().reference.child("Posts")
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0 != null){
                    auth = FirebaseAuth.getInstance()
                    val user  = auth.currentUser
                    Glide.with(this@CommentActivity)
                        .load(user!!.photoUrl)
                        .into(profile_image_comments)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        var recyclerView: RecyclerView? = null
        recyclerView = findViewById(R.id.rv_comment)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.reverseLayout = true
        recyclerView.layoutManager = linearLayoutManager



        commentList = ArrayList()
        commentAdapter = CommentAdapter(this, commentList as ArrayList<Comment>)
        recyclerView.adapter = commentAdapter

        userInfo()
        readComment()
        getPostImageComment()

        txt_post_comment.setOnClickListener {
            if (add_Comment!!.text.toString() == ""){
                Toast.makeText(this, "Silahkan tulis komentar nya", Toast.LENGTH_SHORT).show()
            } else {
                addComment()
            }
        }

    }

    private fun getPostImageComment() {
        val postCommentRef = FirebaseDatabase.getInstance().reference
            .child("Posts").child(postId).child("postimage")

        postCommentRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
//                    val image = snapshot.value.toString()
//                    Picasso.get().load(image).placeholder(R.drawable.ic_launcher_background).into(post_comment_image)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun readComment() {
        var commentRef = FirebaseDatabase.getInstance().reference
            .child("Comments").child(postId)

        commentRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    commentList!!.clear()
                    for (snapshot in snapshot.children){
                        val comment = snapshot.getValue(Comment::class.java)
                        commentList!!.add(comment!!)
                    }
                    commentAdapter!!.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun addComment(){

        val commentsRef = FirebaseDatabase.getInstance().reference
            .child("Comments").child(postId!!)

        val commentMap = HashMap<String, Any>()
        commentMap["comment"] = add_Comment!!.text.toString()
        commentMap["publisherId"] = firebaseUser!!.uid
        commentsRef.push().setValue(commentMap)
        add_Comment!!.text.clear()
    }

    private fun userInfo(){
        val userRef = FirebaseDatabase.getInstance().reference
            .child("Posts").child(firebaseUser!!.uid)

        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    auth = FirebaseAuth.getInstance()
                    val user  = auth.currentUser
                    Glide.with(this@CommentActivity)
                            .load(user!!.photoUrl)
                            .into(profile_image_comments)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}