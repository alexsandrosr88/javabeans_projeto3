package br.ejb;

import br.data.model.Competidor;
import br.data.model.CrudCompetidor;
import java.util.List;
import java.util.Random;
import javax.ejb.Stateful;

/**
 *
 * @author Alexsandro
 */
@Stateful
public class EjbCompetidores {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private final CrudCompetidor competidores;

    public EjbCompetidores() {
        competidores = CrudCompetidor.getInstance();
    }

    public void addCompetidor(String nome) {
        if (!validaNomeDuplicado(nome)) {
            competidores.addCompetidor(new Competidor(nome, 0));
        }
    }

    private boolean validaNomeDuplicado(String nome) {
        return competidores.getCompetidores().stream()
                .anyMatch(competidor -> competidor.getNome()
                .equalsIgnoreCase(nome));
    }

    public List<Competidor> getCompetidores() {
        return competidores.getCompetidores();
    }

    public Competidor primeiroPosicao() {
        if (competidores.getCompetidores().size() > 1) {
            return competidores.getCompetidores().get(0);
        }
        return null;
    }

    public int aleatorios() {
        Random random = new Random();
        return (random.nextInt(9) + 1) * 10;
    }

    public boolean contabilizadorDePontos(String nome, Integer valorA,
            Integer valorB, Integer resposta) {
        Competidor competidor = pesquisaCompetidorPorNome(nome);

        if (validaAcerto(valorA, valorB, resposta)) {
            competidor.setPontos(competidor.getPontos() + 1);
            return true;
        } else {
            competidor.setPontos(competidor.getPontos() + 0);
            return false;
        }
    }

    public Competidor pesquisaCompetidorPorNome(String nome) {
        return competidores.getCompetidores().stream()
                .filter(competidor -> competidor.getNome()
                .equalsIgnoreCase(nome))
                .findFirst().orElse(null);
    }

    private boolean validaAcerto(int valorA, int valorB, int resposta) {
        return valorA + valorB == resposta;
    }
}
