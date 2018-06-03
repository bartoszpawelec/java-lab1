package apps.people;

import java.io.Serializable;

public class Member implements Serializable {
	public String firstName;
	public String lastName;
	public String email;
	private static int nextId = 1;
	public int id;

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

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	interface GetAttr {
		String attr();
	}

	private GetAttr[] attrs = new GetAttr[] { new GetAttr() {
		public String attr() {
			return Integer.toString(id);
		}
	}, new GetAttr() {
		public String attr() {
			return getFirstName();
		}
	}, new GetAttr() {
		public String attr() {
			return getLastName();
		}
	}, new GetAttr() {
		public String attr() {
			return getEmail();
		}
	}, };

	public String attr(int index) {
		return attrs[index].attr();
	}
}
