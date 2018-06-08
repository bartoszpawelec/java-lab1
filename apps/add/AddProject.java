package apps.add;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridLayout;
import java.io.Serializable;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import apps.projects.Project;
import apps.table.PersonTableModel;
import apps.table.dbTable.ProjectDb;

public class AddProject extends JPanel implements Serializable {

    private JTextField title;
    private JTextField executorID;
    private JButton saveButton;
    private JButton cancelButton;
    private boolean ok;
    private JDialog dialog;

    public AddProject() {
        setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        panel.add(new JLabel("Project title:"));
        panel.add(title = new JTextField(""));
        panel.add(new JLabel("Executor ID"));
        panel.add(executorID = new JTextField(""));
        add(panel, BorderLayout.CENTER);

        saveButton = new JButton("Save");
        saveButton.addActionListener(event -> {
            ok = true;
            dialog.setVisible(false);
            ProjectDb.insertProject(title.getText(), 0, Integer.parseInt(executorID.getText()));
        });

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(event -> dialog.setVisible(false));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }


    public Project getProject() {
        return new Project(title.getText());
}
    
    public Project getProject(PersonTableModel personTableModel) {
        Project p = new Project(title.getText());
        p.setExecutor(Integer.parseInt(executorID.getText()), personTableModel.getPersons());
        return p;
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
