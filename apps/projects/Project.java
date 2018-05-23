package apps.projects;

import apps.people.Member;
import java.util.*;

public class Project extends Base
{
  public ArrayList<Task> tasks;
  public ArrayList<Member> members;
  
  public Project(String t)
  {
    super(t);
    tasks = new ArrayList<>();
    members = new ArrayList<>();
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

  public void showMembers () {
    for(Member m : members)
      System.out.println(m);
  }
  
  public String toString() {
    String out = "Project title: " + getTitle();
    return out;
  }
}
