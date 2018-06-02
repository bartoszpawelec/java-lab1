import apps.projects.Task;
import apps.people.Member;
import apps.projects.Project;

import java.io.PrintWriter;
import java.util.*;

import javax.swing.JFrame;

class ProjectManger
{
    public static void main(String[] args) throws OwnException
    {
      ArrayList<Project> projects = new ArrayList<>();
      ArrayList<Member> members = new ArrayList<>();
      /*
      Scanner ins = new Scanner(System.in);
      while(true) {
    	    System.out.print("> ");
	        String cmd = ins.nextLine();
	        if (cmd.trim().equals("quit"))
	            break;
	        Reader.Do(cmd, projects, members);
	      
      }*/
      
      OwnFrame ownFrame = new OwnFrame();
      ownFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      ownFrame.setTitle("Own Frame");
      ownFrame.setVisible(true);
    }
}
