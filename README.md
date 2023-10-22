# anonymized-console-chat
The Anonymized Console Chat application is a lightweight chat platform that allows users to create custom chat groups and engage in real-time text conversations with others within the same group. With this application, users can define their own group names, making it easy to chat with friends, colleagues, or like-minded individuals, all within a simple and intuitive console interface.


## Key Features:

- **Create Custom Groups**: Users have the flexibility to create their own chat groups, defining group names that suit their specific interests or communities.

- **Real-Time Messaging**: Enjoy real-time text messaging with other users who join the same group, making it ideal for instant communication and collaboration.

- **Cross-Platform Compatibility**: This application can be run on various platforms, making it accessible to a wide range of users as it only requires java jdk to execute.

- **Open Source**: The application is open source, enabling customization and integration into other projects.


## Setting up:
1. **Install java**:
   - For Ubuntu/Debian:
       ```bash
       sudo apt update && sudo apt install openjdk-17-jdk
       ```
   - For CentOS:
       ```bash
       sudo yum update && sudo yum install java-11-openjdk
       ```

2. **Clone the repository**:
   - Open your terminal or command prompt and navigate to the directory you want to store the chat application.
   - Clone the repository using git.
       ```bash
       git clone https://github.com/shah-codex/anonymized-console-chat.git
       ```
   - Navigate inside the cloned repository.
       ```bash
       cd anonymized-console-chat
       ```

3. **Compile the sources**:
   - Using `javac` compiling the java files.
       ```bash
       javac client/*.java server/*.java
       ```

4. **Starting up the server**:
   - Starting the server by executing the `server/Main.class` file.
       ```bash
       cd server && java Main
       ```

5. **Starting up the client**:
   - Connecting a client to the server by executing `client/Client.class`.
       ```bash
       cd client && java Client
       ```

6. **Optional: Setting up the chat cleanup**:
   - Creating a cron job that executes every 15 minutes and cleans up the chat that were older than 15 minutes.
   - Making cleanup files executable.
       ```bash
       chmod +x server/*.sh
       ```
   - Execute the following command to edit the cron file.
       ```bash
       crontab -e
       ```
   - Append following line to end of the file. This will execute the `cleanup.sh` file every 15 minutes.
       ```text
       */15  *  *  *  *  /<absolute-path-to>/anonymized-console-chat/server/cleanup.sh
       ```

## Screenshots:
![image](https://github.com/shah-codex/anonymized-console-chat/assets/66596874/a0f3f802-4c7b-469f-8beb-8b47847e6f04)
![image](https://github.com/shah-codex/anonymized-console-chat/assets/66596874/42d1fe6a-d389-4d54-bd5a-2d3afeb0577c)


## Customization:

Feel free to modify the current code to further customize the functionality and behavior of the simulation to suit your needs.


## License:

This project is licensed under the GNU General Public License, Version 2.0. You can find the full text of the license in the [LICENSE](LICENSE) file within this repository.
