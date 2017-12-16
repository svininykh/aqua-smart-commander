package io.hackaday.raspiaqua.smartapp;

import io.hackaday.raspiaqua.proto.Aquarium;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author svininykh-av
 */
public class AquaClientHandler extends SimpleChannelInboundHandler<Aquarium.MessagePacket> {

    private Channel channel;
    private Aquarium.MessagePacket resp;
    BlockingQueue<Aquarium.MessagePacket> resps = new LinkedBlockingQueue<>();

    public Aquarium.MessagePacket sendRequest(List<Aquarium.AquaDevice> devices) {
        Aquarium.MessagePacket.Builder requestBuilder = Aquarium.MessagePacket.newBuilder()
                .setClientName("ubuntu")
                .setServerName("raspberrypi");
        if (!devices.isEmpty()) {
            requestBuilder.addAllDevices(devices).buildPartial();
        }

        // Send request
        channel.writeAndFlush(requestBuilder.build());

        // Now wait for response from server
        boolean interrupted = false;
        for (;;) {
            try {
                resp = resps.take();
                break;
            } catch (InterruptedException ignore) {
                interrupted = true;
            }
        }

        if (interrupted) {
            Thread.currentThread().interrupt();
        }

        return resp;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        channel = ctx.channel();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Aquarium.MessagePacket msg)
            throws Exception {
        resps.add(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
