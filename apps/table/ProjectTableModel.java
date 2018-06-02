package apps.table;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.table.AbstractTableModel;

import apps.projects.Project;

public class ProjectTableModel extends AbstractTableModel {
    private ArrayList<Project> projects = new ArrayList<>();
    private static int columns = 3;
    private String[] columnNames = {"Project Id", "Title", "Project Tasks"};

    public int getRowCount() { return projects.size(); }

    public int getColumnCount() { return columns; }

    public Object getValueAt(int r, int c) {
        return projects.get(r).attr(c);
    }

    public String getColumnName(int c) { return columnNames[c]; }

    public void addProject(Project p) {
        projects.add(p);
    }

    public ArrayList<Project> getProjects() {
        return projects;
    }
}