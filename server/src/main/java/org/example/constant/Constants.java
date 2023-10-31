package org.example.constant;

public class Constants {

    public static final class ServerHandler {
        public static final String UPLOAD_CHUNK_SUCCESSFULLY = "\nTried to upload the file to the server";
        public static final int MAX_LOGIN_ATTEMPTS = 3;
        public static final String DEFAULT_CLIENT_NAME = "Client #";
        public static final String UNKNOWN_COMMAND = "Unknown command. If you need help, write /help";
    }

    public static final class ResponseCommandHandler {
        public static final String REGISTRATION_SUCCESSFUL = "User registered successfully";
        public static final String LOGIN_SUCCESSFUL = "Login successful";
        public static final String LOGOUT_SUCCESSFUL = "Logout successful";
        public static final String AVAILABLE_COMMANDS = String.format(
                "Available commands:%n" +
                        "/register <username> <password>%n" +
                        "  - Register a new user with the specified username and password.%n" +
                        "/login <username> <password>%n" +
                        "  - Log in an existing user with the specified username and password.%n" +
                        "/logout%n" +
                        "  - Log out from the current user account and end the session.%n" +
                        "/help%n" +
                        "  - Display available commands and their descriptions.%n" +
                        "/upload <server_path> <client_path>%n" +
                        "  - Upload a file from the client device to the server device.%n" +
                        "/download <server_path> <client_path>%n" +
                        "  - Download a file from the server device to the client device.%n" +
                        "/copy <source_path> <destination_path>%n" +
                        "  - Copy a file or directory from one location to another.%n" +
                        "/move <source_path> <destination_path>%n" +
                        "  - Move a file or directory from one location to another."
        );
    }

    public static final class Command {
        public static final String REGISTER_COMMAND = "/register";
        public static final String LOGIN_COMMAND = "/login";
        public static final String LOGOUT_COMMAND = "/logout";
        public static final String HELP_COMMAND = "/help";
        public static final String DOWNLOAD_COMMAND = "/download";
        public static final String MOVE_COMMAND = "/move";
        public static final String COPY_COMMAND = "/copy";
    }

    public static final class MoveFileCommandHandler {
        public static final String FILE_MOVED_SUCCESSFULLY = "File moved successfully";
        public static final String ERROR_WHILE_MOVING_FILE = "Error while moving file: ";
    }

    public static final class FileStreamService {
        public static final String UPLOAD_COMMAND = "/upload";
        public static final String DOWNLOAD_COMMAND_RECOGNITION = "/downloadKey ";

    }

    public static final class Server {
        public static final int PORT = 8080;
        public static final int NUMBER_OF_THREADS = 1;
    }

    public static final class DIContainer {
        public static final String CREATE_INSTANCE_ERROR = "Can't create instance of class: {}";
        public static final String DEPENDENCY_NOT_REGISTERED = "Dependency not registered: {}";
    }

    public static final class Chunk {
        public static final int CHUNK_SIZE = 1024;
        public static final String CHUNK_MARKER = "/data ";
    }

    public static final class DownloadCommandHandler {
        public static final String FILE_SENT_SUCCESSFULLY = "File sent successfully";
    }

    public static final class CopyFileCommandHandler {
        public static final String FILE_COPIED_SUCCESSFULLY = "File copied successfully";
    }

    public static final class FileManager {
        public static final String FILE_CLEARED_SUCCESSFULLY = "File cleared successfully";
    }
}
