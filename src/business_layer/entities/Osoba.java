package business_layer.entities;

import java.util.Objects;

public class Osoba extends Utility {

	private Projekt projekt = null;
	private String imie;
	private String nazwisko;
	private String email;
	private Rola rola;

	public Osoba() {
	}
        
        public Osoba(String n) {
            email = n;
        }

	public String getImie() {
		return imie;
	}

	public void setImie(String imie) {
		this.imie = imie;
	}

	public String getNazwisko() {
		return nazwisko;
	}

	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Rola getRola() {
		return rola;
	}

	public void setRola(Rola rola) {
		this.rola = rola;
	}
        
        public boolean sprawdz_email_Kierownika(String email)
        {
            return getEmail().equals(email) && getRola() == Rola.KIEROWNIK_PROJEKTU;
        }
        
        public boolean sprawdz_id_Kierownika()
        {
            Rola rolaOsoby = getRola();
            return rolaOsoby == Rola.KIEROWNIK_PROJEKTU;
        }


	@Override
	public boolean equals(Object obj) {
		return email == null ?
			((Osoba) obj).getEmail() == null : email.equals(((Osoba) obj).getEmail()); 
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 53 * hash + Objects.hashCode(this.imie);
		hash = 53 * hash + Objects.hashCode(this.nazwisko);
		hash = 53 * hash + Objects.hashCode(this.email);
		return hash;
	}

	@Override
	public String toString() {
		return "Imie: " + getImie()
			+ " | Nazwisko: " + getNazwisko()
			+ " | E-mail: " + getEmail()
			+ " | Rola: " + getRola().getText();
	}
        
        public String[] toStringArray() {
            String[] daneOsoby = new String[5];
            daneOsoby[0] = getImie();
            daneOsoby[1] = getNazwisko();
            daneOsoby[2] = getEmail();
            if (getProjekt() == null) {
                daneOsoby[3] = "Nie przydzielono";
            } else {
                daneOsoby[3] = getProjekt().getNazwa();
            }
            daneOsoby[4] = getRola().getText();
            return daneOsoby;
    }


	public Projekt getProjekt() {
		return projekt;
	}

	public void setProjekt(Projekt projekt) {
		this.projekt = projekt;
	}
}
