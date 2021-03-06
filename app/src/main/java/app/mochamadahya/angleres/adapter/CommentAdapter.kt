package app.mochamadahya.angleres.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.mochamadahya.angleres.R
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
import de.hdodenhof.circleimageview.CircleImageView

class CommentAdapter(private val mContext: Context, private val mComment:MutableList<Comment>)
    : RecyclerView.Adapter<CommentAdapter.CommentHolder>(){

    private lateinit var imageUri : Uri
    private lateinit var auth: FirebaseAuth
    private var firebaseUser: FirebaseUser? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
        val view  = LayoutInflater.from(mContext).inflate(R.layout.comment_item_layout, parent, false)
        return CommentHolder(view)
    }

    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        firebaseUser = FirebaseAuth.getInstance().currentUser
        val comment = mComment[position]
        holder.commentTv.text = comment.getComment()


        getUserInfo(holder.imageProfileComment, holder.userNameCommentTv, comment.getPublisher())
    }

    private fun getUserInfo(
        imageProfileComment: CircleImageView,
        userNameCommentTv: TextView,
        publisher: String
    ) {


        val userRef = FirebaseDatabase.getInstance().reference.child("Posts").child(publisher)
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0 != null){
                    auth = FirebaseAuth.getInstance()
                    val user  = auth.currentUser
                    Glide.with(mContext)
                        .load(user!!.photoUrl)
                        .into(imageProfileComment)
                    userNameCommentTv.text = user.displayName
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    override fun getItemCount(): Int {
        return mComment.size
    }

    class CommentHolder (itemView: View)
        : RecyclerView.ViewHolder(itemView){

        var imageProfileComment: CircleImageView = itemView.findViewById(R.id.user_profile_image_comment)
        var userNameCommentTv: TextView = itemView.findViewById(R.id.user_name_comment)
        var commentTv: TextView = itemView.findViewById(R.id.comment_comment)

    }

}