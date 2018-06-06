package apps.projects;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import java.lang.Object;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import apps.people.Member;

public class Task extends Base implements Comparable<Task>, Serializable {

	private Member executor;
	public LocalDate finishDate;
	private static int nextId = 1;
	private int id;
	public ArrayList<Member> members;

	public Task(String t) {
		super(t);
		members = new ArrayList<>();
		finishDate = LocalDate.now();
		id = nextId++;
	}

	public Task(String t, Member e) {
		super(t);
		executor = e;
		finishDate = LocalDate.now();
		id = nextId++;
	}

	public Task(String t, LocalDate ld) {
		super(t);
		finishDate = ld;
		members = new ArrayList<>();
		id = nextId++;
	}
	

    public Task(String t, Member executor, String d) {
        super(t);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(d, formatter);
        finishDate = date;
        this.executor = executor;
        id = nextId++;
    }

	public int getId() {
		return id;
	}
	
	public LocalDate getDate() {
		return finishDate;
	}

	public void addMember(Member m) {
		members.add(m);
	}

	public void showMembers() {
		for (Member m : members)
			System.out.println(m);
	}

	public void assigTo(Member to) {
		executor = to;
	}

	public Member getExecutor() {
		return executor;
	}

	public void setExecutor(int memberID, List<Member> people) {
		for (Member p : people) {
			if (p.getId() == memberID)
				this.executor = p;
		}
	}

	public String toString() {
		return " Task name: " + getTitle() + " Deadline: " + getDate();
	}

	public int compareTo(Task t) {
		return this.finishDate.compareTo(t.finishDate);
	}

	/*
	 * public void modifyAtributes(String attribute, String newValue) { switch
	 * (attribute) { case "name": this.name = newValue; break; case "due": LocalDate
	 * tmp = LocalDate.parse(newValue); this.due = tmp; break; } }
	 * 
	 * public void modifyAtributes(String attribute, Member executor) {
	 * if(attribute.equals("executor")) this.executor = executor; }
	 */
	interface GetAttr {
		String attr();
	}

	public GetAttr[] attrs = new GetAttr[] { new GetAttr() {
		public String attr() {
			return Integer.toString(getId());
		}
	}, new GetAttr() {
		public String attr() {
			return getTitle();
		}
	}, new GetAttr() {
		public String attr() {
			return executor.toString();
		}
	}, new GetAttr() {
		public String attr() {
			return finishDate.toString();
		}
	} };

	public String attr(int index) {
		return attrs[index].attr();
	}

}
