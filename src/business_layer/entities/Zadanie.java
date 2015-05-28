
package business_layer.entities;

public class Zadanie {
    
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
        String temp = "nie przydzielono";
        if (this.osoba != null)
            temp = this.osoba.getEmail();
        String[] array = new String[7];
        array[0] = String.valueOf(this.getIdentyfikator());
        array[1] = this.getNazwa();
        array[2] = String.valueOf(this.getSzacowanyCzas());
        array[3] = String.valueOf(this.getCzasDoZakonczenia());
        array[4] = String.valueOf(this.getCzasRealizacji());
        array[5] = temp;
        array[6] = this.getStatus().getText();
        return array;
    }

}
