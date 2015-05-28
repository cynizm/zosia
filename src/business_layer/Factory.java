package business_layer;
//test
import business_layer.entities.Klient;
import business_layer.entities.Osoba;
import business_layer.entities.Rola;
import business_layer.entities.Ryzyko;
import business_layer.entities.Projekt;
import java.util.Date;

public class Factory {
    
public Factory() {
    }
    
    public Ryzyko createRyzyko (String data[]){
        
        Ryzyko ryzyko = new Ryzyko();
        if(data.length == 2) { // obiekt do wyszukania
        
           ryzyko.setNazwa(data[0]); 
           ryzyko.setOpis(data[1]);
        }
	else { // obiekt do zapamietania
            ryzyko.setNazwa(data[0]);
            ryzyko.setOpis(data[1]);
            ryzyko.setPrwdWystapienia(Float.parseFloat(data[2]));
            ryzyko.setKosztWystapienia(Double.parseDouble(data[3]));
            ryzyko.setDataZgloszenia(new Date());
            ryzyko.setDataZakonczenia(null);
            ryzyko.setAktywne(true);
        }
        return ryzyko;
    }
    
    public Klient createKlient(String data[]) {
        Klient klient = null;
        klient = new Klient();
        klient.setNazwaFirmy(data[0]);
        klient.setNip(data[1]);
        klient.setUlica(data[2]);
        klient.setNrDomu(data[3]);
        klient.setNrLokalu(data[4]);
        klient.setMiejscowosc(data[5]);
        klient.setKodPocztowy(data[6]);
                
        
        return klient;
    }
    
    public Osoba createOsoba(String data[]) {
        Osoba nowa_osoba = null;
        switch (Integer.parseInt(data[0])) {
            case 0: //obiekt ma posluzyc do wyszukania
                nowa_osoba = new Osoba();
                nowa_osoba.setEmail(data[1]);
                break;
            case 1: //obiekt ma zostac zapamietany
                nowa_osoba = new Osoba();
                nowa_osoba.setImie(data[1]);
                nowa_osoba.setNazwisko(data[2]);
                nowa_osoba.setEmail(data[3]);
                nowa_osoba.setRola(Rola.fromString(data[4]));
                break;
        }
        return nowa_osoba;
    }
    public Projekt createProjekt(String[] data ){
        Projekt projekt = new Projekt();
        projekt.setNazwa(data[0]);
        projekt.setData_rozpoczecia(new Date());
        projekt.setData_zakonczenia(null);
        projekt.setStatus(Integer.parseInt(data[1]));
        return projekt;
    }
    
        public Klient createKlient(String NIP) {
        Klient klient = new Klient();
        klient.setNip(NIP);      
        return klient;
    }
        
        public Projekt createProjekt(String emailKierownika){
        Projekt projekt = new Projekt();
        Osoba kierownik = new Osoba();
        kierownik.setEmail(emailKierownika);
        projekt.setKierownik(kierownik);
        return projekt;
    }
}
