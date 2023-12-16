package br.ejb;

import br.data.model.Competidor;
import java.util.List;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 *
 * @author Alexsandro
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup",
            propertyValue = "java/Fila"),
    @ActivationConfigProperty(propertyName = "destinationType",
            propertyValue = "javax.jms.Queue")
})
public class EjbConsumidor implements MessageListener {

    @Override
    public void onMessage(Message msg) {
        try {
            ObjectMessage tm = (ObjectMessage) msg;
            List<Competidor> competidores = (List<Competidor>) tm.getObject();

            for (int i = 0; i < competidores.size(); i++) {
                System.out.println("\nPosição: " + (i + 1) + " - "
                        + competidores.get(i));
            }
        } catch (JMSException e) {
            System.out.println("ERRO");
            System.out.println(e.getMessage());
        }
    }
}
