import com.rabbitmq.client.*;

import java.util.Scanner;

public class Consumer {
    private static final String EXCHANGE_NAME = "directExchanger";

    public static void main(String[] argv) throws Exception {
        Scanner scanner = new Scanner(System.in);

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("admin");
        factory.setPassword("admin");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        String queueName = channel.queueDeclare().getQueue();
        System.out.println("My queue name: " + queueName);

        System.out.println("Введите наименование ключа подписки через команду 'set_topic'");
        String[] arraymsg = scanner.nextLine().split(" ");
        if (arraymsg[0].equals("set_topic")) {
            channel.queueBind(queueName, EXCHANGE_NAME, arraymsg[1]);
            System.out.println(" [*] Waiting for messages");
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            };
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
            });
        }else{
            System.out.println("Неизвестная команда");}
    }
}