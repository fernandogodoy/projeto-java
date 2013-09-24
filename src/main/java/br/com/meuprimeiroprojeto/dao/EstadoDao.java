package br.com.meuprimeiroprojeto.dao;

import br.com.meuprimeiroprojeto.modelo.Estado;
import java.io.Serializable;
import java.util.List;

public interface EstadoDao extends DaoGenerico<Estado, Serializable> {

    public List<Estado> listar(String nome);
}
