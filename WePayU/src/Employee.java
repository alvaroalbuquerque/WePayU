import java.util.Scanner;

public class Employee {
    private int id;
    private String name;
    private String address;
    private int paymentType; //1 - hourly; 2 - salaried; 3 - commissioned;
    //private int paymentMethod; // 1 - 8h+ 1.5x through extra hours; 2 - Last week day of the month; '3' - Each 2 fridays get 2 week worth fixed salary + commission;
    private double paymentAmount;
    private double paymentAux;
    private double bankAccount;

    //--
    private boolean isSyndicate;
    private int syndicateID;
    private double syndicateTax;
    //--

    private double commissionedPercentage;




    //constructor
    public Employee(String name, String address, int paymentType, double paymentAmount, int id)
    {
        this.id = id+1;
        this.name = name;
        this.address = address;
        this.paymentType = paymentType;
        //this.paymentMethod = paymentType;
        this.paymentAmount = paymentAmount;

        if(paymentType == 3)
        {
            Scanner input = new Scanner(System.in);
            System.out.println("Whats the selling percentage ?");
            this.commissionedPercentage = Double.parseDouble(input.nextLine());
        }
        else
        {
            this.commissionedPercentage = 0;
        }

        this.paymentAux = 0.0;
        this.bankAccount = 0.0;

        this.isSyndicate = false;
        this.syndicateID = 0;
        this.syndicateTax = 0;
    }

    public void displayEmployee()
    {
        System.out.println("ID: " + this.id + "\nNAME: " + this.name + "\n"
                         + "ADDRESS: " + this.address + "\n"
                         + "Payment Type ~ Payment Amount: " + this.paymentType + " ~ " + this.paymentAmount);
        if(this.paymentType == 1)
        {
            System.out.println("Paid " + this.paymentAmount + " per hour + extra hours bonus.");
        }
        else if(this.paymentType == 2)
        {
            System.out.println("Paid " + this.paymentAmount + " per month.");
        }
        else if(this.paymentType == 3)
        {
            System.out.println("Paid " + this.paymentAmount + " every two fridays + commission.");
        }

        if(this.isSyndicate)
        {
            System.out.println("Syndicate info/ID: " + this.syndicateID);
        }
        System.out.println("Bank: " + this.bankAccount);
        System.out.println();
    }

    public String getName()
    {
        return this.name;
    }

    public double getPaymentLiquid() {
        return paymentAmount;
    }

    public void editInfo()
    {
        int command;
        Scanner input = new Scanner(System.in);

        System.out.println("Would you like to edit name ?? '0' - NO; '1' - YES");
        command = input.nextInt();
        input.skip("\n");
        if(command == 1)
        {
            System.out.println("Insert the new name: ");
            this.name = input.nextLine();
            command = 0;
        }

        System.out.println("Would you like to edit address ?? '0' - NO; '1' - YES");
        command = input.nextInt();
        input.skip("\n");
        if(command == 1)
        {
            System.out.println("Insert the new address: ");
            this.address = input.nextLine();
            command = 0;
        }

        System.out.println("Would you like to edit payment type ?? '0' - NO; '1' - YES");
        command = input.nextInt();
        input.skip("\n");
        boolean changedPayment = false;
        if(command == 1)
        {
            System.out.println("Press one of the following keys: 1 - hourly; 2 - salaried; 3 - commissioned;");
            this.paymentType = input.nextInt();
            command = 0;

            System.out.println("Would you like to edit payment amount ?? '0' - NO; '1' - YES\nCurrent is: " + this.paymentAmount);
            command = input.nextInt();
            input.skip("\n");
            changedPayment = true;
            if(command == 1)
            {

                System.out.print("Press the new amount: ");
                this.paymentAmount = Double.parseDouble(input.nextLine());
                command = 0;
            }

        }

        if(!changedPayment)
        {
            System.out.println("Would you like to edit payment amount ?? '0' - NO; '1' - YES\nCurrent is: " + this.paymentAmount);
            command = input.nextInt();
            input.skip("\n");
            if(command == 1)
            {
                changedPayment = true;
                System.out.print("Press the new amount: ");
                this.paymentAmount = Double.parseDouble(input.nextLine());
                command = 0;
            }
        }

        /*
        System.out.println("Would you like to edit payment method ?? '0' - NO; '1' - YES");
        command = input.nextInt();
        input.skip("\n");
        if(command == 1)
        {
            System.out.println("Press one of the following keys: 1 - 8h+ 1.5x through extra hours; 2 - Last week day of the month; '3' - Each 2 fridays get 2 week worth fixed salary + commission;" );
            this.paymentMethod = input.nextInt();
            command = 0;
        }*/

        System.out.println("Would you like to edit syndicate settings ?? '0' - NO; '1' - YES");
        command = input.nextInt();
        input.skip("\n");
        if(command == 1)
        {
            System.out.println("Is this employee on a syndicate ? '0' - NO; '1' - YES" );
            command = input.nextInt();

            if(command == 1)
            {
                this.isSyndicate = true;
                System.out.println("Whats the syndicate ID ?");
                this.syndicateID = input.nextInt();
                input.skip("\n");
                System.out.println("Whats the syndicate tax ?");
                this.syndicateTax = Double.parseDouble(input.nextLine());
            }
            command = 0;
        }

    }

    public void updateEmployeeAux()
    {
        if(paymentType == 1) //hourly
        {
            Scanner input = new Scanner(System.in);
            System.out.println(this.name + "is hourly paid please answer the question(s): ");
            System.out.println("How many regular hours " + this.name + " worked ? (Without extra hours)");
            int normalHours = input.nextInt();
            int extraHours = 0;
            if(normalHours == 8)
            {
                System.out.println("Now how many extra hours " + this.name + " worked ?");
                extraHours = input.nextInt();

            }
            this.paymentAux += (normalHours*this.paymentAmount) + (extraHours*this.paymentAmount*1.5);

            System.out.println("Current amount updated!");
        }
        else if(paymentType == 3) //commissioned
        {
            Scanner input = new Scanner(System.in);
            System.out.println(this.name + " is comissioned please answer the question(s): ");
            System.out.println("How much did " + this.name + " selled ? (in R$ Reais)");
            double selled = Double.parseDouble(input.nextLine());

            this.paymentAux += (commissionedPercentage*selled);

            System.out.println("Current amount updated!");
        }

    }

    public void payEmployees(int whichTypeIsPayed)
    {
        if(this.paymentType == whichTypeIsPayed) //
        {
            if(this.paymentType == 1)
            {
                this.bankAccount += (this.paymentAux - (this.paymentAux*this.syndicateTax));
                this.paymentAux = 0;
            }
            else if(this.paymentType == 2)
            {
                this.bankAccount += this.paymentAmount - (this.paymentAmount*this.syndicateTax);
            }
            else if(this.paymentType == 3)
            {
                this.bankAccount += this.paymentAmount + this.paymentAux - ((this.paymentAmount + this.paymentAux)*syndicateTax);
                this.paymentAux = 0;
            }


        }
    }





}
