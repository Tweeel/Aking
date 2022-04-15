package com.example.todoapp_kotlin.pages.mainPage.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp_kotlin.R
import com.example.todoapp_kotlin.adapters.ParentAdapter
import com.example.todoapp_kotlin.database.entities.Parent
import com.example.todoapp_kotlin.database.entities.Task
import com.example.todoapp_kotlin.pages.addTaskPage.AddTaskActivity
import com.example.todoapp_kotlin.viewmodels.MyViewModel
import com.shrikanthravi.collapsiblecalendarview.data.Day
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar


class MonthFragment : Fragment(), ParentAdapter.TaskClickInterfaceParent {

    lateinit var viewModel: MyViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_month, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //declare views
        val collapsibleCalendar = view.findViewById<CollapsibleCalendar>(R.id.calander)!!
        recyclerView = view.findViewById(R.id.recyclerview)
        // initializing our view modal.
        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[MyViewModel::class.java]
        // on below line we are setting layout
        // manager to our recycler view.
        recyclerView.layoutManager = LinearLayoutManager(activity)
        // on below line we are initializing our adapter class.
        val parentAdapter = ParentAdapter(requireActivity(),this,viewModel)
        // on below line we are setting
        // adapter to our recycler view.
        recyclerView.adapter = parentAdapter
        recyclerView.setHasFixedSize(true)

        updateData(parentAdapter,collapsibleCalendar)

        setCalander(parentAdapter,collapsibleCalendar)
    }

    private fun updateData(parentAdapter: ParentAdapter, collapsibleCalendar:CollapsibleCalendar){

        val categories = ArrayList<Parent>()
        val dates = ArrayList<String>()
        viewModel.allTasks.observe(requireActivity()) { list ->
            list?.let {
                categories.clear()
                dates.clear()
                // on below line we are updating our list.
                it.forEach { task ->
                    task.date?.let{ dates.add(task.date)}
                }

                //use a hashset to delete the repeated element
                val datesSet: Set<String> = HashSet(dates)
                dates.clear()
                dates.addAll(datesSet)

                //use the commections sort to sort the list alphabetically
                dates.sort()

                //creat a categories of each date
                for (date in dates) {
                    categories.add(Parent(date, emptyList()))
                }

                //then add each task to hes date categorie
                for (category in categories) {
                    for (task in list) {
                        if (task.date.equals(category.title)) {
                            val thislist = ArrayList<Task>()
                            if (category.tasks.isEmpty()) {
                                thislist.add(task)
                                category.tasks=thislist
                            } else {
                                thislist.addAll(category.tasks)
                                thislist.add(task)
                                category.tasks = thislist
                            }
                        }
                    }
                }
                parentAdapter.updateList(categories)
                it.forEach { task ->
                    task.date?.let{
                        if(task.date[0].isDigit()){
                            val day = Day(task.date.drop(6).toInt(),
                                task.date.dropLast(5).drop(3).toInt()-1,
                                task.date.dropLast(8).toInt())
                            collapsibleCalendar.addEventTag(day.year, day.month, day.day, Color.parseColor("#3D3B62"))
                        }
                    }
                }
            }
        }
    }

    private fun updateDataByDate(parentAdapter: ParentAdapter, date: String) {

        viewModel.date.value = date
        Log.d("test",date)
        val categories = ArrayList<Parent>()
        val dates = ArrayList<String>()

        viewModel.tasksByDate.asLiveData().observe(requireActivity()) { list ->
            Log.d("test","in the live data")
            list?.let {
                Log.d("test","in the list of tasks")

                categories.clear()
                dates.clear()
                // on below line we are updating our list.
                it.forEach { task ->
                    task.date?.let{ dates.add(task.date)}
                }
                Log.d("test",dates.size.toString())
                dates.forEach { print(it) }

                //use a hashset to delete the repeated element
                val datesSet: Set<String> = HashSet(dates)
                dates.clear()
                dates.addAll(datesSet)
                Log.d("test",dates.size.toString())

                //use the commections sort to sort the list alphabetically
                dates.sort()
                Log.d("test","sorted"+dates.size.toString())

                //creat a categories of each date
                for (date in dates) {
                    categories.add(Parent(date, emptyList()))
                }
                Log.d("test",categories.size.toString())

                //then add each task to hes date categorie
                for (category in categories) {
                    for (task in list) {
                        if (task.date.equals(category.title)) {
                            val thislist = ArrayList<Task>()
                            if (category.tasks.isEmpty()) {
                                thislist.add(task)
                                category.tasks=thislist
                            } else {
                                thislist.addAll(category.tasks)
                                thislist.add(task)
                                category.tasks = thislist
                            }
                        }
                    }
                }
                parentAdapter.updateList(categories)
                Log.d("test","parents= "+parentAdapter.allParent.size.toString())
            }
        }
    }

    private fun setCalander(parentAdapter: ParentAdapter,collapsibleCalendar:CollapsibleCalendar){
        collapsibleCalendar.setCalendarListener(object : CollapsibleCalendar.CalendarListener {
            override fun onDayChanged() {

            }

            override fun onClickListener() {
                if(collapsibleCalendar.expanded){
                    collapsibleCalendar.collapse(400)
                }
                else{
                    collapsibleCalendar.expand(400)
                }
            }

            override fun onDaySelect() {
                val day: Day = collapsibleCalendar.selectedDay!!

                val thistoday = if(day.day<10) "0"+(day.day).toString() else day.day.toString()
                val thismonth= if(day.month + 1<10) "0"+(day.month+1).toString() else (day.month + 1).toString()

                updateDataByDate(
                    parentAdapter,
                    thistoday + "/" + thismonth + "/" + day.year.toString()
                )
            }

            override fun onItemClick(v: View) {

            }

            override fun onDataUpdate() {

            }

            override fun onMonthChange() {

            }

            override fun onWeekChange(position: Int) {

            }
        })
    }

    override fun onEditClick(task: Task) {
        val intent = Intent(this.activity, AddTaskActivity::class.java)
        intent.putExtra("id",task.idTask.toString())
        intent.putExtra("title",task.title)
        intent.putExtra("description",task.description)
        intent.putExtra("category",task.categoryName)
        intent.putExtra("color",task.categoryColor)
        intent.putExtra("catevoryid",task.categoryId.toString())
        intent.putExtra("date",task.date)
        intent.putExtra("time",task.time)
        intent.putExtra("state",task.state.toString())
        startActivity(intent)
    }

    override fun onDoneClick(task: Task) {
        if(task.state==0) task.state=1
        else task.state=0
        viewModel.updateTask(task)
    }
}