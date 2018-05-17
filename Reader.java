import java.lang.reflect.Array;
import java.util.*;
import apps.projects.Task;
import apps.people.Member;
import apps.projects.Project;

public class Reader {
	public static String[] Line;

	public static void Do(String s, ArrayList<Project> projects, ArrayList<Member> members) {

		Line = s.split(" ");

		switch (Line[0]) {
			case "add":
				add(projects, members);
				break;

			case "list":
				list(projects,members);
				break;

			case "delete":
				delete(projects,members);
				break;

			case "modify":
				modify(projects,members);
				break;

			default: {
				System.out.println("Error!");
				break;
			}
		}
	}

	public static void add(ArrayList<Project> projects, ArrayList<Member> members) {
		switch (Line[1]) {
			case "project": {

				projects.add(new Project(Line[2]));
				break;
			}

			case "person": {
				members.add(new Member(Line[2], Line[3], Line[4]));

				break;
			}


			default: {
				System.out.println("Error!");
				break;
			}


		}
	}


	public static void delete(ArrayList<Project> projects, ArrayList<Member> members) {

		switch (Line[1]) {
			case "project": {
				int id = Integer.parseInt(Line[2]);
				projects.remove(id);
			}
			break;
		case "person": {
			int id = Integer.parseInt(Line[2]);
			members.remove(id);
			break;
		}
			default: {
				System.out.println("Error!");
				break;
			}
		}

	}

	public static void modify(ArrayList<Project> projects, ArrayList<Member> members) {
		switch (Line[1]) {
			case "project": {
				int id = Integer.parseInt(Line[2]);
				projects.get(id).setTitle(Line[3]);
				break;
			}
			case "person": {
				int id = Integer.parseInt(Line[2]);
				members.get(id).setName(Line[3],Line[4]);
				break;
			}
			default: {
				System.out.println("Error!");
				break;
			}
		}
	}

	public static void list(ArrayList<Project> projects, ArrayList<Member> members) {

		switch (Line[1]) {
			case "project":
				for (Project p : projects) {
					System.out.println(p);
				}
				break;

			case "people":
				for (Member m : members) {
					System.out.println(m);
				}
				break;
			default: {
				System.out.println("Error!");
				break;
			}
		}
	}
}