package com.example.projectteam

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * A simple [Fragment] subclass.
 */
class FragmentTask : Fragment() {

    var name: String = ""
    var position: String = ""
    var image: String = ""

    fun newInstance(name: String, position : String, image : String): FragmentTask {

        val fragmentTask = FragmentTask()
        val bundle = Bundle()
        bundle.putString("position", position)
        bundle.putString("name", name)
        bundle.putString("image", image)
        fragmentTask.setArguments(bundle)

        return fragmentTask
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bundle = arguments
        if (bundle != null) {
            image = bundle.getString("image").toString()
            name = bundle.getString("name").toString()
            position = bundle.getString("position").toString()
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task, container, false)
    }

}
