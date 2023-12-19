
package com.example;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.example.TaskListApp.Task;



public class TaskListTest {
    public TaskListApp app;

    @Before
    public void run() {
        app = new TaskListApp();
    }

    @Test 
    public void TestAddTask(){
        app.newTaskField.setText("New Task");
        app.addTask();
        assertEquals(1, app.taskListModel.size());
    }
    
    @Test
    public void TestRemoveTask(){
        app.newTaskField.setText("New Task");
        app.addTask();
        
        app.taskList.setSelectedIndex(0);
        app.removeTask();

        assertEquals(0, app.taskListModel.size());
    }
    
    @Test 
    public void TestNameTask(){
        app.newTaskField.setText("New Task");
        app.addTask();

        assertEquals("New Task", app.taskListModel.get(0).getDescription());
    }

    @Test
    public void TestFinishTask() {
        app.newTaskField.setText("New Task");
        app.addTask();


        Task selectedTask = app.taskListModel.getElementAt(0);
        selectedTask.setCompleted(!selectedTask.isCompleted());

        assertTrue(selectedTask.isCompleted());

    }

    
}
