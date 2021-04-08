package Entity;

import Interface.AccountManager;

import java.util.*;


public class Account implements AccountManager {
    private int accountId; //Объявление переменных класса
    private String user;
    private String password;
    private String token;

    private static List<Account> AccountList = new ArrayList<Account>(); //Объявление массива с аккаунтами

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accointId) {
        this.accountId = accointId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void addAccount(String userName, String password) //Метод, добавляющий аккаунт
    {
        Random random = new Random(); //Для создания @token
        Account acc = new Account();  //Создаем новый аккаунт
        acc.setPassword(password);    //Задаем пароль
        acc.setUser(userName);        //Задаем пользователя
        acc.setToken(Integer.toString(random.nextInt(999999))); //Задаем токен (рандомное число в формате String)
        AccountList.add(acc);         //Добавляем пользователя в список
        for(int i=0;i<AccountList.size();i++)
            if (acc == AccountList.get(i))
                AccountList.get(i).setAccountId(i);     //Присваиваем полльзователю ID согласно индексу в списке
    }

    public void removeAccount(String userName, String password) //Метод для удаления аккаунта из листа
    {
        for(int i=0;i<AccountList.size();i++) //Проходим по списку
            if(userName == AccountList.get(i).getUser() && (password == AccountList.get(i).getPassword())) //Выбираем нужного пользователя
                AccountList.remove(i);          //Удаляем пользователя
    }

    public List<String> getAllAccounts(){                    //Метод для получения списка с аккаунтами в формате String
        List<String> StringList = new ArrayList<String>();   //Создаем новый список
        for(int i=0;i<AccountList.size();i++) {            //Проходим по списку
            String str = Integer.toString(AccountList.get(i).getAccountId()) + " " + AccountList.get(i).getUser() + " " +
                    AccountList.get(i).getPassword()+" "+AccountList.get(i).getToken();     //Конвертируем
            StringList.add(str);                                                            //Добавляем в список
        }
        return StringList;                                                                  //Возвращаем список
    }

    public String authorize(String userName, String password, Date currentTime){ //Метод для возвращения токена
        String str = " ";
        for(int i=0;i<AccountList.size();i++)                                  //Проходим по списку
            if(userName == AccountList.get(i).getUser() && (password == AccountList.get(i).getPassword()))
                str = AccountList.get(i).getToken();                             //Получаем нужный токен
            return str;                                                          //Возвращам токен
    }

    public static int checkToken(String token)             //Метод для проверки существования токена
    {
        int t = 0;
        for(int i=0;i<AccountList.size();i++)
            if(token == AccountList.get(i).getToken())
                 t = 1;                                    //Если в списке такой токен существует возвращаем 1
            return t;
    }

}
