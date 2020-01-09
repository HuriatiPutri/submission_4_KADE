package com.fgt.myapplication

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.insert
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.fgt.myapplication.api.ApiRepository
import com.fgt.myapplication.helper.Favorite
import com.fgt.myapplication.helper.database
import com.fgt.myapplication.model.DetailEvent
import com.fgt.myapplication.model.Team
import com.fgt.myapplication.R.menu.detail_menu
import com.fgt.myapplication.R.id.add_to_favorite
import com.fgt.myapplication.R.drawable.*
import com.fgt.myapplication.model.DetailLiga
import com.fgt.myapplication.presenter.DetailPresenter
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast

class DetailActivity : AppCompatActivity(), DetailView {

    private var idHomeTeam: String = ""
    private var idAwayTeam: String = ""
    private lateinit var presenter: DetailPresenter

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false


    private lateinit var idEvent : String
    private var home_team : String? = ""
    private var away_team : String? = ""
    private var home_score : String? = ""
    private var away_score : String? = ""
    private var date_event : String? = ""
    private var time_event : String? = ""
    //private lateinit var matchDataItem: MatchDataItem
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private var detail: MutableList<DetailEvent> = mutableListOf()
    override fun getDetailEvent(data: List<DetailEvent>) {
        detail.addAll(data)

        idHomeTeam = detail.first().homeTeamId.toString()
        idAwayTeam = detail.first().awayTeamId.toString()
        when (detail.first().eventId) {
            idEvent -> {

                eventName.text = detail.first().event
                leagueName.text = detail.first().league
                homeTeam.text = detail.first().homeTeam
                awayTeam.text = detail.first().awayTeam
                homeScore.text = detail.first().homeScore
                awayScore.text = detail.first().awayScore
                goalHomeDetail.text = detail.first().homeGoalDetail
                goalAwayDetail.text = detail.first().awayGoalDetail
                homeRedCardDetail.text = detail.first().homeRedCards
                awayRedCardDetail.text = detail.first().awayRedCards
                homeYellowCardDetail.text = detail.first().homeYellowCards
                awayYellowCardDetail.text = detail.first().awayYellowCards
                vs.text = "VS"
            }
        }

        presenter.getTeam(idHomeTeam)
        presenter.getTeam(idAwayTeam)
    }

    override fun showTeam(data: Team) {
        Log.d("idHome2", idHomeTeam)
        when (data.teamId) {

            idHomeTeam -> {
                Picasso.get().load(data.teamBadge).into(homeBadge)
            }

            idAwayTeam -> {
                Picasso.get().load(data.teamBadge).into(awayBadge)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


        setSupportActionBar(toolbar)

        supportActionBar?.title = "Event Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        idEvent = intent.getStringExtra("idEvent")
        home_team = intent.getStringExtra("home_team")
        away_team = intent.getStringExtra("away_team")
        home_score = intent.getStringExtra("home_score")
        away_score = intent.getStringExtra("away_score")
        date_event = intent.getStringExtra("date_event")

        Toast.makeText(this,home_team, Toast.LENGTH_LONG).show()
        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailPresenter(this, request, gson)

        presenter.getDetailEvent(idEvent)

        favoriteState()
    }

    // Membuat Favorite menu pada toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //menuInflater.inflate(detail)
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }

    // fungsi add & remove dan set icon favorite pada saat menu fav di click
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            add_to_favorite -> {
                if (isFavorite)  removeToFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite() {

        // Log.d("Debug", "Add")

        swipeRefreshLayout = detail_swipe_refresh

        try {
            database.use {
                insert(
                    Favorite.TABLE_FAV_MATCH,
                    Favorite.ID_EVENT to idEvent,
                    Favorite.DATE_EVENT to date_event,
                    Favorite.HOME_TEAM to home_team,
                    Favorite.AWAY_TEAM to away_team,
                    Favorite.HOME_SCORE to home_score,
                    Favorite.AWAY_SCORE to away_score
                )
            }
//            Toast.makeText(this@DetailActivity, swipeRefreshLayout, "Added to Your Favorite Match").show()
        }catch (e: SQLiteConstraintException){
//            snackbar(swipeRefreshLayout, e.localizedMessage).show()
        }
    }

    private fun removeToFavorite() {

        // Log.d("Debug", "Remove")

        swipeRefreshLayout = detail_swipe_refresh

        try {
            database.use {
                delete(Favorite.TABLE_FAV_MATCH, "( ID_EVENT = {id_event} )",
                    "id_event" to idEvent)
            }
//            snackbar(swipeRefreshLayout, "Remove From Favorite Match").show()
        }catch (e: SQLiteConstraintException){
//            snackbar(swipeRefreshLayout, e.localizedMessage).show()
        }
    }

    // Membuat Status/State Favorite
    private fun favoriteState(){
        database.use {
            val result = select(Favorite.TABLE_FAV_MATCH)
                .whereArgs("( ID_EVENT = {id_event})",
                    "id_event" to idEvent)
            val favoriteMatch = result.parseList(classParser<Favorite>())
            if (!favoriteMatch.isEmpty()) isFavorite = true
        }
    }
}
