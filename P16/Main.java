import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BookDAO dao = new BookDAO();
        int choice;

        do {
            System.out.println("=== Menu Perpustakaan ===");
            System.out.println("1. Tambah Buku");
            System.out.println("2. Tampilkan Semua Buku");
            System.out.println("3. Update Buku");
            System.out.println("4. Hapus Buku");
            System.out.println("5. Cari Buku by ID");
            System.out.println("0. Keluar");
            System.out.print("Pilih: ");
            choice = sc.nextInt();
            sc.nextLine(); // buang newline

            switch (choice) {
                case 1:
                    System.out.print("Judul: ");
                    String title = sc.nextLine();
                    System.out.print("Penulis: ");
                    String author = sc.nextLine();
                    System.out.print("Penerbit: ");
                    String publisher = sc.nextLine();
                    System.out.print("Tahun: ");
                    int year = sc.nextInt();
                    sc.nextLine();

                    dao.addBook(new Book(title, author, publisher, year));
                    break;

                case 2:
                    List<Book> books = dao.getAllBooks();
                    for (Book b : books) {
                        System.out.println(b);
                    }
                    break;

                case 3:
                    System.out.print("Masukkan ID Buku yang ingin diupdate: ");
                    int updateId = sc.nextInt();
                    sc.nextLine();
                    Book bUpdate = dao.getBookById(updateId);
                    if (bUpdate != null) {
                        System.out.print("Judul baru (" + bUpdate.getTitle() + "): ");
                        String newTitle = sc.nextLine();
                        System.out.print("Penulis baru (" + bUpdate.getAuthor() + "): ");
                        String newAuthor = sc.nextLine();
                        System.out.print("Penerbit baru (" + bUpdate.getPublisher() + "): ");
                        String newPublisher = sc.nextLine();
                        System.out.print("Tahun baru (" + bUpdate.getYear() + "): ");
                        int newYear = sc.nextInt();
                        sc.nextLine();

                        bUpdate.setTitle(newTitle.isEmpty() ? bUpdate.getTitle() : newTitle);
                        bUpdate.setAuthor(newAuthor.isEmpty() ? bUpdate.getAuthor() : newAuthor);
                        bUpdate.setPublisher(newPublisher.isEmpty() ? bUpdate.getPublisher() : newPublisher);
                        bUpdate.setYear(newYear == 0 ? bUpdate.getYear() : newYear);

                        dao.updateBook(bUpdate);
                    } else {
                        System.out.println("Buku tidak ditemukan.");
                    }
                    break;

                case 4:
                    System.out.print("Masukkan ID Buku yang ingin dihapus: ");
                    int delId = sc.nextInt();
                    sc.nextLine();
                    dao.deleteBook(delId);
                    break;

                case 5:
                    System.out.print("Masukkan ID Buku: ");
                    int searchId = sc.nextInt();
                    sc.nextLine();
                    Book bSearch = dao.getBookById(searchId);
                    if (bSearch != null) {
                        System.out.println(bSearch);
                    } else {
                        System.out.println("Buku tidak ditemukan.");
                    }
                    break;

                case 0:
                    System.out.println("Keluar...");
                    break;

                default:
                    System.out.println("Pilihan salah!");
            }
        } while (choice != 0);

        sc.close();
    }
}