package com.jeta.locker.type.password;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.jeta.forms.components.panel.FormPanel;
import com.jeta.locker.type.key.KeyConstants;
import com.jeta.open.gui.framework.JETAController;
import com.jeta.open.gui.framework.JETADialog;
import com.jeta.open.gui.utils.JETAToolbox;

public class PasswordController  extends JETAController {

	public PasswordController(PasswordView view) {
		super(view);
		assignAction( PasswordConstants.ID_GENERATE_PASSWORD, new GeneratePasswordAction() );
		
		assignListener( PasswordConstants.ID_ACCOUNTS_TABLE, new ListSelectionListener() {
        	public void valueChanged(ListSelectionEvent e) {
        	//	uiChanged();
        	}
        });
	}
	
	
	public static class GeneratePasswordAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			PasswordGeneratorView view = new PasswordGeneratorView();
			JETADialog dlg = JETAToolbox.invokeDialog( view, null, "Generate Password", null );
			if (dlg.isOk()) {
				StringSelection stringSelection = new StringSelection(view.getPassword());
				Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
				clpbrd.setContents(stringSelection, null);
			}			
		}
	}
	public class AddAction implements ActionListener {
		@Override
		public void actionPerformed( ActionEvent evt ) {

		}
	}

	public class EditKeyAction implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			FormPanel panel = new FormPanel( "key.jfrm" );		
			panel.setPreferredSize( new java.awt.Dimension(450,500));
			JETADialog dlg = JETAToolbox.invokeDialog( panel, null, "Edit Key", panel.getTextField(KeyConstants.ID_DESCRIPTION) );
			if (dlg.isOk()) {
				
			}
		}
	}
	
	public class DeleteAction implements ActionListener {
		public void actionPerformed( ActionEvent evt ) {
	 		int result = JOptionPane.showConfirmDialog(null,"Delete selected account?", "Confirm", JOptionPane.YES_NO_OPTION);
			if ( result == JOptionPane.YES_OPTION ) {

			}
		}
	}
}
