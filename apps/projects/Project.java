package apps.projects;

import apps.people.Member;

import java.io.Serializable;
import java.util.*;

public class Project extends Base implements Serializable {
	public ArrayList<Task> tasks;
	public ArrayList<Member> members;
	public String title;
	public int id;
	
	public Project(String t) {
		super(t);
		tasks = new ArrayList<>();
		members = new ArrayList<>();
	}

	
	public void addTask(Task t) {
		tasks.add(t);
	}

	public void addMember(Member m) {
		members.add(m);
	}

	public void showTask() {
		for (Task t : tasks)
			System.out.println(t);
	}

	public void showMembers() {
		for (Member m : members)
			System.out.println(m);
	}

	public String toString() {
		String out = " Project title: " + getTitle();
		return out;
	}

	public String getTitle() {
		return title;
	}
	public String getID() {
		return Integer.toString(id);
	}


	interface GetAttr {
		String attr();
	}

	private Project.GetAttr[] attrs = new Project.GetAttr[] { new Project.GetAttr() {
		public String attr() {
			return getID();
		}
	}, new Project.GetAttr() {
		public String attr() {
			return getTitle();
		}
	},
	};

	public String attr(int index) {
		return attrs[index].attr();
	}

}
