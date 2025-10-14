public class Coffee  {
    private String nama;
    private int gula;
    private int esBatu;
    private int susu;
    private int kopi;
    private int air;
    private int harga;
    
    public Coffee(String nama, int gula, int esBatu, int susu, int kopi, int air, int harga){
        this.nama = nama;
        this.gula = gula;
        this.esBatu = esBatu;
        this.susu = susu;
        this.kopi = kopi;
        this.air = air;
        this.harga = harga;
    }
    
    public String getNama (){
        return nama;
    }
    
    public int getGula (){
        return gula;
    }
    
    public int getEsBatu (){
        return esBatu;
    }
    
    public int getSusu (){
        return susu;
    }
    
    public int getKopi (){
        return kopi;
    }
    
    public int getAir (){
        return air;
    }
    
    public int getHarga (){
        return harga;
    }
}