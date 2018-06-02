import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import apps.add.*;
import apps.people.Member;
import apps.table.PersonTableModel;

public class OwnFrame extends JFrame {
	private static final int DEFAULT_WIDTH = 500;
	private static final int DEFAULT_HEIGHT = 300;
	
	private JFileChooser inFile;
	private JFileChooser outFile;
	private AddPerson addPersonDialog = null;
	private DeletePerson deletePersonDialog = null;
	private ModifyPerson modifyPersonDialog = null;
	private PersonTableModel personTableModel;
	JMenuItem importItem = new JMenuItem("Import");
	
	private JTable personTable;
	
	
	
	public OwnFrame() {
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		JMenuBar menuBar = new JMenuBar();
		personTableModel = new PersonTableModel();
		personTable = new JTable(personTableModel);

		JMenu fileMenu = new JMenu("File");
		JMenu memberMenu = new JMenu("Member");
		
		inFile = new JFileChooser();
		outFile = new JFileChooser();


		importItem.addActionListener(event -> { 
			inFile.setCurrentDirectory(new File("."));
			int res = inFile.showOpenDialog(OwnFrame.this);
			if(res == JFileChooser.APPROVE_OPTION)
			{
				String name = inFile.getSelectedFile().getPath();
				try(Scanner inf = new Scanner(Paths.get(name)))
				{
					 while(inf.hasNextLine())
				      {
				        String[] line = inf.nextLine().split(";");
				        personTableModel.addPerson(new Member(line[0], line[1], line[2]));
						personTableModel.fireTableDataChanged();
				      }
				}
				catch(IOException e)
				{
				  JOptionPane.showMessageDialog(OwnFrame.this, "Input file read failed!", "Import error", JOptionPane.ERROR_MESSAGE);
				}
			}
		
		});
		
		JMenuItem exportItem = new JMenuItem("Export");
		exportItem.addActionListener(event -> {
			outFile.setCurrentDirectory(new File("."));
			int res = outFile.showSaveDialog(OwnFrame.this);
			if(res == JFileChooser.APPROVE_OPTION)
			{
				String name = outFile.getSelectedFile().getPath();
				try(PrintWriter outf = new PrintWriter(name);)
				{
					 for(Member p: personTableModel.getPersons())
				      {
				        outf.println(p);
				      }
				}
				catch(IOException e)
				{
				  JOptionPane.showMessageDialog(OwnFrame.this, "Output file read failed!", "Export error", JOptionPane.ERROR_MESSAGE);
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
		JMenuItem importItem = new JMenuItem("Import");
		fileMenu.addSeparator();
		fileMenu.add(importItem);
		fileMenu.add(exportItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);

		memberMenu.add(addPersonItem);
		memberMenu.add(deletePersonItem);
		memberMenu.add(modifyPersonItem);

		JMenu aboutMenu = new JMenu("About");
		JMenuItem aboutItem = new JMenuItem("About ...");
		aboutMenu.add(aboutItem);
		menuBar.add(fileMenu);
		menuBar.add(aboutMenu);
		menuBar.add(memberMenu);
		setJMenuBar(menuBar);
		add(new JScrollPane(personTable));
		pack();
		
	}

	private class AddPersonAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if(addPersonDialog == null) addPersonDialog = new AddPerson();
			
			if(addPersonDialog.showDialog(OwnFrame.this, "Add person"))
			{
				Member m = addPersonDialog.getPerson();
				personTableModel.addPerson(m);
				personTableModel.fireTableDataChanged();
			}
		}
	}

	private class DeletePersonAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (deletePersonDialog == null) deletePersonDialog = new DeletePerson();

			if (deletePersonDialog.showDialog(OwnFrame.this, "Delete person")) {
				int personId = deletePersonDialog.getId();
				personTableModel.deletePerson(personId);
				personTableModel.fireTableDataChanged();


			}
		}
	}

	private class ModifyPersonAction implements  ActionListener {
		public void actionPerformed(ActionEvent event){
			if(modifyPersonDialog == null) modifyPersonDialog = new ModifyPerson();

			if(modifyPersonDialog.showDialog(OwnFrame.this, "Modify person")){
				modifyPersonDialog.getModify(personTableModel);
				personTableModel.fireTableDataChanged();
			}
		}
	}
}








