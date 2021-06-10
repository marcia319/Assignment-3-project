package za.ac.cput.assignment3project;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Hashtable;

/**
 * Marcia Zanele Bika 09/06/2021 211054356
 */
public class ReadStakeholderSer {

    ObjectInputStream input;
    int counterCustomer;
    int counterSupplier;
    
    Hashtable<String, String> age_dict = new Hashtable<String, String>();

    ArrayList<Customer> cust;
    ArrayList<Supplier> sup;

    public ReadStakeholderSer() {
        cust = new ArrayList<>();
        sup = new ArrayList<>();
        counterCustomer = 0;
        counterSupplier = 0;

    }

    public void readFromFile() throws IOException, ClassNotFoundException {
        try {
            input = new ObjectInputStream(new FileInputStream("Stakeholder.ser"));
            while (true) {
                Object inputRead = input.readObject();
                String type = ("" + inputRead).split(" ")[0];
                if (type.charAt(0) == 'S') {
                    Supplier supp = (Supplier) inputRead;
                    sup.add(supp);
                    counterSupplier++;
                } else {
                    Customer custo = (Customer) inputRead;
                    cust.add(custo);
                    counterCustomer++;
                }
            }
        } catch (ClassNotFoundException cnfe) {
            System.out.println(cnfe.getMessage());
        } catch (EOFException e) {
            System.out.println("EOF has been reached");
            input.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void displayCustomer() {
        System.out.println("-------------Customers------------");
        for (Customer c : cust) {
            System.out.println(c);
        }
    }

    public void sortCustomer() {
        System.out.println("Sorting the Customers");
        for (int i = 0; i < counterCustomer - 1; i++) {
            for (int j = i + 1; j < counterCustomer; j++) {
                if (cust.get(i).getStHolderId().compareTo(cust.get(j).getStHolderId()) > 0) {
                    Customer temp = cust.get(i);
                    cust.set(i, cust.get(j));
                    cust.set(j, temp);
                }
            }

        }

    }

    public void setCustomersAge() {
        System.out.println("Getting customers age");
        for (Customer ct: cust){
            String DOB[] = ct.getDateOfBirth().split("-");
            int dobYear = Integer.parseInt(DOB[0]);
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            int age = currentYear - dobYear;
            age_dict.put(ct.getStHolderId(), "" + age);
        }
    }

    public void reformatDOB() {
        System.out.println("Reformarting customers Date of birth");
        for (Customer ct : cust) {
            String DOB[] = ct.getDateOfBirth().split("-");
            String month;
            switch (DOB[1]) {
                case "01":
                    month = "Jan";
                    break;
                case "02":
                    month = "Feb";
                    break;
                case "03":
                    month = "Mar";
                    break;
                case "04":
                    month = "Apr";
                    break;
                case "05":
                    month = "May";
                    break;
                case "06":
                    month = "Jun";
                    break;
                case "07":
                    month = "Jul";
                    break;
                case "08":
                    month = "Aug";
                    break;
                case "09":
                    month = "Sep";
                    break;
                case "10":
                    month = "Oct";
                    break;
                case "11":
                    month = "Nov";
                    break;
                case "12":
                    month = "Dec";
                    break;
                default:
                    month = "Not found";

            }
            String newDOB = DOB[2] + " " + month + " " + DOB[0];
            ct.setDateOfBirth(newDOB);
        }
    }

    public void data() {
        System.out.println("------Customer data--------");
        String data = "";
        data += "============================ CUSTOMERS ================================\n";
        data += "ID\tName\tSurname\t\tDate of Birth\tAge\n";
        data += "=======================================================================\n";

        for (Customer c : cust) {
            if (c.getSurName().length() < 8) {
                data += c.getStHolderId() + "\t" + c.getFirstName() + "\t" + c.getSurName() + "\t\t" + c.getDateOfBirth() + "\t" + age_dict.get(c.getStHolderId()) + "\n";
            } else {
                data += c.getStHolderId() + "\t" + c.getFirstName() + "\t" + c.getSurName() + "\t" + c.getDateOfBirth() + "\t" + age_dict.get(c.getStHolderId()) + "\n";
            }
        }
        int index = 0;
        for (Customer c : cust) {
            if (c.getCanRent() == true) {
                index++;
            }
        }
        data += "\nNumber of customers who can rent: " + index + "\n";
        data += "Number of custommers who cannot rent: " + (cust.size() - index) + "\n";
        try {
            FileWriter fw = new FileWriter("customerOutFile.txt");
            PrintWriter pw = new PrintWriter(fw);
            pw.write(data);
            pw.close();
            fw.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    public void displaySupplier() {
        System.out.println("=================Suppliers===============");
        for (Supplier s : sup) {
            System.out.println(s);
        }
    }

    public void sortSupplier() {
        System.out.println("Sorting Suppliers");
        for (int i = 0; i < counterSupplier - 1; i++) {
            for (int j = i + 1; j < counterSupplier; j++) {
                if (sup.get(i).getName().compareTo(sup.get(j).getName()) > 0) {
                    Supplier supplier = sup.get(i);
                    sup.set(i, sup.get(j));
                    sup.set(j, supplier);
                }
            }
        }
    }

    public void supplierData() {
        String data = "";
        String out = String.format("%-15s %-20s %-15s%-15s", "ID", "Name", "Product Type", "Description");
        data += "============================ SUPPLIERS ================================\n";
        data += out + "\n";
        data += "=======================================================================\n";
        System.out.println("------------------------------");
        for (Supplier supplier : sup) {
            out = String.format("%-15s %-20s %-15s%-15s", supplier.getStHolderId(), supplier.getName(), supplier.getProductType(), supplier.getProductDescription());
            data += out + "\n";
        }
        try {
            FileWriter fw = new FileWriter("supplierOutFile.txt");
            PrintWriter pw = new PrintWriter(fw);
            pw.write(data);
            pw.close();
            fw.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ReadStakeholderSer r = new ReadStakeholderSer();
        r.readFromFile();
        r.displayCustomer();
        r.setCustomersAge();
        r.reformatDOB();
        r.sortCustomer();
        r.data();
        r.displaySupplier();
        r.sortSupplier();
        r.supplierData();

    }
}
