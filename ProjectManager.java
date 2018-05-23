import apps.projects.Task;
import apps.people.Member;
import apps.projects.Project;

import java.io.PrintWriter;
import java.util.*;

class ProjectManger
{
    public static void main(String[] args)
    {
      ArrayList<Project> projects = new ArrayList<>();
      ArrayList<Member> members = new ArrayList<>();
      
      Scanner ins = new Scanner(System.in);
      while(true) {
    	    System.out.print("> ");
	        String cmd = ins.nextLine();
	        if (cmd.trim().equals("quit"))
	            break;
	        Reader.Do(cmd, projects, members);
	      
      }
    }
}
