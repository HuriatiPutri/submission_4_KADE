package com.fgt.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fgt.myapplication.R
import com.fgt.myapplication.helper.Favorite
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick

class FavoriteMatchAdapter(private val favoriteMatch : List<Favorite>,
                           private val listener: (Favorite) -> Unit)
    : RecyclerView.Adapter<FavMatchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavMatchViewHolder {

        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_match, parent, false)
        return FavMatchViewHolder(view)
    }

    override fun getItemCount(): Int = favoriteMatch.size


    override fun onBindViewHolder(holder: FavMatchViewHolder, position: Int) {
        holder.bindItem(favoriteMatch[position], listener)
    }

}

class FavMatchViewHolder (view: View) : RecyclerView.ViewHolder(view) {

    val mDateEvent  : TextView = view.find(R.id.tv_date_event)
    val mHomeTeam   : TextView = view.find(R.id.tv_home_team)
    val mAwayTeam   : TextView = view.find(R.id.tv_away_team)
    val mHomeScore  : TextView = view.find(R.id.tv_home_score)
    val mAwayScore  : TextView = view.find(R.id.tv_away_score)
    val mTime       : TextView = view.find(R.id.tv_time_event)

    /* TODO : Delete parameter -> listener: NextMatchFragment.OnFragmentInteractionListener?
     * Ganti dengan -> listener : (FavoriteMatch) -> Unit
     */
    fun bindItem(itemFav: Favorite, listener: (Favorite) -> Unit ) {
        mDateEvent.text = itemFav.dateEvent
        mHomeTeam.text  = itemFav.homeTeam
        mAwayTeam.text  = itemFav.awayTeam
        if(itemFav.homeScore.equals("null"))
            mHomeScore.text = ""
        else
        mHomeScore.text = itemFav.homeScore

        if(itemFav.awayScore.equals("null"))
            mAwayScore.text = ""
        else
        mAwayScore.text = itemFav.awayScore
//        mTime.text      = itemFav.time

        // Menggunakan simple fragment, dan listener : (FavoriteMatch) -> Unit
        itemView.onClick { listener(itemFav) }

     /*  // Tidak digunakan karean pakai simple fragment diatas
        itemView.setOnClickListener {
            listener?.onFragmentInteraction(item)
        }
    */

    }
}


