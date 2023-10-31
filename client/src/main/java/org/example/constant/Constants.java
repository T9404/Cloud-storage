package org.example.constant;

public class Constants {

    public static final class Commands {
        public static final String CONNECT_COMMAND = "/connect";
        public static final String EXIT_COMMAND = "/exit";
        public static final String ENTER_COMMAND_MESSAGE = "Enter command: " + "["
                + CONNECT_COMMAND + ", " + EXIT_COMMAND + "]";
        public static final String UPLOAD_COMMAND = "/upload";
        public static final String DEFAULT_COMMAND = "/default";
        public static final String DOWNLOAD_COMMAND_RECOGNITION = "/downloadKey ";
    }

    public static final class Server {
        public static final int PORT = 8080;
        public static final String HOST = "localhost";
    }

    public static final class Chunk {
        public static final int CHUNK_SIZE = 1024;
        public static final String CHUNK_MARKER = "/data ";
    }

    public static final class FileUploadService {
        public static final String UPLOAD_COMMAND_RECOGNITION = "/upload ";
    }
}
