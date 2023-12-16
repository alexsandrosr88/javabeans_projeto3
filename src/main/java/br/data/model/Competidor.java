package br.data.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author Alexsandro
 */
@Data
@AllArgsConstructor
public class Competidor implements Serializable{

    private String nome;
    private Integer pontos;
}
