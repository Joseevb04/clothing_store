package es.dws.clothing_store.service;

import es.dws.clothing_store.model.Login;
import es.dws.clothing_store.model.RegisterForm;
import es.dws.clothing_store.model.User;

/** UserService */
public interface UserService {

    User getUserById(int id);

    User addUser(User user);

    User loginUser(Login login);

    User registerUser(RegisterForm data);
}
