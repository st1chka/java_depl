package by.eugenol.interfaces;

import by.eugenol.pojos.Roles;
import by.eugenol.pojos.Users;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface RolesDao<T, IDr>{

    Roles getRolesById(Integer id) throws SQLException;

    List<T> findAll() throws SQLException;

    Serializable save(T o) throws  SQLException;

    void update(T o) throws SQLException;

    boolean deleteRoleById(Serializable id) throws SQLException;

}
