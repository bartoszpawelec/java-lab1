import java.lang.reflect.Array;
import java.util.*;
import apps.projects.Task;
import apps.people.Member;
import apps.projects.Project;

public class Reader {

    public static String[] Line;
    
	public static void Do(String s, ArrayList<Project> projects, ArrayList<Member> members){
		 
	        Line = s.split(" ");

	        switch (Line[0]) {
	            case "add":
	                add(projects,members);
	                break;
	            
	            case "list":
	            	list(projects,members);
	            	break;
	            
	            case "modify":
	            	modify(projects,members);
	            	break;
	            	
	            	
	            case "delete":
	            	delete(projects,members);
	            	break;
	            	
	            default: {
	                System.out.println("Error!");
	                break;
	            }
	        }
	}
	
	public static void add(ArrayList<Project> projects, ArrayList<Member> members) {
	    switch(Line[1]) {
		    case "project":
		    	projects.add(new Project(Line[2]));
		    	break;

		    case "person": 
		    	members.add(new Member(Line[2], Line[3], Line[4]));
				break;
			
			case "person_to_project":
				for(Project p : projects){
					if(p.getTitle().equals(Line[5]))
						p.addMember(new Member(Line[2],Line[3],Line[4]));
				}
				break;

		    case "task":
		    	
		    	for (Project p : projects) { 
                    if(p.getTitle().equals(Line[3]))
		    			p.addTask(new Task(Line[2]));
                }
                break;

		    default: {
		        System.out.println("Error!");
                break;
		    }
		    
		    	
	    }
	}
	
	public static void delete(ArrayList<Project> projects, ArrayList<Member> members) {
		switch(Line[1]) {
	    case "project":
	    	int id = Integer.parseInt(Line[2]);
	    	projects.remove(id);
	    	break;

		case "person":
			int pid = Integer.parseInt(Line[2]);
			members.remove(pid);
			break;
			
			
			
			
	    case "task":
		int tid = Integer.parseInt(Line[2]);
	    	
			for (Project p : projects) { 
				if(p.getTitle().equals(Line[3])) {
					p.tasks.remove(tid);
	
				}
			}    	break;
	    	
	    default: {
	        System.out.println("Error!");
            break;
	    }
            
		}
	}
	
	public static void modify (ArrayList<Project> projects, ArrayList<Member> members) {
		switch(Line[1]) {
		case "project":
			int id = Integer.parseInt(Line[2]);
	    	projects.get(id).setTitle(Line[3]);
            break;

		case "person":
			int pid = Integer.parseInt(Line[2]);
			members.get(pid).setName(Line[3],Line[4]);	
			break;
			
			
			
		case "task":
			int tid = Integer.parseInt(Line[2]);
	    	
			for (Project p : projects) { 
				if(p.getTitle().equals(Line[3])) {
					p.tasks.get(tid).setTitle(Line[4]);
	
				}
			}
			break;
			
			
		default:
			System.out.println("Error!");
            break;	
			
		}
	}
	
	public static void list(ArrayList<Project> projects, ArrayList<Member> members) {

		switch(Line[1]) {
		
		case "project":
			for (Project p : projects) {
		    	System.out.println(p);
		    }
			break;
			
		case "person":
			for (Member m : members) {
				System.out.println(m);
			}
			break;

			case "members":
				for (Project p : projects) {
					if (p.getTitle().equals(Line[2]))
						p.showMembers();
				}
				break;

		case "task":
			 for (Project p : projects)  {
                 if (p.getTitle().equals(Line[2]))
                     p.showTask();
			 }
			 break;

         
		default:
			System.out.println("Error!");
            break;	
		}
	}
}
