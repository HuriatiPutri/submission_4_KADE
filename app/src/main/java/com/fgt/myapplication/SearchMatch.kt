package com.fgt.myapplication

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.fgt.myapplication.adapter.EventAdapter
import com.fgt.myapplication.api.ApiRepository
import com.fgt.myapplication.model.DetailEvent
import com.fgt.myapplication.model.Events
import com.fgt.myapplication.presenter.MainPresenter
import com.google.gson.Gson
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.onRefresh

class SearchMatch : Fragment(), MainView {
    override fun toast() {
        Toast.makeText(context,"Data tidak ada", Toast.LENGTH_LONG).show()
    }
    companion object{
        const val ARG_PARAMS ="PARAMS"
        const val ID_PARAMS ="ID PARAMS"

        fun newInstance(name:String, id:String):SearchMatch{
            val searchMatch = SearchMatch()
            val bundle = Bundle().apply {
                putString(ARG_PARAMS, name)
                putString(ID_PARAMS, id)
            }
            searchMatch.arguments = bundle
            return searchMatch
        }
    }
    private var events: MutableList<Events> = mutableListOf()
    private lateinit var presenter: MainPresenter
    private lateinit var eventList: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var adapter: EventAdapter
    private var listener: OnFragmentInteractionListener? = null
    var items: Item? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_previous, container, false)
        eventList = rootView.findViewById(R.id.prevEventMatch) as RecyclerView
        eventList.layoutManager = LinearLayoutManager(activity)

        adapter = EventAdapter(events) {
            activity?.startActivity(intentFor<DetailActivity>(
                "idEvent" to "${it.eventId}",
                "home_team" to "${it.homeTeam}",
                "away_team" to "${it.awayTeam}",
                "home_score" to "${it.homeScore}",
                "away_score" to "${it.awayScore}",
                "date_event" to "${it.dateEvent}"

            ))
        }

        eventList.adapter = adapter
        progressBar = rootView.findViewById(R.id.progressBar) as ProgressBar
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefresh) as SwipeRefreshLayout


        val request = ApiRepository()
        val gson = Gson()
        presenter = MainPresenter(this, request, gson)


        val name = arguments?.getString(ARG_PARAMS)
        val id = arguments?.getString(ID_PARAMS)

        presenter.getSearchEventsList(name,id);
        swipeRefreshLayout.onRefresh {
            presenter.getSearchEventsList(name,id)
        }

        return rootView
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun showLoading() {
        progressBar.visible()

    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showPrevEvent(data: List<Events>) {

        swipeRefreshLayout.isRefreshing = false
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()

    }

    override fun showNextEvent(data: List<Events>) {

        swipeRefreshLayout.isRefreshing = false
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()

    }
    override fun showSearchEvent(data: List<Events>) {
        swipeRefreshLayout.isRefreshing = false
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
    }
    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }
}
