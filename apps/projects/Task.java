package apps.projects;

import java.util.ArrayList;
import java.io.Serializable;
import java.lang.Object;
import java.time.LocalDate;
import apps.people.Member;

public class Task extends Base implements Comparable<Task>, Serializable {

	private Member executor;
	LocalDate finishDate;

	public ArrayList<Member> members;

	public Task(String t) {
		super(t);
		members = new ArrayList<>();
	}

	public Task(String t, Member e) {
		super(t);
		executor = e;

		members = new ArrayList<>();
	}

	public Task(String t, LocalDate ld) {
		super(t);
		finishDate = ld;
		members = new ArrayList<>();
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

	public String toString() {
		return "Task name: " + getTitle() + " Deadline: " + getDate();
	}

	public int compareTo(Task t) {
		return this.finishDate.compareTo(t.finishDate);
	}

}
