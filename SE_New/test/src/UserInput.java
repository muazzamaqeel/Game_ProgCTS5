public class UserInput {
    private static boolean key_a;
    private static boolean key_w;
    private static boolean key_d;
    private static boolean key_s;
    private boolean doublePress;

    public void setDoublePress(boolean doublePress) {
        this.doublePress = doublePress;
    }

    public boolean isDoublePress() {
        return doublePress;
    }

    public static boolean isKey_a() {
        return key_a;
    }

    public void setKey_a(boolean key_a) {
        this.key_a = key_a;
    }

    public static boolean isKey_w() {
        return key_w;
    }

    public void setKey_w(boolean key_w) {
        this.key_w = key_w;
    }

    public static boolean isKey_d() {
        return key_d;
    }

    public void setKey_d(boolean key_d) {
        this.key_d = key_d;
    }

    public static boolean isKey_s() {
        return key_s;
    }

    public void setKey_s(boolean key_s) {
        this.key_s = key_s;
    }
}
