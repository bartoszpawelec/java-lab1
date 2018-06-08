package apps.projects;

import apps.people.Member;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class Project extends Base implements Serializable {
	public ArrayList<Task> tasks;
	public ArrayList<Member> members;

	private Member executor;
	private static int nextId = 1;
	private int id;

	public Project(String t) {
		super(t);
		tasks = new ArrayList<>();
		members = new ArrayList<>();
		id = nextId++;
	}

	public Project(String t, ArrayList<Task> tasks) {
		super(t);
		this.tasks = tasks;
		id = nextId++;
	}

	public Project(String t,ArrayList<Member> members, ArrayList<Task> tasks) {
		super(t);
		this.tasks = tasks;
		this.members = members;
		id = nextId++;
	}
	
	public Project(String t, Member m, ArrayList<Task> tasks) {
		super(t);
		this.tasks = tasks;
		executor = m;
		id = nextId++;
	}
	
	public Project(String t, Member m)
	{
		super(t);
		executor = m;
		id = nextId++;
	}

	public int getId() {
		return id;
	}

	public void addTask(Task t) {
		tasks.add(t);
	}

	public void addMember(Member m) {
		members.add(m);
	}

	public void setExecutor(int memberID, List<Member> people) {
		for (Member p : people) {
			if (p.getId() == memberID)
				this.executor = p;
		}
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
        String tasksString = "";
        String membersString = "";

        for(Task t : tasks)
            tasksString += t + ";";
        for(Member m : members)
            membersString += m + ";";
        
        return id + ";" + getTitle() + ";" + tasks.size() + ";" + tasksString + ";" + members.size() +";" + membersString;
	}

	interface GetAttr {
		String attr();
	}
/*
	private GetAttr[] attrs = new GetAttr[] { 
	new GetAttr() {
		public String attr() {
			return Integer.toString(getId());
		}
	}, new GetAttr() {
		public String attr() {
			return getTitle();
		}
	}, new GetAttr() {
		public String attr() {
			String taskString = "";
			for (Task t : tasks)
				taskString += (t.getId() + " " + t.getTitle() + " ");
			return taskString;
		}
	}, new GetAttr() {
		public String attr() {
			return executor.toString();
		}
	}};
*/
	

    private GetAttr[] attrs = new GetAttr[] {
            new GetAttr() { public String attr() { return Integer.toString(id);} },
            new GetAttr() { public String attr() { return getTitle();} },
            new GetAttr() { public String attr() {
                String taskString = "";
                for(Task t : tasks)
                    taskString += ( t.id + ") " + t.getTitle() + " ");
                return taskString;
            } },
            new GetAttr() {public String attr() {
            	String memberString = "";
                memberString += executor.id+ ")" + executor.firstName;
                return memberString;
        	} }
    };

	public String attr(int index) {
		return attrs[index].attr();
	}

}
