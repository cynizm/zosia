package business_layer;

import business_layer.entities.Klient;
import business_layer.entities.Osoba;
import business_layer.entities.Projekt;
import business_layer.entities.Rola;
import business_layer.entities.Ryzyko;
import business_layer.entities.Sprint;
import business_layer.entities.StanSprintu;
import business_layer.entities.StatusSprintu;
import java.util.ArrayList;


public class Fasada {

    private ArrayList<Klient> klienci = new ArrayList<>();
    private ArrayList<Osoba> osoby = new ArrayList<>();
    private ArrayList<Projekt> projekty = new ArrayList<>();

    public Fasada() {
        //tymczasowo dodajemy projekt

    }

    public ArrayList<Klient> getListaKlientow() {
        return klienci;
    }

    public void setListaKlientow(ArrayList<Klient> klienci) {
        this.klienci = klienci;
    }

    public Object[] modelOsoby() {
        Object[] os = new Object[osoby.size()];
        int i = 0;
        for (Osoba next : osoby) {
            os[i++] = next.toString();
        }
        return os;
    }

    public synchronized Object[][] modelTablicaZDanymiOsob() {
        Object[][] tablica_osob = new Object[osoby.size()][];
        int i = 0;
        for (Osoba next : osoby) {
            String[] dane_osoby = new String[5];
            dane_osoby[0] = next.getImie();
            dane_osoby[1] = next.getNazwisko();
            dane_osoby[2] = next.getEmail();
            dane_osoby[3] = next.getIdProjektu().toString();
            dane_osoby[4] = next.getRola().getText();
            tablica_osob[i++] = dane_osoby;
        }
        return tablica_osob;
    }

    public synchronized Object[][] modelProjekty() {
        Object[][] tablica_projektow = new Object[projekty.size()][];
        int i = 0;
        for (Projekt next : projekty) {
            String[] dane_projektu = new String[4];

            dane_projektu[0] = next.getNazwa();
            dane_projektu[1] = next.getKierownik().getEmail();
            dane_projektu[2] = next.getData_rozpoczecia().toString();
            dane_projektu[3] = next.getData_zakonczenia() == null ? "" : next.getData_zakonczenia().toString();
            tablica_projektow[i++] = dane_projektu;
            //projekt[i++] = next.toString_();
        }
        return tablica_projektow;
    }

    public synchronized Projekt searchProjekt(Projekt projekt) {
        int idx = projekty.indexOf(projekt);
        if (idx != -1) {
            return projekty.get(idx);
        }
        return null;
    }

    public int addProjekt(String Dane_kierownika, String Dane_projektu[]) {
        int kodBledu = 0;
        Osoba kierownik = szukajKierownika(Dane_kierownika);
        if (kierownik != null) {
            Factory fabryka = new Factory();
            Projekt help1 = fabryka.createProjekt(Dane_projektu);
            help1.setKierownik(kierownik);
            Projekt help2 = searchProjekt(help1);
            if (help2 == null) {
                kierownik.setProjekt(help1);
                projekty.add(help1);
            } else {
                kodBledu = 2;
            }
        } else {
            kodBledu = 1;
        }
        return kodBledu;
    }

    public void setOsoby(ArrayList<Osoba> osoby) {
        this.osoby = osoby;
    }

    public int addRisk(String daneKierownika, String daneRyzyka[]) {
        int kodBledu = 0;
        Osoba kierownik = szukajKierownika(daneKierownika);
        if (kierownik != null) {
            Factory factory = new Factory();
            Projekt projekt = kierownik.getProjekt();
            if (projekt != null) {
                Ryzyko nowe = factory.createRyzyko(daneRyzyka);
                kodBledu = projekt.addRyzyko(nowe);

            } else {
                kodBledu = 2;
            }
        } else {
            kodBledu = 1;
        }
        return kodBledu;
    }

    public Object[][] modelRisks() {
        ArrayList<Ryzyko> ryzyka = new ArrayList<>();
        for (Projekt projekt : projekty) {
            ryzyka.addAll(projekt.getRyzyka());
        }

        Object matrix[][] = new Object[ryzyka.size()][];
        int i = 0;
        for (Ryzyko ryzyko : ryzyka) {
            matrix[i++] = ryzyko.toStringArray();
        }
        return matrix;
    }

    public Object[][] modelRisks(String project_kierownik) {
        ArrayList<Ryzyko> ryzyka = new ArrayList<>();
        for (Projekt projekt : projekty) {
            if (project_kierownik.equals(projekt.kierownikEmail())) {
                ryzyka.addAll(projekt.getRyzyka());
            }
        }

        Object matrix[][] = new Object[ryzyka.size()][];
        int i = 0;
        for (Ryzyko ryzyko : ryzyka) {
            matrix[i++] = ryzyko.toStringArray();
        }
        return matrix;
    }

