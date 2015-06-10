package business_layer;

import business_layer.entities.Klient;
import business_layer.entities.Matrix;
import business_layer.entities.Osoba;
import business_layer.entities.Projekt;
import business_layer.entities.Rola;
import business_layer.entities.Ryzyko;
import business_layer.entities.Sprint;
import business_layer.entities.StanSprintu;
import business_layer.entities.StatusSprintu;
import business_layer.entities.StatusZadania;
import business_layer.entities.Utility;
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
	List<Utility> listaosob = new ArrayList();
	listaosob.addAll(osoby);
	return Matrix.matrix(listaosob);

    }

    public synchronized Object[][] modelProjekty() {
       	List<Utility> listaprojektow = new ArrayList();
	listaprojektow.addAll(projekty);
	return Matrix.matrix(listaprojektow);

    }
    
    public synchronized Object[][] modelTablicaZDanymiZadan(String kierownikProjektu) {
        Projekt p = szukajProjektPoKierowniku(kierownikProjektu);
	if (p != null) {
	    List<Utility> lista = p.getZadania_();
	   return Matrix.matrix(lista);   
	}
	return new Object[0][0];
    }

    
    public synchronized Object[][] modelTablicaZDanymiZespolu(String kierownikProjektu) {        
        Projekt p = szukajProjektPoKierowniku(kierownikProjektu);
	if (p != null) {
	    List<Utility> lista = p.getZespol_();
	    return Matrix.matrix(lista);
	}
	return new Object[0][0];

    }

    public int addProjekt(String Dane_kierownika, String Dane_projektu[]) {
        int kodBledu = 0;
        Osoba kierownik = szukajKierownika(Dane_kierownika);
        if (kierownik != null) {
            Factory fabryka = new Factory();
            Projekt help1 = fabryka.createProjekt(Dane_projektu);
            help1.setKierownik(kierownik);
            Projekt help2 = Matrix.find(projekty, help1); 
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
        List<Utility> ryzyka = new ArrayList<>();
	for (Projekt projekt : projekty) {
	    ryzyka.addAll(projekt.getRyzyka());
	}
	 return Matrix.matrix(ryzyka);
    }


    public Object[][] modelRisks(String project_kierownik) {
        List<Utility> ryzyka = new ArrayList<>();
	for (Projekt projekt : projekty) {
	    if (project_kierownik.equals(projekt.kierownikEmail())) {
		ryzyka.addAll(projekt.getRyzyka());
	    }
	}
	return Matrix.matrix(ryzyka);
    }

    public synchronized String[] modelStatusSprintu() {
        StatusSprintu [] status = StatusSprintu.values();
        String[] nazwy_statusow = new String[status.length];
        for (int i = 0; i < status.length; i++) {
            nazwy_statusow[i] = status[i].getText();
        }
        return nazwy_statusow;
    }

    public synchronized String szukajOsobe(String data[]) {
        Factory factory = new Factory();
        Osoba znaleziona_osoba = Matrix.find(osoby, factory.createOsoba(data));
        if (znaleziona_osoba != null) {
            return znaleziona_osoba.toString();
        }
        return null;
    }

    public synchronized String dodajOsobe(String data[]) {
	Factory factory = new Factory();
	Osoba nowa_osoba = factory.createOsoba(data);
	if (Matrix.find(osoby, nowa_osoba) == null) {
	    osoby.add(nowa_osoba);
	    return "";
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
                Osoba o = Matrix.find(osoby,fabryka.createOsoba(osoba));
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
    
      protected Zadanie szukajZadanie(String id) {
	Zadanie zadanie = new Zadanie();
	zadanie.setIdentyfikator(Integer.parseInt(id));
	return Matrix.find(zadania, zadanie);
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
        if (Matrix.find(klienci, klient) == null) {
            klienci.add(klient);
            return modelKlienci();
        }
        return new String[0];
    }
    
    public synchronized int przypiszKlientaDoProjektu(String NIP, String kierownik) {
        Factory fabryka = new Factory();
        Klient klient = fabryka.createKlient(NIP), szuk_klient;
        szuk_klient = Matrix.find(klienci, klient);
        if (szuk_klient != null) {
	    Projekt projekt = fabryka.createProjekt(kierownik);
            Projekt znaleziony = Matrix.find(projekty, projekt);
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
        return Matrix.find(projekty, temp);
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
    
    public synchronized Object[][] modelSprinty() {
	List<Utility> sprinty = new ArrayList<>();
	for (Projekt projekt : projekty) {
	    sprinty.addAll(projekt.getSprinty());
	}
	return Matrix.matrix(sprinty);
    }


    public Object[][] modelSprinty(String project_kierownik) {
        List<Utility> sprinty = new ArrayList<>();
	for (Projekt projekt : projekty) {
	    if (project_kierownik.equals(projekt.kierownikEmail())) {
		sprinty.addAll(projekt.getSprinty());
	    }
	}
     return Matrix.matrix(sprinty);
    }

    
    public Object[][] modelStanySprintu(String [] dataSprint, String mailKierownika) {
        Osoba kierownik = szukajKierownika(mailKierownika);
        if(kierownik!=null) {
            Factory factory = new Factory();
            Projekt projekt = Matrix.find(projekty, kierownik.getProjekt());
            Sprint s = projekt.findSprint(factory.createSprint(dataSprint));
            if(s != null) {
                List<Utility> stany = s.getStanySprintu_();
		return Matrix.matrix(stany);
	    }
	}
	return new Object[0][0];
    }

    
    
    public static void main(String t[]) {
        // Do nothing because of X and Y.
    }
}
