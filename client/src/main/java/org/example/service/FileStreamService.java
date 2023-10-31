package org.example.service;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.stream.ChunkedFile;
import org.example.constant.Constants;
import org.example.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

/**
 * This class is used to send and receive files
 * <p>
 *     Files are sent in chunks, each chunk is preceded by a marker.
 *     <br>
 *     The marker is used by the client/server to determine that the message is a part of a file.
 * </p>
 */
public class FileStreamService {
    private static final Logger logger = LoggerFactory.getLogger(FileStreamService.class);
    private String downloadClientPath;

    /**
     * First paths[1] is the server path and paths[2] is the client path that getting from user command.
     */
    public void uploadFile(String userCommand, ChannelHandlerContext ctx) {
        String[] paths = extractPathsFromUserCommand(userCommand);
        sendServerPathToServer(ctx, paths[1]);
        sendChunkedFileWithMarker(ctx, paths[2]);
    }

    public boolean downloadFile(String message) {
        if (message.contains(Constants.Commands.DOWNLOAD_COMMAND_RECOGNITION)) {
            handleDownloadCommand(message);
            return true;
        } else if (message.contains(Constants.Chunk.CHUNK_MARKER)) {
            handleDataChunk(message);
            return true;
        }
        return false;
    }

    /**
     * This method extracts the paths from the user command and adds a space to the end of the server path.
     */
    private String[] extractPathsFromUserCommand(String userCommand) {
        String[] messageParts = userCommand.split(" ", 3);
        messageParts[1] += " ";
        return messageParts;
    }

    private void sendServerPathToServer(ChannelHandlerContext ctx, String uploadServerPath) {
        MessageUtil.sendMessage(ctx, Constants.FileUploadService.UPLOAD_COMMAND_RECOGNITION, uploadServerPath);
    }

    private void sendChunkedFileWithMarker(ChannelHandlerContext ctx, String uploadClientPath) {
        try {
            RandomAccessFile file = new RandomAccessFile(uploadClientPath, "r");
            ChunkedFile chunkedFile = new ChunkedFileWithMarker(file);
            ctx.writeAndFlush(chunkedFile);
        } catch (IOException e) {
            logger.error("Failed to send file to server: {}", e.getMessage());
        }
    }

    /**
     * This method handles the download command from the server.
     * <br>
     * It skips the first two parts of the message (download command key and path)
     * and then concatenates the rest of the message.
     */
    private void handleDownloadCommand(String receivedMessage) {
        try {
            String[] messageParts = receivedMessage.split("\\s+");
            this.downloadClientPath = messageParts[1];
            String otherContent =
                    removeDataPrefix(Arrays.stream(messageParts).skip(2).reduce("", (a, b) -> a + "\\s" + b));
            createAndWriteToFile(otherContent);
            logger.info("Download started");
        } catch (Exception e) {
            logger.error("Download failed: {}", e.getMessage());
        }
    }

    private void handleDataChunk(String content) {
        content = removeDataPrefix(content);
        FileWriter fileWriter = new FileWriter(downloadClientPath, content);
        fileWriter.writeToFile();
        logger.info("Chunk received successfully");
    }

    private String removeDataPrefix(String content) {
        return content.replace(Constants.Chunk.CHUNK_MARKER, "");
    }

    private void createAndWriteToFile(String content) {
        FileWriter fileWriter = new FileWriter(downloadClientPath, content);
        fileWriter.clearFile();
        fileWriter.writeToFile();
    }
}
