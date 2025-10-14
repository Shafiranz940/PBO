public class Ingredient {
    private String nama;
    private int stock;
    private String unit;
    
    public Ingredient(String nama, int stock, String unit) {
        this.nama = nama;
        this.stock = stock;
        this.unit = unit;
    }
    
    public boolean Stock(int amount) {
        if(stock >= amount){
            stock -= amount;
            return true;
        }
            return false;
    }
    
    public void pakai(int amount) {
    this.stock -= amount;
    if (this.stock < 0) this.stock = 0;
    }

    
    public void refill (int amount){
        stock += amount;
    }
    
    public int getStock (){
        return stock;
    }
    
    public String getNama (){
        return nama;
    }
}

