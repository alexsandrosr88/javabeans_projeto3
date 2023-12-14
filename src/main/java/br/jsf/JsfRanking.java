package br.jsf;

import br.data.model.Competidor;
import br.ejb.EjbRanking;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Alexsandro
 */
@Named(value = "jsfRanking")
@RequestScoped
public class JsfRanking {

    @EJB
    private EjbRanking ejbRanking;

    /**
     * Creates a new instance of JsfRanking
     */
    public JsfRanking() {
    }
    
    public List<Competidor> getCompetidores(){
        return ejbRanking.getCompetidores();
    }
    
}
