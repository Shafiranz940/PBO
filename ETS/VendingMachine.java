import java.util.Scanner;

public class VendingMachine {
    private Ingredient butuhAir;
    private Ingredient butuhKopi;
    private Ingredient susu;
    private Ingredient gula;
    private Coffee[] menu;
    
    public VendingMachine(Ingredient butuhAir, Ingredient butuhKopi, Ingredient susu, Ingredient gula, Coffee[] menu) {
        this.butuhAir = butuhAir;
        this.butuhKopi = butuhKopi;
        this.susu = susu;
        this.gula = gula;
        this.menu = menu;
    }
    
    public void ShowMenu() {
        System.out.println("=== Pilih Kopi Disini ===");
        for (int i = 0; i < menu.length; i++) {
            System.out.println((i + 1) + ". " + menu[i].getNama() + " (" + menu[i].getHarga() + ")");
        }
    }
    
    public int CalculateHarga(int harga, String size, boolean pilihanEs) {
        double price;
        switch(size.toUpperCase()) {
            case "M": price = harga * 1.2; break;
            case "L": price = harga * 1.5; break;
            default: price = harga; break;
        }
        if (pilihanEs) price += 2000;
        price = price * 1.10;
        return (int) price;
    }
    
    public boolean CekStock(Coffee kopi, int gulaTakaran, boolean pilihanSusu, boolean pilihanEs) {
        if (butuhAir.getStock() < kopi.getAir()) return false;
        if (butuhKopi.getStock() < kopi.getKopi()) return false;
        if (pilihanSusu && susu.getStock() < kopi.getSusu()) return false;
        if (gula.getStock() < gulaTakaran) return false;
        if (pilihanEs && butuhAir.getStock() < 50) return false;
        return true;
    }
    
    public void BuatKopi(Coffee kopi, String size, int gulaTakaran, boolean pilihanSusu, int esTakaran) {
    butuhAir.pakai(kopi.getAir());
    butuhKopi.pakai(kopi.getKopi());
    if (pilihanSusu) susu.pakai(kopi.getSusu());
    gula.pakai(gulaTakaran);
    if (esTakaran > 0) butuhAir.pakai(esTakaran * 20);

    System.out.println("Kopi " + kopi.getNama() + " ukuran " + size + " dengan " +
                       (gulaTakaran == 0 ? "no sugar" : gulaTakaran == 5 ? "less sugar" : "normal sugar") +
                       (pilihanSusu ? ", susu" : "") +
                       (esTakaran == 0 ? ", no ice" : esTakaran == 1 ? ", less ice" : ", normal ice") +
                       " sedang dibuat...");
    System.out.println("Kopi telah siap, silahkan ambil.");

    if (butuhAir.getStock() < 100)
        System.out.println("⚠️  Stok air hampir habis, perlu refill!");
    if (butuhKopi.getStock() < 50)
        System.out.println("⚠️  Stok kopi hampir habis, perlu refill!");
    if (susu.getStock() < 50)
        System.out.println("⚠️  Stok susu hampir habis, perlu refill!");
    if (gula.getStock() < 20)
        System.out.println("⚠️  Stok gula hampir habis, perlu refill!");

    try (java.io.FileWriter fw = new java.io.FileWriter("transaksi_log.txt", true)) {
        fw.write("[" + java.time.LocalTime.now() + "] " + kopi.getNama() + " ukuran " + size +
                 " (" + (esTakaran == 0 ? "no ice" : esTakaran == 1 ? "less ice" : "normal ice") + ")\n");
    } catch (Exception e) {
        System.out.println("Gagal mencatat transaksi.");
    }
}

    
    public void ProsesBayar(int HargaTotal) {
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.println("Masukkan uang: ");
            int uang = sc.nextInt();
            if (uang >= HargaTotal) {
                System.out.println("Kembalian: " + (uang - HargaTotal));
                break;
            } else {
                System.out.println("Uang kurang, coba lagi.");
            }
        }
    }
}
