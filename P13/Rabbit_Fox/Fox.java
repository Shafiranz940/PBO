import java.util.List;

public class Fox extends Animal {

    public Fox(boolean randomAge, Field field, Location location) {
        super(randomAge, field, location);
    }

    private Location findFood() {
        List<Location> adjacent = field.adjacentLocations(location);

        for (Location loc : adjacent) {
            Object animal = field.getObjectAt(loc);
            if (animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if (rabbit.isAlive()) {
                    rabbit.setDead(); // makan kelinci
                    return loc;       // pindah ke posisi kelinci
                }
            }
        }
        return null;
    }

    @Override
    public void act(List<Animal> newAnimals) {
        incrementAge();
        if (isAlive()) {
            giveBirth(newAnimals);

            Location newLocation = findFood();
            if (newLocation == null) {
                List<Location> free = field.getAllFreeAdjacentLocations(location);
                if (free.size() > 0) {
                    newLocation = free.get(rand.nextInt(free.size()));
                }
            }

            if (newLocation != null) {
                setLocation(newLocation);
            } else {
                setDead();
            }
        }
    }

    @Override
    protected int getMaxAge() { return 8; }

    @Override
    protected int getBreedingAge() { return 2; }

    @Override
    protected double getBreedingProbability() { return 0.08; }

    @Override
    protected int getMaxLitterSize() { return 2; }

    @Override
    protected Animal createYoung(Field field, Location location) {
        return new Fox(false, field, location);
    }
}