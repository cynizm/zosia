
package business_layer.entities;

public enum Rola {
    
    KIEROWNIK_PROJEKTU("Kierownik projektu"),
    ANALITYK("Analityk"),
    PROGRAMISTA("Programista"),
    TESTER("Tester");

    private final String text;

    Rola(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static Rola fromString(String text) {
        if (text != null) {
            for (Rola r : Rola.values()) {
                if (text.equalsIgnoreCase(r.text)) {
                    return r;
                }
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }

}