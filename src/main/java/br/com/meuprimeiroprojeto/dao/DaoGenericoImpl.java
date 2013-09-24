package br.com.meuprimeiroprojeto.dao;

import br.com.meuprimeiroprojeto.util.HibernateUtility;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockOptions;
import org.hibernate.criterion.Restrictions;

public class DaoGenericoImpl<T, ID extends Serializable> implements DaoGenerico<T, ID> {

    private final Class<T> oClass;

    public DaoGenericoImpl() {
        this.oClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public Class<T> getObjectClass() {
        return this.oClass;
    }

    @Override
    public T save(T objeto) {
        try {
            Object obj = null;
            HibernateUtility.beginTransaction();
            obj = HibernateUtility.getSession().merge(objeto);
            HibernateUtility.commitTransaction();
            HibernateUtility.closeSession();
            return (T) obj;
        } catch (HibernateException hibernateException) {
            cancel();
            throw hibernateException;
        }
    }

    @Override
    public void delete(T objeto) {
        try {
            HibernateUtility.beginTransaction();
            HibernateUtility.getSession().flush();
            HibernateUtility.getSession().clear();
            HibernateUtility.getSession().delete(objeto);
            HibernateUtility.commitTransaction();
            HibernateUtility.closeSession();
        } catch (HibernateException hibernateException) {
            cancel();
            throw hibernateException;
        }
    }

    @Override
    public void deleteItem(T objeto) {
        try {
            HibernateUtility.beginTransaction();
            HibernateUtility.getSession().delete(objeto);
        } catch (HibernateException hibernateException) {
            cancel();
            throw hibernateException;
        }
    }

    @Override
    public List<T> list() {
        try {
            List list = HibernateUtility.getSession().createCriteria(oClass).list();
            //HibernateUtility.closeSession();
            return (List<T>) list;
        } catch (HibernateException hibernateException) {
            cancel();
            throw hibernateException;
        }
    }

    @Override
    public T getById(Serializable id) {
        try {
            return (T) HibernateUtility.getSession().get(oClass, id);
        } catch (HibernateException hibernateException) {
            cancel();
            throw hibernateException;
        }
    }

    @Override
    public T getById(Serializable id, boolean lock) {
        try {
            if (lock) {
                return (T) HibernateUtility.getSession().get(oClass, id, LockOptions.UPGRADE);
            } else {
                return (T) HibernateUtility.getSession().get(oClass, id);
            }
        } catch (HibernateException hibernateException) {
            cancel();
            throw hibernateException;
        }
    }

    @Override
    public List<T> listCriterio(String subClazz, Map<String, Object> filtrosConsulta, int tipoConsulta) {
        List<T> lista = new ArrayList<T>();
        Set entradas = filtrosConsulta.entrySet();

        try {
            Criteria crit = HibernateUtility.getSession().createCriteria(oClass);
            if (subClazz == null) {
                for (Iterator it = entradas.iterator(); it.hasNext();) {
                    Entry object = (Entry) it.next();
                    if (object.getValue() instanceof Enum) {
                        crit.add(Restrictions.eq(object.getKey().toString(), object.getValue()));
                    } else if (tipoConsulta == 0) {
                        crit.add(Restrictions.ilike(object.getKey().toString(), "%" + object.getValue() + "%"));
                    } else if (tipoConsulta == 1) {
                        crit.add(Restrictions.eq(object.getKey().toString(), object.getValue()));
                    } else if (tipoConsulta == 2) {
                        crit.add(Restrictions.gt(object.getKey().toString(), object.getValue()));
                    } else if (tipoConsulta == 3) {
                        crit.add(Restrictions.ge(object.getKey().toString(), object.getValue()));
                    } else if (tipoConsulta == 4) {
                        crit.add(Restrictions.lt(object.getKey().toString(), object.getValue()));
                    } else if (tipoConsulta == 5) {
                        crit.add(Restrictions.le(object.getKey().toString(), object.getValue()));
                    } else if (tipoConsulta == 2) {
                        crit.add(Restrictions.ne(object.getKey().toString(), object.getValue()));
                    }
                }
            } else {
                for (Iterator it = entradas.iterator(); it.hasNext();) {
                    Entry object = (Entry) it.next();
                    //crit.createCriteria(subClazz).add(Restrictions.ilike(object.getKey().toString(), "%" + object.getValue() + "%"));
                    if (object.getValue() instanceof Enum) {
                    } else if (tipoConsulta == 0) {
                        crit.createCriteria(subClazz).add(Restrictions.ilike(object.getKey().toString(), "%" + object.getValue() + "%"));
                    } else if (tipoConsulta == 1) {
                        crit.createCriteria(subClazz).add(Restrictions.eq(object.getKey().toString(), object.getValue()));
                    } else if (tipoConsulta == 2) {
                        crit.createCriteria(subClazz).add(Restrictions.gt(object.getKey().toString(), object.getValue()));
                    } else if (tipoConsulta == 3) {
                        crit.createCriteria(subClazz).add(Restrictions.ge(object.getKey().toString(), object.getValue()));
                    } else if (tipoConsulta == 4) {
                        crit.createCriteria(subClazz).add(Restrictions.lt(object.getKey().toString(), object.getValue()));
                    } else if (tipoConsulta == 5) {
                        crit.createCriteria(subClazz).add(Restrictions.le(object.getKey().toString(), object.getValue()));
                    } else if (tipoConsulta == 2) {
                        crit.createCriteria(subClazz).add(Restrictions.ne(object.getKey().toString(), object.getValue()));
                    }
                }
            }
            crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            crit.setMaxResults(20);
            lista = (List<T>) crit.list();
            //HibernateUtility.closeSession();
            return lista;
        } catch (HibernateException hibernateException) {
            cancel();
            throw hibernateException;
        }
    }

    @Override
    public List<T> consultaHQL(String consulta) {
        return (List<T>) HibernateUtility.getSession().createQuery(consulta).list();
    }

    @Override
    public void cancel() {
        HibernateUtility.rollbackTransaction();
        HibernateUtility.closeSession();
    }
}
