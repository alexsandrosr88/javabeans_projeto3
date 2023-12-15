package br.jsf;

import br.data.model.Competidor;
import br.ejb.EjbCompetidores;
import java.io.Serializable;
import java.util.List;
import java.util.Queue;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
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

    private Competidor primeiraPosicao;

    @EJB
    private EjbCompetidores ejbCompetidores;

    /**
     * Creates a new instance of JsfCompetidores
     */
    public JsfCompe() {
        ejbCompetidores = new EjbCompetidores();
        valorA = ejbCompetidores.aleatorios();
        valorB = ejbCompetidores.aleatorios();
        primeiraPosicao = ejbCompetidores.primeiroPosicao();
    }

    public List<Competidor> getLista() {
        return ejbCompetidores.getCompetidores();
    }

    public void addCompetidor() {
        if (nome != null) {
            System.out.println(nome);

        }
        ejbCompetidores.addCompetidor(nome);
    }

    public void contabilizaPontos() {

        if (resposta != null && nome != null && ejbCompetidores.contabilizadorDePontos(nome, valorA, valorB,
                resposta)) {
            resetCampos();
            verificaAlteracaoNoPrimeiroColocado(ejbCompetidores
                    .pesquisaCompetidorPorNome(nome));
            mensagemDeRetorno("Você acertou! E obteve um ponto!");
        } else if (resposta == null) {
            mensagemDeRetorno("Digite uma resposta!");
        } else if (nome == null) {
            mensagemDeRetorno("Você clicou no OK?");
        } else {
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

    private void verificaAlteracaoNoPrimeiroColocado(Competidor competidor) {
        if (primeiraPosicao.equals(competidor)) {
            send();
        }
    }

    public void send() {
        try {
            JMSContext context = connectionFactory.createContext();
            context.createProducer().send((Destination) fila,
                    "Alteração do primeiro colocado!");
        } catch (Exception e) {
            System.out.println("ERRO!");
            System.out.println(e.getMessage());
        }
    }
}
