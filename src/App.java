import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    // Skapar en scanner för att läsa indata från användaren
    static Scanner tb = new Scanner(System.in);
    // Pris för vuxen och barn
    static final double PRIS_VUXEN = 299.90;
    static final double PRIS_BARN = 149.90;

    public static void main(String[] args) throws Exception {
        // Boolean för att hålla loopen igång
        boolean loop = true;
        // Index för att spåra bokningar
        int index = 20;
        // Arrayer för att lagra bokningsinformation
        int[] bokning_nr = new int[20];
        String[] bokning_namn = new String[20];
        double[] priser = new double[20];
        // Matris för att visa tillgängliga platser
        String[][] visa_platser = new String[5][4];
        // Fyller matrisen med platsnummer
        int x = 1;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                visa_platser[i][j] = String.valueOf(x);
                x++;
            }
        }
        // Total vinst
        double total_vinst = 0;
        // Huvudloop för att visa menyn
        while (loop) {
            System.out.println("Hej! Välj en tjänst från listan nedan!");
            // Visa menyn och uppdatera loopen baserat på användarval
            loop = visa_meny(priser, index, bokning_nr, bokning_namn, visa_platser, loop, total_vinst);
        }
    }

    // Metod för att visa menyn och hantera användarval
    public static boolean visa_meny(double[] priser, int index, int[] bokning_nr, String[] bokning_namn, String[][] visa_platser, boolean loop, double total_vinst) {
        // Visa menyalternativ
        System.out.println("1. Boka en plats på bussen.");
        System.out.println("2. Hitta/ändra bokning.");
        System.out.println("3. Visa passagerare");
        System.out.println("4. Beräkna vinst av biljetter.");
        System.out.println("5. Avsluta.");
        int val = 0;
        // Loop för att kontrollera användarval
        while (val > 5 || val == 0) {
            try {
                val = tb.nextInt();
                tb.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Välj en tjänst med dess siffra.");
                tb.nextLine();
            }
        }
        // Utför åtgärder baserat på användarens val
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

    // Metod för att boka en plats
    public static void boka_plats(int[] bokning_nr, String[] bokning_namn, double[] priser, String[][] visa_platser, boolean loop) {
        // Läser in personens födelsedatum
        System.out.println("Ange ditt födelsedatum ÅÅÅÅMMDD:");
        int person_nr = 0;
        // Loop för att kontrollera att personnumret är korrekt
        while (person_nr < 10000000 || person_nr >= 100000000) {
            try {
                person_nr = tb.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Använd endast siffror.");
                tb.nextLine();
            }
        }
        // Läser in personens namn
        System.out.println("Ange ditt namn:");
        String namn = tb.nextLine();
        namn = tb.nextLine();
        // Visar tillgängliga platser
        System.out.println("Välj en plats att boka:");
        for (int i = 0; i < 5; i++) {
            System.out.println();
            for (int j = 0; j < 4; j++) {
                System.out.print("[");
                if (Integer.parseInt(visa_platser[i][j]) < 10) {
                    System.out.print("0" + visa_platser[i][j]);
                } else {
                    System.out.print(visa_platser[i][j]);
                }
                System.out.print("]");
                System.out.print(" ");
            }
        }
        System.out.println();
        int val = 0;
        // Loop för att kontrollera val av plats
        while (val > 20 || val == 0) {
            try {
                val = tb.nextInt();
                if (val > 20 || val <= 0) {
                    System.out.println("Skriv en siffra mellan 1-20.");
                    tb.nextLine();
                }
            } catch (InputMismatchException e) {
                System.out.println("Skriv en siffra mellan 1-20.");
                tb.nextLine();
            }
        }
        // Kontrollera om platsen är ledig och boka den
        if (bokning_nr[val - 1] != 0 || !bokning_namn[val - 1].equals("0")) {
            System.out.println("Platsen är redan upptagen:");
        } else {
            System.out.println("Du har bokat platsen " + val + "!");
            // Uppdatera platsstatus
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 4; j++) {
                    if (String.valueOf(val).equals(visa_platser[i][j])) {
                        visa_platser[i][j] = "X";
                        continue;
                    }
                }
            }
            // Spara bokningsinformation
            bokning_namn[val - 1] = namn;
            bokning_nr[val - 1] = person_nr;
            // Bestäm priset baserat på ålder
            if (person_nr > 20060502) {
                priser[val - 1] = PRIS_BARN;
            } else if (person_nr < 20060502) {
                priser[val - 1] = PRIS_VUXEN;
            }
        }
    }

    // Metod för att beräkna total vinst
    public static double beräkna_vinst(double[] priser, int index, double total_vinst) {
        // Basfall: index når noll
        if (index == 0) {
            return total_vinst;
        } else {
            // Beräkna total vinst rekursivt
            total_vinst += priser[index - 1];
            return beräkna_vinst(priser, index - 1, total_vinst);
        }
    }

    public static void visa_passagerare(String[] bokning_namn, int[] bokning_nr){
        // Loop för att skriva ut personnummer för passagerare
        for (int i = 0; i < bokning_nr.length; i++) {
            System.out.print(bokning_nr[i]);
            System.out.print(" ");
        }
        System.out.println("");
        // Loop för att skriva ut namn på passagerare
        for (int i = 0; i < bokning_namn.length; i++) {
            System.out.print(bokning_namn[i]);
            System.out.print(" ");
        }
        // Skapar en kopia av bokning_nr och bokning_namn för sortering
        int[] bokning_nr_sorterad = new int[20];
        for (int i = 0; i < bokning_nr_sorterad.length; i++) {
            bokning_nr_sorterad[i] = bokning_nr[i];
        }
        String[] bokning_namn_sorterad = new String[20];
        for (int i = 0; i < bokning_namn_sorterad.length; i++) {
            bokning_namn_sorterad[i] = bokning_namn[i];
        }
        // Sorterar passagerare baserat på personnummer (ålder)
        boolean byt = true;
        for (int i = 0; i < bokning_nr_sorterad.length-1; i++) {
            byt = false;
            for (int j = 0; j < bokning_nr_sorterad.length-1; j++) {
                if (bokning_nr_sorterad[j]>bokning_nr_sorterad[j+1]) {
                    // Byter plats på personnummer och namn i sorteringen
                    int sort = bokning_nr_sorterad[j];
                    bokning_nr_sorterad[j] = bokning_nr_sorterad[j+1];
                    bokning_nr_sorterad[j+1] = sort;
                    String sort2 = bokning_namn_sorterad[j];
                    bokning_namn_sorterad[j] = bokning_namn_sorterad[j+1];
                    bokning_namn_sorterad[j+1] = sort2;
                    byt = true;
                }
            }
            if (byt == false) {
                break;
            }
        }
        // Skriver ut sorterade personnummer för passagerare
        System.out.println("");
        for (int i = 0; i < bokning_nr_sorterad.length; i++) {
            System.out.print(bokning_nr_sorterad[i]);
            System.out.print(" ");
        }
        // Skriver ut sorterade namn på passagerare
        System.out.println("");
        for (int i = 0; i < bokning_namn_sorterad.length; i++) {
            System.out.print(bokning_namn_sorterad[i]);
            System.out.print(" ");
        }
        System.out.println("");
        // Skapar arrayer för att separera vuxna och barn
        int[] bokning_nr_vuxen = new int[20];
        int[] bokning_nr_barn = new int[20];
        String[] bokning_namn_vuxen = new String[20];
        // Initialiserar bokning_namn_vuxen med "0"
        for (int i = 0; i < bokning_namn_vuxen.length; i++) {
            bokning_namn_vuxen[i] = "0";
        }
        String[] bokning_namn_barn = new String[20];
        // Initialiserar bokning_namn_barn med "0"
        for (int i = 0; i < bokning_namn_barn.length; i++) {
            bokning_namn_barn[i] = "0";
        }
        // Separerar passagerare baserat på ålder (personnummer)
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
        // Skriver ut barnpassagerare
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
        // Skriver ut vuxenpassagerare
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
        // Skriver ut instruktioner för att hitta bokningen via personnummer eller namn
        System.out.println("Hitta din bokning via:");
        System.out.println("1. Personnummer");
        System.out.println("2. Namn");
        System.out.println("Välj en metod med dess nummer.");
        // Variabel för att lagra användarens val
        int val = 0;
        // Loop för att kontrollera användarens val och undvika ogiltiga värden
        while (val != 1 && val != 2) { 
            try {
                // Läser in användarens val
                val = tb.nextInt();
                // Kontrollerar om värdet är ogiltigt
                if (val > 2 || val <= 0) {
                    System.out.println("Välj en metod med dess nummer.");
                    tb.nextLine();
                }
            }
            // Hanterar undantag om användaren inte anger ett heltal
            catch (InputMismatchException e) {
                System.out.println("Välj en metod med dess nummer.");
                tb.nextLine();
            }
        }     
        // Variabel för att kontrollera om en bokning hittades
        boolean bokning = false;
        // Variabel för att lagra index för bokningen som ska ändras
        int ändra_bokning = 0;
        // Hanterar användarens val
        switch (val) {
            // Fall för att hitta bokning via personnummer
            case 1:
                System.out.println("Ange ditt personnummer:");
                int person_nr = 0;
                // Loop för att läsa in och kontrollera personnumret
                while (person_nr > 100000000 || person_nr<10000000) {
                    try {
                        person_nr = tb.nextInt();
                        if (person_nr > 100000000 || person_nr < 10000000) {
                            System.out.println("Ange ett 8-siffrigt personnummer ÅÅÅÅMMDD");
                            tb.nextLine();
                        }
                    }
                    // Hanterar undantag om användaren inte anger ett heltal
                    catch (InputMismatchException e) {
                        System.out.println("Ange ditt personnummer ÅÅÅÅMMDD:");
                        tb.nextLine();
                    }
                }
                tb.nextLine();
                // Loop för att söka igenom bokningar och hitta matchande personnummer
                for (int i = 0; i < bokning_nr.length; i++) {
                    if (bokning_nr[i] == person_nr) {
                        System.out.println("Du har bokat plats "+(i+1)+"!");
                        bokning = true;
                        ändra_bokning = i;
                        break;
                    }
                    else if (i == bokning_nr.length-1){
                        System.out.println("Kunde inte hitta bokning.");
                    }
                }
                break;
            // Fall för att hitta bokning via namn
            case 2:
                tb.nextLine();
                System.out.println("Ange ditt namn:");
                String namn = tb.nextLine();
                // Loop för att söka igenom bokningar och hitta matchande namn
                for (int i = 0; i < bokning_namn.length; i++) {
                    if (bokning_namn[i].equalsIgnoreCase(namn)) {
                        System.out.println("Du har bokat plats "+(i+1)+"!");
                        bokning = true;
                        ändra_bokning = i;
                        break;
                    }
                    else if (bokning_namn.length-1 == i){
                        System.out.println("Kunde inte hitta bokning.");
                    }
                }
                break;
        }
        // Om bokning hittades, fråga användaren om de vill ta bort bokningen
        if (bokning) {
            System.out.println("Vill du ta bort bokningen?");
            String svar = tb.nextLine();
            // Loop för att hantera användarens svar
            while (true) {
                if (svar.equalsIgnoreCase("ja")) {
                    // Tar bort bokningen genom att sätta namn och personnummer till "0"
                    bokning_namn[ändra_bokning] = "0";
                    bokning_nr[ändra_bokning] = 0;
                    break;
                }
                else if (svar.equalsIgnoreCase("nej")) {
                    break;
                }
                else{
                    System.out.println("Skriv ja eller nej.");
                    svar = tb.nextLine();
                }
            }
        }
        // Lägg till funktionalitet för att faktiskt ändra bokningen
    }
}