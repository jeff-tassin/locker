package com.jeta.locker.type.password;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import org.json.JSONObject;

import com.jeta.forms.components.panel.FormPanel;
import com.jeta.locker.common.LockerConstants;
import com.jeta.locker.common.LockerUtils;
import com.jeta.locker.main.Worksheet;
import com.jeta.open.gui.framework.JETAPanel;


public class PasswordView extends JETAPanel {
 
	private PasswordTableModel m_model;
	private JTable m_table;
	private boolean m_showPasswords = false;
 
    public PasswordView( PasswordTableModel model ) {
        super(new BorderLayout());
 
        add( new FormPanel("passwordAccounts.jfrm"));
        m_table = getTable(PasswordConstants.ID_ACCOUNTS_TABLE);
        m_table.setModel(model);
        m_model = model;
        m_table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        m_table.setFillsViewportHeight(true);
        m_table.setAutoCreateRowSorter(true);
        m_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        m_table.setCellSelectionEnabled(true);
        m_table.setRowHeight(26);
        m_table.setGridColor( new Color(225,225,225) );
      
        m_table.setComponentPopupMenu( createContextMenu() );
        Font font = m_table.getFont();
        if (font.getSize() < 14 ) {
        	m_table.setFont( new Font( font.getFamily(), Font.PLAIN, 14 ) );
        }
        
        
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
        	Color evenColor = new Color(245,245,245);
            Border padding = BorderFactory.createEmptyBorder(0, 5, 0, 5);
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                    int row, int column) {
            	
            	if ( m_model.isPasswordColumn(column) && value != null ) {
            		value = m_showPasswords ? value : "*******************";
            	}  
            	if ( row % 2 == 0 ) {
                	this.setBackground(Color.white);
                } else {
                	this.setBackground(evenColor);
                }
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus,row, column);
               // setBorder(BorderFactory.createCompoundBorder(getBorder(), padding));
              
                return this;
            }
        };
    
        m_table.setDefaultRenderer( String.class, cellRenderer);
        setUIDirector( new PasswordUIDirector(this));
        setController( new PasswordController(this));
    }
    
    public void showPasswords( boolean show ) {
    	m_showPasswords = show;
    	m_model.fireTableDataChanged();
    }
    
    private JPopupMenu createContextMenu() {
        JMenuItem copy = new JMenuItem("Copy");
        JPopupMenu menu = new JPopupMenu();
        menu.add(copy);
        copy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = m_table.convertRowIndexToModel(m_table.getSelectedRow());
				int col = m_table.convertColumnIndexToModel(m_table.getSelectedColumn());
				String sval = String.valueOf(m_model.getValueAt(row, col));
				StringSelection stringSelection = new StringSelection(sval);
				Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
				clpbrd.setContents(stringSelection, null);
			}
        });
        return menu;
    }
    
    public PasswordTableModel getModel() {
    	return m_model;
    }
 
    public Worksheet getWorksheet() {
    	return m_model.getWorksheet();
    }
   
}