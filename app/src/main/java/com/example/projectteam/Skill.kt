package com.example.projectteam

class Skill(var name : String, var score : Float) {


    companion object {
        fun setScoreOfGraph(size: Int, skill : Array<String>, score : Array<Float>): ArrayList<Skill> {
            val student: ArrayList<Skill> = ArrayList()
            for (i in 0 until size) {
                student.add(Skill(skill[i], score[i]))
            }
            return student
        }
    }


}