package br.com.meuprimeiroprojeto.dao;

import br.com.meuprimeiroprojeto.modelo.Cidade;
import br.com.meuprimeiroprojeto.modelo.Estado;
import java.io.Serializable;
import java.util.List;

public interface CidadeDao extends DaoGenerico<Cidade, Serializable> {

    public List<Cidade> listar(String nome);
}
