package apps.modify;
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

import apps.people.Member;
import apps.table.PersonTableModel;

public class ModifyPerson extends JPanel implements Serializable{
    private JTextField firstName;
    private JTextField lastName;
    private JTextField email;
    private JTextField id;
    private JButton saveButton;
    private JButton cancelButton;
    private boolean ok;
    private JDialog dialog;

    public ModifyPerson() {
        setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        panel.add(new JLabel("Member Id:"));
        panel.add(id = new JTextField(""));
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

    public void getModify(PersonTableModel personTableModel)
    {
        int tmp = Integer.parseInt(id.getText())-1;
        Member m = personTableModel.getPersons().get(tmp);
        m.firstName = firstName.getText();
        m.lastName = lastName.getText();
        m.email = email.getText();

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
