package br.com.meuprimeiroprojeto.util;

import br.com.meuprimeiroprojeto.modelo.Cidade;
import br.com.meuprimeiroprojeto.modelo.Estado;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateUtility {

    private static final SessionFactory factory;
    private static final ThreadLocal sessionThread = new ThreadLocal();
    private static final ThreadLocal transactionThread = new ThreadLocal();

    public static Session getSession() {
        Session session = (Session) sessionThread.get();
        if ((session == null) || (!(session.isOpen()))) {
            session = factory.openSession();
            sessionThread.set(session);
        }
        return ((Session) sessionThread.get());
    }

    public static void closeSession() {
        Session session = (Session) sessionThread.get();
        if ((session != null) && (session.isOpen())) {
            sessionThread.set(null);
            session.close();
        }
    }

    public static void beginTransaction() {
        Transaction transaction = getSession().beginTransaction();
        transactionThread.set(transaction);
    }

    public static void commitTransaction() {
        Transaction transaction = (Transaction) transactionThread.get();
        if ((transaction != null) && (!(transaction.wasCommitted())) && (!(transaction.wasRolledBack()))) {
            transaction.commit();
            transactionThread.set(null);
        }
    }

    public static void rollbackTransaction() {
        Transaction transaction = (Transaction) transactionThread.get();
        if ((transaction != null) && (!(transaction.wasCommitted())) && (!(transaction.wasRolledBack()))) {
            transaction.rollback();
            transactionThread.set(null);
        }
    }

    static {
        try {
            factory = new Configuration()
                    /***POSTGRESQL***/
//                    .setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
//                    .setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
//                    .setProperty("hibernate.connection.url", "jdbc:postgresql://localhost/meuprimeiroprojeto")
//                    .setProperty("hibernate.connection.username", "postgres")
//                    .setProperty("hibernate.connection.password", "postgres")
                    /***MYSQL***/
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLInnoDBDialect")
                    .setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
                    
                    //CONFIGURAÇÃO DA BASE DE DADOS ** Verificar sempre Username e Password caso necessario alterar.
                    .setProperty("hibernate.connection.url", "jdbc:mysql://localhost/meuprimeiroprojeto")
                    .setProperty("hibernate.connection.username", "root")
                    .setProperty("hibernate.connection.password", "root")
                    
//                    .setProperty("hibernate.connection.datasource", "jdbc/dbSGC") //data source (so pra aplicacao web e tem q configurar no tomcat)
                    .setProperty("hibernate.hbm2ddl.auto", "update")                  
                    .setProperty("hibernate.c3p0.max_size", "10")
                    .setProperty("hibernate.c3p0.min_size", "2")
                    .setProperty("hibernate.c3p0.timeout", "5000")
                    .setProperty("hibernate.c3p0.max_statements", "10")
                    .setProperty("hibernate.c3p0.idle_test_period", "3000")
                    .setProperty("hibernate.c3p0.acquire_increment", "2")
                    .setProperty("show_sql", "true")
                    .setProperty("use_outer_join", "true")
                    .setProperty("hibernate.generate_statistics", "true")
                    .setProperty("hibernate.use_sql_comments", "true")
                    .setProperty("hibernate.format_sql", "true")
                    //CLASSES PARA MAPEAMENTO
                    .addAnnotatedClass(Cidade.class)
                    .addAnnotatedClass(Estado.class)                    
                    
                    
                    .buildSessionFactory();
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String [] args) {

    }

}
