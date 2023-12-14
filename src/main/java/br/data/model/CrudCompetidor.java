package br.data.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Alexsandro
 */
public class CrudCompetidor {

    private static List<Competidor> competidores;

    private static CrudCompetidor instancia;

    private CrudCompetidor() {
        super();
    }

    public static CrudCompetidor getInstance() {
        if (instancia == null) {
            competidores = new ArrayList<>();
            instancia = new CrudCompetidor();
        }
        return instancia;
    }

    public void addCompetidor(Competidor competidor) {
        competidores.add(competidor);
    }

    public List<Competidor> getCompetidores() {
        return listaOrdenadaPorPontos();
    }

    private List<Competidor> listaOrdenadaPorPontos() {
        return competidores.stream()
                .sorted(Comparator.comparing(Competidor::getPontos).reversed())
                .collect(Collectors.toList());
    }
}