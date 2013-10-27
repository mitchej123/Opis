package mcp.mobius.opis.overlay;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.world.ChunkCoordIntPair;
import mapwriter.api.IMwChunkOverlay;
import mapwriter.api.IMwDataProvider;
import mapwriter.map.MapView;
import mcp.mobius.opis.network.Packet0x02RequestChunkStatus;

public class OverlayLoadedChunks implements IMwDataProvider {

	public class ChunkOverlay implements IMwChunkOverlay{

		Point coord;
		boolean forced;
		
		public ChunkOverlay(int x, int z, boolean forced){
			this.coord = new Point(x, z);
			this.forced = forced;
		}
		
		@Override
		public Point getCoordinates() {	return this.coord; }

		@Override
		public int getColor() {	return this.forced ? 0x500000ff : 0x5000ff00; }

		@Override
		public float getFilling() {	return 1.0f; }

		@Override
		public boolean hasBorder() { return true; }

		@Override
		public float getBorderWidth() { return 0.5f; }

		@Override
		public int getBorderColor() { return 0xff000000; }
		
	}	
	
	public static OverlayLoadedChunks instance = new OverlayLoadedChunks();
	private OverlayLoadedChunks(){};
	
	
	public HashMap<ChunkCoordIntPair, Boolean> chunks = new HashMap<ChunkCoordIntPair, Boolean>();
	
	@Override
	public ArrayList<IMwChunkOverlay> getChunksOverlay(int dim, double centerX,	double centerZ, double minX, double minZ, double maxX, double maxZ) {
		ArrayList<IMwChunkOverlay> overlays = new ArrayList<IMwChunkOverlay>();
		for (ChunkCoordIntPair chunk : chunks.keySet())
			overlays.add(new ChunkOverlay(chunk.chunkXPos, chunk.chunkZPos, chunks.get(chunk)));
		return overlays;
	}

	@Override
	public String getStatusString(int dim, int bX, int bY, int bZ) {
		int xChunk = bX >> 4;
		int zChunk = bZ >> 4;
		ChunkCoordIntPair chunkCoord = new ChunkCoordIntPair(xChunk, zChunk);
		
		if (chunks.containsKey(chunkCoord)){
			if (chunks.get(chunkCoord))
				return ", Force loaded";
			else
				return ", Player loaded";
		}
		else
			return ", Not loaded";
	}

	@Override
	public void onMiddleClick(int dim, int bX, int bZ, MapView mapview) {
	}

	@Override
	public void onDimensionChanged(int dimension, MapView mapview) {
		PacketDispatcher.sendPacketToServer(Packet0x02RequestChunkStatus.create(dimension));
	}

	@Override
	public void onMapCenterChanged(double vX, double vZ, MapView mapview) {
	}

	@Override
	public void onZoomChanged(int level, MapView mapview) {
	}

	@Override
	public void onOverlayActivated(MapView mapview) {
		PacketDispatcher.sendPacketToServer(Packet0x02RequestChunkStatus.create(mapview.getDimension()));
	}
}
