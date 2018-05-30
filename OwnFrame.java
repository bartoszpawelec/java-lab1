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

import apps.people.Member;

public class OwnFrame extends JFrame {
	private static final int DEFAULT_WIDTH = 500;
	private static final int DEFAULT_HEIGHT = 300;
	private JTable table;
	private JFileChooser inFile;
	private JFileChooser outFile;
	private AddPerson dialog = null;
	private PersonTableModel model;
	
	public OwnFrame() {
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		table = new JTable();
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem importItem = new JMenuItem("Import");
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
				        model.addPerson(new Member(line[0], line[1], line[2]));
						model.fireTableDataChanged();
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
					 for(Member p: model.getPersons())
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
		add(new JScrollPane(table));
		pack();
		
	}

	private class NewPersonAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if(dialog == null) dialog = new AddPerson();
			
			if(dialog.showDialog(OwnFrame.this, "Add person"))
			{
				Member p = dialog.getPerson();
				model.addPerson(p);
				model.fireTableDataChanged();
			}
		}
	}
	
}






class PersonTableModel extends AbstractTableModel {

	private ArrayList<Member> people = new ArrayList<>();
	private static int columns = 4;
	private String[] columnNames = {"Id", "First name", "Last name", "Email"};
	
	public PersonTableModel() {
	}
	
	public int getRowCount() {
		return people.size();
	}
	
	public int getColumnCount() {
		return columns;
	}
	
	public Object getValueAt(int r, int c) {
		return people.get(r).attr(c);
	}
	
	public String getColumnName(int c) {
		return columnNames[c];
	}
	
	public void addPerson(Member p) {
		people.add(p);
	}
	
	public ArrayList<Member> getPersons() {
		return people;
	}
}


