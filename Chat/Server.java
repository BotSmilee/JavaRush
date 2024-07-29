package Chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();

    public static void sendBroadcastMessage(Message message) {
        for (Connection conn : connectionMap.values()
        ) {
            try {
                conn.send(message);
            } catch (IOException e) {
                ConsoleHelper.writeMessage("Не удалось отправить сообщение.");
            }
        }
    }

    public static void main(String[] args) {
        int port = ConsoleHelper.readInt();
        try (ServerSocket ss = new ServerSocket(port)) {
            ConsoleHelper.writeMessage("Сервер запущен.");
            while (true) {
                new Handler(ss.accept()).start();
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    private static class Handler extends Thread {
        private Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            ConsoleHelper.writeMessage("Установлено новое соединение с удалённым адресом - " + socket.getRemoteSocketAddress());
            try {
                Connection conn = new Connection(socket);
                String clientName = serverHandshake(conn);
                notifyUsers(conn, clientName);
                sendBroadcastMessage(new Message(MessageType.USER_ADDED, clientName));
                serverMainLoop(conn, clientName);
                connectionMap.remove(clientName);
                sendBroadcastMessage(new Message(MessageType.USER_REMOVED, clientName));
            } catch (IOException | ClassNotFoundException e) {
                ConsoleHelper.writeMessage("Произошла ошибка при обмене данными с удалённым сервером.");
            }
            ConsoleHelper.writeMessage("Соединение с удалённым сервером закрыто.");
        }

        private void notifyUsers(Connection connection, String userName) throws IOException {
            for (String pair : connectionMap.keySet()
            ) {
                if (!userName.equals(pair)) connection.send(new Message(MessageType.USER_ADDED, pair));
            }
        }

        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException {
            while (true) {
                Message message = connection.receive();
                if (message.getType() == MessageType.TEXT) {
                    sendBroadcastMessage(new Message(MessageType.TEXT, userName + ": " + message.getData()));
                } else {
                    ConsoleHelper.writeMessage("Ошибка");
                }
            }
        }

        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException {
            while (true) {
                connection.send(new Message(MessageType.NAME_REQUEST));

                Message message = connection.receive();
                if (message.getType() != MessageType.USER_NAME) {
                    ConsoleHelper.writeMessage("Получено сообщение от " + socket.getRemoteSocketAddress() + ". Тип сообщения не соответствует протоколу.");
                    continue;
                }

                String userName = message.getData();

                if (userName.isEmpty()) {
                    ConsoleHelper.writeMessage("Попытка подключения к серверу с пустым именем от " + socket.getRemoteSocketAddress());
                    continue;
                }

                if (connectionMap.containsKey(userName)) {
                    ConsoleHelper.writeMessage("Попытка подключения к серверу с уже используемым именем от " + socket.getRemoteSocketAddress());
                    continue;
                }
                connectionMap.put(userName, connection);

                connection.send(new Message(MessageType.NAME_ACCEPTED));
                return userName;
            }
        }
    }
}
