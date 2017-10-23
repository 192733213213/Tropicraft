package net.tropicraft.core.common.network;

import java.nio.charset.Charset;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessagePlayerSwimData implements IMessage{
	
	public PlayerSwimData data;
	
	public MessagePlayerSwimData() { }
	public MessagePlayerSwimData(PlayerSwimData d) {

		data = d;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		int len = buf.readInt();
		UUID uuid = UUID.fromString(new String(buf.readBytes(len).toString(Charset.defaultCharset())));
		if(data == null) {
			data = new PlayerSwimData(uuid);
		}
		data.rotationYawHead = buf.readFloat();
		data.prevRotationYawHead = buf.readFloat();
		data.rotationYaw = buf.readFloat();
		data.prevRotationYaw = buf.readFloat();
		data.renderYawOffset = buf.readFloat();
		data.prevRenderYawOffset = buf.readFloat();
		data.rotationPitch = buf.readFloat();
		data.prevRotationPitch = buf.readFloat();

		data.targetRotationPitch = buf.readFloat();
		data.targetRotationYaw = buf.readFloat();
		data.targetRotationRoll = buf.readFloat();
		
		data.currentRotationPitch = buf.readFloat();
		data.currentRotationYaw = buf.readFloat();
		data.currentRotationRoll = buf.readFloat();
		
		data.targetHeadPitchOffset = buf.readFloat();
		data.currentHeadPitchOffset = buf.readFloat();

		data.currentSwimSpeed = buf.readFloat();
		data.targetSwimSpeed = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		
		buf.writeInt(data.playerUUID.toString().getBytes().length);
		buf.writeBytes(data.playerUUID.toString().getBytes());
		buf.writeFloat(data.rotationYawHead);
		buf.writeFloat(data.prevRotationYawHead);
		buf.writeFloat(data.rotationYaw);
		buf.writeFloat(data.prevRotationYawHead);
		buf.writeFloat(data.renderYawOffset);
		buf.writeFloat(data.prevRenderYawOffset);
		buf.writeFloat(data.rotationPitch);
		buf.writeFloat(data.prevRotationYawHead);

		buf.writeFloat(data.targetRotationPitch);
		buf.writeFloat(data.targetRotationYaw);
		buf.writeFloat(data.targetRotationRoll);
		
		buf.writeFloat(data.currentRotationPitch);
		buf.writeFloat(data.currentRotationYaw);
		buf.writeFloat(data.currentRotationRoll);

		buf.writeFloat(data.targetHeadPitchOffset);
		buf.writeFloat(data.currentHeadPitchOffset);

		buf.writeFloat(data.currentSwimSpeed);
		buf.writeFloat(data.targetSwimSpeed);


	}

	public static class PlayerSwimData {
		
		public PlayerSwimData(UUID associatedPlayer) {
			this.playerUUID = associatedPlayer;
		}
		
		public UUID playerUUID;
		public float rotationYawHead = 0f;
		public float prevRotationYawHead = 0f;
		public float rotationYaw = 0f;
		public float prevRotationYaw = 0f;
		public float renderYawOffset = 0f;
		public float prevRenderYawOffset = 0f;
		public float rotationPitch = 0f;
		public float prevRotationPitch = 0f;

		public float targetRotationPitch = 0f;
		public float targetRotationYaw = 0f;
		public float targetRotationRoll = 0f;
		
		public float currentRotationPitch = 0f;
		public float currentRotationYaw = 0f;
		public float currentRotationRoll = 0f;
		
		public float targetHeadPitchOffset = 0f;
		public float currentHeadPitchOffset = 0f;

		public float currentSwimSpeed = 0f;
		public float targetSwimSpeed = 0f;
	}
	
	
	public static final class Handler implements IMessageHandler<MessagePlayerSwimData, IMessage> {
		@Override
		public IMessage onMessage(MessagePlayerSwimData message, MessageContext ctx) {
			// We received this on the server, send to all other players
			if(ctx.side.equals(Side.SERVER)) {
				EntityPlayerMP player = ctx.getServerHandler().playerEntity;
				BlockPos p = player.getPosition();
				TCPacketHandler.INSTANCE.sendToAllAround(message, new TargetPoint(player.world.provider.getDimension(), p.getX(), p.getY(), p.getZ(), 32D));
			}
			return null;
		}
	}
}