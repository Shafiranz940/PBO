public class MainApp {
    public static void main(String[] args) {
        Human human = new Human("Alex");
        human.breathe();
        human.grow();
        human.speak("Hello everyone!");

        Animal animal = new Animal("Rabbit");
        animal.breathe();
        animal.grow();
        animal.move();

        Plant plant = new Plant("Rose");
        plant.breathe();
        plant.grow();
        plant.processFood();
    }
}