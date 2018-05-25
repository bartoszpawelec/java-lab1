package apps.projects;

import java.io.Serializable;

abstract public class Base implements Serializable{

	private String title;
	private static int nextId = 1;
	private int id;
	
	
	public Base(String t) {
		this.title = t;
		id = nextId++;
	}

	public String getTitle() {
		return title;
	}

	public int getId() {
		return id;
	}

	public void setTitle(String newTitle) {
		title = newTitle;

	}

}
