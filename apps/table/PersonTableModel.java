package apps.table;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.table.AbstractTableModel;

import apps.people.Member;
public class PersonTableModel extends AbstractTableModel {

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

	public void deletePerson(int tmp) {
		Iterator<Member> personIterator = people.iterator();
		while (personIterator.hasNext()) {
			Member m = personIterator.next();
			if (m.id == tmp)
				personIterator.remove();
		}
	}
	public ArrayList<Member> getPersons() {
		return people;
	}
}
