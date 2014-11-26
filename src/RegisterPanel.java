import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class RegisterPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = -3831920790532914938L;
	
	private JTextField username = new JTextField();
	private JPasswordField passwordA = new JPasswordField();
	private JPasswordField passwordB = new JPasswordField();
	private JButton submit = new JButton("Register");

	public RegisterPanel() {
		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.0;
		this.add(new JLabel("Username", SwingConstants.RIGHT), c);
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(this.username, c);
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0.0;
		this.add(new JLabel("Password", SwingConstants.RIGHT), c);
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(this.passwordA, c);
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 0.0;
		this.add(new JLabel("Verify password", SwingConstants.RIGHT), c);
		c.gridx = 1;
		c.gridy = 2;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(this.passwordB, c);
		c.gridx = 0;
		c.gridy = 3;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 2;
		this.add(this.submit, c);
		this.submit.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		String pwA = new String(this.passwordA.getPassword());
		String pwB = new String(this.passwordB.getPassword());
		
		if (! pwA.equals(pwB)) {
			return;
		}
		
		String un = this.username.getText();
		
		// send to DB
	}
}