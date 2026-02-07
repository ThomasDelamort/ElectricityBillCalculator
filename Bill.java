import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Bill extends JFrame implements ActionListener {
    JLabel customerName, unitsConsumed;
    JTextField name, inputUnits;

    JPanel inputPanel = new JPanel();
    JPanel customerType = new JPanel();
    JPanel discountsPanel = new JPanel();

    ButtonGroup group = new ButtonGroup();
    JRadioButton residential, commercial;
    JCheckBox senior, loyalty;

    JButton Submit = new JButton("Submit");
    JButton close = new JButton("Close");

    public Bill() {
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 0, 10, 10));
        setLocationRelativeTo(null);
        setResizable(false);

        customerName = new JLabel("Customer Name:");
        name = new JTextField(10);
        unitsConsumed = new JLabel("Units Consumed (kWh):");
        inputUnits = new JTextField(10);

        inputPanel.setLayout(new GridLayout(2, 2, 10, 3));
        inputPanel.add(customerName);
        inputPanel.add(name);
        inputPanel.add(unitsConsumed);
        inputPanel.add(inputUnits);
        add(inputPanel);

        residential = new JRadioButton("Residential");
        commercial = new JRadioButton("Commercial");
        group.add(residential); group.add(commercial);

        customerType.setLayout(new GridLayout(1, 2));
        customerType.add(residential); customerType.add(commercial);
        customerType.setBorder(BorderFactory.createTitledBorder("Customer Type: "));
        add(customerType);

        senior = new JCheckBox("Senior Citizen (20%)");
        loyalty = new JCheckBox("Loyalty Discount (10%)");
        discountsPanel.setLayout(new GridLayout(2, 2, 5, 5));
        discountsPanel.add(senior); discountsPanel.add(loyalty);
        discountsPanel.add(Submit); discountsPanel.add(close);
        discountsPanel.setBorder(BorderFactory.createTitledBorder("Discounts:"));
        add(discountsPanel);

        Submit.addActionListener(this);
        close.addActionListener(this);

        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == close) {
            System.exit(0);
        }
        if (e.getSource() == Submit) {
            String customer = name.getText().trim();
            String unitsText = inputUnits.getText().trim();
            
            String type = "";
            double baseBill = 0;
            double surcharge = 0;
            double units = 0;
            double discount = 0;
            String dis = "none";
            double finalBill = 0;

            if (customer.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Customer name must NOT be empty",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                units = Double.parseDouble(unitsText);
                if (units <= 0) {
                    JOptionPane.showMessageDialog(this, "Must input a number greater than 0", "", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ni) {
                JOptionPane.showMessageDialog(this, "Number not valid.", "", JOptionPane.ERROR_MESSAGE);
            }
            if (!residential.isSelected() && !commercial.isSelected()) {
                JOptionPane.showMessageDialog(this, "Must select customer type", "", JOptionPane.ERROR_MESSAGE);
            } else {
                if (residential.isSelected()) {
                    type = "Residential";
                    baseBill = units * 6.50;
                } else {
                    type = "Commercial";
                    baseBill = units * 8.75;
                }
                if (units > 500) {
                    surcharge = baseBill * .05;
                }
                if (senior.isSelected()) {
                    dis = "20%";
                    discount = baseBill * 0.2;
                }
                if (loyalty.isSelected()) {
                    dis = "10%";
                    discount = baseBill * 0.1;
                }
                if (senior.isSelected() && loyalty.isSelected()) {
                    dis = "30%";
                    discount = baseBill * 0.3;
                }
                finalBill = baseBill + surcharge - discount;

                String summary = "Customer Name: "+customer+
                        "\nCustomer Type: "+type+
                        "\nUnits Consumed: "+units+" kWh"+
                        "\nBase Bill: ₱"+String.format("%.2f", baseBill)+
                        "\nSurcharge: ₱"+String.format("%.2f", surcharge)+
                        "\nDiscount Applied: "+dis+
                        "\nFinal Bill: ₱"+String.format("%.2f", finalBill);

                JOptionPane.showMessageDialog(this, summary, "Electricity Bill Summary", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    public static void main (String[] args) {
        new Bill();
    }
}
