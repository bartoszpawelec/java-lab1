package apps.projects;

import apps.people.Member;
import java.util.*;

public class Project extends Base {
  private ArrayList<Task> tasks;
  private ArrayList<Member> members;
  private String name;

  public Project(String t) {
    super(t);
    tasks = new ArrayList<>();
    members = new ArrayList<>();
  }

  public Project(String t, String name) {
    super(t);
    tasks = new ArrayList<>();
    members = new ArrayList<>();
    this.name = name;
  }





  public String getName(int id) {
    return name;
  }


  public void addTask(Task t) {
    tasks.add(t);
  }

  public void addMember(Member m) {
    members.add(m);
  }

  public String toString() {
    String out = "Project title: " + getTitle();
    return out;
  }
}
