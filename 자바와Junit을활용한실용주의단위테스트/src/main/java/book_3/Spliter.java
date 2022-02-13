package book_3;

public class Spliter {

    public String[] split(final String raw, final String delimeter) {
        return raw.split(delimeter);
    }

    public String refine(final String s) {
        return s.replace("(", "").replace(")", "");
    }

    public String findCharacterFromIndex(final String raw, final int i) {
        return String.valueOf(raw.charAt(i));
    }
}
