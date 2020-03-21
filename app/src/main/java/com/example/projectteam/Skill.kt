package com.example.projectteam

class SkillData(var name : String, var score : Float) {


    companion object {
        fun setScoreOfGraph(size: Int, skill : Array<String>, score : Array<Float>): ArrayList<SkillData> {
            val student: ArrayList<SkillData> = ArrayList()
            for (i in 0 until size) {
                student.add(SkillData(skill[i], score[i]))
            }
            return student
        }
    }


}