
package business_layer.entities;

import java.util.Date;
import java.util.Objects;

public class Ryzyko {
    private String nazwa;
    private String opis;
    private float prwdWystapienia;
    private double kosztWystapienia;
    private Date dataZgloszenia;
    private Date dataZakonczenia;
    private boolean aktywne;
    
    private Projekt projekt;
    
    public Ryzyko(){      
    }
    
    public Ryzyko(String nazwa, String opis, float prwdWystapienia, double kosztWystapienia) {
        this.nazwa = nazwa;
        this.opis = opis;
        this.prwdWystapienia = prwdWystapienia;
        this.kosztWystapienia = kosztWystapienia;
        this.dataZgloszenia = new Date();
        this.dataZakonczenia = null;
        this.aktywne = true;
        this.projekt = null;
    }
    
    public String getNazwa() {
        return nazwa;
    }

    public String getOpis() {
        return opis;
    }

    public float getPrwdWystapienia() {
        return prwdWystapienia;
    }

    public double getKosztWystapienia() {
        return kosztWystapienia;
    }

    public Date getDataZgloszenia() {
        return dataZgloszenia;
    }

    public Date getDataZakonczenia() {
        return dataZakonczenia;
    }

    public boolean isAktywne() {
        return aktywne;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public void setPrwdWystapienia(float prwdWystapienia) {
        this.prwdWystapienia = prwdWystapienia;
    }

    public void setKosztWystapienia(double kosztWystapienia) {
        this.kosztWystapienia = kosztWystapienia;
    }

    public void setDataZgloszenia(Date dataZgloszenia) {
        this.dataZgloszenia = dataZgloszenia;
    }

    public void setDataZakonczenia(Date dataZakonczenia) {
        this.dataZakonczenia = dataZakonczenia;
    }

    public void setAktywne(boolean aktywne) {
        this.aktywne = aktywne;
    }
    
     public Projekt getProjekt() {
        return projekt;
    }

    public void setProjekt(Projekt projekt) {
        this.projekt = projekt;
    }
    
    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
            return false;
        }
	if (Objects.equals(this.nazwa,((Ryzyko) obj).nazwa)) {
            return Objects.equals(this.opis, ((Ryzyko) obj).opis);
        }
	return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.nazwa);
        hash = 19 * hash + Objects.hashCode(this.opis);
        return hash;
    }
    
    @Override
    public String toString() {
        return "Ryzyko{" + "nazwa=" + nazwa + ", opis=" + opis + ", prwdWystapienia=" + prwdWystapienia + ", kosztWystapienia=" + kosztWystapienia + ", dataZgloszenia=" + dataZgloszenia + ", dataZakonczenia=" + dataZakonczenia + ", aktywne=" + aktywne + '}';
    }
    
    public String[] toStringArray() {
        String[] array = new String[7];
        array[0] = this.getNazwa();
        array[1] = this.getOpis();
        array[2] = Float.toString(this.getPrwdWystapienia());
        array[3] = Double.toString(this.getKosztWystapienia());
        array[4] = this.getDataZgloszenia().toString();
        array[5] = this.isAktywne() == true ? "" : this.getDataZgloszenia().toString();
        array[6] = Boolean.toString(this.isAktywne());
        return array;
    }
    
}
