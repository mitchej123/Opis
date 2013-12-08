package mcp.mobius.opis.network.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import mcp.mobius.opis.network.Packets;
import net.minecraft.network.packet.Packet250CustomPayload;

public class Packet_ReqTeleportEID {
	public byte header;
	public int  eid;
	public int  dim;

	public Packet_ReqTeleportEID(Packet250CustomPayload packet) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		
		try{
			this.header    = inputStream.readByte();
			this.eid       = inputStream.readInt();
			this.dim       = inputStream.readInt();
		} catch (IOException e){}				
	}

	public static Packet250CustomPayload create(int eid, int dim){
		Packet250CustomPayload packet = new Packet250CustomPayload();
		ByteArrayOutputStream bos     = new ByteArrayOutputStream(1);
		DataOutputStream outputStream = new DataOutputStream(bos);

		try{
			outputStream.writeByte(Packets.REQ_TELEPORT_EID);
			outputStream.writeInt(eid);
			outputStream.writeInt(dim);
		}catch(IOException e){}
		
		packet.channel = "Opis";
		packet.data    = bos.toByteArray();
		packet.length  = bos.size();		
		
		return packet;
	}
}