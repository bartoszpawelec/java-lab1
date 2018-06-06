package apps.modify;

import apps.projects.Project;
import apps.table.ProjectTableModel;
import apps.table.dbTable.ProjectDb;

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

public class ModifyProject extends JPanel implements Serializable{
	private JTextField Title;
	private JTextField Id;
	private JTextField Executor;
	private JButton saveButton;
	private JButton cancelButton;
	private boolean ok;
	private JDialog dialog;

	public ModifyProject() {
		setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 2));
		panel.add(new JLabel("Project ID:"));
		panel.add(Id = new JTextField(""));
		panel.add(new JLabel("Title:"));
		panel.add(Title = new JTextField(""));
		panel.add(new JLabel("Executor Id:"));
		panel.add(Executor = new JTextField(""));

		add(panel, BorderLayout.CENTER);

		saveButton = new JButton("Modify");
		saveButton.addActionListener(event -> {
			ok = true;
			dialog.setVisible(false);
			ProjectDb.modifyProject(Integer.parseInt(Id.getText()), Title.getText());
		});

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(event -> dialog.setVisible(false));

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		add(buttonPanel, BorderLayout.SOUTH);
	}

	public void getModifiedProject(ProjectTableModel ptm) {
		Project p = ptm.getProjects().get(Integer.parseInt(Id.getText()) - 1);
		p.setTitle(Title.getText());
	}

	public boolean showDialog(Component parent, String title) {
		ok = false;

		Frame owner = null;
		if (parent instanceof Frame)
			owner = (Frame) parent;
		else
			owner = (Frame) SwingUtilities.getAncestorOfClass(Frame.class, parent);

		if (dialog == null || dialog.getOwner() != owner) {
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
