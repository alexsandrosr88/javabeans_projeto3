package br.ejb;

import br.data.model.CrudCompetidor;
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
    
    private final CrudCompetidor competidores;

    public EjbConsumidor() {
        competidores = CrudCompetidor.getInstance();
    }

    @Override
    public void onMessage(Message msg) {
        
        for(int i = 0; i< competidores.getCompetidores().size(); i++){
            System.out.println("\nPosição: "+ (i+1) + "Nome: "+ competidores
                    .getCompetidores().get(i).getNome() + 
                    " Pontos: "+ competidores.getCompetidores()
                            .get(i).getPontos());
        } 
    }
}
