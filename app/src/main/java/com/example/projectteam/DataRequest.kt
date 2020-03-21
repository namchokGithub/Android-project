package com.example.projectteam

import android.content.Context
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.team.MyRecyclerAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.apache.http.client.ClientProtocolException
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class DataRequest constructor(actility: FragmentActivity, inflater: LayoutInflater, container: ViewGroup?) : AsyncTask<String, Void, String>() {

    var actility: FragmentActivity = actility
    var inflater: LayoutInflater = inflater
    var container: ViewGroup? = container


    override fun doInBackground(vararg params: String?): String? {
        var result: String? = ""

        try {
            getData()
        } catch (e: ClientProtocolException) {
        } catch (e: IOException) {
        }

        return result!!
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
    }

    private fun getData() {
        val view = inflater.inflate(R.layout.fragment_member_of_team, container, false)
        val mRootRef = FirebaseDatabase.getInstance().reference
        val mMessagesRef = mRootRef.child("team2")
        val recyclerView: RecyclerView = view.findViewById(R.id.recyLayout)
        mMessagesRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val data = JSONArray()

                val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(actility!!.baseContext)

                recyclerView.layoutManager = layoutManager

                for (ds in dataSnapshot.children) {

                    val jObject = JSONObject()

                    val firstname = ds.child("firstname").getValue(String::class.java)!!
                    val position = ds.child("position").getValue(String::class.java)!!
                    val image = ds.child("image").getValue(String::class.java)!!

                    val css = ds.child("css").getValue(String::class.java)!!
                    val php = ds.child("php").getValue(String::class.java)!!
                    val html = ds.child("html").getValue(String::class.java)!!
                    val sql = ds.child("sql").getValue(String::class.java)!!
                    val javaScript = ds.child("javascript").getValue(String::class.java)!!

                    jObject.put("key", ds.key)
                    jObject.put("firstname", firstname)
                    jObject.put("position", position)
                    jObject.put("image", image)

                    jObject.put("css", css)
                    jObject.put("php", php)
                    jObject.put("html", html)
                    jObject.put("sql", sql)
                    jObject.put("javaScript", javaScript)

                    data.put(jObject)

                }

                val adapter = MyRecyclerAdapter(actility, data)
                recyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(actility.baseContext, error.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }

}
