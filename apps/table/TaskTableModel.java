package apps.table;


import javax.swing.table.AbstractTableModel;

import apps.projects.Task;

import java.util.ArrayList;
import java.util.Iterator;

public class TaskTableModel extends AbstractTableModel {

    private ArrayList<Task> tasks = new ArrayList<>();
    private static int columns = 4;
    private String[] columnNames = {"Task Id", "Task Name", "Task Executor", "Due"};

    public TaskTableModel() {
    }

    public int getRowCount() {
        return tasks.size();
    }

    public int getColumnCount() {
        return columns;
    }

    public Object getValueAt(int r, int c) {
        return tasks.get(r).attr(c);
    }

    public String getColumnName(int c) {
        return columnNames[c];
    }

    public void addTask(Task t) {
        tasks.add(t);
    }

    public void deleteTask(int i, ProjectTableModel projectTableModel) {
        Iterator<Task> taskIterator = tasks.iterator();
        while (taskIterator.hasNext()) {
            Task t = taskIterator.next();
            if (t.getId() == i) {
//            		projectTableModel.getProjects().remove(t);    
                taskIterator.remove();
            }
        }
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}