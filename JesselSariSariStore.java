import java.util.*;

public class JesselSariSariStore {

    static Scanner scanner = new Scanner(System.in);

    // Product data
    static String[] names  = {"Coca-Cola", "Lucky Me!", "Ligo Sardines", "Boy Bawang", "Mineral Water", "Century Tuna", "Nova", "Colgate"};
    static double[] prices = {65.00, 12.00, 23.00, 12.00, 8.00, 28.00, 25.00, 18.00};
    static int[]    stocks = {20, 50, 30, 20, 40, 25, 15, 20};

    // Cart: productIndex -> quantity
    static Map<Integer, Integer> cart = new LinkedHashMap<>();

    public static void main(String[] args) {
        System.out.println("\n  ★  JESSEL SARI SARI STORE  ★");
        System.out.println("  Welcome! Kumusta ka? 😊\n");

        while (true) {
            System.out.println("  [1] View Products  [2] Add to Cart  [3] View Cart  [4] Checkout  [0] Exit");
            System.out.print("  Choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> viewProducts();
                case "2" -> addToCart();
                case "3" -> viewCart();
                case "4" -> checkout();
                case "0" -> { System.out.println("\n  Salamat! Hanggang muli! 👋\n"); return; }
                default  -> System.out.println("  Invalid choice.\n");
            }
        }
    }

    static void viewProducts() {
        System.out.println("\n  # | Product          | Price    | Stock");
        System.out.println("  " + "-".repeat(42));
        for (int i = 0; i < names.length; i++) {
            System.out.printf("  %d | %-16s | ₱%-7.2f | %d%n", i + 1, names[i], prices[i], stocks[i]);
        }
        System.out.println();
    }

    static void addToCart() {
        viewProducts();
        System.out.print("  Enter product number: ");
        int index = Integer.parseInt(scanner.nextLine().trim()) - 1;

        if (index < 0 || index >= names.length) { System.out.println("  Invalid product.\n"); return; }

        System.out.print("  Enter quantity: ");
        int qty = Integer.parseInt(scanner.nextLine().trim());

        if (qty > stocks[index]) {
            System.out.println("  Not enough stock! Only " + stocks[index] + " left.\n");
            return;
        }

        cart.merge(index, qty, Integer::sum);
        System.out.println("  ✔ " + names[index] + " x" + qty + " added to cart.\n");
    }

    static void viewCart() {
        if (cart.isEmpty()) { System.out.println("  Cart is empty.\n"); return; }

        System.out.println("\n  --- Your Cart ---");
        double total = 0;
        for (var entry : cart.entrySet()) {
            int i = entry.getKey(), qty = entry.getValue();
            double subtotal = prices[i] * qty;
            System.out.printf("  %-16s x%d = ₱%.2f%n", names[i], qty, subtotal);
            total += subtotal;
        }
        System.out.printf("  TOTAL: ₱%.2f%n%n", total);
    }

    static void checkout() {
        if (cart.isEmpty()) { System.out.println("  Cart is empty!\n"); return; }

        viewCart();
        double total = cart.entrySet().stream()
                .mapToDouble(e -> prices[e.getKey()] * e.getValue()).sum();

        System.out.print("  Enter amount paid: ₱");
        double paid = Double.parseDouble(scanner.nextLine().trim());

        if (paid < total) { System.out.printf("  Not enough! Need ₱%.2f more.%n%n", total - paid); return; }

        // Deduct stock
        cart.forEach((i, qty) -> stocks[i] -= qty);

        System.out.println("\n  ========== RECEIPT ==========");
        System.out.println("  ★  JESSEL SARI SARI STORE  ★");
        System.out.println("  ==============================");
        cart.forEach((i, qty) ->
            System.out.printf("  %-16s x%d = ₱%.2f%n", names[i], qty, prices[i] * qty));
        System.out.println("  ------------------------------");
        System.out.printf("  TOTAL      : ₱%.2f%n", total);
        System.out.printf("  PAID       : ₱%.2f%n", paid);
        System.out.printf("  CHANGE     : ₱%.2f%n", paid - total);
        System.out.println("  ==============================");
        System.out.println("  Salamat! Please come again 😊\n");

        cart.clear();
    }
}
