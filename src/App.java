import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    static Scanner tb = new Scanner(System.in);
    static final double PRIS_VUXEN = 299.90;
    static final double PRIS_BARN = 149.90;
    public static void main(String[] args) throws Exception {
        boolean loop = true;
        int index = 20;
        int[] bokning_nr = new int[20];
        //arrayer för att spara bokningsinformation
        for (int i = 0; i < bokning_nr.length; i++) {
            bokning_nr[i] = 0;
        }
        String[] bokning_namn = new String[20];
        for (int i = 0; i < bokning_namn.length; i++) {
            bokning_namn[i] = "0";
        }
        double[] priser = new double[20];
        for (int j = 0; j < priser.length; j++) {
            priser[j] = 0;
        }
        //matris för att visa platser
        String[][] visa_platser = new String[5][4];
        int x = 1;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                visa_platser[i][j] = String.valueOf(x);
                x++;
            }
        }
        double total_vinst = 0;
        //loop för att visa menyn, menyne är en bollean för att loopen ska kunna brytas
        while (loop) {
            System.out.println("Hej! Välj en tjänst från listan nedan!");
            loop = visa_meny(priser, index, bokning_nr, bokning_namn, visa_platser, loop, total_vinst);
        }
    }

    public static boolean visa_meny(double[] priser, int index, int[] bokning_nr, String[] bokning_namn, String[][] visa_platser, boolean loop, double total_vinst){
        //visa menyalternativ
        System.out.println("1. Boka en plats på bussen.");
        System.out.println("2. Hitta/ändra bokning.");
        System.out.println("3. Visa passagerare");
        System.out.println("4. Beräkna vinst av biljetter.");
        System.out.println("5. Avsluta.");
        int val = 0;
        //kontrollera användarval
        while (val>5 || val==0) {
            try {
                val = tb.nextInt();
                tb.nextLine();
            } 
            catch (InputMismatchException e) {
                System.out.println("Välj en tjänst med dess siffra.");
                tb.nextLine();
            }
        }
        //tjänst som utförs baserat på användarens val
        switch (val) {
            case 1:
                boka_plats(bokning_nr, bokning_namn, priser, visa_platser, loop);
                return true;
            case 2:
                hitta_bokning(priser, bokning_nr, bokning_namn, visa_platser);
                return true;
            case 3:
                visa_passagerare(bokning_namn, bokning_nr);
                return true;
            case 4:
                System.out.println(beräkna_vinst(priser, index, total_vinst));
                return true;
            case 5:
                return false;
            default:
                return true;
        }
    }
    
    
    public static void boka_plats(int[] bokning_nr, String[] bokning_namn, double[] priser, String[][] visa_platser, boolean loop){
        //läser in användarens personuppgifter
        System.out.println("Ange ditt födelsedatum ÅÅÅÅMMDD:");
        int person_nr = 0;
        while (person_nr<10000000 || person_nr>=100000000) {
            try {
                person_nr = tb.nextInt();
            } 
            catch (InputMismatchException e) {
                System.out.println("Använd endast siffror.");
                tb.nextLine();
            }
        }
        System.out.println("Ange ditt namn:");
        String namn = tb.nextLine();
        namn = tb.nextLine();
        //visa tillgängliga platser
        System.out.println("Välj en plats att boka:");
        for (int i = 0; i < 5; i++) {
            System.out.println();
            for (int j = 0; j < 4; j++) {
                System.out.print("[");
                if (Integer.parseInt(visa_platser[i][j])<10) {
                    System.out.print("0"+visa_platser[i][j]);
                }
                else{
                    System.out.print(visa_platser[i][j]);
                }
                System.out.print("]");
                System.out.print(" ");
            }
        }
        System.out.println();
        //tar in val för vilken plats användaren vill boka
        int val = 0;
        while (val>20 || val==0) {
            try {
                val = tb.nextInt();
                if (val > 20 || val <=0) {
                    System.out.println("Skriv en siffra mellan 1-20.");
                    tb.nextLine();
                }
            } 
            catch (InputMismatchException e) {
                System.out.println("Skriv en siffra mellan 1-20.");
                tb.nextLine();
            }
        }
        if (bokning_nr[val-1] != 0 || bokning_namn[val-1] != "0") {
            System.out.println("Platsen är redan upptagen:");
        }
        else{
            System.out.println("Du har bokat platsen "+val+"!");
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 4; j++) {
                    if (String.valueOf(val) == visa_platser[i][j]) {
                        visa_platser[i][j] = "X";
                        continue;
                    }
                }
            }
            //Kolla om platsen är en fönsterplats
            if (val%4==1 || val%4==0){
                System.out.println("Platsen du har bokat är en fönsterplats.");
            }
            else{
                System.out.println("Platsen du har bokat är en gångplats.");
            }
            //spara bokningsinformation och pris
            bokning_namn[val-1] = namn;
            bokning_nr[val-1] = person_nr;
            if (person_nr>20060502) {
                priser[val-1] = PRIS_BARN;
            }
            else if (person_nr<20060502) {
                priser[val-1] = PRIS_VUXEN;
            }
        }
    }
    
    public static double beräkna_vinst(double[] priser, int index,  double total_vinst){
        if (index == 0) {
            //basfall då rekursionen har körts klart
            return total_vinst;
        }
        else{
            //beräkna värdet av arrayen med rekursion
            total_vinst += priser[index-1];
            return beräkna_vinst(priser, index-1, total_vinst);
        }        
    }

    public static void visa_passagerare(String[] bokning_namn, int[] bokning_nr){
        //visa alla bokningar med namn och personnummer utifrån plats
        for (int i = 0; i < bokning_nr.length; i++) {
            System.out.print(bokning_nr[i]);
            System.out.print(" ");
        }
        System.out.println("");
        for (int i = 0; i < bokning_namn.length; i++) {
            System.out.print(bokning_namn[i]);
            System.out.print(" ");
        }
        //skapa nya arrayer för att kunna sortera utan att göra om bokningsuppgifterna
        int[] bokning_nr_sorterad = new int[20];
        for (int i = 0; i < bokning_nr_sorterad.length; i++) {
            bokning_nr_sorterad[i] = bokning_nr[i];
        }
        String[] bokning_namn_sorterad = new String[20];
        for (int i = 0; i < bokning_namn_sorterad.length; i++) {
            bokning_namn_sorterad[i] = bokning_namn[i];
        }
        boolean byt = true;
        //loop för att arrayen ska sorteras
        for (int i = 0; i < bokning_nr_sorterad.length-1; i++) {
            byt = false;
            for (int j = 0; j < bokning_nr_sorterad.length-1; j++) {
                if (bokning_nr_sorterad[j]>bokning_nr_sorterad[j+1]) {
                    int sort = bokning_nr_sorterad[j];
                    bokning_nr_sorterad[j] = bokning_nr_sorterad[j+1];
                    bokning_nr_sorterad[j+1] = sort;
                    String sort2 = bokning_namn_sorterad[j];
                    bokning_namn_sorterad[j] = bokning_namn_sorterad[j+1];
                    bokning_namn_sorterad[j+1] = sort2;
                    byt = true;
                }
            }
            //bryt loopen när sorteringen är färdig
            if (byt == false) {
                break;
            }
        }
        //skriv ut den sorterade arrayen med namn och pers nr.
        System.out.println("");
        for (int i = 0; i < bokning_nr_sorterad.length; i++) {
            System.out.print(bokning_nr_sorterad[i]);
            System.out.print(" ");
        }
        System.out.println("");
        for (int i = 0; i < bokning_namn_sorterad.length; i++) {
            System.out.print(bokning_namn_sorterad[i]);
            System.out.print(" ");
        }
        System.out.println("");
        //skapa nya arrayer för att sortera passagerare i barn och vuxna
        int[] bokning_nr_vuxen = new int[20];
        int[] bokning_nr_barn = new int[20];
        String[] bokning_namn_vuxen = new String[20];
        for (int i = 0; i < bokning_namn_vuxen.length; i++) {
            bokning_namn_vuxen[i] = "0";
        }
        String[] bokning_namn_barn = new String[20];
        for (int i = 0; i < bokning_namn_barn.length; i++) {
            bokning_namn_barn[i] = "0";
        }
        //föra över värden från array baserat på födelsedatum
        for (int i = 0; i < bokning_nr_sorterad.length; i++) {
            if (bokning_nr_sorterad[i]>20060502) {
                bokning_nr_barn[i] = bokning_nr_sorterad[i];
                bokning_namn_barn[i] = bokning_namn_sorterad[i];
            }
            else if (bokning_nr_sorterad[i]<20060502) {
                bokning_nr_vuxen[i] = bokning_nr_sorterad[i];
                bokning_namn_vuxen[i] = bokning_namn_sorterad[i];
            }
        }
        //skriv ut barn respektive vuxna passagerare
        System.out.println("Barnpassagerare:");
        for (int i = 0; i < bokning_nr_barn.length; i++) {
            if (bokning_nr_barn[i] == 0) {
                System.out.print("");
            }
            else{
                System.out.print(bokning_nr_barn[i]);
                System.out.print(" ");
            }
        }
        System.out.println("");
        for (int i = 0; i < bokning_namn_barn.length; i++) {
            if (bokning_namn_barn[i].equals("0")) {
                System.out.print("");
            }
            else{
                System.out.print(bokning_namn_barn[i]);
                System.out.print(" ");
            }
        }
        System.out.println("");
        System.out.println("Vuxenpassagerare:");
        for (int i = 0; i < bokning_nr_vuxen.length; i++) {
            if (bokning_nr_vuxen[i] == 0) {
                System.out.print("");
            }
            else{
                System.out.print(bokning_nr_vuxen[i]);
                System.out.print(" ");
            }
        }
        System.out.println("");
        for (int i = 0; i < bokning_namn_vuxen.length; i++) {
            if (bokning_namn_vuxen[i].equals("0")) {
                System.out.print("");
            }
            else{
                System.out.print(bokning_namn_vuxen[i]);
                System.out.print(" ");
            }
        }
        System.out.println("");
        //LÄGG TILL SÅ ATT DEN SORTERAR UTIFRÅN ÅLDER OCH SKRIVER UT VILKA SOM ÄR ÖVER OCH UNDER 18 ÅR!!
        
    }

    public static void hitta_bokning(double[] priser, int[] bokning_nr, String[] bokning_namn, String[][] visa_platser){
        //ge användaren alternativ för en tjänst
        System.out.println("Hitta din bokning via:");
        System.out.println("1. Personnummer");
        System.out.println("2. Namn");
        System.out.println("Välj en metod med dess nummer.");
        int val = 0;
        //kontrollera användarens val
        while (val != 1 && val != 2) { 
            try {
                val = tb.nextInt();
                if (val > 2 || val <= 0) {
                    System.out.println("Välj en metod med dess nummer.");
                    tb.nextLine();
                }
            }
            catch (InputMismatchException e) {
                System.out.println("Välj en medod med dess nummer.");
                tb.nextLine();
            }
        }     
        boolean bokning = false;
        int ändra_bokning = 0;
        //vilken åtgärd som ska utföras baserad på användarens val
        switch (val) {
            case 1:
                System.out.println("Ange ditt personnummer:");
                int person_nr = 0;
                //kontrollera personnummer
                while (person_nr > 100000000 || person_nr<10000000) {
                    try {
                        person_nr = tb.nextInt();
                        if (person_nr > 100000000 || person_nr < 10000000) {
                            System.out.println("Ange ett 8-siffrigt personnummer ÅÅÅÅMMDD");
                            tb.nextLine();
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Ange ditt personnummer ÅÅÅÅMMDD:");
                        tb.nextLine();
                    }
                }
                tb.nextLine();
                //gå igenom array och hitta bokning
                for (int i = 0; i < bokning_nr.length; i++) {
                    if (bokning_nr[i] == person_nr) {
                        System.out.println("Du har bokat plats "+i+1+"!");
                        bokning = true;
                        ändra_bokning = i;
                        break;
                    }
                    //skriva ut om bokning inte finns
                    else if (i == bokning_nr.length-1){
                        System.out.println("Kunde inte hitta bokning.");
                    }
                }
                break;
            case 2:
                tb.nextLine();
                System.out.println("Ange ditt namn:");
                String namn = tb.nextLine();
                //gå igenom array för att hitta plats med namn
                for (int i = 0; i < bokning_namn.length; i++) {
                    if (bokning_namn[i].equalsIgnoreCase(namn)) {
                        System.out.println("Du har bokat plats "+i+1+"!");
                        bokning = true;
                        ändra_bokning = i;
                        break;
                    }
                    //skriva ut om bokning inte finns
                    else if (bokning_namn.length-1 == i){
                        System.out.println("Kunde inte hitta bokning.");
                    }
                }
                break;
        }
        //om bokningen hittades, fråga om användaren vill ta bort bokningen
        if (bokning) {
            System.out.println("Vill du ta bort bokningen?");
            String svar = tb.nextLine();
            while (true) {
                //tar bort bokningsvärdena från alla arrayer
                if (svar.equalsIgnoreCase("ja")) {
                    bokning_namn[ändra_bokning] = "0";
                    bokning_nr[ändra_bokning] = 0;
                    break;
                }
                //ändrar inte bokningen
                else if (svar.equalsIgnoreCase("nej")) {
                    break;
                }
                //kontrollerar användarens inmatning
                else{
                    System.out.println("Skriv ja eller nej.");
                    svar = tb.nextLine();
                }
            }
        }
        //LÄGG TILL SÅ DE FAKTISKT KAN ÄNDRA BOKNINGEN 
    } 
}