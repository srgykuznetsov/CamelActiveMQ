import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import java.util.Base64;

public class ActiveMqRouteBuilder extends RouteBuilder
{
    public void configure() throws Exception
    {
        //This route is used to add test message to input queue
        from("file:D:/SEREJA/JAVA/CamelActiveMQ/CamelInputFolder")
                .process(new Base64DecodeProcessor())
                .to("jms:queue:task2TargetQ");

        from("jms:queue:task2TargetQ")
                .process(new Base64EncodeProcessor())
                .to("jms:queue:task2ControlQ");

    }
}

class Base64DecodeProcessor implements Processor
{
    @Override
    public void process(Exchange exchange) throws Exception {
        String payload = exchange.getIn().getBody(String.class);
        if (payload == null || payload.length() == 0)
        {
            System.err.println("Empty message detected!");
        }
        byte[] decoded = Base64.getDecoder().decode(payload);
        exchange.getIn().setBody(decoded);
    }
}

class Base64EncodeProcessor implements Processor
{
    @Override
    public void process(Exchange exchange) throws Exception {
        byte[] abtPayload = exchange.getIn().getBody(byte[].class);
        if (abtPayload == null || abtPayload.length == 0)
        {
            System.err.println("Empty message detected!");
        }
        String strEncoded = Base64.getEncoder().encodeToString(abtPayload);
        exchange.getIn().setBody(strEncoded);
    }
}
