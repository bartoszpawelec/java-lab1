package apps.table;


import javax.swing.table.AbstractTableModel;

import apps.projects.Task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class TaskTableModel extends AbstractTableModel implements Serializable {

    private ArrayList<Task> tasks = new ArrayList<>();
    private static int columns = 4;
    private String[] columnNames = {"Task Id", "Task Name", "Task Executor", "Deadline"};

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
        int tmp=0;
        Iterator<Task> taskIterator = tasks.iterator();
        while (taskIterator.hasNext()) {
            Task t = taskIterator.next();
            if (t.getId() == i) {
            		projectTableModel.getProjects().remove(t);
                taskIterator.remove();
                tmp++;
            }
        }
        System.out.println("Nie istnieje zadanie o takim ID!");
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}