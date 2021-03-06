package apps.people;

import java.io.Serializable;

public class Member implements Serializable {
	private String firstName;
	private String lastName;
	private String email;
	private static int nextId = 1;
	private int id;

	public Member(String fn, String ln, String e) {
		firstName = fn;
		lastName = ln;
		email = e;
		id = nextId++;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return firstName + " " + lastName + " " + email;
	}

	public void setName(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String toString() {
		return " First name: " + firstName + " Surname: " + lastName + " Email: " + email;
	}
}
