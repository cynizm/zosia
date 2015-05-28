
package business_layer.entities;

public enum StatusSprintu{
    NIEROZPOCZETY("Nierozpoczęty"),
    W_TRAKCIE("W trakcie"),
    ZAKONCZONY("Zakończony");

    private final String text;

    StatusSprintu(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static StatusSprintu fromString(String text) {
        if (text != null) {
            for (StatusSprintu s : StatusSprintu.values()) {
                if (text.equalsIgnoreCase(s.text)) {
                    return s;
                }
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}
