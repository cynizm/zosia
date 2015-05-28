
package business_layer.entities;

public enum StatusZadania {
    NIEROZPOCZETE("Nierozpoczete"),
    ANALIZA("Analiza"),
    IMPLEMENTACJA("Implementacja"),
    TESTOWANIE("Testowanie"),
    ZAKONCZONE("Zakonczone");
    
    private final String text;

    StatusZadania(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static StatusZadania fromString(String text) {
        if (text != null) 
            for (StatusZadania s : StatusZadania.values()) 
                if (text.equalsIgnoreCase(s.text)) 
                    return s;
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}
