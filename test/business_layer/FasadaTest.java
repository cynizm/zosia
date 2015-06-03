package business_layer;

import business_layer.entities.Klient;
import business_layer.entities.Osoba;
import business_layer.entities.Projekt;
import business_layer.entities.Rola;
import business_layer.entities.Ryzyko;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.Date;

/**
 *
 * @author Mikolaj
 */
public class FasadaTest {

    public FasadaTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    public Klient klient(String dane_klient[]) {
        Klient klient = new Klient();
        klient.setNazwaFirmy(dane_klient[0]);
        klient.setNip(dane_klient[1]);
        klient.setUlica(dane_klient[2]);
        klient.setNrDomu(dane_klient[3]);
        klient.setNrLokalu(dane_klient[4]);
        klient.setMiejscowosc(dane_klient[5]);
        klient.setKodPocztowy(dane_klient[6]);

        return klient;
    }

    public List<Klient> klienci() {
        List<Klient> klienci = new ArrayList<>();
        String dane_klienta1[] = {"firma1", "1545183328", "ulica1", "1a", "12", "miejscowosc1", "11-111"};
        String dane_klienta2[] = {"firma2", "8195536504", "ulica2", "2b", "21", "miejscowosc2", "22-222"};
        klienci.add(klient(dane_klienta1));
        klienci.add(klient(dane_klienta2));

        return klienci;
    }

    public Osoba osoba(String dane_osoba[]) {
        Osoba osoba = new Osoba();
        osoba.setImie(dane_osoba[1]);
        osoba.setNazwisko(dane_osoba[2]);
        osoba.setEmail(dane_osoba[3]);
        osoba.setRola(Rola.fromString(dane_osoba[5]));

        return osoba;
    }

    public List<Osoba> osoby() {
        List<Osoba> osoby = new ArrayList<>();
        String dane_osoba1[] = {"1", "imie1", "nazwisko1", "email1", "1", "Kierownik projektu"};
        String dane_osoba2[] = {"1", "imie2", "nazwisko2", "email2", "2", "Programista"};
        osoby.add(osoba(dane_osoba1));
        osoby.add(osoba(dane_osoba2));

        return osoby;
    }
    
    public Projekt projekt(String data[]) {
        Projekt projekt = new Projekt();
        projekt.setNazwa(data[0]);
        projekt.setData_rozpoczecia(new Date());
        projekt.setData_zakonczenia(null);
        projekt.setStatus(Integer.parseInt(data[1]));

        return projekt;
    }

    public List<Projekt> projekty() {
        List<Projekt> projekty = new ArrayList<>();
        String dane_projekt1[] = {"Nazwa1", "1"};
        String dane_projekt2[] = {"Nazwa2", "2"};
        projekty.add(projekt(dane_projekt1));
        projekty.add(projekt(dane_projekt2));

        return projekty;
    }
    
    public Ryzyko ryzyko(String data[]) {
        Ryzyko ryzyko = new Ryzyko();
        
        ryzyko.setNazwa(data[0]);
        ryzyko.setOpis(data[1]);
        ryzyko.setPrwdWystapienia(Float.parseFloat(data[2]));
        ryzyko.setKosztWystapienia(Double.parseDouble(data[3]));
        ryzyko.setDataZgloszenia(new Date());
        ryzyko.setDataZakonczenia(null);
        ryzyko.setAktywne(true);
        
        return ryzyko;
    }
    

    /**
     * Test of getListaKlientow method, of class Fasada.
     */
    @Test
    public void testGetListaKlientow() {
        System.err.println("getListaKlientow");
        Fasada instance = new Fasada();
        List<Klient> expResult = klienci();
        instance.setListaKlientow(expResult);
        List<Klient> result = instance.getListaKlientow();
        assertEquals(expResult, result);

    }

    /**
     * Test of setListaKlientow method, of class Fasada.
     */
    @Test
    public void testSetListaKlientow() {
        System.err.println("setListaKlientow");
        List<Klient> klienci = klienci();
        Fasada instance = new Fasada();
        instance.setListaKlientow(klienci);

        assertEquals(klienci, instance.getListaKlientow());
    }

    /**
     * Test of getOsoby method, of class Fasada.
     */
    @Test
    public void testGetOsoby() {
        System.err.println("modelOsoby");
        Fasada instance = new Fasada();
        List<Osoba> expResult = osoby();
        instance.setOsoby(expResult);
        List<Osoba> result = instance.getOsoby();
        assertEquals(expResult, result);
    }

    /**
     * Test of setOsoby method, of class Fasada.
     */
    @Test
    public void testSetOsoby() {
        System.err.println("setOsoby");
        List<Osoba> osoby = osoby();
        Fasada instance = new Fasada();
        instance.setOsoby(osoby);
        List<Osoba> expResult = instance.getOsoby();
        assertEquals(osoby, expResult);
    }

    /**
     * Test of modelProjekty method, of class Fasada.
     */
    @Test
    public void testGetProjekty() {
        System.err.println("getProjekty");
        Fasada instance = new Fasada();
        
        List<Projekt> expResult = projekty();
        instance.setProjekty(expResult);
        List<Projekt> result = instance.getProjekty();
        assertEquals(expResult, result);
    }

    /**
     * Test of setProjekty method, of class Fasada.
     */
    @Test
    public void testSetProjekty() {
        System.err.println("setProjekty");
        List<Projekt> projekty = projekty();
        Fasada instance = new Fasada();
        instance.setProjekty(projekty);
        List<Projekt> result = instance.getProjekty();
        assertEquals(projekty, result);
    }

