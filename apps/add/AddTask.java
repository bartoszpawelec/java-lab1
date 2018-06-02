package apps.add;

import javax.swing.*;

import apps.projects.Task;
import apps.table.PersonTableModel;
import apps.table.ProjectTableModel;

import java.awt.*;
import java.time.LocalDate;

public class AddTask extends JPanel {
    private JTextField name;
    private JTextField projectID;
    private JTextField Due;
    private JTextField executorID;
    private JButton saveButton;
    private JButton cancelButton;
    private boolean ok;
    private JDialog dialog;

    public AddTask() {
        setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        panel.add(new JLabel("Task name:"));
        panel.add(name = new JTextField(""));
        panel.add(new JLabel("Project ID:"));
        panel.add(projectID = new JTextField(""));
        panel.add(new JLabel("Due: "));
        panel.add(Due = new JTextField(LocalDate.now().toString()));
        panel.add(new JLabel("Executor ID:"));
        panel.add(executorID = new JTextField(""));

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

    public Task getTask(ProjectTableModel projectTableModel, PersonTableModel personTableModel) {
        Task t = new Task(name.getText());
        t.setExecutor(Integer.parseInt(executorID.getText()), personTableModel.getPersons());
        projectTableModel.addTask(t, Integer.parseInt(projectID.getText()));
        t.finishDate = LocalDate.parse(Due.getText());
        return t;
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

        dialog.setLocationRelativeTo(null);
        dialog.setTitle(title);
        dialog.setVisible(true);
        return ok;
    }
}
