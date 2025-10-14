import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Ingredient air = new Ingredient("Air", 1000, "ml");
        Ingredient kopi = new Ingredient("Kopi", 500, "gr");
        Ingredient susu = new Ingredient("Susu", 300, "ml");
        Ingredient gula = new Ingredient("Gula", 200, "gr");
        
        Coffee[] menu = {
            new Coffee("Espresso", 5, 0, 0, 10, 100, 10000),
            new Coffee("Americano", 5, 0, 0, 8, 150, 12000),
            new Coffee("Latte", 5, 0, 20, 10, 120, 15000),
            new Coffee("Cappuccino", 5, 0, 25, 10, 120, 18000)
        };
        
        VendingMachine vm = new VendingMachine(air, kopi, susu, gula, menu);
        Scanner sc = new Scanner(System.in);
        
        System.out.println("=== Selamat Datang di Mesin Kopi Otomatis ===");
        vm.ShowMenu();
        
        System.out.print("Pilih nomor kopi: ");
        int pilihan = sc.nextInt();
        Coffee kopiDipilih = menu[pilihan - 1];
        
        System.out.print("Pilih ukuran (S/M/L): ");
        String size = sc.next();
        
        System.out.print("Tambahkan susu? (y/n): ");
        boolean tambahSusu = sc.next().equalsIgnoreCase("y");
        
        System.out.println("Pilih tingkat gula: ");
        System.out.println("1. No Sugar\n2. Less Sugar\n3. Normal Sugar");
        int gulaPilihan = sc.nextInt();
        int gulaTakaran = switch(gulaPilihan) {
            case 1 -> 0;
            case 2 -> 5;
            default -> 10;
        };
        
        System.out.println("Pilih tingkat es: ");
        System.out.println("1. No Ice\n2. Less Ice\n3. Normal Ice");
        int esPilihan = sc.nextInt();
        int esTakaran = switch(esPilihan) {
            case 1 -> 0;
            case 2 -> 1;
            default -> 2;
        };
        
        if (!vm.CekStock(kopiDipilih, gulaTakaran, tambahSusu, esTakaran > 0)) {
            System.out.println("Stok bahan tidak mencukupi. Mohon tunggu admin melakukan refill.");
            return;
        }
        
        int totalHarga = vm.CalculateHarga(kopiDipilih.getHarga(), size, esTakaran > 0);
        System.out.println("Harga sebelum pajak + tambahan: " + kopiDipilih.getHarga());
        System.out.println("Total (termasuk pajak 10% dan opsi es): " + totalHarga);
        
        vm.ProsesBayar(totalHarga);
        vm.BuatKopi(kopiDipilih, size, gulaTakaran, tambahSusu, esTakaran);
    }
}
