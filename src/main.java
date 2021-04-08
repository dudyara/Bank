import Entity.Client;
import Entity.Deposit;
import Interface.DepositManager;

import java.util.ArrayList;
import java.util.List;
import static Entity.Client.loadClients;

class MainTest //Класс для тестирования методов
{
    public static void main(String[] args) throws Exception
    {
        List<Client> cl = loadClients();    //Загружаем список клиентов
        for(int i = 0; i < cl.size(); i++)
            System.out.print(cl.get(i).getClientId() + " " + cl.get(i).getFirstName() + " " + cl.get(i).getSecondName() + " " +
                    cl.get(i).getThirdName() +" " + cl.get(i).getPasportData()+"\n");           //Вывод в консоль для проверки
    }
}