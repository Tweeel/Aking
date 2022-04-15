package com.example.todoapp_kotlin.pages.mainPage.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp_kotlin.R
import com.example.todoapp_kotlin.adapters.CollapsedAdapter
import com.example.todoapp_kotlin.database.entities.Collapsed
import com.example.todoapp_kotlin.database.entities.Task
import com.example.todoapp_kotlin.pages.addTaskPage.AddTaskActivity
import com.example.todoapp_kotlin.viewmodels.MyViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TasksFragment : Fragment(), PopupMenu.OnMenuItemClickListener,
    CollapsedAdapter.TaskClickInterfaceCollapsed, CollapsedAdapter.CollapsedInterface {

    private lateinit var viewModel: MyViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var collapsedAdapter: CollapsedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerview)

        recyclerViewSetup()

        view.findViewById<ImageView>(R.id.menu).setOnClickListener{
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

    private fun showMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(requireContext(), v)
        popup.menuInflater.inflate(menuRes, popup.menu)
        popup.setOnMenuItemClickListener(this)
        // Show the popup menu.
        popup.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
//        when(item?.itemId){
//            R.id.incomplete ->{
//                item.isChecked = true
//                viewModel.incompletedTasks.observe(requireActivity()) { list ->
//                    list?.let {
//                        // on below line we are updating our list.
//                        taskAdapter.updateList(it)
//                    }
//                }
//            }
//            R.id.completed ->{
//                item.isChecked = true
//                viewModel.completedTasks.observe(requireActivity()) { list ->
//                    list?.let {
//                        // on below line we are updating our list.
//                        taskAdapter.updateList(it)
//                    }
//                }
//            }
//            R.id.all ->{
//                item.isChecked = true
//                viewModel.allTasks.observe(requireActivity()) { list ->
//                    list?.let {
//                        // on below line we are updating our list.
//                        taskAdapter.updateList(it)
//                    }
//                }
//            }
//            else -> item?.let { super.onOptionsItemSelected(it) }
//        }
        return true
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

    override fun onCollapsedClick(collapsed: Collapsed) {
        collapsed.collapsed = collapsed.collapsed != true
        collapsedAdapter.notifyDataSetChanged()
    }

    private fun recyclerViewSetup(){
        // on below line we are
        // initializing our view modal.
        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[MyViewModel::class.java]
        // on below line we are setting layout
        // manager to our recycler view.
        recyclerView.layoutManager = LinearLayoutManager(activity)
        // on below line we are initializing our adapter class.
        collapsedAdapter = CollapsedAdapter(requireActivity(), this,this,viewModel)

        // on below line we are setting
        // adapter to our recycler view.
        recyclerView.adapter = collapsedAdapter
        recyclerView.setHasFixedSize(true)

        val currentDate= LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val date = currentDate.format(formatter)

        viewModel.date.value = date.toString()

        val categories: MutableList<Collapsed> = ArrayList()
        val completedTasks = Collapsed("Completed Tasks", emptyList(),false)
        val incompletedTasks = Collapsed("Incompleted Tasks", emptyList())
        val intask: MutableList<Task> = ArrayList()
        val comtask: MutableList<Task> = ArrayList()

        // Update the cached copy of the words in the adapter.
        // Get all the words from the database
        // and associate them to the adapter.
        viewModel.todayTasks.asLiveData().observe(requireActivity()) { tasks ->
            comtask.clear()
            intask.clear()
            incompletedTasks.tasks = emptyList()
            completedTasks.tasks = emptyList()
            categories.clear()
            for (task in tasks) {
                if (task.state == 0) {
                    intask.add(task)
                    incompletedTasks.tasks = intask
                } else {
                    comtask.add(task)
                    completedTasks.tasks = comtask
                }
            }
            categories.add(incompletedTasks)
            categories.add(completedTasks)
            collapsedAdapter.updateList(categories)
        }
    }
}
