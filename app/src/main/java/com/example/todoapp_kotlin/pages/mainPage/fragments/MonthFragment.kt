package com.example.todoapp_kotlin.pages.mainPage.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp_kotlin.R
import com.example.todoapp_kotlin.adapters.TaskAdapter
import com.example.todoapp_kotlin.database.entities.Task
import com.example.todoapp_kotlin.pages.addTaskPage.AddTaskActivity
import com.example.todoapp_kotlin.viewmodels.MyViewModel
import com.shrikanthravi.collapsiblecalendarview.data.Day
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar


class MonthFragment : Fragment(), TaskAdapter.TaskClickInterface,
    TaskAdapter.TaskDoneClickInterface {

    private lateinit var viewModel: MyViewModel
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

        recyclerView = view.findViewById(R.id.recyclerview)
        // on below line we are setting layout
        // manager to our recycler view.
        recyclerView.layoutManager = LinearLayoutManager(activity)
        // on below line we are initializing our adapter class.
        val taskAdapter = TaskAdapter(requireActivity(), this,this)

        // on below line we are setting
        // adapter to our recycler view.
        recyclerView.adapter = taskAdapter
        recyclerView.setHasFixedSize(true)

        // on below line we are
        // initializing our view modal.
        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[MyViewModel::class.java]

        // on below line we are calling all notes method
        // from our view modal class to observer the changes on list.
        viewModel.allTasks.observe(requireActivity()) { list ->
            list?.let {
                // on below line we are updating our list.
                taskAdapter.updateList(it)
            }
        }

        //add the onswipe to delete a note
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val task = taskAdapter.allTasks[viewHolder.adapterPosition]
                viewModel.deleteTask(task)
            }
        }).attachToRecyclerView(recyclerView)

        val collapsibleCalendar = view.findViewById<CollapsibleCalendar>(R.id.calander)!!

        //To hide or show expand icon
//        collapsibleCalendar.setExpandIconVisible(true)
//        val today = GregorianCalendar()
//
//        collapsibleCalendar.addEventTag(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(
//            Calendar.DAY_OF_MONTH))
//
//        today.add(Calendar.DATE, 1)
//        collapsibleCalendar.selectedDay = Day(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(
//            Calendar.DAY_OF_MONTH))
//
//        collapsibleCalendar.addEventTag(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(
//            Calendar.DAY_OF_MONTH), Color.BLUE)
//
//        collapsibleCalendar.params = CollapsibleCalendar.Params(0, 100)

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
                val thismonth= if(day.month + 1<10) "0"+(day.month).toString() else (day.month + 1).toString()
                Log.d("test" ,thistoday + "/" + thismonth + "/" + day.year.toString())

                viewModel.date.value = thistoday + "/" + thismonth + "/" + day.year.toString()
                viewModel.tasksByDate.asLiveData().observe(requireActivity()) { list ->
                    list?.let{
                        // on below line we are updating our list.
                        taskAdapter.updateList(it)
                    }
                }
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
        intent.putExtra("date",task.date)
        intent.putExtra("time",task.time)
        intent.putExtra("state",task.state.toString())
        startActivity(intent)
    }

    override fun onDoneClick(task: Task) {
        if(task.state==0) viewModel.updateTask(Task(task.idTask,task.title,task.description,task.date,task.time,task.categoryName,1))
        else viewModel.updateTask(Task(task.idTask,task.title,task.description,task.date,task.time,task.categoryName,0))
    }
}