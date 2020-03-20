package com.example.team

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectteam.FragmentGraphSkill
import com.example.projectteam.FragmentTask
import com.example.projectteam.R
import org.json.JSONArray

class MyRecyclerAdapter(fragment: FragmentActivity, val dataSource: JSONArray) : RecyclerView.Adapter<MyRecyclerAdapter.Holder>() {

    private val thiscontext : Context = fragment.baseContext
    private val thisFragment = fragment

    class Holder(view : View) : RecyclerView.ViewHolder(view) {

        private val View = view

        lateinit var layOut : LinearLayout
        lateinit var titleTextView: TextView
        lateinit var detailTextView: TextView
        lateinit var image: ImageView
        lateinit var imageURL: String

        lateinit var javaScript: String
        lateinit var css: String
        lateinit var html: String
        lateinit var sql: String
        lateinit var php: String

        fun Holder(){

            layOut = View.findViewById<View>(R.id.recyview_layout) as LinearLayout
            titleTextView = View.findViewById<View>(R.id.name) as TextView
            detailTextView = View.findViewById<View>(R.id.positon) as TextView
            image = View.findViewById<View>(R.id.imageProfile) as ImageView

        }

    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.recy_layout_member, parent, false))
    }


    override fun getItemCount(): Int {
        return dataSource.length()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.Holder()

        holder.titleTextView.text = dataSource.getJSONObject(position).getString("firstname").toString()
        holder.detailTextView.text = dataSource.getJSONObject(position).getString("position").toString()
        holder.imageURL = dataSource.getJSONObject(position).getString("image").toString()

        holder.css = dataSource.getJSONObject(position).getString("css").toString()
        holder.html = dataSource.getJSONObject(position).getString("html").toString()
        holder.sql = dataSource.getJSONObject(position).getString("sql").toString()
        holder.php = dataSource.getJSONObject(position).getString("php").toString()
        holder.javaScript = dataSource.getJSONObject(position).getString("javaScript").toString()

        Glide.with(thiscontext)
            .load(dataSource.getJSONObject(position).getString("image").toString())
            .into(holder.image)

        holder.layOut.setOnClickListener{

            val graphSkill = FragmentGraphSkill().newInstance(holder.titleTextView.text.toString(), holder.detailTextView.text.toString(), holder.imageURL)
            graphSkill.setSkill( holder.css, holder.html, holder.sql, holder.php, holder.javaScript)
            val fm = thisFragment.supportFragmentManager
            val transaction =  fm.beginTransaction()
            transaction.replace(R.id.layout, graphSkill,"fragment_MemberDetail")
            transaction.addToBackStack("fragment_MemberDetail")
            transaction.commit()

        }

    }



}

