package com.example.advanced_android.views

import android.content.Context
import android.content.LocusId
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.advanced_android.Game
import com.example.advanced_android.GameAdapter

import com.example.advanced_android.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_games_list.*
import kotlinx.android.synthetic.main.item_layout.view.*
import org.json.JSONArray
import org.json.JSONObject


class GamesListFragment : Fragment() {
    private var listener: ViewDetails? = null

    var gameList: ArrayList<Game> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_games_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = GameAdapter(gameList) {id ->
            onButtonPressed(id)
        }
        activity?.let {
            fetchData(it)
        }
    }


    fun onButtonPressed(id: Int) {
        listener?.viewDetails(id)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ViewDetails) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun fetchData(context: Context) {
        val queue = Volley.newRequestQueue(context)
        gameList.clear()
        val request = JsonArrayRequest(
            Request.Method.GET,
            "https://my-json-server.typicode.com/bgdom/cours-android/games/",
            null,
            Response.Listener { response ->
                for (i in 0 until response.length()) {
                    gameList.add(
                        Game(
                            response.getJSONObject(i).getInt("id"),
                            response.getJSONObject(i).getString("name"),
                            response.getJSONObject(i).getString("description"),
                            response.getJSONObject(i).getString("link"),
                            response.getJSONObject(i).getString("img")
                        )
                    )
                }
                initRecyclerView(gameList)
            },
            Response.ErrorListener { error ->
                Log.e("test", error.localizedMessage)
            }
        )

        queue.add(request)
    }

    fun initRecyclerView(gameList: ArrayList<Game>) {
        recyclerView.adapter?.notifyDataSetChanged()
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface ViewDetails {
        fun viewDetails(id: Int)
    }

}
