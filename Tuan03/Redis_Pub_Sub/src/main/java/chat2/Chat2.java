package chat2;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.apache.log4j.BasicConfigurator;

import redis.clients.jedis.Jedis;

public class Chat2 extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea chatArea;
	private JTextField txtMessage;
	private JButton sendButton;
	private Jedis jedis;

	public Chat2() {

		setTitle("Chat 2");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		chatArea = new JTextArea();
		chatArea.setEditable(false);
		add(new JScrollPane(chatArea), BorderLayout.CENTER);

		txtMessage = new JTextField();
		sendButton = new JButton("Gửi");
		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.add(txtMessage, BorderLayout.CENTER);
		bottomPanel.add(sendButton, BorderLayout.EAST);
		add(bottomPanel, BorderLayout.SOUTH);

		setSize(400, 300);
		setVisible(true);

		jedis = new Jedis("localhost", 6379);
		// System.out.println("Redis connected: " + jedis.isConnected());
		// Kiểm tra xem có lịch sử tin nhắn trong Redis không
		if (jedis.exists("chat_history")) {
			// Lấy lịch sử tin nhắn từ Redis
			List<String> chatHistory = jedis.lrange("chat_history", 0, -1);
			// Hiển thị lịch sử tin nhắn lên giao diện
			for (String message : chatHistory) {
				chatArea.append(message + "\n");
			}
		}

		createFormEvent();
		listenMessage();
	}

	public void createFormEvent() {
		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String message = txtMessage.getText().trim();
				if (message.isBlank()) {
				} else {
					txtMessage.setText("");
//                    jedis.rpush("chat_history", message);
					sendMessage("Chat 2: " + message + '\n');
				}
			}
		});
	}

	private void listenMessage() {
		// thiết lập môi trường cho JMS
		BasicConfigurator.configure();
		// thiết lập môi trường cho JJNDI
		Properties settings = new Properties();
		settings.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		settings.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
		// tạo context
		Context ctx = null;
		try {
			ctx = new InitialContext(settings);
			// lookup JMS connection factory
			Object obj = ctx.lookup("TopicConnectionFactory");
			ConnectionFactory factory = (ConnectionFactory) obj;
			// tạo connection
			Connection con = factory.createConnection("admin", "admin");
			// nối đến MOM
			con.start();
			// tạo session
			Session session = con.createSession(/* transaction */false, /* ACK */Session.CLIENT_ACKNOWLEDGE);
			// tạo consumer
			Destination destination = (Destination) ctx.lookup("dynamicTopics/thanthidet");
			MessageConsumer receiver = session.createConsumer(destination);
			// receiver.receive();//blocked method
			// Cho receiver lắng nghe trên queue, chừng có message thì notify
			receiver.setMessageListener(new MessageListener() {
				@Override
				// có message đến queue, phương thức này được thực thi
				public void onMessage(Message msg) {// msg là message nhận được
					try {
						if (msg instanceof TextMessage) {
							TextMessage tm = (TextMessage) msg;
							String txt = tm.getText();
							chatArea.append(txt);
//                            jedis.rpush("chat_history", txt);
							System.out.println("XML= " + txt);
							msg.acknowledge();// gửi tín hiệu ack
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void sendMessage(String message) {
		// thiết lập môi trường cho JMS logging
		BasicConfigurator.configure();
		// thiết lập môi trường cho JJNDI
		Properties settings = new Properties();
		settings.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		settings.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
		// tạo context
		Context ctx = null;
		try {
			ctx = new InitialContext(settings);
			// lookup JMS connection factory
			Object obj = ctx.lookup("TopicConnectionFactory");
			ConnectionFactory factory = (ConnectionFactory) obj;
			// tạo connection
			Connection con = factory.createConnection("admin", "admin");
			// nối đến MOM
			con.start();

			Session session = con.createSession(/* transaction */false, /* ACK */Session.AUTO_ACKNOWLEDGE);
			Destination destination = (Destination) ctx.lookup("dynamicTopics/thanthidet");
			// tạo producer
			MessageProducer producer = session.createProducer(destination);
			// Tạo 1 message
			Message msg = session.createTextMessage(message);
			//// gửi
			producer.send(msg);
			// shutdown connection
			session.close();
			con.close();
			System.out.println("Finished...");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Chat2();
			}
		});
	}
}
