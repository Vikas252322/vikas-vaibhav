import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Vector;

class ProductGUI {
    private String name;
    private String category;
    private double price;

    public ProductGUI(String name, String category, double price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + " - ₹" + price;
    }
}

public class VV418008 {  // Class name matches the file name
    private static Vector<ProductGUI> supplements = new Vector<>();
    private static Vector<ProductGUI> equipment = new Vector<>();
    private static Vector<ProductGUI> purchasedProducts = new Vector<>();
    private static double totalAmount = 0;

    private JFrame frame;
    private JTextArea displayArea;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VV418008::new);  // Make sure VV418008 is used here
    }

    public VV418008() {  // Constructor name matches the class name
        createGUI();
        loadProducts();
    }

    private void createGUI() {
        frame = new JFrame("Gym Product Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        displayArea = new JTextArea();
        displayArea.setFont(new Font("Arial", Font.PLAIN, 16));
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 3, 10, 10));

        JButton supplementsButton = new JButton("Select Supplements");
        JButton equipmentButton = new JButton("Select Equipment");
        JButton addButton = new JButton("Add New Product");
        JButton discountButton = new JButton("Apply Discount");
        JButton cancelButton = new JButton("Cancel a Product");
        JButton exitButton = new JButton("Exit");

        Dimension buttonSize = new Dimension(200, 60);
        supplementsButton.setPreferredSize(buttonSize);
        equipmentButton.setPreferredSize(buttonSize);
        addButton.setPreferredSize(buttonSize);
        discountButton.setPreferredSize(buttonSize);
        cancelButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);

        supplementsButton.addActionListener(this::selectSupplements);
        equipmentButton.addActionListener(this::selectEquipment);
        addButton.addActionListener(this::addNewProduct);
        discountButton.addActionListener(this::applyDiscount);
        cancelButton.addActionListener(this::cancelProduct);
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(supplementsButton);
        buttonPanel.add(equipmentButton);
        buttonPanel.add(addButton);
        buttonPanel.add(discountButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(exitButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void loadProducts() {
        // Hardcoded data for supplements (at least 5 products)
        supplements.add(new ProductGUI("Whey Protein", "Supplement", 1500.0));
        supplements.add(new ProductGUI("BCAA", "Supplement", 1200.0));
        supplements.add(new ProductGUI("Multivitamins", "Supplement", 800.0));
        supplements.add(new ProductGUI("Creatine", "Supplement", 1000.0));
        supplements.add(new ProductGUI("Pre-workout", "Supplement", 1400.0));

        // Hardcoded data for equipment (at least 5 products)
        equipment.add(new ProductGUI("Treadmill", "Equipment", 35000.0));
        equipment.add(new ProductGUI("Dumbbells", "Equipment", 5000.0));
        equipment.add(new ProductGUI("Exercise Bike", "Equipment", 25000.0));
        equipment.add(new ProductGUI("Weight Bench", "Equipment", 15000.0));
        equipment.add(new ProductGUI("Elliptical Trainer", "Equipment", 27000.0));

        displayArea.append("Products loaded successfully.\n");
    }

    private void selectSupplements(ActionEvent e) {
        selectProducts(supplements);
    }

    private void selectEquipment(ActionEvent e) {
        selectProducts(equipment);
    }

    private void selectProducts(Vector<ProductGUI> products) {
        displayArea.append("\nAvailable Products:\n");
        for (int i = 0; i < products.size(); i++) {
            displayArea.append((i + 1) + ". " + products.get(i) + "\n");
        }
        String input = JOptionPane.showInputDialog(frame, "Choose product numbers to purchase (comma separated):");
        if (input != null && !input.trim().isEmpty()) {
            String[] choices = input.split(",");
            for (String choice : choices) {
                try {
                    int index = Integer.parseInt(choice.trim()) - 1;
                    if (index >= 0 && index < products.size()) {
                        ProductGUI selectedProduct = products.get(index);
                        purchasedProducts.add(selectedProduct);
                        totalAmount += selectedProduct.getPrice();
                        displayArea.append("Purchased: " + selectedProduct + "\n");
                    } else {
                        displayArea.append("Invalid choice: " + choice + "\n");
                    }
                } catch (NumberFormatException ex) {
                    displayArea.append("Invalid input: " + choice + "\n");
                }
            }
        }
    }

    private void addNewProduct(ActionEvent e) {
        String[] options = {"Supplement", "Equipment"};
        int categoryOption = JOptionPane.showOptionDialog(frame, "Select category to add new product:",
                "Category", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (categoryOption >= 0) {
            String category = options[categoryOption];
            String name = JOptionPane.showInputDialog(frame, "Enter product name:");
            String priceInput = JOptionPane.showInputDialog(frame, "Enter product price:");
            try {
                double price = Double.parseDouble(priceInput);
                ProductGUI newProduct = new ProductGUI(name, category, price);

                if (category.equalsIgnoreCase("Supplement")) {
                    supplements.add(newProduct);
                } else if (category.equalsIgnoreCase("Equipment")) {
                    equipment.add(newProduct);
                }
                displayArea.append("Product added successfully.\n");
            } catch (NumberFormatException ex) {
                displayArea.append("Invalid price.\n");
            }
        }
    }

    private void applyDiscount(ActionEvent e) {
        String input = JOptionPane.showInputDialog(frame, "Enter discount percentage:");
        try {
            double discount = Double.parseDouble(input);
            totalAmount -= totalAmount * (discount / 100);
            displayArea.append("Discount applied successfully. Total Amount: ₹" + totalAmount + "\n");
        } catch (NumberFormatException ex) {
            displayArea.append("Invalid discount percentage.\n");
        }
    }

    private void cancelProduct(ActionEvent e) {
        displayArea.append("\nPurchased Products:\n");
        for (int i = 0; i < purchasedProducts.size(); i++) {
            displayArea.append((i + 1) + ". " + purchasedProducts.get(i) + "\n");
        }
        String input = JOptionPane.showInputDialog(frame, "Choose a product number to cancel:");
        try {
            int choice = Integer.parseInt(input) - 1;
            if (choice >= 0 && choice < purchasedProducts.size()) {
                ProductGUI cancelledProduct = purchasedProducts.remove(choice);
                totalAmount -= cancelledProduct.getPrice();
                displayArea.append("Cancelled: " + cancelledProduct + "\n");
            } else {
                displayArea.append("Invalid choice.\n");
            }
        } catch (NumberFormatException ex) {
            displayArea.append("Invalid input.\n");
        }
    }
}
