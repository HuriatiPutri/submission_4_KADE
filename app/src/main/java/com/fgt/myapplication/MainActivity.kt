package com.fgt.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.View
import android.view.ViewManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_main1.*
import kotlinx.android.synthetic.main.activity_main1.bottom_navigation
import kotlinx.android.synthetic.main.activity_main1.view.*
import kotlinx.android.synthetic.main.activity_main2.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.__SearchView_OnQueryTextListener
import org.jetbrains.anko.sdk25.coroutines.onMenuItemClick
import org.jetbrains.anko.toolbar

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: RecyclerViewAdapter
    private var items: MutableList<Item> = mutableListOf()

    private lateinit var listTeam: RecyclerView

    inline fun ViewManager.customFrameLayout(theme: Int = 0, init: FrameLayout.() -> Unit): FrameLayout {
        return ankoView({ FrameLayout(it) }, theme = theme, init = init)


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        initData()
        mainUI()


        adapter = RecyclerViewAdapter(items){
            startActivity(intentFor<Main1Activity>("name" to it, "id" to it.id))
        }
        listTeam.adapter=adapter
    }

    private fun initData(){
        val id = resources.getStringArray(R.array.club_id)
        val name = resources.getStringArray(R.array.club_name)
        val image = resources.obtainTypedArray(R.array.club_image)
        val desc = resources.getStringArray(R.array.club_desc)
        items.clear()
        for (i in name.indices) {
            items.add(Item(id[i],name[i],
                desc[i],
                image.getResourceId(i, 0)

            ))

        }

        //Recycle the typed array
        image.recycle()
    }

    private fun mainUI() {

        linearLayout {
            toolbar {
                id = R.id.toolbar
                title = "Daftar Liga"
                popupTheme = R.style.AppTheme
                lparams(
                    width = matchParent, height = wrapContent
                )
                backgroundColor = R.color.replyblue

                inflateMenu(R.menu.menu_search)

                val searchitem = menu?.findItem(R.id.search)
                
                searchView().setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        searchView().clearFocus()
                        searchView().setQuery("", false)
                        searchitem!!.collapseActionView();
                        Toast.makeText(this@MainActivity, "Looking for $query", Toast.LENGTH_LONG)
                            .show()
                        val searchMatch = SearchMatch.newInstance(query,"4328")
                        val fragmentManager = supportFragmentManager
                        val fragmentTransaction = fragmentManager.beginTransaction()
                        fragmentTransaction.replace(R.id.mainContainer, searchMatch)
                        fragmentTransaction.commit()
//                fragment = SearchMatch()
//
//                val transaction = fragmentManager.beginTransaction()
//
//                transaction.replace(R.id.mainContainer, fragment).commit()
                        return true
                    }

                    override fun onQueryTextChange(p0: String?): Boolean {
                        return false
                    }
                })
            }

            frameLayout {
                id = R.id.mainContainer
                lparams(width = matchParent, height = matchParent)
            }

            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            relativeLayout {
                lparams(width = matchParent, height = wrapContent)

                listTeam = recyclerView {
                    id = R.id.club_list
                    lparams(width = matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(this@MainActivity)
                }

            }

        }
    }

}