    public synchronized Osoba szukajOsobe(Osoba osoba) {
        int idx = osoby.indexOf(osoba);
        if (idx != -1) {
            return osoby.get(idx);
        }
        return null;
    }

    public synchronized String szukajOsobe(String data[]) {
        Factory factory = new Factory();
        Osoba znaleziona_osoba = szukajOsobe(factory.createOsoba(data));
        if (znaleziona_osoba != null) {
            return znaleziona_osoba.toString();
        }
        return null;
    }

    public synchronized Osoba dodajOsobe(String data[]) {
        Factory factory = new Factory();
        Osoba nowa_osoba = factory.createOsoba(data);
        if (this.szukajOsobe(nowa_osoba) == null) {
            osoby.add(nowa_osoba);
            return nowa_osoba;
        }
        return null;
    }

    private Osoba szukajKierownika(String email) {
        for (Osoba o : osoby) {
            if (o.sprawdz_email_Kierownika(email)) {
                return o;
            }
        }
        return null;
    }

    public String sprawdzKierownika(String dane) {
        for (Osoba osoba : osoby) {
            if (osoba.sprawdz_id_Kierownika(dane)) {
                return osoba.toString();
            }
        }
        return null;
    }

    public synchronized String[] modelRole() {
        Rola[] role = Rola.values();
        String[] nazwy_rol = new String[role.length];
        for (int i = 0; i < role.length; i++) {
            nazwy_rol[i] = role[i].getText();
        }
        return nazwy_rol;
    }
    
    public synchronized String[] modelStatusSprintu() {
        StatusSprintu [] status = StatusSprintu.values();
        String[] nazwy_statusow = new String[status.length];
        for (int i = 0; i < status.length; i++) {
            nazwy_statusow[i] = status[i].getText();
        }
        return nazwy_statusow;
    }

    public synchronized void wyswietlOsoby() {
        Object[][] help_list = modelTablicaZDanymiOsob();
        for (Object[] rekord : help_list) {
            for (int j = 0; j < 5; j++) {
                System.out.print(rekord[j] + " ");
            }
            System.out.println();
        }
    }

    public synchronized String[] dodajKlienta(String data[]) {
        Factory fabryka = new Factory();
        Klient klient = fabryka.createKlient(data);
        if (szukajKlienta(klient) == false) {// jezeli nie ma takiego klienta
            klienci.add(klient);
            return modelKlienci();
        }
        return null;
    }

    public synchronized String[] modelKlienci() {
        int size = klienci.size();
        String[] tablica = new String[size];
        for (int i = 0; i < size; i++) {
            tablica[i] = klienci.get(i).toString();
        }
        return tablica;
    }

    // do debugowania
    public synchronized void wyswietlListeKlientow() {
        for (int i = 0; i < klienci.size(); i++) {
            System.out.print(klienci.get(i).toString());
        }
    }

    public synchronized boolean szukajKlienta(Klient klient) {
        int k = klienci.indexOf(klient);
        return (-1 != k);
    }

    private void showRyzyka() {
        for (Projekt projekt : projekty) {
            for (Ryzyko ryzyko : projekt.getRyzyka()) {
                System.out.println(ryzyko.toString());
            }
        }
    }

    public ArrayList<Klient> getKlienci() {
        return klienci;
    }

    public void setKlienci(ArrayList<Klient> klienci) {
        this.klienci = klienci;
    }

    public ArrayList<Projekt> getProjekty() {
        return projekty;
    }

    public ArrayList<Osoba> getOsoby() {
        return osoby;
    }

    public void setProjekty(ArrayList<Projekt> projekty) {
        this.projekty = projekty;
    }

    public Object[] pobierzTabliceProjektow() {
        ArrayList<String> tablica = new ArrayList<>();
        projekty.stream()
                .forEach((projekt) -> tablica.add(projekt.getNazwa()));
        return tablica.toArray();
    }

    public Object[] pobierzTabliceKierownikow() {
        ArrayList<String> tablica = new ArrayList<>();
        osoby.stream()
                .filter((osoba) -> (osoba.getRola() == Rola.KIEROWNIK_PROJEKTU))
                .forEach((osoba) -> tablica.add(osoba.getEmail()));
        return tablica.toArray();
    }
    
    public synchronized Sprint szukajSprint(String dataSprint[], String dataKierownik) {
        Factory factory = new Factory();
        Osoba kierownik = szukajKierownika(dataKierownik);
        if (kierownik != null) {
            Projekt projekt = kierownik.getProjekt();
            return projekt.findSprint(factory.createSprint(dataSprint));
        } else {
            return null;
        }
    }
    
