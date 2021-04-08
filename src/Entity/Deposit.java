package Entity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.text.*;
import java.io.File;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.List;

import Interface.DepositManager;

public class Deposit implements DepositManager {
    private int DepositID;  //Переменные класса
    private Client Client;
    private double ammount;
    private double percent;
    private double pretermPercent;
    private int termDays;
    private Date startDate;
    private boolean withPercentCapitalization;

    public int getDepositID() {
        return DepositID;
    }   //Геттеры-сеттеры

    public void setDepositID(int depositID) {
        DepositID = depositID;
    }

    public Client getClient() {
        return Client;
    }

    public void setClient(Client client) {
        Client = client;
    }

    public double getAmmount() {
        return ammount;
    }

    public void setAmmount(double ammount) {
        this.ammount = ammount;
    }

    public double getPretermPercent() {
        return pretermPercent;
    }

    public void setPretermPercent(double pretermPercent) {
        this.pretermPercent = pretermPercent;
    }

    public int getTermDays() {
        return termDays;
    }

    public void setTermDays(int termDays) {
        this.termDays = termDays;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public boolean isWithPercentCapitalization() {
        return withPercentCapitalization;
    }

    public void setWithPercentCapitalization(boolean withPercentCapitalization) {
        this.withPercentCapitalization = withPercentCapitalization;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public static List<Deposit> loadDeposits() throws IOException, ParseException { //Метод для загрузки вкладов из файла
        List<Client> ClientList = Entity.Client.loadClients();
        BufferedReader reader = new BufferedReader(new FileReader("src/Data/Deposits.csv")); //Путь к файлу

        DateFormat dateFormat=new SimpleDateFormat("dd.MM.yyyy");   //Формат даты

        String line = null;
        Scanner scanner = null;
        int index = 0;
        List<Deposit> DepositList = new ArrayList<>();  //Создаем список вкладов

        while ((line = reader.readLine()) != null)
        {
            Deposit deposit= new Deposit();
            scanner = new Scanner(line);
            scanner.useDelimiter(",");
            while (scanner.hasNext())                   //Считываем параметры
            {
                String data = scanner.next();
                if (index == 0)
                    deposit.setDepositID(Integer.parseInt(data));
                else if (index == 1)
                {
                    Client client = new Client();       //Находим владельца вклада из списка клиентов
                    for(int k=0;k<ClientList.size();k++)
                    {
                        if (ClientList.get(k).getClientId() == Integer.parseInt(data))
                            client = ClientList.get(k);
                    }
                    deposit.setClient(client);
                }
                else if (index == 2)
                    deposit.setAmmount(Double.parseDouble(data));
                else if (index == 3)
                    deposit.setPercent(Double.parseDouble(data));
                else if (index == 4)
                    deposit.setPretermPercent(Double.parseDouble(data));
                else if (index == 5)
                    deposit.setTermDays(Integer.parseInt(data));
                else if (index == 6)
                    deposit.setStartDate(dateFormat.parse(data));
                else if (index == 7)
                    if(Integer.parseInt(data) == 1)
                        deposit.setWithPercentCapitalization(true);
                    else
                        deposit.setWithPercentCapitalization(false);
                index++;
            }
            index = 0;
            DepositList.add(deposit);                    //Добавляем Вклад в список
        }
        reader.close();
        return DepositList;                             //Возвращаем список
    }

    public Deposit addDeposit(Client client, double ammount, double percent, double pretermPercent, int termDays,//Метод по добавлению вклада
                           Date startDate, boolean withPercentCapitalization,String token) throws IOException, ParseException {
        if (Account.checkToken(token) == 1) {   //Проверка на существование токена
            List<Deposit> DepositsList = loadDeposits();    //Загружаем список вкладов

            Deposit deposit = new Deposit();    //Создаем новый вклад
            deposit.setDepositID(DepositsList.get(DepositsList.size()).getDepositID() + 1); //Присваиваем новый ID
            deposit.setClient(client);          //Заполняем остальные параметры
            deposit.setAmmount(ammount);
            deposit.setPercent(percent);
            deposit.setStartDate(startDate);
            deposit.setPretermPercent(pretermPercent);
            deposit.setTermDays(termDays);
            deposit.setWithPercentCapitalization(withPercentCapitalization);

            DepositsList.add(deposit);          //Добавляем вклад в список вкладов
            saveDeposits();                     //Запускаем метод сохранения в файл
            return deposit;
        }
        else
            return null;                        //Если токена не существует
    }

    public void saveDeposits() throws IOException, ParseException { //Метод для сохранения списка вкладов в файл

        PrintWriter writer = new PrintWriter(new File("src/Data/Deposits.csv"));    //Путь к файлу
        List<Deposit> DepositsList = loadDeposits();        //Получаем список вкладов
        for(int i = 0;i<DepositsList.size();i++)
        {
            StringBuilder sb = new StringBuilder();         //Создаем SB

            sb.append(DepositsList.get(i).getDepositID());  //Записываем все параметры в файл
            sb.append(',');
            sb.append(DepositsList.get(i).getClient());
            sb.append(',');
            sb.append(DepositsList.get(i).getAmmount());
            sb.append(',');
            sb.append(DepositsList.get(i).getPretermPercent());
            sb.append(',');
            sb.append(DepositsList.get(i).getPercent());
            sb.append(',');
            sb.append(DepositsList.get(i).getTermDays());
            sb.append(',');
            sb.append(DepositsList.get(i).getStartDate());
            sb.append(',');
            sb.append(DepositsList.get(i).isWithPercentCapitalization());
            sb.append(',');
            sb.append('\n');
            writer.write(sb.toString());
        }
    }

    public List<Deposit> getClientDeposits(Client client,String token) throws IOException, ParseException { //Метод для получения списка
        if (Account.checkToken(token) == 1) {       //Проверка на существование токена                      // вкладов клиента
            List<Deposit> DepositList = loadDeposits();     //Загрузка списка вкладов
            List<Deposit> ClientDeposits = new ArrayList<>();   //Создание нового списка
            for (int i = 0; i < DepositList.size(); i++) {      //Проходим по всем вкладам
                if (DepositList.get(i).getClient() == client)
                    ClientDeposits.add(DepositList.get(i));     //Подходящие записываем в новый список
            }
            return ClientDeposits;                              //Возвращаем новый список
        }
        else
            return null;                                        //Если токена нет ничего не возвразаем
    }

    public List<Deposit> getAllDeposits() throws IOException, ParseException {  //Метод для получения всех депозитов с выводом в консоль
        List<Deposit> DepositList = loadDeposits(); //Загружаем лист деепозитов
        for (int i = 0;i<DepositList.size();i++)    //Выводим в консоль
            System.out.print(DepositList.get(i).getDepositID() +  " " + DepositList.get(i).getClient().getFirstName() + " "
                    + DepositList.get(i).getClient().getSecondName()+ " " + DepositList.get(i).getClient().getThirdName() +  " "
                    + DepositList.get(i).getAmmount() + " " + DepositList.get(i).getPercent()+ " " + DepositList.get(i).getPretermPercent() +" "
                    + DepositList.get(i).getTermDays()+ " " + DepositList.get(i).getStartDate()+ " " +
                    DepositList.get(i).isWithPercentCapitalization()+"\n");
        return DepositList; //Возвращаем все депозиты
    }

    public double getEarnings(Deposit deposit, Date currentDate,String token) // Метод для вывода дохода по вкладу
    {
        if (Account.checkToken(token) == 1) {   //Проверка токена
        long milliseconds = currentDate.getTime() - deposit.getStartDate().getTime();   //Разница в миллисекундах между первым днем вклада и текущим
        int days = (int) (milliseconds / (24 * 60 * 60 * 1000));    //Разница в днях между первым днем вклада и текущим
        int years = days % 365; //Считаем количество чистых лет
        double award;   //Переменная, означающая доход
        if (deposit.isWithPercentCapitalization() == true)  //При капитализаии процентов
            award = deposit.getAmmount() * (Math.pow((deposit.getPercent() / 100) + 1, years));
        else    //Без нее
            award = deposit.getAmmount() + years * (deposit.getAmmount() * (deposit.getPercent() / 100));
        return award;   //Возвращаем Доход по вкладу
        }
        else
            return 0;   //Если токена не существует
    }

    public double removeDeposit(Deposit deposit, Date closeDate,String token) throws IOException, ParseException {  //Метод для удаления вклада
        if (Account.checkToken(token) == 1) {   //Проверка токена
            List<Deposit> DepositList = loadDeposits();     //Загрузка списка вкладов
            long milliseconds = closeDate.getTime() - deposit.getStartDate().getTime();  //Разница в миллисекундах между первым днем вклада и текущим
            int days = (int) (milliseconds / (24 * 60 * 60 * 1000));    //Разница в днях
            int years = days % 365; //Количество лет
            double award;   //Переменная, означающая доход
            if (deposit.isWithPercentCapitalization() == true)  //При капитализаии процентов
                award = deposit.getAmmount() * (Math.pow((deposit.getPretermPercent() / 100) + 1, years));
            else           //Без нее
                award = deposit.getAmmount() + years * (deposit.getAmmount() * (deposit.getPretermPercent() / 100));

            for (int i = 0; i < DepositList.size(); i++) {  //Удаление вклада из списка
                if (deposit == DepositList.get(i))
                    DepositList.remove(i);
            }

            saveDeposits(); //Сохранение списка в файл

            return award;   //Возвращаем доход по вкладу
        }
        else
            return 0;   //Если токена не существует
    }

}
