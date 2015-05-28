
package business_layer.entities;

import java.util.Date;

public class StanSprintu {
        
    private Date data_aktualizacji;
    private Integer numer_dnia_sprintu;
    private Integer ilosc_zadan_nierozpoczetych;
    private Integer ilosc_zadan_w_analizie;
    private Integer ilosc_zadan_w_implementacji;
    private Integer ilosc_zadan_w_testach;
    private Integer ilosc_zadan_zakonczonych;
    
    public StanSprintu(){
        data_aktualizacji=new Date();
        numer_dnia_sprintu=0;
        ilosc_zadan_nierozpoczetych=0;
        ilosc_zadan_w_analizie=0;
        ilosc_zadan_w_implementacji=0;
        ilosc_zadan_w_testach=0;
        ilosc_zadan_zakonczonych=0;
    }

    public Date getDataAktualizacji(){
        return this.data_aktualizacji;
    }
    
    public void setDataAktualizacji( Date data_aktualizacji){
        this.data_aktualizacji=data_aktualizacji;
    }
    
    public Integer getNumerDniaSprintu() {
        return numer_dnia_sprintu;
    }

    public void setNumerDniaSprintu(Integer numer_dnia_sprintu) {
        this.numer_dnia_sprintu = numer_dnia_sprintu;
    }

    public Integer getIloscZadanNierozpoczetych() {
        return ilosc_zadan_nierozpoczetych;
    }

    public void setIloscZadanNierozpoczetych(Integer ilosc_zadan_nierozpoczetych) {
        this.ilosc_zadan_nierozpoczetych = ilosc_zadan_nierozpoczetych;
    }

    public Integer getIloscZadanWAnalizie() {
        return ilosc_zadan_w_analizie;
    }

    public void setIloscZadanWAnalizie(Integer ilosc_zadan_w_analizie) {
        this.ilosc_zadan_w_analizie = ilosc_zadan_w_analizie;
    }

    public Integer getIloscZadanWImplementacji() {
        return ilosc_zadan_w_implementacji;
    }

    public void setIloscZadanWImplementacji(Integer ilosc_zadan_w_implementacji) {
        this.ilosc_zadan_w_implementacji = ilosc_zadan_w_implementacji;
    }

    public Integer getIloscZadanWTestach() {
        return ilosc_zadan_w_testach;
    }

    public void setIloscZadanWTestach(Integer ilosc_zadan_w_testach) {
        this.ilosc_zadan_w_testach = ilosc_zadan_w_testach;
    }

    public Integer getIloscZadanZakonczonych() {
        return ilosc_zadan_zakonczonych;
    }

    public void setIloscZadanZakonczonych(Integer ilosc_zadan_zakonczonych) {
        this.ilosc_zadan_zakonczonych = ilosc_zadan_zakonczonych;
    }
    public String toString(){
        String str = "Data aktualizacji: " + this.data_aktualizacji.toString() + " numer dnia sprintu: "+ this.numer_dnia_sprintu.toString() +
                " ilość zadań nierozpoczętych: "+ this.ilosc_zadan_nierozpoczetych.toString()+ " ilość zadań w analizie: "+ this.ilosc_zadan_w_analizie.toString()+ " ilość zadań w implementacji: "+
                this.ilosc_zadan_w_implementacji.toString()+" ilość zadań w testach: "+this.ilosc_zadan_w_testach.toString()+" ilość zadań zakończonych: "+this.ilosc_zadan_zakonczonych.toString();
        return str;
    }
    
    public String[] toStringArray() {
        String[] array = new String[7];
        

        array[0] = numer_dnia_sprintu.toString();
        array[1] = ilosc_zadan_nierozpoczetych.toString();
        array[2] = ilosc_zadan_w_analizie.toString();
        array[3] = ilosc_zadan_w_implementacji.toString();
        array[4] = ilosc_zadan_w_testach.toString();
        array[5] = ilosc_zadan_zakonczonych.toString();
        array[6] = data_aktualizacji.toString();
        return array;
    }
        public boolean equals(Object obj) {    
        return (getNumerDniaSprintu().equals(((StanSprintu)obj).getNumerDniaSprintu()));
    }
}
