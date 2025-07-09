import java.util.*;

class Stock {
    String symbol;
    String name;
    double price;

    public Stock(String symbol, String name, double price) {
        this.symbol = symbol;
        this.name = name;
        this.price = price;
    }
}

class Transaction {
    String type;
    Stock stock;
    int quantity;
    double totalPrice;

    public Transaction(String type, Stock stock, int quantity) {
        this.type = type;
        this.stock = stock;
        this.quantity = quantity;
        this.totalPrice = quantity * stock.price;
    }

    public String toString() {
        return type + " " + quantity + " shares of " + stock.symbol + " at ₹" + stock.price + " each. Total: ₹" + totalPrice;
    }
}

class Portfolio {
    Map<String, Integer> holdings = new HashMap<>();
    List<Transaction> history = new ArrayList<>();

    public void buy(Stock stock, int quantity) {
        holdings.put(stock.symbol, holdings.getOrDefault(stock.symbol, 0) + quantity);
        history.add(new Transaction("Bought", stock, quantity));
    }

    public void sell(Stock stock, int quantity) {
        if (holdings.getOrDefault(stock.symbol, 0) >= quantity) {
            holdings.put(stock.symbol, holdings.get(stock.symbol) - quantity);
            history.add(new Transaction("Sold", stock, quantity));
        } else {
            System.out.println("Not enough shares to sell.");
        }
    }

    public void viewPortfolio(Map<String, Stock> market) {
        System.out.println("Your Portfolio:");
        double totalValue = 0;
        for (String symbol : holdings.keySet()) {
            int qty = holdings.get(symbol);
            Stock s = market.get(symbol);
            double value = qty * s.price;
            totalValue += value;
            System.out.println(symbol + " - " + qty + " shares @ ₹" + s.price + " = ₹" + value);
        }
        System.out.println("Total Portfolio Value: ₹" + totalValue);
    }

    public void viewHistory() {
        System.out.println("\nTransaction History:");
        for (Transaction t : history) {
            System.out.println(t);
        }
    }
}

public class StockTradingPlatform {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Sample market stocks
        Map<String, Stock> market = new HashMap<>();
        market.put("TCS", new Stock("TCS", "Tata Consultancy Services", 3600.0));
        market.put("INFY", new Stock("INFY", "Infosys", 1550.0));
        market.put("RELI", new Stock("RELI", "Reliance", 2800.0));

        Portfolio userPortfolio = new Portfolio();

        while (true) {
            System.out.println("\n=== Stock Trading Platform ===");
            System.out.println("1. View Market");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. View Transaction History");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\nAvailable Stocks:");
                    for (Stock stock : market.values()) {
                        System.out.println(stock.symbol + " - " + stock.name + " @ ₹" + stock.price);
                    }
                    break;
                case 2:
                    System.out.print("Enter stock symbol to buy: ");
                    String buySymbol = sc.next().toUpperCase();
                    if (market.containsKey(buySymbol)) {
                        System.out.print("Enter quantity: ");
                        int qty = sc.nextInt();
                        userPortfolio.buy(market.get(buySymbol), qty);
                        System.out.println("Stock purchased successfully!");
                    } else {
                        System.out.println("Invalid symbol.");
                    }
                    break;
                case 3:
                    System.out.print("Enter stock symbol to sell: ");
                    String sellSymbol = sc.next().toUpperCase();
                    if (market.containsKey(sellSymbol)) {
                        System.out.print("Enter quantity: ");
                        int qty = sc.nextInt();
                        userPortfolio.sell(market.get(sellSymbol), qty);
                    } else {
                        System.out.println("Invalid symbol.");
                    }
                    break;
                case 4:
                    userPortfolio.viewPortfolio(market);
                    break;
                case 5:
                    userPortfolio.viewHistory();
                    break;
                case 6:
                    System.out.println("Exiting... Happy Trading!");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}