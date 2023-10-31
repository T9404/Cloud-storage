package org.example.service;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;
import org.example.constant.Constants;

import java.io.IOException;
import java.io.RandomAccessFile;

public class ChunkedFileWithMarker extends ChunkedFile {

    public ChunkedFileWithMarker(RandomAccessFile file) throws IOException {
        super(file, Constants.Chunk.CHUNK_SIZE);
    }

    @Override
    public ByteBuf readChunk(ByteBufAllocator allocator) throws Exception {
        ByteBuf dataChunk = super.readChunk(allocator);
        ByteBuf markerBuf = Unpooled.copiedBuffer(Constants.Chunk.CHUNK_MARKER, CharsetUtil.UTF_8);
        ByteBuf result = allocator.buffer(markerBuf.readableBytes() + dataChunk.readableBytes());
        result.writeBytes(markerBuf);
        result.writeBytes(dataChunk);
        return result;
    }
}
