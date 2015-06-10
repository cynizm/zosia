
package business_layer.entities;

public class Zadanie extends Utility {
    
    private int identyfikator;
    private String nazwa;
    private StatusZadania status;
    private int szacowanyCzas;
    private int czasDoZakonczenia;
    private int czasRealizacji;
    private Osoba osoba;

    public Zadanie(){   
        osoba = null;
        status = null;
    }  
    
    public int getIdentyfikator() {
        return identyfikator;
    }

    public String getNazwa() {
        return nazwa;
    }

    public StatusZadania getStatus() {
        return status;
    }

    public int getSzacowanyCzas() {
        return szacowanyCzas;
    }

    public int getCzasDoZakonczenia() {
        return czasDoZakonczenia;
    }

    public int getCzasRealizacji() {
        return czasRealizacji;
    }

    public Osoba getOsoba() {
        return osoba;
    }

    public void setIdentyfikator(int identyfikator) {
        this.identyfikator = identyfikator;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public void setStatus(StatusZadania status) {
        this.status = status;
    }

    public void setSzacowanyCzas(int szacowanyCzas) {
        this.szacowanyCzas = szacowanyCzas;
    }

    public void setCzasDoZakonczenia(int czasDoZakonczenia) {
        this.czasDoZakonczenia = czasDoZakonczenia;
    }

    public void setCzasRealizacji(int czasRealizacji) {
        this.czasRealizacji = czasRealizacji;
    }

    public void setOsoba(Osoba osoba) {
        this.osoba = osoba;
    }
    
    @Override
    public boolean equals(Object obj) {
    return obj != null && this.identyfikator == ((Zadanie)obj).identyfikator;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.identyfikator;
        return hash;
    }
    
    @Override
    public String toString() {
        String temp = "nie przydzielono";
        if (osoba != null)
            temp = osoba.getEmail();
        return "Identyfikator: " + getIdentyfikator()
            + " | Nazwa: " + getNazwa()
            + " | Szacowany czas: " + getSzacowanyCzas()
            + " | Czas do zakonczenia: " + getCzasDoZakonczenia()
            + " | Czas realizacji: " + getCzasRealizacji()
            + " | Osoba: " + temp
            + " | Status: " + getStatus().getText();
    }
    
    public String[] toStringArray() {
        String[] daneZadania = new String[7];
	daneZadania[0] = Integer.toString(getIdentyfikator());
	daneZadania[1] = getNazwa();
	daneZadania[2] = getStatus().getText();
	daneZadania[3] = Integer.toString(getSzacowanyCzas());
	daneZadania[4] = Integer.toString(getCzasDoZakonczenia());
	daneZadania[5] = Integer.toString(getCzasRealizacji());
	daneZadania[6] = "Nie przydzielono";
	return daneZadania;
    }
}
