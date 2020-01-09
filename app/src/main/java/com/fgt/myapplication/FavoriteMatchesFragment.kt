package com.fgt.myapplication


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.fgt.myapplication.adapter.FavoriteMatchAdapter
import com.fgt.myapplication.helper.Favorite
import com.fgt.myapplication.helper.database
import kotlinx.android.synthetic.main.fragment_favorite_matches.*

import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.onRefresh

class FavoriteMatchesFragment : Fragment() {

    private var favoriteMatch : MutableList<Favorite> = mutableListOf()
    private lateinit var adapter: FavoriteMatchAdapter
    private lateinit var listMatch : RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = FavoriteMatchAdapter(favoriteMatch){
            activity?.startActivity(intentFor<DetailActivity>(
                "idEvent" to "${it.idEvent}",
                "home_team" to "${it.homeTeam}",
                "away_team" to "${it.awayTeam}",
                "home_score" to "${it.homeScore}",
                "away_score" to "${it.awayScore}",
                "date_event" to "${it.dateEvent}"

            ))
        }

//        listMatch.layoutManager = LinearLayoutManager(ctx);

        listMatch = rv_fav_match
        listMatch.layoutManager = LinearLayoutManager(ctx)

        listMatch.adapter = adapter
        showFavoriteMatch()

        fav_swipe_refresh.onRefresh {
            favoriteMatch.clear()
            showFavoriteMatch()
        }

    }

    private fun showFavoriteMatch() {
        context?.database?.use {
            fav_swipe_refresh.isRefreshing = false
            val result = select(Favorite.TABLE_FAV_MATCH)
            val favorite = result.parseList(classParser<Favorite>())
            favoriteMatch.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_matches, container, false)
    }

    override fun onResume() {
        super.onResume()
        favoriteMatch.clear()
        showFavoriteMatch()
    }
//
}
