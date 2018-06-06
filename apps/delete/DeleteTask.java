package apps.delete;

import javax.swing.*;


import java.awt.*;
import java.io.Serializable;


public class DeleteTask extends JPanel implements Serializable {
    private JTextField taskID;
    private JButton deleteButton;
    private JButton cancelButton;
    private boolean ok;
    private JDialog dialog;

    public DeleteTask() {
        setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        panel.add(new JLabel("Task ID:"));
        panel.add(taskID = new JTextField(""));


        add(panel, BorderLayout.CENTER);

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(event -> {
            ok = true;
            dialog.setVisible(false);
        });

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(event -> dialog.setVisible(false));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(deleteButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public int getId() {
        int tmp = Integer.parseInt(taskID.getText());
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

        dialog.setLocationRelativeTo(null);
        dialog.setTitle(title);
        dialog.setVisible(true);
        return ok;
    }
}
