package app.mochamadahya.angleres

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import app.mochamadahya.angleres.fragment.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.itemHome -> {
                moveToFragment(HomeFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.itemFeed -> {
                moveToFragment(FeedFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.itemPesan -> {
                moveToFragment(ChatsFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.itemTroli -> {
                moveToFragment(ShopFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.itemAkun -> {
                moveToFragment(AccountFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.bottom_nav)

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        moveToFragment(HomeFragment())
    }

    private fun moveToFragment(fragment: Fragment) {
        val fragmentTrans = supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.frame_container, fragment)
        fragmentTrans.commit()

    }
}