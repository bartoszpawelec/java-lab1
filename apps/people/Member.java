package apps.people;

public class Member
{
  private String firstName;
  private String lastName;
  private String email;

  public Member(String fn, String ln, String e)
  {
    firstName = fn;
    lastName = ln;
    email = e;
  }

  public String getName() {
    return firstName + lastName;
  }

  public void setName(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }


  public String toString()
  {
    return firstName + " " + lastName + " (" + email + ")";
  }
}