    public synchronized int addSprint(String dataSprint[], String dataKierownik) {
        int kodBledu = 0;
        Osoba kierownik = szukajKierownika(dataKierownik);
        if (kierownik != null) {
            Factory factory = new Factory();
            Projekt projekt = kierownik.getProjekt();
            if (projekt != null) {
                Sprint nowy = factory.createSprint(dataSprint);
                kodBledu = projekt.addSprint(nowy);
            } else {
                kodBledu = 2;
            }
        } else {
            kodBledu = 1;
        }
        return kodBledu;
    }
    
     public synchronized int addStanSprintu(String dataStanSprintu[],String dataSprint[], String dataKierownik) {
        int kodBledu = 0;
        Osoba kierownik = szukajKierownika(dataKierownik);
        if (kierownik != null) {
            Factory factory = new Factory();
            Projekt projekt = kierownik.getProjekt();
            if (projekt != null) {
                return projekt.addStanSprintu(factory.createSprint(dataSprint), factory.createStanSprintu(dataStanSprintu));
            } else {
                kodBledu = 2;
            }
        } else {
            kodBledu = 1;
        }
        return kodBledu;
    }
    
    public synchronized StanSprintu szukajStanSprintu(String dataStanSprintu[],String dataSprint[], String dataKierownik) {
        StanSprintu stan = null;
        Factory factory = new Factory();
        Osoba kierownik = szukajKierownika(dataKierownik);
        if (kierownik != null) {
            Projekt projekt = kierownik.getProjekt();
            if (projekt != null) {
               stan = projekt.findStanSprintu(factory.createSprint(dataSprint), factory.createStanSprintu(dataStanSprintu));
            }
        }
        return stan;
    }
    
    
    public Object[][] modelSprinty() {
        ArrayList<Sprint> sprinty = new ArrayList<>();
        for (Projekt projekt : projekty) {
                sprinty.addAll(projekt.getSprinty());
        }

        Object matrix[][] = new Object[sprinty.size()][];
        int i = 0;
        for (Sprint sprint : sprinty) {
            matrix[i++] = sprint.toStringArray();
        }
        return matrix;
    }

    public Object[][] modelSprinty(String project_kierownik) {
        ArrayList<Sprint> sprinty = new ArrayList<>();
        for (Projekt projekt : projekty) {
            if (project_kierownik.equals(projekt.kierownikEmail())) {
                sprinty.addAll(projekt.getSprinty());
            }
        }

        Object matrix[][] = new Object[sprinty.size()][];
        int i = 0;
        for (Sprint sprint : sprinty) {
            matrix[i++] = sprint.toStringArray();
        }
        return matrix;
    }
    
    public Object[][] modelStanySprintu(String [] dataSprint, String mailKierownika) {
        Osoba kierownik = szukajKierownika(mailKierownika);
        if(kierownik!=null) {
            Factory factory = new Factory();
            Projekt projekt = this.searchProjekt(kierownik.getProjekt());
            Sprint s = projekt.findSprint(factory.createSprint(dataSprint));
            if(s != null) {
                ArrayList<StanSprintu> stany = s.getStanySprintu();
                Object matrix[][] = new Object[stany.size()][];
                int i = 0;
                for (StanSprintu stan : stany) {
                    matrix[i++] = stan.toStringArray();
                }
                return matrix;
            }
        }
        return null; 
    }
    
