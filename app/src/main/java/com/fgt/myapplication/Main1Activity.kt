package com.fgt.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.fgt.myapplication.api.ApiRepository
import com.fgt.myapplication.model.DetailLiga
import com.fgt.myapplication.model.Events
import com.fgt.myapplication.presenter.DetailLigaPresenter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main1.*
import kotlinx.android.synthetic.main.activity_main1.toolbar

class Main1Activity : AppCompatActivity(), DetailLigaView {

    private var detail: MutableList<DetailLiga> = mutableListOf()
    override fun getDetailLiga(data: List<DetailLiga>) {
        detail.addAll(data)

        txtname.text = detail.first().league
        txtdesc.text = detail.first().desc
    }

    private var idLiga: String=""
    private var name: String = ""
    private var desc: String = ""
    private lateinit var presenter: DetailLigaPresenter

    var items: Item? = null


    private lateinit var fragment: Fragment
    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)

        setSupportActionBar(toolbar)
        bottom_navigation.inflateMenu(R.menu.bottom_navigation_menu)
        fragmentManager = getSupportFragmentManager()

        fragmentManager.beginTransaction().replace(R.id.mainContainer, PreviousMatch()).commit()

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            val id = item.getItemId()
            when (id) {
                R.id.previous -> fragment = PreviousMatch()
                R.id.next -> fragment = NextMatch()
                R.id.fav -> fragment = FavoriteMatchesFragment()
            }

            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.mainContainer, fragment).commit()
            true
        }

        val intent = intent
        items = intent.getParcelableExtra("name")

        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailLigaPresenter(this, request, gson)
        presenter.getDetailLiga(items?.id)
//        idLiga = intent.("id").toString()
        Glide.with(this).load(items?.image).into(logo)
        loadPreviousFragment()
    }
//    override fun getDetailLiga(data: List<DetailLiga>) {
//        txtname.text = data.lastIndex.
//        txtdesc.text = data.desc
//    }

    @SuppressLint("ServiceCast")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)
//        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchView

        val searchitem = menu?.findItem(R.id.search)


        val searchView = searchitem?.actionView as SearchView

//        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                searchView.clearFocus()
                searchView.setQuery("",false)
                searchitem.collapseActionView();
                Toast.makeText(this@Main1Activity, "Looking for $query", Toast.LENGTH_LONG).show()
               val searchMatch = SearchMatch.newInstance(query,idLiga)
                val fragmentManager = supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.mainContainer, searchMatch)
                fragmentTransaction.commit()
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }


    private fun loadPreviousFragment() {

        fragmentManager = getSupportFragmentManager()

        fragmentManager.beginTransaction().replace(R.id.mainContainer, PreviousMatch()).commit()

    }
}
