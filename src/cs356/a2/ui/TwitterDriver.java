package cs356.a2.ui;

import javax.swing.SwingUtilities;

public class TwitterDriver {

public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				AdminUI admin = AdminUI.getInstance();
				admin.init();
			}
		});
	}
	
}