    /**
     * Test of addRisk method, of class Fasada.
     */
    @Test
    public void testAddRisk() {
        System.err.println("addRisk");
        String daneKierownika = "email1";
        String[] daneRyzyka = {"nazwa", "opis", "0.9", "1234"};
        List<Osoba> osoby = osoby();
        String dane_projekt1[] = {"Nazwa1", "1"};
        
        Fasada instance = new Fasada();
        instance.setOsoby(osoby);
        
        instance.addProjekt(daneKierownika, dane_projekt1);
        
        int expResult = 0;
        int result = instance.addRisk(daneKierownika, daneRyzyka);
        assertEquals(expResult, result);
    }

    /**
     * Test of modelRisks method, of class Fasada.
     */
    @Test
    public void testModelRisks() {
        System.err.println("getRisks");
        String daneKierownika = "email1";
        String[] daneRyzyka = {"nazwa", "opis", "0.9", "1234.0", (new Date()).toString(), "", "true"};
        List<Osoba> osoby = osoby();
        String dane_projekt1[] = {"Nazwa1", "1"};
        
        Fasada instance = new Fasada();
        instance.setOsoby(osoby);
        
        instance.addProjekt(daneKierownika, dane_projekt1);
        instance.addRisk(daneKierownika, daneRyzyka);
        
        Object[][] expResult = new Object[1][];
        expResult[0] = daneRyzyka;
        Object[][] result = instance.modelRisks();
        assertArrayEquals(expResult, result);

    }

    /**
     * Test of szukajOsobe method, of class Fasada.
     */
    @Test
    public void testSzukajOsobe_Osoba() {
        System.err.println("szukajOsobe");
        List<Osoba> osoby = osoby();
        Fasada instance = new Fasada();
        instance.setOsoby(osoby);
        Osoba expResult = osoby.get(0);
        Osoba result = instance.szukajOsobe(osoby.get(0));
        assertEquals(expResult, result);
    }

    /**
     * Test of szukajOsobe method, of class Fasada.
     */
    @Test
    public void testSzukajOsobe_StringArr() {
        System.err.println("szukajOsobe");
        String[] data = {"0", "email1"};
        List<Osoba> osoby = osoby();
        Fasada instance = new Fasada();
        instance.setOsoby(osoby);
        String expResult = osoby.get(0).toString();
        String result = instance.szukajOsobe(data);
        assertEquals(expResult, result);
    }

    /**
     * Test of dodajOsobe method, of class Fasada.
     */
    @Test
    public void testDodajOsobe() {
        System.err.println("dodajOsobe");
        String dane_osoba1[] = {"1", "imie1", "nazwisko1", "email1", "1", "Tester"};
        String dane_osoba2[] = {"1", "imie2", "nazwisko2", "email2", "2", "Programista"};
        Fasada instance = new Fasada();
        Osoba expResultForCorrectData = osoba(dane_osoba1);
        Object reultForCorrectData = instance.dodajOsobe(dane_osoba1);
        instance.dodajOsobe(dane_osoba2);
        assertEquals(expResultForCorrectData, reultForCorrectData);
        int expListLength = 2;
        assertEquals(expListLength, instance.getOsoby().size());
    }

    /**
     * Test of getTablicaZDanymiOsob method, of class Fasada.
     */
    @Test
    public void testGetTablicaZDanymiOsob() {
        System.err.println("modelTablicaZDanymiOsob");
        Fasada instance = new Fasada();
        instance.setOsoby(osoby());
        Object[][] expResult = new Object[instance.getOsoby().size()][];
        String[] dane1 = {"imie1", "nazwisko1", "email1", "1", "Kierownik projektu"};
        String[] dane2 = {"imie2", "nazwisko2", "email2", "2", "Programista"};
        expResult[0] = dane1;
        expResult[1] = dane2;
        Object[][] result = instance.modelTablicaZDanymiOsob();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of modelRole method, of class Fasada.
     */
    @Test
    public void testGetRole() {
        System.err.println("getRole");
        Fasada instance = new Fasada();
        String[] expResult = {"Kierownik projektu", "Analityk", "Programista", "Tester"};
        String[] result = instance.modelRole();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of dodajKlienta method, of class Fasada.
     */
    @Test
    public void testDodajKlienta() {
        System.err.println("dodajKlienta");
        String[] data = {"firma1", "1545183328", "ulica1", "1a", "12", "miejscowosc1", "11-111"};
        Fasada instance = new Fasada();
        Klient expResult = klient(data);
        instance.dodajKlienta(data);
        Klient result = instance.getKlienci().get(0);
        assertEquals(expResult, result);
    }

    /**
     * Test of szukajKlienta method, of class Fasada.
     */
    @Test
    public void testSzukajKlienta() {
        System.err.println("szukajKlienta");
        Fasada instance = new Fasada();
        instance.setListaKlientow(klienci());
        // Klient expResult = instance.getListaKlientow().get(0);
        //boolean result = instance.szukajKlienta(instance.getListaKlientow().get(0));
        //assertEquals(true, result);
    }

    /**
     * Test of pobierzTabliceKierownikow method, of class Fasada.
     */
    @Test
    public void testPobierzTabliceKierownikow() {
        System.err.println("pobierzTabliceKierownikow");
        Fasada instance = new Fasada();
        instance.setOsoby(osoby());
        Object[] expResult = new Object[1];
        expResult[0] = "email1";
        Object[] result = instance.pobierzTabliceKierownikow();
        assertArrayEquals(expResult, result);
    }

}
