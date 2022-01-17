package by.eugenol.dao;

import by.eugenol.data.SessionFactoryHolder;
import by.eugenol.interfaces.RolesDao;
import by.eugenol.pojos.Roles;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.sql.*;
import java.util.*;

public class RolesDaoImpl implements RolesDao <Roles, Integer> {

    private final SessionFactory sessionFactory;

    public RolesDaoImpl() {
        this.sessionFactory = SessionFactoryHolder.getSessionFactory();
    }

    public RolesDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public Roles getRolesById(Integer id) throws SQLException {
        Session session = sessionFactory.openSession();
        Transaction tr = session.beginTransaction();
        Roles roles = session.load(Roles.class, id);
        tr.commit();
        session.close();
        return roles;
    }

    @Override
    public List<Roles> findAll() throws SQLException {
        List<Roles> roles = (List<Roles>) SessionFactoryHolder
                .getSessionFactory()
                .openSession()
                .createQuery("From Roles").list();
        return roles;
    }

    @Override
    public Serializable save(Roles roles) throws SQLException {
        Session session = sessionFactory.openSession();
        Serializable id = null;
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            session.saveOrUpdate(roles);
            tr.commit();
        } catch (Exception e) {
            if (tr != null) tr.rollback();
            e.printStackTrace();
        } finally {
            if (session != null)
                session.close();
        }
        return id;
    }

    @Override
    public void update(Roles roles) throws SQLException {
        Session session = sessionFactory.openSession();
        Serializable id = null;
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            session.update(roles);
            tr.commit();
        } catch (Exception e) {
            if (tr != null) tr.rollback();
            e.printStackTrace();
        } finally {
            if (session != null)
                session.close();
        }
    }

    @Override
    public boolean deleteRoleById(Serializable id) throws SQLException {
        Session session = sessionFactory.openSession();
        Roles roles = session.get(Roles.class, id);
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            session.delete(roles);
            tr.commit();
        } catch (Exception e) {
            if (tr != null) tr.rollback();
            e.printStackTrace();
        } finally {
            if (session != null)
                session.close();
        }
        return roles != null;
    }
}
