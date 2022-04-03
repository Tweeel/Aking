package com.example.todoapp_kotlin.pages.mainPage.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.PopupMenu
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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TasksFragment : Fragment(), TaskAdapter.TaskClickInterface,
    TaskAdapter.TaskDoneClickInterface, PopupMenu.OnMenuItemClickListener {

    private lateinit var viewModel: MyViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var menu: ImageView

     lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // on below line we are initializing
        // all our variables.
        menu = view.findViewById(R.id.menu)
        recyclerView = view.findViewById(R.id.recyclerview)
        // on below line we are setting layout
        // manager to our recycler view.
        recyclerView.layoutManager = LinearLayoutManager(activity)
        // on below line we are initializing our adapter class.
        taskAdapter = TaskAdapter(requireActivity(), this,this)

        // on below line we are setting
        // adapter to our recycler view.
        recyclerView.adapter = taskAdapter
        recyclerView.setHasFixedSize(true)

        val currentDate= LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val date = currentDate.format(formatter)

        // on below line we are
        // initializing our view modal.
        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[MyViewModel::class.java]

        viewModel.date.value = date.toString()

        // on below line we are calling all notes method
        // from our view modal class to observer the changes on list.
        viewModel.todayTasks.asLiveData().observe(requireActivity()) { list ->
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

        registerForContextMenu(menu)
        menu.setOnClickListener{
            showMenu(it, R.menu.toolbar_menu)

        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        activity?.menuInflater?.inflate(R.menu.toolbar_menu,menu)
        Toast.makeText(activity,"onCreateContextMenu",Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("RestrictedApi")
    private fun showMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(requireContext(), v)
        popup.menuInflater.inflate(menuRes, popup.menu)
        popup.setOnMenuItemClickListener(this)
        // Show the popup menu.
        popup.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.incomplete ->{
                item.isChecked = true
                viewModel.incompletedTasks.observe(requireActivity()) { list ->
                    list?.let {
                        // on below line we are updating our list.
                        taskAdapter.updateList(it)
                    }
                }
            }
            R.id.completed ->{
                item.isChecked = true
                viewModel.completedTasks.observe(requireActivity()) { list ->
                    list?.let {
                        // on below line we are updating our list.
                        taskAdapter.updateList(it)
                    }
                }
            }
            R.id.all ->{
                item.isChecked = true
                viewModel.allTasks.observe(requireActivity()) { list ->
                    list?.let {
                        // on below line we are updating our list.
                        taskAdapter.updateList(it)
                    }
                }
            }
            else -> item?.let { super.onOptionsItemSelected(it) }
        }
        return true
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
        if(task.state==0) task.state=1
        else task.state=0
        viewModel.updateTask(task)
    }
}
