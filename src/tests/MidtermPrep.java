import java.util.ArrayList;

public class MidtermPrep<T> {

    private T elements[];

    public static void main(String[] args) {

        String test1 = "cse12";
        String test2 = "CSE8A";
        int result = test1.compareTo(test2);

        System.out.println(result);
    }

    private void test() {
        elements = (T[]) new Object[12];
    }

}
