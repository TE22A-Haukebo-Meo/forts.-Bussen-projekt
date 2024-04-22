import java.util.Scanner;

public class App {
    static Scanner tb = new Scanner(System.in);
    static final double PRIS_VUXEN = 299.90;
    static final double PRIS_BARN = 149.90;
    public static void main(String[] args) throws Exception {
        boolean loop = true;
        int index = 0;
        int[] bokning_nr = new int[20];
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
        String[][] visa_platser = new String[5][4];
        int x = 1;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                visa_platser[i][j] = String.valueOf(x);
                x++;
            }
        }
        double total_vinst = beräkna_vinst(priser, index, bokning_namn, bokning_nr, visa_platser, loop);
        while (loop) {
            System.out.println("Hej! Välj en tjänst från listan nedan!");
            visa_meny(priser, index, bokning_nr, bokning_namn, visa_platser, loop);
        }
    }

    public static void visa_meny(double[] priser, int index, int[] bokning_nr, String[] bokning_namn, String[][] visa_platser, boolean loop){
        int val = tb.nextInt();
        try {
            val = tb.nextInt();
        } 
        catch (Exception e) {
            System.out.println("Välj en tjänst med dess siffra.");
        }
        System.out.println("1. Boka en plats på bussen.");
        System.out.println("2. Hitta/ändra bokning.");
        System.out.println("3. Visa passagerare");
        System.out.println("4. Beräkna vinst av biljetter.");
        System.out.println("5. Avsluta.");
        while (val>5 || val==0) {
            System.out.println("Välj en tjänst med dess siffra.");
        }
        switch (val) {
            case 1:
                boka_plats(bokning_nr, bokning_namn, priser, visa_platser, loop);
                break;
            case 2:
                hitta_bokning(priser, index,bokning_nr, bokning_namn, visa_platser);
                break;
            case 3:
                visa_passagerare(bokning_namn, bokning_nr);
                break;
            case 4:
                beräkna_vinst(priser, index, bokning_namn, bokning_nr, visa_platser, loop);
                break;
            case 5:
                avsluta(loop);
                break;
        }
    }
    
    
    public static void boka_plats(int[] bokning_nr, String[] bokning_namn, double[] priser, String[][] visa_platser, boolean loop){
        System.out.println("Ange ditt födelsedatum ÅÅÅÅMMDD:");
        int person_nr = tb.nextInt();
        try {
            person_nr = tb.nextInt();
        } catch (Exception e) {
            System.out.println("Använd endast siffror.");
        }
        while (person_nr<10000000 || person_nr>=100000000) {
            System.out.println("Ange ditt födelsedatum ÅÅÅÅMMDD:");
            person_nr = tb.nextInt();
        }
        System.out.println("Ange ditt namn:");
        String namn = tb.nextLine();
        System.out.println("Välj en plats att boka:");
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.println(visa_platser[i][j]);
            }
        }
        int val = 0;
        while (val>20 || val==0) {
            System.out.println("Skriv en siffra mellan 1-20.");
            try {
                val = tb.nextInt();
                tb.nextLine();
            } catch (Exception e) {
                System.out.println("Skriv en siffra mellan 1-20.");
                val = 0;
                tb.nextLine();
            }
            if (bokning_nr[val] != 0 || bokning_namn[val] != "0") {
                System.out.println("Platsen är redan upptagen:");
                val=0;
                tb.nextLine();
            }
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                if (String.valueOf(val) == visa_platser[i][j]) {
                    visa_platser[i][j] = "X";
                    continue;
                }
            }
        }
        bokning_namn[val] = namn;
        bokning_nr[val] = person_nr;
        if (person_nr>20060502) {
            priser[val] = PRIS_BARN;
        }
        else if (person_nr<20060502) {
            priser[val] = PRIS_VUXEN;
        }
        visa_meny(priser, val, bokning_nr, bokning_namn, visa_platser, loop);
    }

    public static void avsluta(boolean loop){
        loop = false;
    }

    public static double beräkna_vinst(double[] priser, int index, String[] bokning_namn, int[] bokning_nr, String[][] visa_platser, boolean loop){
        if (priser.length > index) {
            double pris = priser[index];
            index++;
            return pris+beräkna_vinst(priser, index, bokning_namn, bokning_nr, visa_platser, loop);
        }
        else{
            return 0;
        }
        visa_meny(priser, index, bokning_nr, bokning_namn, visa_platser, loop);
    }

    public static void visa_passagerare(String[] bokning_namn, int[] bokning_nr){
        for (int i = 0; i < bokning_nr.length; i++) {
            if (bokning_nr[i] == 0) {
                break;
            }
            System.out.print(bokning_nr);
            System.out.print(bokning_namn);
        }
    }

    public static void hitta_bokning(double[] priser, int index, int[] bokning_nr, String[] bokning_namn, String[][] visa_platser){
        System.out.println("Hitta din bokning via:");
        System.out.println("1. Namn");
        System.out.println("2. Personnummer");
        int val = tb.nextInt();
        switch (val) {
            case 1:
                System.out.println("Ange ditt personnummer:");
                int person_nr = tb.nextInt();
                try {
                    person_nr = tb.nextInt();
                } catch (Exception e) {
                    System.out.println("Ange ditt personnummer ÅÅÅÅMMDD:");
                }
                for (int i = 0; i < bokning_nr.length; i++) {
                    if (bokning_nr[i] == person_nr) {
                        System.out.println("Du har bokat plats "+i+"!");
                    }
                    else{
                        System.out.println("Kunde inte hitta bokning.");
                    }
                }
                break;
            case 2:
                System.out.println("Ange ditt namn:");
                String namn = tb.nextLine();
                for (int i = 0; i < bokning_namn.length; i++) {
                    if (bokning_namn[i] == namn) {
                        System.out.println("Du har bokat plats "+i+"!");
                    }
                    else{
                        System.out.println("Kunde inte hitta bokning.");
                    }
                }
        }
    }
        
}
