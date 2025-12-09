public class Plant extends LivingBeing {
    public Plant(String name) {
        super(name);
    }

    @Override
    public void grow() {
        System.out.println(name + " grows taller.");
    }

    public void processFood() {
        System.out.println(name + " is making food through photosynthesis.");
    }
}