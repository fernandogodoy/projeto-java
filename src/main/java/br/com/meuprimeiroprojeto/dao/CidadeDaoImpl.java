package br.com.meuprimeiroprojeto.dao;

import br.com.meuprimeiroprojeto.modelo.Cidade;
import br.com.meuprimeiroprojeto.util.HibernateUtility;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

public class CidadeDaoImpl extends DaoGenericoImpl<Cidade, Serializable> implements CidadeDao {

    public List<Cidade> listar(String nome) {
        Criteria crit = HibernateUtility.getSession().createCriteria(Cidade.class);
        crit.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
        return crit.list();
    }
}
