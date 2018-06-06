package apps.delete;
import apps.table.dbTable.PersonDb;

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


public class DeletePerson extends JPanel implements Serializable {
    private JTextField personID;
    private JButton deleteButton;
    private JButton cancelButton;
    private boolean ok;
    private JDialog dialog;

    public DeletePerson() {
        setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(new JLabel("Member id:"));
        panel.add(personID = new JTextField(""));
        add(panel, BorderLayout.CENTER);

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(event -> {
            ok = true;
            dialog.setVisible(false);
            PersonDb.deletePerson(Integer.parseInt(personID.getText()));
        });

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(event -> dialog.setVisible(false));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(deleteButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    public int getId() {
        int tmp = Integer.parseInt(personID.getText());
        return tmp;
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
            dialog.getRootPane().setDefaultButton(deleteButton);
            dialog.pack();
        }

        dialog.setTitle(title);
        dialog.setVisible(true);
        return ok;
    }

}

