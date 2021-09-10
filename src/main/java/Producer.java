import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Scanner;


public class Producer {
    private static final String EXCHANGE_NAME = "directExchanger";



    public static void main(String[] argv) throws Exception {
        Scanner scanner = new Scanner(System.in);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("admin");
        factory.setPassword("admin");
        try (Connection connection = factory.newConnection()) {
            try (Channel channel = connection.createChannel()) {
                channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

                while (true) {
                    String msg = scanner.nextLine();
                    String[] arraymsg = msg.split(" ");
                    String message = msg.replace(arraymsg[0], " ");
                    if (arraymsg[0].equals("exit")) return;
                    channel.basicPublish(EXCHANGE_NAME, arraymsg[0], null, message.getBytes("UTF-8"));
                    System.out.println(" [x] Sent '" + message + "'");

                }
            }
        }
    }
}