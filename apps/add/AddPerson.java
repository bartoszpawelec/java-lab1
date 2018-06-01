package apps.add;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import apps.people.Member;

public class AddPerson extends JPanel {
    private JTextField firstName;
    private JTextField lastName;
    private JTextField email;
    private JButton saveButton;
    private JButton cancelButton;
    private boolean ok;
    private JDialog dialog;
    
    public AddPerson() {
    	setLayout(new BorderLayout());
    	JPanel panel = new JPanel();
    	panel.setLayout(new GridLayout(3, 2));
    	panel.add(new JLabel("First name:"));
    	panel.add(firstName = new JTextField(""));
    	panel.add(new JLabel("Last name:"));
    	panel.add(lastName = new JTextField(""));
    	panel.add(new JLabel("Email:"));
    	panel.add(email = new JTextField(""));
    	add(panel, BorderLayout.CENTER);
    	
    	saveButton = new JButton("Save");
    	saveButton.addActionListener(event -> {
    		ok = true;
    		dialog.setVisible(false);
    	});
    	
    	cancelButton = new JButton("Cancel");
    	cancelButton.addActionListener(event -> dialog.setVisible(false));
    	
    	JPanel buttonPanel = new JPanel();
    	buttonPanel.add(saveButton);
    	buttonPanel.add(cancelButton);
    	add(buttonPanel, BorderLayout.SOUTH);
    }
    
    public Member getPerson() {
    	return new Member(firstName.getText(),
    			          lastName.getText(),
    			          email.getText());
    }
    
    public boolean showDialog(Component parent, String title) {
    	ok = false;
    	
    	Frame owner = null;
    	if(parent instanceof Frame)
    		owner = (Frame) parent;
    	else
    		owner = (Frame) SwingUtilities.getAncestorOfClass(Frame.class, parent);
    	
    	if(dialog == null || dialog.getOwner() != owner)
    	{
    		dialog = new JDialog(owner, true);
    		dialog.add(this);
    		dialog.getRootPane().setDefaultButton(saveButton);
    		dialog.pack();
    	}
    	
    	dialog.setTitle(title);
    	dialog.setVisible(true);
    	return ok;
    }
}
