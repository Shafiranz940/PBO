import java.util.HashMap;
import java.util.Map;

public class SimulatorView {
    private int row;
    private int col;
    private Map<Class<?>, Character> fieldView;

    public SimulatorView(int row, int col) {
        this.row = row;
        this.col = col;
        fieldView = new HashMap<>();
    }

    public void setView(Class<?> animalClass, Character viewChar) {
        fieldView.put(animalClass, viewChar);
    }

    public void showField(int step, Field field) {
        System.out.println("Step: " + step);

        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                Object obj = field.getObjectAt(new Location(r, c));
                if (obj == null) {
                    System.out.print(".");
                } else {
                    Character ch = fieldView.get(obj.getClass());
                    System.out.print(ch != null ? ch : "?");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}