package org.example.service;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.stream.ChunkedFile;
import org.example.constant.Constants;
import org.example.util.MessageUtil;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class FileStreamService {
    private String serverPath;
    private String clientPath;

    public void moveFile(String receivedMessage) throws IOException {
        String[] messageParts = receivedMessage.split("\\s+", 3);
        Path originalPath = Paths.get(messageParts[1]);
        Path destinationPath = Paths.get(messageParts[2]);
        Files.move(originalPath, destinationPath);
    }

    public void sendFile(ChannelHandlerContext ctx, String input) throws IOException {
        extractPathsFromUserCommand(input);
        sendServerPathToClient(ctx);
        sendChunkedFileWithMarker(ctx);
    }

    public boolean upload(String message) {
        if (message.contains(Constants.FileStreamService.UPLOAD_COMMAND)) {
            handleUploadCommand(message);
            return true;
        } else if (message.contains(Constants.Chunk.CHUNK_MARKER)) {
            handleDataChunk(message);
            return true;
        }
        return false;
    }

    public void copyFile(String command) throws IOException {
        String[] messageParts = command.split("\\s+", 3);
        Path originalPath = Paths.get(messageParts[1]);
        Path destinationPath = Paths.get(messageParts[2]);
        Files.copy(originalPath, destinationPath);
    }

    private void extractPathsFromUserCommand(String userCommand) {
        String[] parts = userCommand.split("\\s+", 3);
        this.serverPath = parts[1];
        this.clientPath = parts[2] + " ";
    }

    private void sendServerPathToClient(ChannelHandlerContext ctx) {
        MessageUtil.sendMessage(ctx, Constants.FileStreamService.DOWNLOAD_COMMAND_RECOGNITION, clientPath);
    }

    private void sendChunkedFileWithMarker(ChannelHandlerContext ctx) throws IOException {
        RandomAccessFile file = new RandomAccessFile(serverPath, "r");
        ChunkedFile chunkedFile = new ChunkedFileWithMarker(file);
        ctx.writeAndFlush(chunkedFile);
    }

    private void handleUploadCommand(String receivedMessage) {
        String[] messageParts = receivedMessage.split("\\s+");
        this.serverPath = messageParts[1];
        String otherContent =
                removeDataPrefix(Arrays.stream(messageParts).skip(2).reduce("", (a, b) -> a + "\\s" + b));
        createAndWriteToFile(otherContent);
    }

    private void handleDataChunk(String content) {
        content = removeDataPrefix(content);
        FileWriter fileWriter = new FileWriter(serverPath, content);
        fileWriter.writeToFile();
    }

    private void createAndWriteToFile(String content) {
        FileWriter fileWriter = new FileWriter(serverPath, content);
        fileWriter.clearFile();
        fileWriter.writeToFile();
    }

    private String removeDataPrefix(String content) {
        return content.replace(Constants.Chunk.CHUNK_MARKER, "");
    }
}
