import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import apps.add.AddPerson;
import apps.people.Member;
import apps.table.PersonTableModel;

public class OwnFrame extends JFrame {
	private static final int DEFAULT_WIDTH = 500;
	private static final int DEFAULT_HEIGHT = 300;
	
	private JFileChooser inFile;
	private JFileChooser outFile;
	private AddPerson addPersonDialog = null;
	private PersonTableModel personTableModel;
	
	private JTable personTable;
	
	
	
	public OwnFrame() {
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		personTable = new JTable();
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		
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
		JMenuItem newItem = new JMenuItem("New");
		newItem.addActionListener(new NewPersonAction());
		fileMenu.add(newItem);
		fileMenu.addSeparator();
		fileMenu.add(importItem);
		fileMenu.add(exportItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		JMenu aboutMenu = new JMenu("About");
		JMenuItem aboutItem = new JMenuItem("About ...");
		aboutMenu.add(aboutItem);
		menuBar.add(fileMenu);
		menuBar.add(aboutMenu);
		setJMenuBar(menuBar);
		add(new JScrollPane(personTable));
		pack();
		
	}

	private class NewPersonAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if(addPersonDialog == null) addPersonDialog = new AddPerson();
			
			if(addPersonDialog.showDialog(OwnFrame.this, "Add person"))
			{
				Member p = addPersonDialog.getPerson();
				personTableModel.addPerson(p);
				personTableModel.fireTableDataChanged();
			}
		}
	}
	
}








