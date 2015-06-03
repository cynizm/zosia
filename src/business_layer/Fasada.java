package business_layer;

import business_layer.entities.Klient;
import business_layer.entities.Osoba;
import business_layer.entities.Projekt;
import business_layer.entities.Rola;
import business_layer.entities.Ryzyko;
import business_layer.entities.Sprint;
import business_layer.entities.StanSprintu;
import business_layer.entities.StatusSprintu;
import business_layer.entities.StatusZadania;
import business_layer.entities.Zadanie;
import java.util.ArrayList;
import java.util.List;

public class Fasada {

    private List<Klient> klienci = new ArrayList<>();
    private List<Osoba> osoby = new ArrayList<>();
    private List<Projekt> projekty = new ArrayList<>();
    private final List<Zadanie> zadania = new ArrayList<>();

    public Fasada() {
        //tymczasowo dodajemy projekt

    }

    public List<Klient> getListaKlientow() {
        return klienci;
    }

    public void setListaKlientow(List<Klient> klienci) {
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
        Object[][] tablicaOsob = new Object[osoby.size()][];
        int i = 0;
        for (Osoba next : osoby) {
            String[] daneOsoby = new String[5];
	    daneOsoby[0] = next.getImie();
	    daneOsoby[1] = next.getNazwisko();
	    daneOsoby[2] = next.getEmail();
	    if (next.getProjekt() == null) {
		daneOsoby[3] = "Nie przydzielono";
	    } else {
		daneOsoby[3] = next.getProjekt().getNazwa();
	    }
	    daneOsoby[4] = next.getRola().getText();
	    tablicaOsob[i++] = daneOsoby;
	}
	return tablicaOsob;
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

    public void setOsoby(List<Osoba> osoby) {
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
        List<Ryzyko> ryzyka = new ArrayList<>();
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
        List<Ryzyko> ryzyka = new ArrayList<>();
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
    
    public synchronized String[] modelStatusSprintu() {
        StatusSprintu [] status = StatusSprintu.values();
        String[] nazwy_statusow = new String[status.length];
        for (int i = 0; i < status.length; i++) {
            nazwy_statusow[i] = status[i].getText();
        }
        return nazwy_statusow;
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
                throw new RuntimeException("Nie można przypisać osoby! " + e.getMessage());
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
            if (osoba.sprawdz_id_Kierownika()) {
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

    public synchronized String[] dodajKlienta(String data[]) {
        Factory fabryka = new Factory();
        Klient klient = fabryka.createKlient(data);
        if (szukajKlienta(klient) == null) {
            klienci.add(klient);
            return modelKlienci();
        }
        return new String[0];
    }
    
    public synchronized int przypiszKlientaDoProjektu(String NIP, String kierownik) {
        Factory fabryka = new Factory();
        Klient klient = fabryka.createKlient(NIP), szuk_klient;
        szuk_klient = this.szukajKlienta(klient);
        if (szuk_klient != null) {
	    Projekt projekt = fabryka.createProjekt(kierownik);
	    Projekt znaleziony = searchProjekt(projekt);
	    if (znaleziony != null) {
		znaleziony.setKlient(szuk_klient);
		return 0;
	    }
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

    public synchronized Klient szukajKlienta(Klient klient) {
        int k = klienci.indexOf(klient);
	if (-1 != k) {
	    return klienci.get(k);
	}
	return null;

    }

    public List<Klient> getKlienci() {
        return klienci;
    }

    public void setKlienci(List<Klient> klienci) {
        this.klienci = klienci;
    }

    public List<Projekt> getProjekty() {
        return projekty;
    }

    public List<Osoba> getOsoby() {
        return osoby;
    }

    public void setProjekty(List<Projekt> projekty) {
        this.projekty = projekty;
    }

    public Object[] pobierzTabliceProjektow() {
        List<String> tablica = new ArrayList<>();
        projekty.stream()
                .forEach(projekt -> tablica.add(projekt.getKierownik().getEmail()));
        return tablica.toArray();
    }

    public Object[] pobierzTabliceKierownikow() {
        List<String> tablica = new ArrayList<>();
        osoby.stream()
                .filter(osoba -> osoba.getRola() == Rola.KIEROWNIK_PROJEKTU)
                .forEach(osoba -> tablica.add(osoba.getEmail()));
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
                throw new RuntimeException("Nie można utworzyć zadania! " + e.getMessage());
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
        List<String> tablica = new ArrayList<>();
        klienci.stream()
                .forEach(klient -> tablica.add(klient.getNip()));
        return tablica.toArray();
    }
    
    public Object[] pobierzDostepnychKierownikow() {
        List<String> tablica = new ArrayList<>();
        osoby.stream()
                .filter(osoba -> osoba.getRola() == Rola.KIEROWNIK_PROJEKTU)
                .filter(osoba -> osoba.getProjekt() == null)
                .forEach(osoba -> tablica.add(osoba.getEmail()));
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
        List<Sprint> sprinty = new ArrayList<>();
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
        List<Sprint> sprinty = new ArrayList<>();
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
                List<StanSprintu> stany = s.getStanySprintu();
                Object matrix[][] = new Object[stany.size()][];
                int i = 0;
                for (StanSprintu stan : stany) {
                    matrix[i++] = stan.toStringArray();
                }
                return matrix;
            }
        }
        return new Object[0][0]; 
    }
    
    
    public static void main(String t[]) {
        // Do nothing because of X and Y.
    }
}
