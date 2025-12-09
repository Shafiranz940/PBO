import java.util.List;
import java.util.Random;

public abstract class Animal {
    protected int age;
    protected boolean alive;
    protected Location location;
    protected Field field;
    protected static final Random rand = new Random();

    public Animal(boolean randomAge, Field field, Location location) {
        this.field = field;
        this.location = null;
        alive = true;

        if (randomAge) {
            age = rand.nextInt(getMaxAge());
        } else {
            age = 0;
        }

        setLocation(location);
    }

    public int getAge() { return age; }
    public boolean isAlive() { return alive; }
    public Location getLocation() { return location; }

    public void setLocation(Location newLocation) {
        if (location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    public void setDead() {
        alive = false;
        if (location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    public boolean canBreed() {
        return age >= getBreedingAge();
    }

    protected void incrementAge() {
        age++;
        if (age > getMaxAge()) {
            setDead();
        }
    }

    protected void giveBirth(List<Animal> newAnimals) {
        List<Location> free = field.getAllFreeAdjacentLocations(location);
        int births = breed();

        for (int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Animal young = createYoung(field, loc);
            newAnimals.add(young);
        }
    }

    protected int breed() {
        int births = 0;
        if (canBreed() && rand.nextDouble() <= getBreedingProbability()) {
            births = rand.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }

    public abstract void act(List<Animal> newAnimals);

    protected abstract int getMaxAge();
    protected abstract int getBreedingAge();
    protected abstract double getBreedingProbability();
    protected abstract int getMaxLitterSize();
    protected abstract Animal createYoung(Field field, Location location);
}