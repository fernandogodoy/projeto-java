/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.meuprimeiroprojeto.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Fernado
 */
@Entity
public class Cidade {
 
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idCidade;
    private String nome;    
    @ManyToOne
    private Estado estado;
    
     //GETTERS E  SETTERS OMITIDOS
}
