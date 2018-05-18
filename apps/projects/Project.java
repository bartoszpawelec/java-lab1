package apps.projects;

import apps.people.Member;
import java.util.*;

public class Project extends Base
{
  public ArrayList<Task> tasks;
  public ArrayList<Member> members;
  public int id;
  
  public Project(String t)
  {
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
  
  public void showTask () {
      for(Task t : tasks)
          System.out.println(t);
  }
  
  public String toString() {
    String out = "Project title: " + getTitle();
    return out;
  }
}
