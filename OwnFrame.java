import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

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

public class OwnFrame extends JFrame {
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
	JMenuItem importItem = new JMenuItem("Import");

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

		inFile = new JFileChooser();
		outFile = new JFileChooser();

		importItem.addActionListener(event -> {
			inFile.setCurrentDirectory(new File("."));
			int res = inFile.showOpenDialog(OwnFrame.this);
			if (res == JFileChooser.APPROVE_OPTION) {
				String name = inFile.getSelectedFile().getPath();
				try (Scanner inf = new Scanner(Paths.get(name))) {
					while (inf.hasNextLine()) {
						String[] line = inf.nextLine().split(";");
						personTableModel.addPerson(new Member(line[0], line[1], line[2]));
						personTableModel.fireTableDataChanged();
					}
				} catch (IOException e) {
					JOptionPane.showMessageDialog(OwnFrame.this, "Input file read failed!", "Import error",
							JOptionPane.ERROR_MESSAGE);
				}
			}

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
				} catch (IOException e) {
					JOptionPane.showMessageDialog(OwnFrame.this, "Output file read failed!", "Export error",
							JOptionPane.ERROR_MESSAGE);
				}
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

		JMenuItem importItem = new JMenuItem("Import");
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

			if (deletePersonDialog.showDialog(OwnFrame.this, "Delete person")) {
				int personId = deletePersonDialog.getId();
				personTableModel.deletePerson(personId);
				personTableModel.fireTableDataChanged();

			}
		}
	}

	private class ModifyPersonAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (modifyPersonDialog == null)
				modifyPersonDialog = new ModifyPerson();

			if (modifyPersonDialog.showDialog(OwnFrame.this, "Modify person")) {
				modifyPersonDialog.getModify(personTableModel);
				personTableModel.fireTableDataChanged();
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
				System.out.println("BŁĄD: Podaj ID osoby!");
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
			}catch (NumberFormatException e) {
				System.out.println("BŁĄD: Podaj ID projektu!");
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
			}catch(NumberFormatException e) {
				System.out.println("BŁĄD: Podaj ID osoby!");
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
				}
			} catch (NumberFormatException e) {
				System.out.println("BŁĄD: Podaj ID projektu oraz osoby!");
			}
		}
	}

    private class ModifyTaskAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
			if (modifyTaskDialog == null) modifyTaskDialog = new ModifyTask();
			try {
				if (modifyTaskDialog.showDialog(OwnFrame.this, "Modify task")) {
					modifyTaskDialog.getModifiedTask(taskTableModel, personTableModel);
					taskTableModel.fireTableDataChanged();
				}
			}catch(NumberFormatException e) {
				System.out.println("BŁĄD: Podaj ID osoby oraz projektu!");
			}
		}
    }

    public class DeleteTaskAction implements ActionListener {
		public void  actionPerformed(ActionEvent event) {
			if (deleteTaskDialog == null) deleteTaskDialog = new DeleteTask();
			try {
				if (deleteTaskDialog.showDialog(OwnFrame.this, "Delete task")) {
					int taskID = deleteTaskDialog.getId();
					taskTableModel.deleteTask(taskID, projectTableModel);
					taskTableModel.fireTableDataChanged();
				}
			}catch (NumberFormatException e) {
				System.out.println("Podaj ID zadania!");
			}
		}
	}

}
