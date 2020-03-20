package com.example.projectteam

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.facebook.login.LoginManager

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
        // Inflate the layout for this fragment
        return view

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
