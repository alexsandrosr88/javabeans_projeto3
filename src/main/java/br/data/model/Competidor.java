package br.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Alexsandro
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Competidor {

    private String nome;
    private int pontos;
}
