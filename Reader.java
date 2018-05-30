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
import java.util.Date;

public class Reader {

	public static String[] Line;

	public static void Do(String s, ArrayList<Project> projects, ArrayList<Member> members) {
		try {
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
		} catch (Exception ex) {
			ex.printStackTrace(System.out);
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
			ArrayList<Member> newMemb = (ArrayList<Member>) input.readObject();
			ArrayList<Project> newProj = (ArrayList<Project>) input.readObject();

			for (Member m : newMemb) {
				members.add(m);
			}
			for (Project p : newProj) {
				projects.add(p);
			}
		} catch (FileNotFoundException fex) {
			fex.printStackTrace();

		}catch (ClassNotFoundException cex) {
			cex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	public static void add(ArrayList<Project> projects, ArrayList<Member> members) throws OwnException {
		switch (Line[1]) {
		case "project":
			try {
				projects.add(new Project(Line[2]));
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Blad: Nie podano nazwy projektu!");
			}

			break;

		case "person":
			try {
				members.add(new Member(Line[2], Line[3], Line[4]));
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Blad: Brakuje danych o osobie!");
			}

			break;

		case "person_to_project":
			try {
				int id = Integer.parseInt(Line[2]);

				for (Project p : projects) {
					if (p.getTitle().equals(Line[3]))
						for (Member m : members)
							if (m.getId() == id)
								p.addMember(m);
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Blad: Brakuje nazwy projektu lub ID osoby");
			}
			break;

		case "person_to_task":
			try {
				int mid = Integer.parseInt(Line[2]);

				for (Project p : projects) {
					if (p.getTitle().equals(Line[3])) {
						for (Member m : members)
							if (m.getId() == mid)
								p.addMember(m);
						for (Task t : p.tasks) {
							if (t.getTitle().equals(Line[4])) {
								for (Member m : p.members)
									if (m.getId() == mid)
										t.addMember(m);

							}
						}
					}
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Blad: Brakuje danych: nazwy projektu/ID osoby/nazwy zadania");
			}

			break;

		case "task":
			try {
				LocalDate localDate = LocalDate.parse(Line[4]);
				for (Project p : projects) {
					if (p.getTitle().equals(Line[3]))
						p.addTask(new Task(Line[2], localDate));
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Blad: Brakuje nazwy zadania lub daty");
			}
			break;

		default: {
			System.out.println("Error!");
			break;
		}

		}
	}

	public static void delete(ArrayList<Project> projects, ArrayList<Member> members) throws OwnException {
		switch (Line[1]) {
		case "project":

			if (projects.isEmpty())
				throw new OwnException("project");

			int id = Integer.parseInt(Line[2]);

			for (Project p : projects)
				if (p.getId() == id)
					projects.remove(p);
			break;

		case "person":

			if (members.isEmpty())
				throw new OwnException("person");

			int mid = Integer.parseInt(Line[2]);

			for (Member m : members)
				if (m.getId() == mid)
					members.remove(m);

			break;

		case "task":
			if (projects.isEmpty())
				throw new OwnException("task");

			int tid = Integer.parseInt(Line[2]);

			for (Project p : projects) {
				if (p.getTitle().equals(Line[3])) {
					for (Task t : p.tasks)
						if (t.getId() == tid)
							p.tasks.remove(t);
				}
			}
			break;

		default: {
			System.out.println("Error!");
			break;
		}

		}
	}

	public static void modify(ArrayList<Project> projects, ArrayList<Member> members) throws OwnException {
		switch (Line[1]) {
		case "project":

			if (projects.isEmpty())
				throw new OwnException("project");

			int id = Integer.parseInt(Line[2]);
			for (Project p : projects)
				if (p.getId() == id)
					p.setTitle(Line[3]);

			break;

		case "person":

			if (members.isEmpty())
				throw new OwnException("person");

			int pid = Integer.parseInt(Line[2]);
			for (Member m : members)
				if (m.getId() == pid)
					m.setName(Line[3], Line[4]);
			break;

		case "task":

			if (members.isEmpty())
				throw new OwnException("task");

			int tid = Integer.parseInt(Line[2]);

			for (Project p : projects) {
				if (p.getTitle().equals(Line[3])) {
					for (Task t : p.tasks)
						if (t.getId() == tid)
							t.setTitle(Line[4]);
				}
			}
			break;

		default:
			System.out.println("Error!");
			break;

		}
	}

	public static void list(ArrayList<Project> projects, ArrayList<Member> members) throws OwnException {

		switch (Line[1]) {

		case "project":

			if (projects.isEmpty())
				throw new OwnException("project");

			for (Project p : projects) {
				System.out.println(p);
			}
			break;

		case "person":

			if (projects.isEmpty())
				throw new OwnException("person");

			for (Member m : members) {
				System.out.println(m);
			}
			break;

		case "members_of_project":

			if (projects.isEmpty())
				throw new OwnException("members of project");

			for (Project p : projects) {
				if (p.getTitle().equals(Line[2]))
					p.showMembers();
			}
			break;

		case "assign_to_task":

			if (projects.isEmpty())
				throw new OwnException("assign to task");

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

			if (projects.isEmpty())
				throw new OwnException("task");

			LocalDate today = LocalDate.now();

			if (Line[2].equals("asc")) {
				for (Project p : projects) {
					if (p.getTitle().equals(Line[3]))
						Collections.sort(p.tasks);
					p.showTask();
				}

			}

			else if (Line[2].equals("desc")) {
				for (Project p : projects) {
					if (p.getTitle().equals(Line[3]))
						Collections.sort(p.tasks);
					Collections.reverse(p.tasks);
					p.showTask();
				}
			}

			else if (Line[2].equals("tardy")) {

				for (Project p : projects) {
					if (p.getTitle().equals(Line[3]))
						for (Task t : p.tasks) {
							if (today.isAfter((t.getDate()))) {
								System.out.println(t.toString());
							}

						}
				}
			} else
				System.out.println("should be: list task asc/desc/tardy projectName");
			break;

		default:
			System.out.println("Error!");
			break;
		}
	}
}
