package br.com.meuprimeiroprojeto.dao;

import br.com.meuprimeiroprojeto.modelo.Estado;
import br.com.meuprimeiroprojeto.util.HibernateUtility;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

public class EstadoDaoImpl extends DaoGenericoImpl<Estado, Serializable> implements EstadoDao {

    public List<Estado> listar(String nome) {
        Criteria crit = HibernateUtility.getSession().createCriteria(Estado.class);
        crit.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
        return crit.list();
    }
}
