package apps.table;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.table.AbstractTableModel;

import apps.projects.Project;

import apps.projects.Task;

public class ProjectTableModel extends AbstractTableModel {
	private ArrayList<Project> projects = new ArrayList<>();
	private static int columns = 4;
	private String[] columnNames = { "Project Id", "Title", "Project Tasks","Project Executor" };

	public int getRowCount() {
		return projects.size();
	}

	public int getColumnCount() {
		return columns;
	}

	public Object getValueAt(int r, int c) {
		return projects.get(r).attr(c);
	}

	public String getColumnName(int c) {
		return columnNames[c];
	}

	public void addProject(Project p) {
		projects.add(p);
	}

	public void addTask(Task t, int projectID) {
		for (Project p : projects)
			if (p.getId() == projectID)
				p.addTask(t);
	}

	public void deleteProject(int i) {
		Iterator<Project> projectIterator = projects.iterator();
		while (projectIterator.hasNext()) {
			Project p = projectIterator.next();
			if (p.getId() == i)
				projectIterator.remove();
		}
	}

	public ArrayList<Project> getProjects() {
		return projects;
	}
}