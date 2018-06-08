import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import javax.print.attribute.standard.NumberOfInterveningJobs;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;

import apps.add.*;
import apps.delete.DeletePerson;
import apps.delete.DeleteProject;
import apps.delete.DeleteTask;
import apps.modify.ModifyPerson;
import apps.modify.ModifyProject;
import apps.modify.ModifyTask;
import apps.people.Member;
import apps.projects.Project;
import apps.projects.Task;
import apps.table.PersonTableModel;
import apps.table.ProjectTableModel;
import apps.table.TaskTableModel;

public class OwnFrame extends JFrame implements Serializable{
	private static final int DEFAULT_WIDTH = 500;
	private static final int DEFAULT_HEIGHT = 300;

	private JFileChooser inFile;
	private JFileChooser outFile;
	private AddPerson addPersonDialog = null;
	private DeletePerson deletePersonDialog = null;
	private ModifyPerson modifyPersonDialog = null;

	private AddProject addProjectDialog = null;
	private DeleteProject deleteProjectDialog = null;
	private ModifyProject modifyProjectDialog = null;

	private AddTask addTaskDialog = null;
	private ModifyTask modifyTaskDialog = null;
	private DeleteTask deleteTaskDialog = null;
	private PersonTableModel personTableModel;
	private ProjectTableModel projectTableModel;
	private TaskTableModel taskTableModel;

	private JTable personTable;
	private JTable projectTable;
	private JTable taskTable;

