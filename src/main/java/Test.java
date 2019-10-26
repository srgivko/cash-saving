public class Test {
    public static void main(String[] args) {
        test();
    }

    public static int test() {
        try {
            System.out.println(1);
            throw new RuntimeException();
        } catch (Exception ex) {
            System.out.println(2);
            return 2;
        } finally {
            System.out.println(3);
            return 3;
        }
    }
}
