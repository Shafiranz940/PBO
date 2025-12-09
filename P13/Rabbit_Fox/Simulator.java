import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulator {
    private List<Animal> animals;
    private Field field;
    private int step;
    private SimulatorView view;
    private static final Random rand = new Random();

    private final int initialRabbits = 8; // jumlah kelinci awal
    private final int initialFoxes = 3;   // jumlah Rubah awal dijamin muncul

    public Simulator(int row, int col) {
        field = new Field(row, col);
        animals = new ArrayList<>();
        view = new SimulatorView(row, col);

        view.setView(Rabbit.class, 'R');
        view.setView(Fox.class, 'F');

        reset();
    }

    public void simulate(int maxSteps) {
        for (int i = 0; i < maxSteps; i++) {
            simulateOneStep();
        }
    }

    public void reset() {
        step = 0;
        animals.clear();
        field.clearAll();
        populate();
    }

    public void simulateOneStep() {
        step++;
        List<Animal> newAnimals = new ArrayList<>();

        for (Animal animal : new ArrayList<>(animals)) {
            animal.act(newAnimals);
        }

        animals.addAll(newAnimals);
        view.showField(step, field);
    }

    private void populate() {
        animals.clear();

        // Tempatkan kelinci secara acak
        int rabbitsPlaced = 0;
        while (rabbitsPlaced < initialRabbits) {
            int row = rand.nextInt(field.getRow());
            int col = rand.nextInt(field.getCol());
            Location loc = new Location(row, col);
            if (field.getObjectAt(loc) == null) {
                Rabbit rabbit = new Rabbit(true, field, loc);
                animals.add(rabbit);
                field.place(rabbit, loc);
                rabbitsPlaced++;
            }
        }

        // Tempatkan Rubah secara acak
        int foxesPlaced = 0;
        while (foxesPlaced < initialFoxes) {
            int row = rand.nextInt(field.getRow());
            int col = rand.nextInt(field.getCol());
            Location loc = new Location(row, col);
            if (field.getObjectAt(loc) == null) {
                Fox fox = new Fox(true, field, loc);
                animals.add(fox);
                field.place(fox, loc);
                foxesPlaced++;
            }
        }

        // Opsional: tambahkan kelinci ekstra acak
        for (int row = 0; row < field.getRow(); row++) {
            for (int col = 0; col < field.getCol(); col++) {
                Location loc = new Location(row, col);
                if (field.getObjectAt(loc) == null && Math.random() < 0.05) {
                    Rabbit rabbit = new Rabbit(true, field, loc);
                    animals.add(rabbit);
                    field.place(rabbit, loc);
                }
            }
        }
    }
}