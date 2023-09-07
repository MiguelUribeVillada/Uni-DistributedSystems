# Introduction:

This project represents my first foray into distributed systems, where I had the opportunity to design and implement a small-scale RMI-based server for remote file management. Using Java RMI, I created a robust system allowing users to download, upload, delete, and list files on a remote server. Additionally, our group incorporated a custom file operation to enhance the system's functionality. This project provided valuable insights into distributed computing and remote communication, showcasing our skills in both server-side and client-side implementations.

<br>

# Usage Instructions:

To utilize this remote file management system, follow these steps in your console:

1. Compile the necessary Java files to generate the required .class files for proper execution:
```
javac FileOps.java
javac FileOpsImpl.java
javac Client.java
javac Server.java
```
<br>

2. Start the server by typing the following command:
```
java Server
```

<br>

3. To execute specific client actions simultaneously in a separate console, use the following command structure:
```
java Client download/upload/delete/list/rename
```

Replace "download," "upload," "delete," "list," or "rename" with the desired operation, and follow the prompts to interact with the remote file system.

<br>

These instructions will help you set up and run the project effectively, enabling you to manage files on the remote server seamlessly.
