package br.ejb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 *
 * @author Alexsandro
 */
@MessageDriven(activationConfig ={
    @ActivationConfigProperty(propertyName = "destinationLookup",
            propertyValue = "java/Fila"),
    @ActivationConfigProperty(propertyName = "destinationType",
            propertyValue = "javax.jms.Queue")
})
public class EjbConsumidor implements MessageListener{

    @Override
    public void onMessage(Message msg) {
        System.out.println("Mensagem recebida!");
    }
    
}
