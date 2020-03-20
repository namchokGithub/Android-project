package com.example.projectteam

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.fragment_graph_skill.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentGraphSkill : Fragment() {

    var name: String = ""
    var position: String = ""
    var image: String = ""

    var css: String = ""
    var php: String = ""
    var html: String = ""
    var javaScript: String = ""
    var sql: String = ""

    lateinit var Pie_id : PieChart

    fun newInstance(name: String, position : String, image : String): FragmentGraphSkill {

        val graph = FragmentGraphSkill()
        val bundle = Bundle()
        bundle.putString("position", position)
        bundle.putString("name", name)
        bundle.putString("image", image)
        graph.setArguments(bundle)

        return graph
    }

    fun setSkill(css: String, php : String, html : String, javaScript : String, sql : String): FragmentGraphSkill {

        val graph = FragmentGraphSkill()
        val bundle = Bundle()
        bundle.putString("php", php)
        bundle.putString("css", css)
        bundle.putString("html", html)
        bundle.putString("javaScript", javaScript)
        bundle.putString("sql", sql)
        graph.setArguments(bundle)

        return graph
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        var view = inflater.inflate(R.layout.fragment_graph_skill, container, false)

        val bundle = arguments
        if (bundle != null) {
            image = bundle.getString("image").toString()
            name = bundle.getString("name").toString()
            position = bundle.getString("position").toString()

            php = bundle.getString("php").toString()
            css = bundle.getString("css").toString()
            html = bundle.getString("html").toString()
            javaScript = bundle.getString("javaScript").toString()
            sql = bundle.getString("sql").toString()

            Toast.makeText(activity, php, Toast.LENGTH_SHORT).show()


        }

        Pie_id = view.findViewById(R.id.pie_id)
//        Pie_Chart(Pie_id)

        val textName = view.findViewById<TextView>(R.id.nameMember)
        val textPos = view.findViewById<TextView>(R.id.positionMember)

        textName.text = name
        textPos.text = position

//        Glide.with(activity!!.baseContext)
//            .load(image)
//            .into(imageMember)


        return view
    }

    fun Pie_Chart( chart: PieChart){

        //ปิด Description
        chart.description.isEnabled = false
        val skill : Array<String> = arrayOf("HTML", "CSS", "PHP", "SQL", "JAVASCRIPT")
        val score : Array<Float> = arrayOf(html.toFloat(), css.toFloat(), php.toFloat(), sql.toFloat(), javaScript.toFloat())

        //สุ่มข้อมูล 5 อัน
        val listData = Skill.setScoreOfGraph(5, skill, score)


        val entries: ArrayList<PieEntry> = ArrayList()
        for (score in listData) {
            entries.add(PieEntry(score.score, score.name))
        }

        val dataset = PieDataSet(entries, "Skill of Member")

        //กำหนดให้มีช่องว่างตรงกลางได้
        dataset.selectionShift = 10f

        //กำหนด Size จำนสนข้อมูลที่แสดง
        dataset.valueTextSize = 10f

        //ตั้งค่า color
        dataset.setColors(*ColorTemplate.COLORFUL_COLORS) // set the color

        //เซ้ทช่องว่างความห่างของข้อมูล
        dataset.setSliceSpace(3f)

        //กำหนดข้อมูล
        val data = PieData(dataset)
        chart.setData(data)

        //กำหนดให้มีช่องว่างตรงกลางได้
        chart.setHoleRadius(30F)
        chart.setTransparentCircleRadius(40F)

        //กำหนด animation
        chart.animateY(3000)

        //อาตัวหนังสือออกมาไว้ข้างนอกตัวแผนภูมิ
        //X คือ ชื่อข้อมูล
        //Y คือ ค่าของข้อมูล
//        dataset.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE)
        dataset.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE)

        //เส้นที่โยงออกมา
        dataset.setValueLinePart1Length(0.5f)
        dataset.setValueLinePart2Length(0.5f)

        //กำหนดให้แสดงเป็น %
        chart.setUsePercentValues(true)
        dataset.setValueFormatter(PercentFormatter(chart))

        // entry label styling
        chart.setEntryLabelColor(Color.WHITE)

        //ข้อความตรงกลางแผนภูมิ
        chart.setCenterText("My Android");
        chart.setCenterTextSize(5F)

    }



}
