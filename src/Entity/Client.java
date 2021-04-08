package Entity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;

public class Client
{
    private int ClientID;       //Объявление переменных класса
    private String firstName;
    private String secondName;
    private String thirdName;
    private String pasportData;

    public int getClientId() {
        return ClientID;
    }       //Геттеры-сеттеры

    public void setClientID(int ClientId) {
        this.ClientID=ClientId;
    }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getThirdName() {
        return thirdName;
    }

    public void setThirdName(String thirdName) {
        this.thirdName = thirdName;
    }

    public String getPasportData() {
        return pasportData;
    }

    public void setPasportData(String pasportData) {
        this.pasportData = pasportData;
    }

    public static List<Client> loadClients() throws IOException     //Метод по загрузке Клиентов из файла
    {
        BufferedReader reader = new BufferedReader(new FileReader("src/Data/Clients.csv")); //Путь к файлу

        String line = null;
        Scanner scanner = null;
        int index = 0;
        List<Client> ClientList = new ArrayList<>();                //Создаем список клиентов

        while ((line = reader.readLine()) != null)
        {
            Client client = new Client();                           //Создаем нового клиента
            scanner = new Scanner(line);
            scanner.useDelimiter(",");
            while (scanner.hasNext())
            {
                String data = scanner.next();                       //Считываем все параметры и конвертируем если необходимо
                if (index == 0)
                    client.setClientID((Integer.parseInt(data)));
                else if (index == 1)
                    client.setFirstName(data);
                else if (index == 2)
                    client.setSecondName(data);
                else if (index == 3)
                    client.setThirdName(data);
                else if (index == 4)
                    client.setPasportData(data);
                index++;
            }
            index = 0;
            ClientList.add(client);                                 //Добавляем клиента в список
            System.out.print(client.getClientId() + " " + client.getFirstName() + " " + client.getSecondName() + " " + client.getThirdName()
                    +" " + client.getPasportData()+"\n");           //Вывод в консоль для проверки
        }
        reader.close();
        return ClientList;                                          //Возвращаем готовый лист клиентов
    }
}


