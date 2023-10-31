# Cloud storage

## Functionalities 

### User Registration and Login:

* Users can register with the application by providing their credentials (e.g., username and password).
* Registered users can log in with their credentials to access their cloud storage.

### Session Management:

* Users have sessions associated with their logins to maintain their authentication state.

### File Upload:

* Users can upload files from their local storage to the server.
* The application should handle large files, possibly by splitting them into smaller chunks for efficient transfer.

### File Download:

* Users can browse and select files stored on the server for download.
* The application should provide options to download files to the local machine.

### File Movement and Copying:

* Users can move and copy files within their cloud storage.
* They can create folders or directories to organize their files.

### Rate Limiting:

* Implement rate limiting to restrict the number of requests a user can make within a given time frame to prevent abuse or overloading the server.

### Logout:

* Users can log out from their sessions to securely end their access to the system.

### Error Handling and Logging:

* Implement error handling to provide meaningful error messages to users when issues arise.
* Maintain logs for system and security auditing purposes.

### User-Friendly Interface:

* Create a user-friendly interface for both the server and client applications with appropriate messages and hints for users.

## How to Run

### Running the Server

The server, by default, listens on port 8080. To change the port, modify the `PORT` constant in the `Server.java` file. Ensure to update the port in the client constant in `Client.java` accordingly.

1. Open the project.

2. Project Navigation:
   - Navigate to `src/main/java`.
   - Locate the package `org.example`.
   - Find the class `ServerApp.java`.

3. Run 'ServerApp.main()'. This will start the server application.

### Running the Client

1. Open the project.

2. Project Navigation:
   - Navigate to `src/main/java`.
   - Locate the package `org.example`.
   - Find the class `ClientApp.java`.

5. Run 'ClientApp.main()'. This will start the client application.

## How to Use

### Launching the Server

The server is now running, ready to accept client connections.

### Launching Several Clients

You can launch multiple client instances to connect to the server.

### Use the Hints

The application provides hints and messages to guide you through its usage. 

## Features

Chunk Write Handler is used for transferring big files between the client and the server. 