    public static void main(String t[]) {

        Fasada fasada = new Fasada();

        //....Testowanie klientow............................
        String k1[] = {"0", "Firma1", "Nip1", "Ulica1", "Nrdomu1", "Nrlokalu1",
            "Miejscowosc1", "Kodpocztowy1"};
        String k2[] = {"0", "Firma2", "Nip2", "Ulica2", "Nrdomu2", "Nrlokalu2",
            "Miejscowosc2", "Kodpocztowy2"};
        String k3[] = {"0", "Firma3", "Nip3", "Ulica3", "Nrdomu3", "Nrlokalu3",
            "Miejscowosc3", "Kodpocztowy3"};
        fasada.dodajKlienta(k1);
        fasada.dodajKlienta(k2);
        fasada.dodajKlienta(k3);

        String k4[] = {"0", "Firma4", "Nip1", "Ulica4", "Nrdomu4", "Nrlokalu4",
            "Miejscowosc4", "Kodpocztowy4"}; // ten sam nip co byl wczesniej
        fasada.dodajKlienta(k4); // k4 nie zostanie dodany
        fasada.wyswietlListeKlientow();

        //......Testowanie osob...............................
        String t1[] = {"1", "Piotr", "Osipa", "piotr.osipa@pwr.edu.pl",
            "0", "Kierownik projektu"};
        String t2[] = {"1", "Łukasz", "Jabłoński", "lukasz.jablonski@pwr.edu.pl",
            "0", "Programista"};
        String t3[] = {"1", "Marcin", "Słowiński", "marcin.slowinski@pwr.edu.pl",
            "0", "Programista"};
        String t4[] = {"1", "Paweł", "Andziul", "pawel.andziul@pwr.edu.pl",
            "0", "Analityk"};
        String t5[] = {"1", "Michał", "Wilner", "michal.wilner@pwr.edu.pl",
            "0", "Tester"};
        String t6[] = {"1", "Paweł", "Szpak", "pawel.szpak@pwr.edu.pl",
            "0", "Tester"};
        
        fasada.dodajOsobe(t1);
        fasada.dodajOsobe(t2);
        fasada.dodajOsobe(t3);
        fasada.dodajOsobe(t4);
        fasada.dodajOsobe(t5);
        fasada.dodajOsobe(t6);
        fasada.dodajOsobe(t3); //jeszcze raz ta sama osoba
        fasada.dodajOsobe(t1); //jeszcze raz ta sama osoba

        System.out.println("====== struktura zawierajaca osoby ======");
        System.out.println(fasada.modelOsoby().toString());
        System.out.println("========== podglad tabeli osob ==========");
        fasada.wyswietlOsoby();
        System.out.println("=========== wyszukiwanie osob ===========");
        String s1[] = {"0", "pawel.szpak@pwr.edu.pl"};
        String s2[] = {"0", "adam.szpak@pwr.edu.pl"};
        String s3[] = {"0", "piotr.osipa@pwr.edu.pl"};
        System.out.println("Szukanie osoby o adresie e-mail: " + s1[1]);
        System.out.println(fasada.szukajOsobe(s1));
        System.out.println("Szukanie osoby o adresie e-mail: " + s2[1]);
        System.out.println(fasada.szukajOsobe(s2));
        System.out.println("Szukanie osoby o adresie e-mail: " + s3[1]);
        System.out.println(fasada.szukajOsobe(s3));

        //.....testowanie projektu................................
        Projekt p = new Projekt();
        String o = fasada.szukajOsobe(t1);
		//o.setProjekt(p);
        //fasada.modelProjekty().add(p);

        //....Testowanie ryzyka ......................................
        String r1[] = {"Ryzyko nr1", "Opis nr1", "10", "110.21"};
        String r2[] = {"Ryzyko nr2", "Opis nr2", "20", "120.22"};
        String r3[] = {"Ryzyko nr3", "Opis nr3", "30", "130.23"};
        String daneKierownika = "piotr.osipa@pwr.edu.pl";
        fasada.addRisk(daneKierownika, r1);
        fasada.addRisk(daneKierownika, r2);
        fasada.addRisk(daneKierownika, r3);

        fasada.showRyzyka();
        int result = fasada.addRisk(daneKierownika, r1);
        if (result == 3) {
            System.out.println("Takie ryzyko już istnieje!");
        } else if (result == 2) {
            System.out.println("Kierownik nie ma zadnego projektu!");
        } else if (result == 1) {
            System.out.println("Wybrana osoba nie jest kierownikiem!");
        }
                //....Testowanie Sprintu ......................................
        System.out.println("Test sprintu");
        String p1[] = {"Projekt","3"};
        String p2[] = {"Projekt1","3"};
        String sp1[] = {"1","Nierozpoczęty"};
        String sp2[] = {"2","Nierozpoczęty"};
        String sp3[] = {"3","Nierozpoczęty"};
        String st1[] = {"1","1","1","2","2","2"};
        String st2[] = {"2","1","1","2","2","2"};
        String st3[] = {"3","1","1","2","2","2"};
        
        fasada.addProjekt(daneKierownika, p1);
        fasada.addProjekt(daneKierownika, p2);
        fasada.addSprint(sp1, daneKierownika);
        fasada.addSprint(sp2, daneKierownika);
        fasada.addSprint(sp3, daneKierownika);
        fasada.addStanSprintu(st1, sp1, daneKierownika);
        fasada.addStanSprintu(st2, sp2, daneKierownika);
        fasada.addStanSprintu(st3, sp3, daneKierownika);
        
        Sprint res = fasada.szukajSprint(sp1, daneKierownika);
        res.toStringArray();
        System.out.println(res.toString());
        res = fasada.szukajSprint(sp2, daneKierownika);
        System.out.println(res.toString());
        
        StanSprintu rst = fasada.szukajStanSprintu(st1,sp1,daneKierownika);
        System.out.println(rst.toString());
        
        rst = fasada.szukajStanSprintu(st2,sp2,daneKierownika);
        System.out.println(rst.toString());
    }
}