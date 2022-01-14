package by.eugenol;

import by.eugenol.dao.RolesDaoImpl;
import by.eugenol.dao.UsersDaoImpl;
import by.eugenol.data.SessionFactoryHolder;
import by.eugenol.pojos.Roles;
import by.eugenol.pojos.Users;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Transient;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {

        UsersDaoImpl usersDao = new UsersDaoImpl();

        RolesDaoImpl rolesDao = new RolesDaoImpl();

        Session session = SessionFactoryHolder.getSessionFactory().openSession();

        Transaction tr = session.beginTransaction();

        session.save(rolesDao);

        System.out.println(usersDao.getUsersById(1));
    }
}
