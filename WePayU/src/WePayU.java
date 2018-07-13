import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

public class WePayU {

    public static void printingList(ArrayList<Employee> employees, int listSize)
    {
        for(int i = 0; i < listSize ; i++)
        {
            employees.get(i).displayEmployee();
        }
    }

    public static int lastBusinessDay(Calendar today)
    {
        Calendar lastDay = Calendar.getInstance();
        lastDay.add(Calendar.MONTH, 1);
        lastDay.set(Calendar.DAY_OF_MONTH, 1);
        do{
            lastDay.add(Calendar.DAY_OF_MONTH, -1);
        }while (lastDay.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ||
                lastDay.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY);

        return lastDay.get(Calendar.DAY_OF_MONTH);
    }

    public static void payEmployees(Calendar today, ArrayList<Employee> employees, int countFridays)
    {
        if(today.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) //hourly
        {
            for(Employee current : employees)
            {
                current.payEmployees(1); //1 = hourly -> is payed;
            }

            System.out.println("~~Hourly paid employees were paid.");
        }
        if(countFridays == 2)
        {
            for(Employee current : employees)
            {
                current.payEmployees(3); //3 = commissioned -> is payed;
            }
            System.out.println("~~Commissioned employees were paid.");
        }
        if(today.get(Calendar.DAY_OF_MONTH) == lastBusinessDay(today))
        {
            for(Employee current : employees)
            {
                current.payEmployees(2); //2 = salaried -> is payed;
            }
            System.out.println("~~Salaried employees were paid.");
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Calendar today = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("EEE, MMM d");
        String formatted;

        int command = 1;
        int countFridays = 0;
        boolean commissionedGetPaid = false;

        ArrayList<Employee> employees = new ArrayList<>();

        do
        {
            if(today.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) countFridays++;

            formatted = format1.format(today.getTime());
            System.out.println("* Today is " + formatted + " *");
            System.out.println("                   MENU\nPress one of the keys: \n" +
                    "'0' - Exit; \n" +
                    "'1' - Add an employee; \n" +
                    "'2' - Edit an employee info; \n" +
                    "'3' - Display all employees; \n" +
                    "'4' - Remove an employee; \n" +
                    "'5' - Update the day of work;");
            command = input.nextInt();
            input.skip("\n");


            if(command == 1)
            {
                Employee newEmployee;
                String inputName, inputAddress;
                int inputPaymentType;
                double inputPaymentAmount;

                System.out.println("What`s the employee`s name ??");
                inputName = input.nextLine();
                System.out.println("What`s the employee`s address ??");
                inputAddress = input.nextLine();
                System.out.println("What`s the employee`s payment type ?? '1' - hourly; '2' - salaried; '3' - commissioned");
                inputPaymentType = input.nextShort();
                input.skip("\n");
                System.out.println("What`s the employee`s payment amount ??\nIf paid hourly, the amount per hour;\nIf paid salaried, the amount per month;\n" +
                        "If paid commissioned, the amount per 2 weeks;");
                inputPaymentAmount = Double.parseDouble(input.nextLine());

                newEmployee = new Employee(inputName, inputAddress, inputPaymentType, inputPaymentAmount, employees.size());

                employees.add(newEmployee);

            }
            if(command == 2)
            {
                String nameEdit;
                boolean thereIsnt = true;
                System.out.println("Whats the name of the employee you want to edit ?");
                nameEdit = input.nextLine();

                int listSize = employees.size();

                for(int i = 0; i < listSize ; i++)
                {
                    if(nameEdit.equals(employees.get(i).getName()))
                    {
                        employees.get(i).editInfo();
                        thereIsnt = false;
                    }
                }
                if(thereIsnt)
                {
                    System.out.println("There is not a employee with that name !");
                }

            }
            if(command == 3){
                if(employees.size() == 0)
                {
                    System.out.println("#######################################################\n" +
                            "#######################################################");
                    System.out.println("                There is no employees!");
                    System.out.println("#######################################################\n" +
                            "#######################################################");
                }
                else
                {
                    System.out.println("\n#######################################################\n" +
                            "#######################################################\n" +
                            "                     EMPLOYEES");

                    printingList(employees, employees.size());

                    System.out.println("#######################################################\n" +
                            "#######################################################\n");
                }
            }
            if(command == 4)
            {
                    String nameEdit;
                    boolean thereIsnt = true;
                    System.out.println("Whats the name of the employee you want to remove ?");
                    nameEdit = input.nextLine();

                    int listSize = employees.size();

                    for(int i = 0; i < listSize ; i++)
                    {
                        if(nameEdit.equals(employees.get(i).getName()))
                        {
                            employees.remove(i);
                            thereIsnt = false;
                        }
                    }
                    if(thereIsnt)
                    {
                        System.out.println("There is not a employee with that name !");
                    }



            }
            if(command == 5)
            {
                for(Employee current : employees)
                {
                    current.updateEmployeeAux();
                }
                payEmployees(today, employees, countFridays);
                if(countFridays == 2) countFridays = 0;
                if(today.getActualMaximum(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH))
                {
                    today.roll(Calendar.MONTH, 1);
                    today.set(Calendar.DAY_OF_MONTH, 1);
                }
                else
                {
                    today.roll(Calendar.DAY_OF_MONTH, 1);
                }
                System.out.println("-------------------------------------------\n" +
                        "Day passed!\n-------------------------------------------");
            }


        }while(command != 0);

        input.close();
    }
}