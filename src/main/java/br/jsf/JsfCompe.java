package br.jsf;

import br.data.model.Competidor;
import br.ejb.EjbCompetidores;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
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
        if(nome != null){
            ejbCompetidores.addCompetidor(nome);
        }
    }

    public void contabilizaPontos() {

        if (resposta != null && ejbCompetidores.contabilizadorDePontos(nome, valorA, valorB,
                resposta)) {
            resetCampos();
            mensagemDeRetorno("Você acertou! E obteve um ponto!");
        } else if (resposta == null) {
            mensagemDeRetorno("Digite uma resposta!");
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
}
