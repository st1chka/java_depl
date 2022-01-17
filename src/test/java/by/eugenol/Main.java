package by.eugenol;

import by.eugenol.dao.UsersDaoImpl;
import by.eugenol.data.SessionFactoryHolder;
import by.eugenol.interfaces.UsersDao;
import by.eugenol.pojos.Roles;
import by.eugenol.pojos.Users;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws SQLException {

        Session session = SessionFactoryHolder.getSessionFactory().openSession();
        Transaction tr = session.beginTransaction();

        // Add subscription
        Roles roles1 = new Roles();
        roles1.setName("Admin");

        Roles roles2 = new Roles();
        roles2.setName("Manager");

        Roles roles3 = new Roles();
        roles3.setName("Anonym");


        Set<Roles> rolesSet = new HashSet<Roles>();
        rolesSet.add(roles1);
        rolesSet.add(roles2);
        rolesSet.add(roles3);

        // Add Users

        Users user1 = new Users();
        user1.setLogin("Mikiiie");

        UsersDao<Users, Integer> usersDao = new UsersDaoImpl();
        usersDao.saveUserWithRoles(rolesSet, user1);



    }
}
