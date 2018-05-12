package apps.projects;

import apps.people.Member;
import java.util.*;

public class Project extends Base
{
  private ArrayList<Task> tasks;
  private ArrayList<Member> members;

  public Project(String t)
  {
    super(t);
    tasks = new ArrayList<>();
    members = new ArrayList<>();
  }

  public void addTask(Task t)
  {
    tasks.add(t);
  }

  public void addMember(Member m)
  {
    members.add(m);
  }

  public String toString()
  {
    String out = "Project title: " + getTitle();
    return out;
  }
}
