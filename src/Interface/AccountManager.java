package Interface;

import Entity.Account;
import java.util.*;

public interface AccountManager {
    /*
      Метод добавляет нового пользователя системы
     */
    void addAccount(String userName, String password);

    /*
      Метод удаляет пользователя системы
     */
    void removeAccount(String userName, String password);

    /*
      Метод возвращает список всех аккаунтов
     */
    List<String> getAllAccounts();

    /*
      Метод авторизирует пользователя и возвращает Token для доступа методам системы
     */
    String authorize(String userName, String password, Date currentTime);

}