	public OwnFrame() {
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		JMenuBar menuBar = new JMenuBar();
		personTableModel = new PersonTableModel();
		personTable = new JTable(personTableModel);
		projectTableModel = new ProjectTableModel();
		projectTable = new JTable(projectTableModel);
		taskTableModel = new TaskTableModel();
		taskTable = new JTable(taskTableModel);

		JMenu fileMenu = new JMenu("File");
		JMenu memberMenu = new JMenu("Member");
		JMenu projectMenu = new JMenu("Project");
		JMenu taskMenu = new JMenu("Task");
		
		JMenuItem importItem = new JMenuItem("Import");
		
		inFile = new JFileChooser();
		outFile = new JFileChooser();
		
		importItem.addActionListener(event -> {
			inFile.setCurrentDirectory(new File("."));
			int res = inFile.showOpenDialog(OwnFrame.this);
			if (res == JFileChooser.APPROVE_OPTION) {
				String name = inFile.getSelectedFile().getPath();
				try (Scanner inf = new Scanner(Paths.get(name))) {


					 while(inf.hasNextLine())
				      {
				        String[] line = inf.nextLine().split(";");
				        personTableModel.addPerson(new Member(line[1], line[2], line[3]));
						personTableModel.fireTableDataChanged();
				      }
					/*
					inf.next();
					int number = inf.nextInt();
					inf.nextLine();
					for (int i = 1; i <= number; i++) {
						String[] line = inf.nextLine().split(";");
						personTableModel.addPerson(new Member(line[1], line[2],line[3]));
						personTableModel.fireTableDataChanged();
					}	*/
/*
										
					inf.next();
					number = inf.nextInt();
					inf.nextLine();
					for (int i = 1; i <= number; i++) {
						String[] line = inf.nextLine().split(";");
						Member p = personTableModel.getPersons().get(Integer.parseInt(line[3]) - 1);
						taskTableModel.addTask(new Task(line[1], p));
						taskTableModel.fireTableDataChanged();
					}

					inf.next();
					number = inf.nextInt();
					inf.nextLine();
					for (int i = 1; i <= number; i++) {
						String[] line = inf.nextLine().split(";");
						ArrayList<Task> tasks = new ArrayList<>();
						for (int j = 0; j < Integer.parseInt(line[2]); j++) {
							Task t = taskTableModel.getTasks().get(Integer.parseInt(line[3 + j * 4]) - 1);
							tasks.add(t);
						}

						projectTableModel.getProjects().add(new Project(line[1], tasks));
						projectTableModel.fireTableDataChanged();
					}
*/
				} catch (IOException e) {
					JOptionPane.showMessageDialog(OwnFrame.this, "Input file read failed!", "Import error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JMenuItem importDB = new JMenuItem("Import DB");
		importDB.addActionListener(event -> {
			ImportFromDB();

		});
		JMenuItem exportItem = new JMenuItem("Export");
		exportItem.addActionListener(event -> {
			outFile.setCurrentDirectory(new File("."));
			int res = outFile.showSaveDialog(OwnFrame.this);
			if (res == JFileChooser.APPROVE_OPTION) {
				String name = outFile.getSelectedFile().getPath();
				try (PrintWriter outf = new PrintWriter(name);) {
					for (Member p : personTableModel.getPersons()) {
						outf.println(p);
					}
					for (Task t : taskTableModel.getTasks()) {
						outf.println(t);
					}
					for (Project p : projectTableModel.getProjects()) {
						outf.println(p);
					}
				} catch (IOException e) {
					JOptionPane.showMessageDialog(OwnFrame.this, "Output file read failed!", "Export error",
							JOptionPane.ERROR_MESSAGE);
				}/*
				try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(name))) {
					output.writeObject(personTableModel.getPersons());
					output.writeObject(projectTableModel.getProjects());

				} catch (IOException ex) {
					ex.printStackTrace();
				}*/

			}
		});
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(event -> System.exit(0));

		JMenuItem addPersonItem = new JMenuItem("New");
		addPersonItem.addActionListener(new AddPersonAction());
		JMenuItem deletePersonItem = new JMenuItem("Delete");
		deletePersonItem.addActionListener(new DeletePersonAction());
		JMenuItem modifyPersonItem = new JMenuItem("Modify");
		modifyPersonItem.addActionListener(new ModifyPersonAction());

		JMenuItem addProjectItem = new JMenuItem("New");
		addProjectItem.addActionListener(new AddProjectAction());
		JMenuItem deleteProjectItem = new JMenuItem("Delete");
		deleteProjectItem.addActionListener(new DeleteProjectAction());
		JMenuItem modifyProjectItem = new JMenuItem("Modify");
		modifyProjectItem.addActionListener(new ModifyProjectAction());

		JMenuItem addTaskItem = new JMenuItem("New");
		addTaskItem.addActionListener(new AddTaskAction());
		JMenuItem modifyTaskItem = new JMenuItem("Modify");
		modifyTaskItem.addActionListener(new ModifyTaskAction());
		JMenuItem deleteTaskItem = new JMenuItem("Delete");
		deleteTaskItem.addActionListener(new DeleteTaskAction());

		fileMenu.addSeparator();
		fileMenu.add(importItem);
		fileMenu.add(exportItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);

		memberMenu.add(addPersonItem);
		memberMenu.add(deletePersonItem);
		memberMenu.add(modifyPersonItem);

		projectMenu.add(addProjectItem);
		projectMenu.add(deleteProjectItem);
		projectMenu.add(modifyProjectItem);

		taskMenu.add(addTaskItem);
		taskMenu.add(modifyTaskItem);
		taskMenu.add(deleteTaskItem);

		menuBar.add(fileMenu);
		menuBar.add(memberMenu);
		menuBar.add(projectMenu);
		menuBar.add(taskMenu);

		setJMenuBar(menuBar);

		setLayout(new GridLayout(1, 3));
		JPanel personPanel = new JPanel();
		JPanel projectPanel = new JPanel();
		JPanel taskPanel = new JPanel();

		personPanel.setLayout(new BorderLayout());
		projectPanel.setLayout(new BorderLayout());
		taskPanel.setLayout(new BorderLayout());

		personPanel.add(personTable.getTableHeader(), BorderLayout.PAGE_START);
		personPanel.add(personTable, BorderLayout.CENTER);
		projectPanel.add(projectTable.getTableHeader(), BorderLayout.PAGE_START);
		projectPanel.add(projectTable, BorderLayout.CENTER);
		taskPanel.add(taskTable.getTableHeader(), BorderLayout.PAGE_START);
		taskPanel.add(projectTable, BorderLayout.CENTER);

		add(new JScrollPane(personTable));
		add(new JScrollPane(projectTable));
		add(new JScrollPane(taskTable));
		pack();

	}

	private class AddPersonAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (addPersonDialog == null)
				addPersonDialog = new AddPerson();
			if (addPersonDialog.showDialog(OwnFrame.this, "Add person")) {
				Member m = addPersonDialog.getPerson();
				personTableModel.addPerson(m);
				personTableModel.fireTableDataChanged();
			}
		}
	}

	private class DeletePersonAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (deletePersonDialog == null)
				deletePersonDialog = new DeletePerson();
			try {
				if (deletePersonDialog.showDialog(OwnFrame.this, "Delete person")) {
					int personId = deletePersonDialog.getId();
					personTableModel.deletePerson(personId);
					personTableModel.fireTableDataChanged();

				}
			} catch (NumberFormatException e) {
				System.out.println("Blad: Podaj ID!");
			} catch (IndexOutOfBoundsException ex) {
				System.out.println("Blad: Nie ma projektu o takim ID!");
			}
		}
	}

	private class ModifyPersonAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (modifyPersonDialog == null)
				modifyPersonDialog = new ModifyPerson();
			try {
				if (modifyPersonDialog.showDialog(OwnFrame.this, "Modify person")) {
					modifyPersonDialog.getModify(personTableModel);
					personTableModel.fireTableDataChanged();
				}
			} catch (NumberFormatException e) {
				System.out.println("Blad: Podaj ID!");
			} catch (IndexOutOfBoundsException ex) {
				System.out.println("Blad: Nie ma projektu o takim ID! ");
			}
		}
	}

	private class AddProjectAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (addProjectDialog == null)
				addProjectDialog = new AddProject();
			try {
				if (addProjectDialog.showDialog(OwnFrame.this, "Add project")) {
					Project p = addProjectDialog.getProject(personTableModel);
					projectTableModel.addProject(p);
					projectTableModel.fireTableDataChanged();
				}
			} catch (NumberFormatException e) {
				System.out.println("Blad: Podaj ID osoby!");
			} catch (NullPointerException e) {
				System.out.println("Blad: Podaj ID osoby!");
			} catch(Exception e) {
				System.out.println("Blad: Podaj ID osoby!");
			}
		}
	}

	private class DeleteProjectAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (deleteProjectDialog == null)
				deleteProjectDialog = new DeleteProject();
			try {
				if (deleteProjectDialog.showDialog(OwnFrame.this, "Delete project")) {
					int projectID = deleteProjectDialog.getId();
					projectTableModel.deleteProject(projectID);
					projectTableModel.fireTableDataChanged();

				}
			} catch (NumberFormatException e) {
				System.out.println("Blad: Podaj ID projektu!");
			} catch (NullPointerException e) {
				System.out.println("Blad: Podaj ID projektu");

			}
		}
	}

	private class ModifyProjectAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (modifyProjectDialog == null)
				modifyProjectDialog = new ModifyProject();
			try {
				if (modifyProjectDialog.showDialog(OwnFrame.this, "Modify project")) {
					modifyProjectDialog.getModifiedProject(projectTableModel);
					projectTableModel.fireTableDataChanged();
				}
			} catch (NumberFormatException e) {
				System.out.println("Blad: Podaj ID projektu!");
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Blad: Podaj ID projektu!");
			}
		}
	}

	private class AddTaskAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (addTaskDialog == null)
				addTaskDialog = new AddTask();
			try {
				if (addTaskDialog.showDialog(OwnFrame.this, "Add task")) {
					Task t = addTaskDialog.getTask(projectTableModel, personTableModel);
					taskTableModel.addTask(t);
					taskTableModel.fireTableDataChanged();
					projectTableModel.fireTableDataChanged();
				}
			} catch (NumberFormatException e) {
				System.out.println("Blad: Podaj ID projektu oraz osoby!");
			} catch (IndexOutOfBoundsException e) {
				System.out.println(("Blad: Podaj ID projektu oraz osoby"));
			}
		}
	}

	private class ModifyTaskAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (modifyTaskDialog == null)
				modifyTaskDialog = new ModifyTask();
			try {
				if (modifyTaskDialog.showDialog(OwnFrame.this, "Modify task")) {
					modifyTaskDialog.getModifiedTask(taskTableModel, personTableModel);
					taskTableModel.fireTableDataChanged();
				}
			} catch (NumberFormatException e) {
				System.out.println("Blad: Podaj ID osoby oraz projektu!");
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Blad: Podaj ID osoby oraz projektu");
			}
		}
	}

	public class DeleteTaskAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (deleteTaskDialog == null)
				deleteTaskDialog = new DeleteTask();
			try {
				if (deleteTaskDialog.showDialog(OwnFrame.this, "Delete task")) {
					int taskID = deleteTaskDialog.getId();
					taskTableModel.deleteTask(taskID, projectTableModel);
					taskTableModel.fireTableDataChanged();
				}
			} catch (NumberFormatException e) {
				System.out.println("Podaj ID zadania!");
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Podaj ID zadania!");
			}
		}
	}
	

	public void ImportFromDB() {
		Connection con = null;
		try {
			try {
				Class.forName("org.sqlite.JDBC");
				con = DriverManager.getConnection("jdbc:sqlite:ProjectManager.db");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			Statement stmt = con.createStatement();
			String sql = "select * from people";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				personTableModel.addPerson(new Member(rs.getString(2), rs.getString(3), rs.getString(4)));
				personTableModel.fireTableDataChanged();
			}
			
			String sql2 = "select * from tasks";
			ResultSet rs2 = stmt.executeQuery(sql2);
			while (rs2.next()) {
				try {
				Member p = personTableModel.getPersons().get(rs2.getInt(4) - 1);
				
				taskTableModel.addTask(new Task(rs.getString(2), p, rs.getString(3)));
				taskTableModel.fireTableDataChanged();
				}
				catch (IndexOutOfBoundsException e) {
					
				}
			}
			Statement stmt2 = con.createStatement();
			Statement stmt3 = con.createStatement();
			
			String sql3 = "select * from projects";
			ResultSet rs3 = stmt.executeQuery(sql3);
			while (rs3.next()) {
				ArrayList<Task> tasks = new ArrayList<>();
				ArrayList<Member> members = new ArrayList<>();
				//String sql4 = "select id from tasks where project="+rs3.getInt(1);
				String sql5 = "select id from people where project="+rs3.getInt(1);
				//ResultSet rs4 = stmt2.executeQuery(sql4);
				ResultSet rs5 = stmt3.executeQuery(sql5);
				/*
				while(rs4.next()) {
					try {
						Task t = taskTableModel.getTasks().get(rs4.getInt(1) - 1);
						tasks.add(t);
					}catch (IndexOutOfBoundsException e) {
					}
				}*/
				while(rs5.next()) {
					try {
						Member m = personTableModel.getPersons().get(rs5.getInt(4) - 1);	
						members.add(m);		
					}catch(IndexOutOfBoundsException e) {
						
					}
					
				}
				projectTableModel.addProject(new Project(rs3.getString(2), members, tasks));
				projectTableModel.fireTableDataChanged();
			}
			
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
