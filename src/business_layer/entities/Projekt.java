
package business_layer.entities;


import business_layer.Factory;
import java.util.ArrayList;
import java.util.Date;

public class Projekt {

    
    private ArrayList<Ryzyko> ryzyka = new ArrayList<>();
    private ArrayList<Stan> stany = new ArrayList<>();
    private ArrayList<Osoba> zespol = new ArrayList<>();
    private ArrayList<Sprint> sprinty = new ArrayList<>();
    
    private String nazwa;
    private int status;
    private Klient klient;
    private Date data_rozpoczecia;
    private Date data_zakonczenia;
    private Osoba kierownik;
        
    public Projekt(){
        nazwa = "";
        status = 0;
        klient = null;
        data_rozpoczecia = new Date();
        data_zakonczenia = new Date();
        kierownik = null;
        ryzyka = new ArrayList();
        stany = new ArrayList();
        zespol = new ArrayList();
    }    
    
    
    public void setNazwa(String slowo){
        nazwa=slowo;
    }
    public String getNazwa(){
        return nazwa;
    }
    public Osoba getKierownik(){
        return this.kierownik;
    }
    public void setKierownik(Osoba kierownik){
        this.kierownik = kierownik;
        this.zespol.add(kierownik);
    }
   
    
    public Klient getKlient(){
        return klient;
    }
    public void setKlient(Klient osoba){
        klient=osoba;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int _status) {
        status=_status;
    }
    public Date getData_rozpoczecia(){
        return data_rozpoczecia;
    }
    public Date getData_zakonczenia(){
        return data_zakonczenia;
    }
    public void setData_rozpoczecia(Date data) {
        data_rozpoczecia=data;
    }
    public void setData_zakonczenia(Date data){
        data_zakonczenia=data;
    }
    public ArrayList<Osoba> getZespol(){
        return zespol;
    }
    public void setZespol(Osoba osoba){
        zespol.add(osoba);
    }
    public ArrayList<Stan> getStan() {
        return stany;
    }
    public void setStan(Stan stan){
       stany.add(stan);
    }
    
    
    @Override
    public String toString(){
        String help ="\nNazwa: "+this.getNazwa()
                + "\nKierownik: " + getKierownik().toString()
                + "\nKlient: " + getKlient().toString() 
                + "\nData rozpoczecia: " + getData_rozpoczecia().toString() + "\n";
        return help;
    }
    
    public String[] toString_() {
        String[] title = new String[4];
        title[0] = this.getNazwa();
        title[1] = getKierownik().toString();
        title[2] = getData_rozpoczecia().toString();
        title[3] = getData_zakonczenia() == null ? "" : getData_zakonczenia().toString();
        
        return title;
    }
    
    @Override
    public boolean equals(Object obj) {
	if (obj != null && this.kierownik.equals(((Projekt)obj).kierownik)) {
        	return true;
        }
	return false;
    }
	
    
    public String kierownikEmail(){
        return this.kierownik.getEmail();
    }
    
    public Ryzyko findRyzyko(Ryzyko ryzyko) {
	int index = ryzyka.indexOf(ryzyko);
	if (index != -1) {
            return ryzyka.get(index);
	}
	return null;
    }

    public int addRyzyko(Ryzyko nowe) {
        Ryzyko ryzyko = findRyzyko(nowe);
        if (ryzyko == null) {
            ryzyka.add(nowe);
            return 0;
        }
        return 3;
    }
        
    public void setRyzyka(ArrayList<Ryzyko> ryzyka) {
        this.ryzyka = ryzyka;
    }

    public ArrayList<Ryzyko> getRyzyka() {
        return ryzyka;
    }
    
    public Sprint findSprint(Sprint sprint) {
	int index = this.sprinty.indexOf(sprint);
	if (index != -1) {
            return this.sprinty.get(index);
	}
	return null;
    }

  public int addSprint(Sprint sprint) {
        Sprint s = findSprint(sprint);
        if (s == null) {
            this.sprinty.add(sprint);
            return 0;
        }
        return 3;
    }
    
    
    public int addstanSprintu(String datastanSprint[], String dataSprint[])
    {
         Factory factory = new Factory();
         Sprint sprint=factory.createSprint(dataSprint);
            Sprint s = findSprint(sprint);
            if (s != null) {
               // Sprint sprint = szukajSprint(dataSprint,dataKierownik);
                //StanSprintu stan = factory.createStanSprintu(dataStanSprintu);
                return s.addStanSprintu(datastanSprint);
            }
        return 2;
    }
    
    public StanSprintu szukajStanuSprintu(String datastanSprint[], String dataSprint[]) {
        return null;
    }
    
    public ArrayList <Sprint> getSprinty(){
        return sprinty;
    }


    public int addStanSprintu(Sprint sprint_, StanSprintu stansprintu_) {
            Sprint sprint = findSprint(sprint_);
            if (sprint != null) {
                return sprint.addStanSprintu(stansprintu_.toStringArray());
            }
            return 2;
        }
    public StanSprintu findStanSprintu(Sprint sprint_, StanSprintu stansprintu_) {
        Sprint sprint = findSprint(sprint_);
	int index = sprint.getStanySprintu().indexOf(stansprintu_);
	if (index != -1) {
            return sprint.getStanySprintu().get(index);
	}
	return null;
    }
    
}
