import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.*;
import apps.projects.Task;
import apps.people.Member;
import apps.projects.Project;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;
import java.time.LocalDate;

public class Reader {

	public static String[] Line;

	public static void Do(String s, ArrayList<Project> projects, ArrayList<Member> members) {

		Line = s.split(" ");

		switch (Line[0]) {
		case "add":
			add(projects, members);
			break;

		case "list":
			list(projects, members);
			break;

		case "modify":
			modify(projects, members);
			break;

		case "delete":
			delete(projects, members);
			break;

		case "export":
			export(projects, members);
			break;

		case "import":
			read(projects, members);
			break;

		default: {
			System.out.println("Error!");
			break;
		}
		}

	}

	public static void export(ArrayList<Project> projects, ArrayList<Member> members) {

		/*
		 * try { PrintWriter zapis = new PrintWriter(Line[1]);
		 * 
		 * for (Project p : projects) { zapis.println(p.getTitle()); for (Task t :
		 * p.tasks) { zapis.println(t); for (Member m : t.members) {
		 * zapis.println(m.getName()); } } }
		 * 
		 * zapis.close();
		 * 
		 * } catch (FileNotFoundException ex) { ex.printStackTrace(); }
		 */

		try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(Line[1]))) {
			output.writeObject(members);
			output.writeObject(projects);

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	public static void read(ArrayList<Project> projects, ArrayList<Member> members) {
		/*
		 * try { File plik = new File(Line[1]); Scanner in = new Scanner(plik);
		 * projects.add(new Project(in.nextLine())); in.close(); } catch
		 * (FileNotFoundException ex) { ex.printStackTrace(); }
		 */

		try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(Line[1]))) {
			ArrayList<Project> newProj;
			ArrayList<Member> newMemb;

			newMemb = (ArrayList<Member>) input.readObject();

			newProj = (ArrayList<Project>) input.readObject();

			for (Member m : newMemb) {
				members.add(m);
			}
			for (Project p : newProj) {
				projects.add(p);
			}
		} catch (FileNotFoundException fex) {
			fex.printStackTrace();

		} catch (ClassNotFoundException cex) {
			cex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	public static void add(ArrayList<Project> projects, ArrayList<Member> members) {
		switch (Line[1]) {
		case "project":
			projects.add(new Project(Line[2]));
			break;

		case "person":
			members.add(new Member(Line[2], Line[3], Line[4]));
			break;

		case "person_to_project":
			int id = Integer.parseInt(Line[2]);
			Member memberToAdd = members.get(id);

			for (Project p : projects) {
				if (p.getTitle().equals(Line[3]))
					p.addMember(memberToAdd);
			}
			break;

		case "person_to_task":
			int mid = Integer.parseInt(Line[2]);
			Member memberToAddToTask = members.get(mid);

			for (Project p : projects) {
				if (p.getTitle().equals(Line[3])) {
					p.addMember(memberToAddToTask);
					for (Task t : p.tasks) {
						if (t.getTitle().equals(Line[4])) {
							t.addMember(memberToAddToTask);
						}
					}
				}
			}
			break;

		case "task":
			LocalDate localDate = LocalDate.parse(Line[4]);
			for (Project p : projects) {
				if (p.getTitle().equals(Line[3]))
					p.addTask(new Task(Line[2], localDate));
			}
			break;

		default: {
			System.out.println("Error!");
			break;
		}

		}
	}

	public static void delete(ArrayList<Project> projects, ArrayList<Member> members) {
		switch (Line[1]) {
		case "project":
			int id = Integer.parseInt(Line[2]);
			projects.remove(id);
			break;

		case "person":
			int pid = Integer.parseInt(Line[2]);
			members.remove(pid);
			break;

		case "task":
			int tid = Integer.parseInt(Line[2]);

			for (Project p : projects) {
				if (p.getTitle().equals(Line[3])) {
					p.tasks.remove(tid);

				}
			}
			break;

		default: {
			System.out.println("Error!");
			break;
		}

		}
	}

	public static void modify(ArrayList<Project> projects, ArrayList<Member> members) {
		switch (Line[1]) {
		case "project":
			int id = Integer.parseInt(Line[2]);
			projects.get(id).setTitle(Line[3]);
			break;

		case "person":
			int pid = Integer.parseInt(Line[2]);
			members.get(pid).setName(Line[3], Line[4]);
			break;

		case "task":
			int tid = Integer.parseInt(Line[2]);

			for (Project p : projects) {
				if (p.getTitle().equals(Line[3])) {
					p.tasks.get(tid).setTitle(Line[4]);

				}
			}
			break;

		default:
			System.out.println("Error!");
			break;

		}
	}

	public static void list(ArrayList<Project> projects, ArrayList<Member> members) {

		switch (Line[1]) {

		case "project":
			for (Project p : projects) {
				System.out.println(p);
			}
			break;

		case "person":
			for (Member m : members) {
				System.out.println(m);
			}
			break;

		case "members_of_project":
			for (Project p : projects) {
				if (p.getTitle().equals(Line[2]))
					p.showMembers();
			}
			break;

		case "assign_to_task":
			for (Project p : projects) {
				if (p.getTitle().equals(Line[2])) {
					for (Task t : p.tasks) {
						if (t.getTitle().equals(Line[3])) {
							t.showMembers();
						}
					}
				}
			}

			break;

		case "task":
			if (Line[2].equals("asc")) {
				for (Project p : projects) {
					if (p.getTitle().equals(Line[3]))
						Collections.sort(p.tasks);
					p.showTask();
				}
			
			}
			else if (Line[2].equals("desc")) {
				
			}
			else if (Line[2].equals("tardy")) {
			}
				

			break;

		default:
			System.out.println("Error!");
			break;
		}
	}
}
