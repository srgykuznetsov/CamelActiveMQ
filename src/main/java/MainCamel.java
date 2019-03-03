import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

import javax.jms.ConnectionFactory;

public class MainCamel
{
	public static void main(String[] args) {
		ActiveMqRouteBuilder routeBuilder = new ActiveMqRouteBuilder();
		CamelContext ctx = new DefaultCamelContext();

		//configure jms component
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://0.0.0.0:61616");
		ctx.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

		try {
			ctx.addRoutes(routeBuilder);
			ctx.start();
			Thread.sleep(1 * 60 * 1000);
			ctx.stop();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
