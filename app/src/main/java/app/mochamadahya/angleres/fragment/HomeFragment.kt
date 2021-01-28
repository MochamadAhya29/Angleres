package app.mochamadahya.angleres.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.mochamadahya.angleres.MainActivity
import app.mochamadahya.angleres.R
import app.mochamadahya.angleres.activity.*
import app.mochamadahya.angleres.adapter.PostAdapter
import app.mochamadahya.angleres.model.Post
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.img_bisnis
import kotlinx.android.synthetic.main.fragment_home.img_event
import kotlinx.android.synthetic.main.fragment_home.img_livestreaming
import kotlinx.android.synthetic.main.fragment_home.img_lokasi
import kotlinx.android.synthetic.main.fragment_home.img_rent
import kotlinx.android.synthetic.main.fragment_home.img_store
import java.util.ArrayList


class HomeFragment : Fragment() {

    var sampleImages = intArrayOf(
        R.drawable.gambar1,
        R.drawable.gambar2
    )
    lateinit var playerView: PlayerView
    private var player: SimpleExoPlayer? = null
    private var rv_video_home: RecyclerView? = null
    private var btn_video_Add: ImageView? = null

    private var postAdapter: PostAdapter? = null
    private var postList: MutableList<Post>? = null
    private var followingList: MutableList<Post>? = null

    private val bottomsheetdialog : BottomSheetDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_home, container, false)



        rv_video_home = root.findViewById(R.id.rv_video_home)
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        rv_video_home?.layoutManager = linearLayoutManager


        postList = ArrayList()
        postAdapter = context?.let { PostAdapter(it, postList as ArrayList<Post>) }
        rv_video_home?.adapter = postAdapter

        val carouselView = root.findViewById(R.id.carouselViewHome) as CarouselView;
        carouselView.setPageCount(sampleImages.size)
        carouselView.setImageListener(imageListener)

        getPost()


//        if (!CommonUtil.isOnline(root.context)){
//            internetDialog()
//        }




//        rv_video_home?.visibility = View.GONE
//        shimmer_layout?.visibility = View.VISIBLE
//        shimmer_layout?.startShimmer()
//        getVideoData()

        return root
    }

    private fun getPost() {
        val postsRef = FirebaseDatabase.getInstance().reference.child("Posts")
        postsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                postList?.clear()

                for (snapshot in p0.children){
                    val post = snapshot.getValue(Post::class.java)
                    post?.let { postList?.add(it) }
                    postAdapter!!.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    var imageListener: ImageListener = object : ImageListener {
        override fun setImageForPosition(position: Int, imageView: ImageView) {
            // You can use Glide or Picasso here
            imageView.setImageResource(sampleImages[position])
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imgLocation: ImageView = view.findViewById(R.id.img_lokasi)
        img_lokasi.setOnClickListener {
            startActivity(Intent(context, LokasiActivity::class.java))
        }
        img_event.setOnClickListener {
            startActivity(Intent(context, EventActivity::class.java))
        }
        img_store.setOnClickListener {
            startActivity(Intent(context, TokoActivity::class.java))
        }
        img_rent.setOnClickListener {
            startActivity(Intent(context, PenyewaanActivity::class.java))
        }
        img_livestreaming.setOnClickListener {
            startActivity(Intent(context, LiveStreamingActivity::class.java))
        }
        img_bisnis.setOnClickListener {
            startActivity(Intent(context, BisnisActivity::class.java))
        }
//        img_video.setOnClickListener {
//            startActivity(Intent(context, VideoActivity::class.java))
//        }


        img_addvideo.setOnClickListener {
            startActivity(Intent(context, UploadVideoActivity::class.java))
        }

//        img_option.setOnClickListener {
//            val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetStyle)
//            val bottomsheetview : View = LayoutInflater.from(context).inflate(
//                R.layout.bottom_sheet, bottomsheetcontainer, false
//            )
//
//            bottomSheetDialog.setContentView(bottomsheetview)
//            bottomSheetDialog.show()
//        }

    }
}