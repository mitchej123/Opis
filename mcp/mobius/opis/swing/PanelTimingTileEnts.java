package mcp.mobius.opis.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import mcp.mobius.opis.data.holders.stats.StatAbstract;
import mcp.mobius.opis.network.enums.AccessLevel;
import net.miginfocom.swing.MigLayout;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class PanelTimingTileEnts extends JPanel implements ActionListener{
	private JTable table;
	private JButtonAccess btnCenter;
	private JButtonAccess btnTeleport;
	private JButtonAccess btnReset;
	private JButtonAccess btnRun;
	private JLabel lblSummary;

	/**
	 * Create the panel.
	 */
	public PanelTimingTileEnts() {
		setLayout(new MigLayout("", "[][][][grow][]", "[][grow][]"));
		
		btnCenter = new JButtonAccess("Center Map", AccessLevel.NONE);
		add(btnCenter, "cell 0 0");
		btnCenter.addActionListener(this);
		
		btnTeleport = new JButtonAccess("Teleport", AccessLevel.PRIVILEGED);
		add(btnTeleport, "cell 1 0");
		btnTeleport.addActionListener(this);
		
		btnReset = new JButtonAccess("Reset Highlight", AccessLevel.PRIVILEGED);
		add(btnReset, "cell 2 0");
		btnReset.addActionListener(this);
		
		btnRun = new JButtonAccess("Run Opis", AccessLevel.PRIVILEGED);
		add(btnRun, "cell 4 0");
		btnRun.addActionListener(this);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "cell 0 1 5 1,grow");
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Type", "Mod", "Dim", "Pos", "Update Time"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, Integer.class, Object.class, StatAbstract.class
			};
			boolean[] columnEditables = new boolean[] {
					false, false, false, false, false
			};			
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.setAutoCreateRowSorter(true);		
		scrollPane.setViewportView(table);
		
		lblSummary = new JLabel("New label");
		add(lblSummary, "cell 0 2 5 1,alignx center");
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );		
		
		for (int i = 0; i < table.getColumnCount(); i++)
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);			

	}

	public JButton getBtnCenter()  {return btnCenter;}
	public JButton getBtnTeleport(){return btnTeleport;}
	public JButton getBtnReset()   {return btnReset;}
	public JButton getBtnRun()     {return btnRun;}
	public JTable  getTable()      {return table;}
	public JLabel  getLblSummary() {return lblSummary;}

	@Override
	public void actionPerformed(ActionEvent e) {}
}
