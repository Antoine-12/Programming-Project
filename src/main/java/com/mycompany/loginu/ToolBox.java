package com.mycompany.loginu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class ToolBox {

    public static ArrayList<StockTaking> BookReport = new ArrayList<>();

    public static void cleanjtxt(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }

    public static boolean verPass(String password) {

        boolean pUppercase = false;
        boolean pLowercase = false;
        boolean pNumber = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                pUppercase = true;
            } else if (Character.isLowerCase(c)) {
                pLowercase = true;
            } else if (Character.isDigit(c)) {
                pNumber = true;
            }
        }

        return pUppercase && pLowercase && pNumber;

    }

    public void readUserXML() {
        try {
            ProjectU.users.clear();

            DocumentBuilderFactory read = DocumentBuilderFactory.newInstance();
            DocumentBuilder folder = read.newDocumentBuilder();
            Document doc = folder.parse(new File("UserQuery.xml"));
            doc.getDocumentElement().normalize();

            NodeList lst = doc.getElementsByTagName("user");

            for (int i = 0; i < lst.getLength(); i++) {
                Node nod = lst.item(i);

                if (nod.getNodeType() == Node.ELEMENT_NODE) {
                    Element elmt = (Element) nod;

                    User ur = new User();

                    ur.setName(elmt.getElementsByTagName("name").item(0).getTextContent().trim());
                    ur.setUser(elmt.getElementsByTagName("usr").item(0).getTextContent().trim());
                    ur.setPassword(elmt.getElementsByTagName("password").item(0).getTextContent().trim());
                    ur.setRole(Integer.parseInt(elmt.getElementsByTagName("role").item(0).getTextContent().trim()));

                    ProjectU.users.add(ur);
                }
            }
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(NewUser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(NewUser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NewUser.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void readJsonBooks() {

        try (FileReader read = new FileReader("BookQuery.json")) {

            JSONTokener token = new JSONTokener(read);
            JSONArray bookList = new JSONArray(token);

            ProjectU.books.clear();

            for (Object obj : bookList) {
                JSONObject jsonBook = (JSONObject) obj;

                Book b = new Book();

                b.setAuthor((String) jsonBook.get("author"));
                b.setTitle((String) jsonBook.get("title"));
                b.setPrice((Double.parseDouble(jsonBook.get("price").toString())));
                b.setGenre((String) jsonBook.get("style"));
                b.setStockQuantity((Integer.parseInt(jsonBook.get("amount").toString())));

                ProjectU.books.add(b);
            }

        } catch (IOException ex) {
            System.getLogger(ToolBox.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

    }

    public void writeJsonBooks() {

        JSONArray bookList = new JSONArray();

        for (Book bk : ProjectU.books) {
            JSONObject bookObj = new JSONObject();

            bookObj.put("author", bk.getAuthor());
            bookObj.put("title", bk.getTitle());
            bookObj.put("price", bk.getPrice());
            bookObj.put("style", bk.getGenre());
            bookObj.put("amount", bk.getStockQuantity());

            bookList.put(bookObj);

        }

        try (FileWriter file = new FileWriter("BookQuery.json")) {
            file.write(bookList.toString(6));
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writeCsvSalesRepo() {
        try (BufferedWriter write = new BufferedWriter(new FileWriter("LoadSales.csv", true))) {

            for (StockTaking st : ProjectU.stockT) {
                write.write(st.getSeller() + "," + st.getNoVat() + ", " + st.getTotal() + ", " + st.getDiscount() + "," + st.getDiscType() + ", " + st.getDate()
                        + ", " + st.getCustomer() + ", " + st.getNIT() + ", " + st.getAddress() + "\n");
            }

        } catch (IOException ex) {
            System.getLogger(ToolBox.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

    }

    public void readCsvSalesRepo() {

        try (BufferedReader read = new BufferedReader(new FileReader("LoadSales.csv"))) {
            String line;

            ProjectU.stockT.clear();

            while ((line = read.readLine()) != null) {
                String[] sl = line.split(",");

                if (sl.length >= 9) {
                    StockTaking sk = new StockTaking();
                    sk.setSeller(sl[0].trim());
                    sk.setNoVat(Double.parseDouble(sl[1].trim()));
                    sk.setTotal(Double.parseDouble(sl[2].trim()));
                    sk.setDiscount(Double.parseDouble(sl[3].trim()));
                    sk.setDiscType(sl[4].trim());
                    sk.setDate(sl[5].trim());
                    sk.setCustomer(sl[6].trim());
                    sk.setNIT(sl[7].trim());
                    sk.setAddress(sl[8].trim());

                    ProjectU.stockT.add(sk);

                }

            }

        } catch (IOException ex) {
            System.getLogger(ToolBox.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

    }

    public void readCsvBookRepo() {

        JFileChooser choosefile = new JFileChooser();

        int cxSelection = choosefile.showOpenDialog(null);

        if (cxSelection == JFileChooser.APPROVE_OPTION) {
            File folder = choosefile.getSelectedFile();

            BookReport.clear();

            try (BufferedReader read = new BufferedReader(new FileReader(folder))) {

                String line;

                while ((line = read.readLine()) != null) {
                    String[] data = line.split(",");

                    if (data.length >= 4) {
                        StockTaking st = new StockTaking();
                        st.setDate(data[0].trim());
                        st.setQuantity(Integer.parseInt(data[1].trim()));
                        st.setTitle(data[2].trim());
                        st.setPrice(Double.parseDouble(data[3].trim()));

                        BookReport.add(st);

                    }

                }

            } catch (IOException ex) {
                System.getLogger(ToolBox.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }

        }

    }

    public void writeCsvBookRepo() {
        try (BufferedWriter write = new BufferedWriter(new FileWriter("SoldBookReport.csv", true))) {

            for (StockTaking br : ProjectU.stockT) {
                write.write(br.getDate() + "," + br.getQuantity() + "," + br.getTitle() + "," + br.getPrice() + "\n");
            }

        } catch (IOException ex) {
            System.getLogger(ToolBox.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

    }

    public void writeUsersBinary() {
        try (FileOutputStream fos = new FileOutputStream("users.dat"); 
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(ProjectU.users);
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(ToolBox.class.getName())
                    .log(Level.SEVERE, "Error writing users.dat", ex);
        }
    }

    public void readUsersBinary() {
        try (FileInputStream fis = new FileInputStream("users.dat"); 
                ObjectInputStream ois = new ObjectInputStream(fis)) {

            List<User> loaded = (List<User>) ois.readObject();
            ProjectU.users.clear();
            ProjectU.users.addAll(loaded);

        } catch (IOException | ClassNotFoundException ex) {
        }
    }

    public void writeBooksBinary() {
        try (FileOutputStream fos = new FileOutputStream("books.dat"); 
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(ProjectU.books);
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(ToolBox.class.getName())
                    .log(Level.SEVERE, "Error writing books.dat", ex);
        }
    }

    public void writePromoCodesBinary() {
        try (FileOutputStream fos = new FileOutputStream("promocodes.dat"); 
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(ProjectU.prco);
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(ToolBox.class.getName())
                    .log(Level.SEVERE, "Error writing promocodes.dat", ex);
        }
    }

    public void readPromoCodesBinary() {
        try (FileInputStream fis = new FileInputStream("promocodes.dat"); 
                ObjectInputStream ois = new ObjectInputStream(fis)) {
            List<PromoCode> loaded = (List<PromoCode>) ois.readObject();
            ProjectU.prco.clear();
            ProjectU.prco.addAll(loaded);
        } catch (IOException | ClassNotFoundException ex) {
        }
    }
    
    public void writeSalesBinary() {
        try (FileOutputStream fos = new FileOutputStream("sales.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(ProjectU.stockT);  
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(ToolBox.class.getName())
                  .log(Level.SEVERE, "Error writing sales.dat", ex);
        }
    }

}
