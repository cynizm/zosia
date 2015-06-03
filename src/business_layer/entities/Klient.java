
package business_layer.entities;

import java.util.Objects;

public class Klient {
    
    private String nazwaFirmy;
    private String ulica;
    private String nrDomu;
    private String nrLokalu;
    private String kodPocztowy;
    private String miejscowosc;
    private String nip;
    
    public Klient() {
        
    }
       
    public void zmienAdres(String ulica, String nrDomu, String nrLokalu, String kodPocztowy, String miejscowosc) {
        this.ulica = ulica;
        this.nrDomu = nrDomu;
        this.nrLokalu = nrLokalu;
        this.kodPocztowy = kodPocztowy;
        this.miejscowosc = miejscowosc;       
    }
    
    public String getNrLokalu() {
        return nrLokalu;
    }
    
    public String getNrDomu() {
        return nrDomu;
    }
    
    public String getUlica() {
        return ulica;
    }
    
    public String getNazwaFirmy() {
        return nazwaFirmy;
    }
    
    public String getMiejscowosc() {
        return miejscowosc;
    }
    
    public String getKodPocztowy() {
        return kodPocztowy;
    }
    
    public String getNip() {
        return nip;
    }
    
    public void setNrLokalu(String nrLokalu) {
        this.nrLokalu = nrLokalu;
    }
    
    public void setNrDomu(String nrDomu) {
        this.nrDomu = nrDomu;
    }
    
    public void setUlica(String ulica) {
        this.ulica = ulica;
    }
    
    public void setNazwaFirmy(String nazwaFirmy) {
        this.nazwaFirmy = nazwaFirmy;
    }    
 
    public void setMiejscowosc(String miejscowosc) {
        this.miejscowosc = miejscowosc;
    }
    
    public void setKodPocztowy(String kodPocztowy) {
        this.kodPocztowy = kodPocztowy;
    }   
    
    public void setNip(String nip) {
        this.nip = nip;
    }
    
    // sprawdzamy na podstawie nipu (unikatowa wartosc)
    @Override
    public boolean equals(Object obj) {    
        return getNip().equals(((Klient) obj).getNip());
    }
    
    
	@Override
	public int hashCode() {
		int hash = 3;
		hash = 53 * hash + Objects.hashCode(nazwaFirmy);
		hash = 53 * hash + Objects.hashCode(ulica);
		hash = 53 * hash + Objects.hashCode(nrDomu);
		hash = 53 * hash + Objects.hashCode(nrLokalu);
		hash = 53 * hash + Objects.hashCode(kodPocztowy);
		hash = 53 * hash + Objects.hashCode(miejscowosc);
		hash = 53 * hash + Objects.hashCode(nip);
		return hash;
	}
    
    @Override
    public String toString() {
        String val = "\nNazwa firmy: " + getNazwaFirmy();
        val += " NIP: " + getNip();
        val += " Adres: " + getUlica();
        val += " " + getNrDomu() + "/" + getNrLokalu();
        val += " " + getKodPocztowy() + " " + getMiejscowosc();
        return val;
    }
    
        public String[] toStringArray() {
        String[] client = new String[7];
        client[0]=getNazwaFirmy();
        client[1]=getNip();
        client[2]=getUlica();
        client[3]=getNrDomu();
        client[4]=getNrLokalu();
        client[5]=getKodPocztowy();
        client[6]=getMiejscowosc();
        return client;
    }
    
}
