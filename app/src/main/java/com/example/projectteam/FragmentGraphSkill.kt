package com.example.projectteam

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.facebook.FacebookSdk
import com.facebook.login.LoginManager
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_graph_skill.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentGraphSkill : Fragment() {

    var name: String = ""
    var position: String = ""
    var image: String = ""
    lateinit var skill : Array<String>

    var css: String = ""
    var php: String = ""
    var html: String = ""
    var javaScript: String = ""
    var sql: String = ""

    lateinit var Pie_id : PieChart

    fun newInstance(name: String, position : String, image : String, skill : Array<String>): FragmentGraphSkill {

        val graph = FragmentGraphSkill()
        val bundle = Bundle()
        bundle.putString("position", position)
        bundle.putString("name", name)
        bundle.putString("image", image)

        bundle.putStringArray("skill", skill)

        graph.setArguments(bundle)

        return graph
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        var view = inflater.inflate(R.layout.fragment_graph_skill, container, false)

        val imageURL = view.findViewById<ImageView>(R.id.imageMember)

        val btnBack = view.findViewById<Button>(R.id.Back)
        val btnChoose = view.findViewById<Button>(R.id.choose)

        val textName = view.findViewById<TextView>(R.id.nameMember)
        val textPos = view.findViewById<TextView>(R.id.positionMember)

        val textTitle = view.findViewById<TextView>(R.id.Skill)
        val textPhp = view.findViewById<TextView>(R.id.php)
        val textHtml = view.findViewById<TextView>(R.id.html)
        val textCSS = view.findViewById<TextView>(R.id.css)
        val textSQL = view.findViewById<TextView>(R.id.sql)
        val textJavascript = view.findViewById<TextView>(R.id.javascript)


        val bundle = arguments
        if (bundle != null) {
            image = bundle.getString("image").toString()
            name = bundle.getString("name").toString()
            position = bundle.getString("position").toString()
            skill = bundle.getStringArray("skill") as Array<String>

            php = skill[3]
            css =  skill[0]
            html =  skill[1]
            javaScript =  skill[4]
            sql = skill[2]

            // Toast.makeText(activity, php, Toast.LENGTH_SHORT).show()
        }

        Pie_id = view.findViewById(R.id.pie_id)
        pieChart(Pie_id)

        textTitle.text = "คะแนนทักษะของ$name"
        textHtml.text = "HTML $html คะแนน"
        textCSS.text = "CSS $css คะแนน"
        textPhp.text = "PHP $php คะแนน"
        textJavascript.text = "JavaScript $javaScript คะแนน"
        textSQL.text = "SQL $sql คะแนน"

        textName.text = name
        textPos.text = position

        Glide.with(activity!!.baseContext)
            .load(image)
            .into(imageURL)

        btnBack.setOnClickListener {
            activity?.onBackPressed()
        }


        //ประกาศตัวแปร DatabaseReference รับค่า Instance และอ้างถึง path ที่เราต้องการใน database
        val mRootRef = FirebaseDatabase.getInstance().reference

        //อ้างอิงไปที่ path ที่เราต้องการจะจัดการข้อมูล ตัวอย่างคือ users และ messages
        val mMember = mRootRef.child("choose member")

        btnChoose.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(activity!!)
            builder.setMessage("คุณต้องการเลือก $name?")
            builder.setPositiveButton("ตกลง"
            ) { dialog, id ->
                val message = ChooseData(name, true)
                mMember.push().setValue(message)
                Toast.makeText(activity, "คุณได้เลือก $name แล้ว", Toast.LENGTH_SHORT).show()
                activity?.onBackPressed()
            }
            builder.setNegativeButton("ยกเลิก"
            ) { dialog, which ->
                Toast.makeText(
                    FacebookSdk.getApplicationContext(),
                    "ยกเลิก", Toast.LENGTH_SHORT
                ).show()
            }
            builder.show()


        }


        return view
    }

    private fun pieChart(chart: PieChart){

        //ปิด Description
        chart.description.isEnabled = false
        val skill : Array<String> = arrayOf("HTML", "CSS", "PHP", "SQL", "JAVASCRIPT")
        val score : Array<Float> = arrayOf(html.toFloat(), css.toFloat(), php.toFloat(), sql.toFloat(), javaScript.toFloat())

        //สุ่มข้อมูล 5 อัน
        val listData = SkillData.setScoreOfGraph(5, skill, score)

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
        dataset.setColors(*ColorTemplate.PASTEL_COLORS) // set the color

        //เซ้ทช่องว่างความห่างของข้อมูล
        dataset.sliceSpace = 1f

        //กำหนดข้อมูล
        val data = PieData(dataset)
        chart.data = data

        //กำหนดให้มีช่องว่างตรงกลางได้
        chart.holeRadius = 60F
        chart.transparentCircleRadius = 40F

        //กำหนด animation
        chart.animateY(1000)

        //อาตัวหนังสือออกมาไว้ข้างนอกตัวแผนภูมิ
        //X คือ ชื่อข้อมูล
        //Y คือ ค่าของข้อมูล
        dataset.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        dataset.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE

        //เส้นที่โยงออกมา
        dataset.valueLinePart1Length = 0.5f
        dataset.valueLinePart2Length = 0.5f

        //กำหนดให้แสดงเป็น %
        chart.setUsePercentValues(true)
        dataset.valueFormatter = PercentFormatter(chart)

        // entry label styling
        chart.setEntryLabelColor(Color.BLACK)

        //ข้อความตรงกลางแผนภูมิ
        chart.centerText = "Skill Of Programming"
        chart.setCenterTextSize(10F)

    }

    data class ChooseData(
        var name: String? = "",
        var status: Boolean
    )


}
