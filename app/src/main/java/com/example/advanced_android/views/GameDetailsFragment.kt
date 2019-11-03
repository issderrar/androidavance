package com.example.advanced_android.views

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.advanced_android.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_game_details.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ID_PARAM = "gameId"


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [GameDetailsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [GameDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameDetailsFragment : Fragment() {
    private var gameId : Int? = null
    private var url: VisitWebsite? = null
    private var link: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            gameId = it.getInt(ID_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_game_details, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            fetchData(it)
        }
        button.setOnClickListener {
            val openURL = Intent(android.content.Intent.ACTION_VIEW)
            openURL.data = Uri.parse(link)
            startActivity(openURL)
        }
    }

    private fun fetchData(context: Context) {
        val queue = Volley.newRequestQueue(context)
        val request = JsonObjectRequest(
            Request.Method.GET,
            "https://my-json-server.typicode.com/bgdom/cours-android/games/"+gameId,
            null,
            Response.Listener { response ->
                title.text = response.getString("name")
                description.text = response.getString("description")
                Picasso.get().load(response.getString("img")).into(imageView)
                link = response.getString("link")

            },
            Response.ErrorListener { error ->
                Log.e("test", error.localizedMessage)
            }
        )

        queue.add(request)
    }


    interface VisitWebsite {
        fun visitWebsite(link : String)
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GameDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(gameId : Int) =
            GameDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ID_PARAM, gameId)
                }
            }
    }
}
