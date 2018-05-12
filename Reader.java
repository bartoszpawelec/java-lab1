import java.util.*;
import apps.projects.Task;
import apps.people.Member;
import apps.projects.Project;

public class Reader {
    public static String[] Line;
    
	public static void Do(String s, ArrayList<Project> projects){
		 
	        Line = s.split(" ");

	        switch (Line[0]) {
	            case "add":
	                add(projects);
	                break;
	            
	            case "list":
	            	list(projects);
	            	break;
	            	
	            default: {
	                System.out.println("Error!");
	                break;
	            }
	        }
	}
	
	public static void add(ArrayList<Project> projects) {
	    switch(Line[1]) {
		    case "project":
		    	projects.add(new Project(Line[2]));
		    	break;
		   
		    default: {
		        System.out.println("Error!");
                break;
		    }
		    
		    	
	    }
	}
	
	public static void delete(ArrayList<Project> projects) {
		switch(Line[1]) {
	    case "project":
	    	for (Project p : projects) {
                if (p..equals(Line[2]))  
                    projects.remove(p);
            }
	    	break;
	    	
	    default: {
	        System.out.println("Error!");
            break;
	    }
	    
	    	
    }
	}
	
	public static void list(ArrayList<Project> projects) {

	    for (Project p : projects) {
	    	System.out.println(p);
	    }

	}
}