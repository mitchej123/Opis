package mcp.mobius.opis.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import mcp.mobius.opis.data.holders.stats.StatAbstract;
import mcp.mobius.opis.network.enums.AccessLevel;
import mcp.mobius.opis.network.enums.Message;
import mcp.mobius.opis.network.packets.client.Packet_ReqData;
import net.miginfocom.swing.MigLayout;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import cpw.mods.fml.common.network.PacketDispatcher;

public class PanelTimingHandlers extends JPanel implements ActionListener{
	private JTable table;
	private JButtonAccess btnRun;
	private JLabel lblSummary;

	/**
	 * Create the panel.
	 */
	public PanelTimingHandlers() {
		setLayout(new MigLayout("", "[grow][]", "[][grow][]"));
		
		btnRun = new JButtonAccess("Run Opis", AccessLevel.PRIVILEGED);
		add(btnRun, "cell 1 0");
		btnRun.addActionListener(this);
		
		JScrollPane scrollPane = new JScrollPane();
		
		add(scrollPane, "cell 0 1 2 1,grow");
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Name", "Update Time"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, StatAbstract.class
			};
			boolean[] columnEditables = new boolean[] {
					false, false
			};			
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.setAutoCreateRowSorter(true);		
		scrollPane.setViewportView(table);
		
		lblSummary = new JLabel("TmpText");
		add(lblSummary, "cell 0 2 2 1,alignx center");
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );		
		
		for (int i = 0; i < table.getColumnCount(); i++)
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);			

	}

	public JButton getBtnRun()    {return btnRun;}
	public JTable  getTable()     {return table;}
	public JLabel  getLblSummary(){return lblSummary;}

	@Override
	public void actionPerformed(ActionEvent e) {
		// RUN OPIS Button
		if (e.getSource() == this.getBtnRun()){
			PacketDispatcher.sendPacketToServer(Packet_ReqData.create(Message.COMMAND_START));
		}		
	}
}
