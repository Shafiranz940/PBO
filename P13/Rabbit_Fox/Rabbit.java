import java.util.List;

public class Rabbit extends Animal {

    public Rabbit(boolean randomAge, Field field, Location location) {
        super(randomAge, field, location);
    }

    @Override
    public void act(List<Animal> newAnimals) {
        incrementAge();
        if (isAlive()) {
            giveBirth(newAnimals);

            // Bergerak ke lokasi kosong secara acak
            List<Location> free = field.getAllFreeAdjacentLocations(location);
            if (free.size() > 0) {
                Location newLocation = free.get(rand.nextInt(free.size()));
                setLocation(newLocation);
            }
        }
    }

    @Override
    protected int getMaxAge() { return 5; }

    @Override
    protected int getBreedingAge() { return 1; }

    @Override
    protected double getBreedingProbability() { return 0.12; }

    @Override
    protected int getMaxLitterSize() { return 4; }

    @Override
    protected Animal createYoung(Field field, Location location) {
        return new Rabbit(false, field, location);
    }
}