package apps.projects;

import java.io.Serializable;

abstract public class Base implements Serializable {

	private String title;

	public Base(String t) {
		this.title = t;

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String newTitle) {
		title = newTitle;

	}

}
