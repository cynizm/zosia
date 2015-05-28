package business_layer;

import business_layer.entities.Klient;
import business_layer.entities.Osoba;
import business_layer.entities.Projekt;
import business_layer.entities.Rola;
import business_layer.entities.Ryzyko;
import business_layer.entities.StatusZadania;
import business_layer.entities.Zadanie;
import java.util.ArrayList;
import java.util.List;

public class Fasada {

    private ArrayList<Klient> klienci = new ArrayList<>();
    private ArrayList<Osoba> osoby = new ArrayList<>();
    private ArrayList<Projekt> projekty = new ArrayList<>();
    private final List<Zadanie> zadania = new ArrayList<>();

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
            dane_osoby[3] = next.getRola().getText();
            tablica_osob[i++] = dane_osoby;
        }
        return tablica_osob;
    }

    public synchronized Object[][] modelProjekty() {
        Object[][] tablica_projektow = new Object[projekty.size()][];
        int i = 0;
        for (Projekt next : projekty) {
            String[] dane_projektu = new String[5];
            dane_projektu[0] = next.getNazwa();
            dane_projektu[1] = next.getKierownik().getEmail();
            if(next.getKlient()!=null)
                dane_projektu[2] = next.getKlient().getNip();
            else
                dane_projektu[2]="brak";
            dane_projektu[3] = next.getData_rozpoczecia().toString();
            dane_projektu[4] = next.getData_zakonczenia() == null ? "" : next.getData_zakonczenia().toString();
            tablica_projektow[i++] = dane_projektu;
            //projekt[i++] = next.toString_();
        }
        return tablica_projektow;
    }
    
    public synchronized Object[][] modelTablicaZDanymiZadan(String kierownikProjektu) {
        Projekt p = szukajProjektPoKierowniku(kierownikProjektu);
        if (p != null) {
            List<Zadanie> lista = p.getZadania();
            Object[][] tablicaZadan = new Object[lista.size()][];
            int i = 0;
            for (Zadanie next : lista) {
                String[] daneZadania = new String[7];
                daneZadania[0] = Integer.toString(next.getIdentyfikator());
                daneZadania[1] = next.getNazwa();
                daneZadania[2] = next.getStatus().getText();
                daneZadania[3] = Integer.toString(next.getSzacowanyCzas());
                daneZadania[4] = Integer.toString(next.getCzasDoZakonczenia());
                daneZadania[5] = Integer.toString(next.getCzasRealizacji());
                daneZadania[6] = "Nie przydzielono";
                tablicaZadan[i++] = daneZadania;
            }
            return tablicaZadan;
        }
        return new Object[0][0];
    }
    
    public synchronized Object[][] modelTablicaZDanymiZespolu(String kierownikProjektu) {        
        Projekt p = szukajProjektPoKierowniku(kierownikProjektu);
        if (p != null) {
            List<Osoba> lista = p.getZespol();
            Object[][] tablicaOsob = new Object[lista.size()][];
            int i = 0;
            for (Osoba next : lista) {
                String[] daneOsoby = new String[7];
                daneOsoby[0] = next.getImie();
                daneOsoby[1] = next.getNazwisko();
                daneOsoby[2] = next.getEmail();
                daneOsoby[3] = next.getRola().getText();
                tablicaOsob[i++] = daneOsoby;
            }
            return tablicaOsob;
        }
        return new Object[0][0];
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
    
    protected Osoba szukajOsobe(String email) {
        int ile = osoby.size();
        for(int i=0; i<ile; i++)
            if(osoby.get(i).getEmail().equals(email))
                return osoby.get(i);
        return null;
    }

    public synchronized Object dodajOsobe(String data[]) {
        Factory factory = new Factory();
        Osoba nowa_osoba = factory.createOsoba(data);
        if (this.szukajOsobe(nowa_osoba) == null) {
            osoby.add(nowa_osoba);
            return nowa_osoba;
        }
        return null;
    }
    
    public String dodajOsobeDoProjektu(String kierownikProjektu, String dodawanaOsoba) {
        Projekt p = szukajProjektPoKierowniku(kierownikProjektu);
        if (p == null)
            return "Projekt nie istnieje!";
        else {
            try {
                String[] osoba = {"0", dodawanaOsoba};
                Factory fabryka = new Factory();
                Osoba o = szukajOsobe(fabryka.createOsoba(osoba));
                if (o != null)
                    return p.dodajOsobe(o);
                else
                    return "Osoba nie istnieje w systemie!";
            } catch (Exception e) {
                return "Nie można przypisać osoby! " + e.getMessage();
            }
        }
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
    
     public synchronized String[] modelStatusyZadan() {
        StatusZadania[] statusy = StatusZadania.values();
        String[] nazwyStatusow = new String[statusy.length];
        for (int i = 0; i < statusy.length; i++) 
            nazwyStatusow[i] = statusy[i].getText();
        return nazwyStatusow;
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
    
    public synchronized int przypiszKlientaDoProjektu(String NIP, String kierownik) {
        Factory fabryka = new Factory();
        Klient klient = fabryka.createKlient(NIP);
        Projekt projekt = fabryka.createProjekt(kierownik);
        Projekt znaleziony = searchProjekt(projekt);
        if( znaleziony!=null){
             znaleziony.setKlient(klient);       
             return 0;
        }
        return 1;
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
                .forEach((projekt) -> tablica.add(projekt.getKierownik().getEmail()));
        return tablica.toArray();
    }

    public Object[] pobierzTabliceKierownikow() {
        ArrayList<String> tablica = new ArrayList<>();
        osoby.stream()
                .filter((osoba) -> (osoba.getRola() == Rola.KIEROWNIK_PROJEKTU))
                .forEach((osoba) -> tablica.add(osoba.getEmail()));
        return tablica.toArray();
    }
    
     protected Zadanie szukajZadanie(String id) {
        int ile = zadania.size();
        for (int i=0; i<ile; i++)
            if (String.valueOf(zadania.get(i).getIdentyfikator()).equals(id))
                return zadania.get(i);
        return null;
    }
     
     public String dodajZadanieDoProjektu(String kierownikProjektu, String[] zadanie) {
        Projekt p = szukajProjektPoKierowniku(kierownikProjektu);
        if (p == null)
            return "Projekt nie istnieje!";
        else {
            try {
                Factory fabryka = new Factory();
                Zadanie z = fabryka.createZadanie(zadanie);
                return p.dodajZadanie(z);
            } catch (Exception e) {
                return "Nie można utworzyć zadania! " + e.getMessage();
            }
        }
    }
     
     protected Projekt szukajProjektPoKierowniku(String kierownikProjektu) {
        Factory fabryka = new Factory();
        Osoba kierownik = szukajKierownika(kierownikProjektu);
        String[] data = {"","0"};
        Projekt temp = fabryka.createProjekt(data);
        temp.setKierownik(kierownik);
        return this.searchProjekt(temp);
    }
    
     public Object[] pobierzTabliceKlientow() {
        ArrayList<String> tablica = new ArrayList<>();
        klienci.stream()
                .forEach((klient) -> tablica.add(klient.getNip()));
        return tablica.toArray();
    }
    
    public Object[] pobierzDostepnychKierownikow() {
        ArrayList<String> tablica = new ArrayList<>();
        osoby.stream()
                .filter((osoba) -> (osoba.getRola() == Rola.KIEROWNIK_PROJEKTU))
                .filter((osoba) -> (osoba.getProjekt() == null))
                .forEach((osoba) -> tablica.add(osoba.getEmail()));
        return tablica.toArray();
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
    }
}
