package app.mochamadahya.angleres.activity

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.VideoView
import app.mochamadahya.angleres.R
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    private var firebaseUser: FirebaseUser? = null
    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference: DatabaseReference = firebaseDatabase.reference
    private val childReference: DatabaseReference = databaseReference.child("postimage")


    lateinit var playerView: PlayerView
    private var player: SimpleExoPlayer? = null
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition = 0L

    private var videoUrl: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setTitle("Postingan")
        tv_title_detail.setText(intent.getStringExtra("judul"))
        tv_description_detail.setText(intent.getStringExtra("description"))
        txt_like_detail.setText(intent.getStringExtra("like"))

        videoUrl = intent.getStringExtra("postimage")!!

        val likesRef = FirebaseDatabase.getInstance().reference
            .child("Likes").child(intent.getStringExtra("postid")!!)
        likesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    txt_like_detail.text = "${p0.childrenCount.toString()}  Likes"
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

        video_detail.findViewById<VideoView>(R.id.video_detail)

        val videoRef = FirebaseDatabase.getInstance().getReference()
            .child("Posts").child(intent.getStringExtra("postid")!!)

        videoRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    initializePlayer()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.edit_post -> startActivity(Intent(this, EditPostinganActivity::class.java).apply {
                putExtra("judul", tv_title_detail.text.toString())
                putExtra("description", tv_description_detail.text.toString())
            })
        }
        return super.onOptionsItemSelected(item)
    }


    private fun initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(this)
        video_detail.player = player

        val uri = Uri.parse(videoUrl)
        val mediaSource = buildMediaSource(uri)

        player?.playWhenReady = playWhenReady
        player?.seekTo(currentWindow, playbackPosition)
        player?.prepare(mediaSource, false, false)
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        val dataSourceFactory: DataSource.Factory =
            DefaultDataSourceFactory(this, "exoplayer-codelab")
        val mediaSourceFactory = ProgressiveMediaSource.Factory(dataSourceFactory)
        val mediaSource1 = mediaSourceFactory.createMediaSource(uri)
        val concatenatingMediaSource = ConcatenatingMediaSource()

        concatenatingMediaSource.addMediaSource(mediaSource1)
        return concatenatingMediaSource
    }


    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N || player == null) {
            initializePlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            releasePlayer()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }

    private fun releasePlayer() {
        if (player != null) {
            playWhenReady = player!!.playWhenReady
            playbackPosition = player!!.currentPosition
            currentWindow = player!!.currentWindowIndex
            player = null
        }
    }

    override fun onBackPressed() {
        releasePlayer()
        finish()
        super.onBackPressed()

    }
}