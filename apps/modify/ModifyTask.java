package apps.modify;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridLayout;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.concurrent.Executor;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import apps.projects.Task;
import apps.table.PersonTableModel;
import apps.table.TaskTableModel;
import apps.table.dbTable.TaskDb;


public class ModifyTask extends JPanel implements Serializable{
    private JTextField Title;
    private JTextField Id;
    private JTextField Due;
    private JTextField Executor;
    private JButton saveButton;
    private JButton cancelButton;
    private boolean ok;
    private JDialog dialog;

    public ModifyTask() {
        setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        panel.add(new JLabel("Task ID:"));
        panel.add(Id = new JTextField(""));
        panel.add(new JLabel("Title:"));
        panel.add(Title = new JTextField(""));
        panel.add(new JLabel("Due:"));
        panel.add(Due = new JTextField(LocalDate.now().toString()));
        panel.add(new JLabel("Executor Id:"));
        panel.add(Executor = new JTextField(""));

        add(panel, BorderLayout.CENTER);

        saveButton = new JButton("Modify");
        saveButton.addActionListener(event -> {
            ok = true;
            dialog.setVisible(false);
            TaskDb.modifyTask(Title.getText(),LocalDate.parse(Due.getText()), Integer.parseInt(Executor.getText()),Integer.parseInt(Id.getText()));
       });

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(event -> dialog.setVisible(false));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void getModifiedTask(TaskTableModel ttm, PersonTableModel ptm) {
        Task t = ttm.getTasks().get(Integer.parseInt(Id.getText()) - 1);
        t.setTitle(Title.getText()); 
        t.finishDate = LocalDate.parse(Due.getText());
        t.setExecutor(Integer.parseInt(Id.getText()), ptm.getPersons());
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
