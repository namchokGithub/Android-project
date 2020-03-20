package com.example.projectteam

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.team.MyRecyclerAdapter
import com.facebook.login.LoginManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.json.JSONArray
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 */
class FragmentMemberOfTeam : Fragment() {

    var PhotoURL : String = ""
    var Name : String = ""
    var User : String = ""

    fun newInstance(url: String,name : String): FragmentMemberOfTeam {

        val profile = FragmentMemberOfTeam()
        val bundle = Bundle()
        bundle.putString("PhotoURL", url)
        bundle.putString("Name", name)
        profile.setArguments(bundle)

        return profile
    }

    fun setUser(user: String, pass : String): FragmentMemberOfTeam {

        val profile = FragmentMemberOfTeam()
        val bundle = Bundle()
        bundle.putString("user", user)
        bundle.putString("pass", pass)
        profile.setArguments(bundle)

        return profile
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_member_of_team, container, false)

        val ivProfilePicture = view.findViewById(R.id.iv_profile) as ImageView
        val textUsername = view.findViewById(R.id.textUsername) as TextView
        val loginButton = view.findViewById(R.id.btnLogin) as Button

        Glide.with(activity!!.baseContext)
            .load(PhotoURL)
            .into(ivProfilePicture)

        PhotoURL?.let{
            Glide.with(activity!!.baseContext)
                .load("https://www.kindpng.com/picc/m/24-248325_profile-picture-circle-png-transparent-png.png")
                .into(ivProfilePicture)
        }


        textUsername.setText(Name)
        Name?.let { textUsername.setText(User) }



        loginButton.setOnClickListener{
            LoginManager.getInstance().logOut()
            activity!!.supportFragmentManager.popBackStack()
        }


        val mRootRef = FirebaseDatabase.getInstance().reference
        val mMessagesRef = mRootRef.child("team2")

        mMessagesRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val data = JSONArray()
                val recyclerView: RecyclerView = view.findViewById(R.id.recyLayout)
                val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity!!.baseContext)
                recyclerView.layoutManager = layoutManager
                for (ds in dataSnapshot.children) {

                    val jObject = JSONObject()

                    val firstname = ds.child("firstname").getValue(String::class.java)!!

                    val position = ds.child("position").getValue(String::class.java)!!
                    val image = ds.child("image").getValue(String::class.java)!!

                    jObject.put("key",ds.key)
                    jObject.put("firstname",firstname)
                    jObject.put("position",position)
                    jObject.put("image",image)

                    data.put(jObject)

                }

                val adapter = MyRecyclerAdapter(activity!!.baseContext as FragmentActivity,data)

                recyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {

            }


        })


//        val jsonString : String = loadJsonFromAsset("recipes.json", activity!!.baseContext).toString()
//        val json = JSONObject(jsonString)
//        val jsonArray = json.getJSONArray("recipes")
//
//        val recyclerView: RecyclerView = view.findViewById(R.id.recyLayout)
//
//        //ตั้งค่า Layout
//        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity!!.baseContext)
//        recyclerView.layoutManager = layoutManager
//
//        //ตั้งค่า Adapter
//        val adapter = MyRecyclerAdapter(activity!!.baseContext as FragmentActivity,jsonArray)
//        recyclerView.adapter = adapter


        // Inflate the layout for this fragment
        return view

    }

    private fun loadJsonFromAsset(filename: String, context: Context): String? {
        val json: String?

        try {
            val inputStream = context.assets.open(filename)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, Charsets.UTF_8)
        } catch (ex: java.io.IOException) {
            ex.printStackTrace()
            return null
        }

        return json
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments
        if (bundle != null) {
            PhotoURL = bundle.getString("PhotoURL").toString()
            Name = bundle.getString("Name").toString()
            User = bundle.getString("user").toString()
        }

    }


}
