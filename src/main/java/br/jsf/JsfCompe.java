package br.jsf;

import br.data.model.Competidor;
import br.ejb.EjbCompetidores;
import java.io.Serializable;
import java.sql.SQLOutput;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Alexsandro
 */
@Getter
@Setter
@Named(value = "jsfCompe")
@SessionScoped
public class JsfCompe implements Serializable {

    private String nome;

    private Integer valorA;

    private Integer valorB;

    private Integer resposta;

    @Resource(lookup = "java:comp/DefaultJMSConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "java/Fila")
    private Queue fila;

    @EJB
    private EjbCompetidores ejbCompetidores;

    /**
     * Creates a new instance of JsfCompetidores
     */
    public JsfCompe() {
        ejbCompetidores = new EjbCompetidores();
        valorA = ejbCompetidores.aleatorios();
        valorB = ejbCompetidores.aleatorios();
    }

    public List<Competidor> getLista() {
        return ejbCompetidores.getCompetidores();
    }

    public void addCompetidor() {
        if (nome == null) {
            mensagemDeRetorno("Você digitou um nome e clicou no OK?");
        }
        else{
            ejbCompetidores.addCompetidor(nome); 
        }
    }

    public void contabilizaPontos() {

        if (resposta != null && ejbCompetidores.contabilizadorDePontos(nome, valorA, valorB,
                resposta)) {
            resetCampos();
            mensagemDeRetorno("Você acertou! E obteve um ponto!");
            verificaAlteracaoNoPrimeiroColocado(nome);
            
        } 
        else if (resposta == null) {
            mensagemDeRetorno("Digite uma resposta!");
        }
        else {
            mensagemDeRetorno("Você errou! Tente de novo!");
        }

    }
   
    private void resetCampos() {
        valorA = ejbCompetidores.aleatorios();
        valorB = ejbCompetidores.aleatorios();
        resposta = null;
    }

    public void mensagemDeRetorno(String mensagem) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem,
                        mensagem));
    }

    private void verificaAlteracaoNoPrimeiroColocado(String nome) {        
        
        if (primeiraPosicao().getNome().equals(nome)){
            send();
        }
    }
    
    private Competidor primeiraPosicao(){
        return ejbCompetidores.primeiraPosicao();
    }

    public void send() {
        
        try {
            List<Competidor> competidores = ejbCompetidores.getCompetidores();
            Connection conn = connectionFactory.createConnection();
            Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            ObjectMessage om = session.createObjectMessage();

            om.setObject((Serializable) competidores);
            JMSContext context = connectionFactory.createContext();

            context.createProducer().send(fila, om);
        } 
        catch (JMSException e) {
            System.out.println("ERRO!");
            System.out.println(e.getMessage());
        }
    }
}