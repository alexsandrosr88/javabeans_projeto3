package br.ejb;

import br.data.model.Competidor;
import br.data.model.CrudCompetidor;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Alexsandro
 */
@Stateless
public class EjbRanking {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    private final CrudCompetidor competidores;
    
    public EjbRanking(){
        competidores = CrudCompetidor.getInstance();
    }
    
    public List<Competidor> getCompetidores(){
          return competidores.getCompetidores();
    }
}
