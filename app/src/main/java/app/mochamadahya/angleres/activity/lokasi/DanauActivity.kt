package app.mochamadahya.angleres.activity.lokasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import app.mochamadahya.angleres.R
import app.mochamadahya.angleres.activity.maps.MapsDanau
import app.mochamadahya.angleres.activity.maps.MapsSungai
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.activity_danau.*
import kotlinx.android.synthetic.main.activity_sungai.*

class DanauActivity : AppCompatActivity() {
    var sampleImages = intArrayOf(
        R.drawable.gambar1,
        R.drawable.gambar2
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_danau)

        setTitle("Danau")

        val carouselView = findViewById(R.id.carouselView_danau) as CarouselView;
        carouselView.setPageCount(sampleImages.size)
        carouselView.setImageListener(imageListener)

        img_danau_lokasi.setOnClickListener {
            startActivity(Intent(this, MapsDanau::class.java))
        }

        img_danau_sewa.setOnClickListener {
//            startActivity(Intent(this, LautActivity::class.java))
        }

        img_danau_lainnya.setOnClickListener {
//            startActivity(Intent(this, KolamActivity::class.java))
        }
    }
    var imageListener: ImageListener = object : ImageListener {
        override fun setImageForPosition(position: Int, imageView: ImageView) {
            // You can use Glide or Picasso here
            imageView.setImageResource(sampleImages[position])
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.danau_item, menu)
        return true
    }
}