
package com.mycompany.loginu;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author osmar
 */
public class SalesReport extends javax.swing.JFrame {

     ToolBox tb = new ToolBox();
    /**
     * Creates new form SaleTable
     */
    public SalesReport() {
        initComponents();
        tb.readCsvSalesRepo();
        showTable();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Go back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1023, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(55, 55, 55))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jButton1)
                .addGap(32, 32, 32))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    
    private void showTable(){
        String header [] = {"Seller","SubTotal NO VAT","SubTotal VAT","Descount Q.","Discount Type","Date","CX","NIT","Address"};
        
        DefaultTableModel tb = new DefaultTableModel(header,ProjectU.stockT.size());
        jTable1.setModel(tb);
        
        TableModel tbl = jTable1.getModel();
        
        for (int i = 0; i < ProjectU.stockT.size(); i++) {
            
            StockTaking tk = ProjectU.stockT.get(i);
            
            tbl.setValueAt(tk.getSeller(), i, 0);
            tbl.setValueAt(tk.getNoVat(), i, 1);
            tbl.setValueAt(String.valueOf(tk.getTotal()), i, 2);
            tbl.setValueAt(tk.getDiscount(), i, 3);
            tbl.setValueAt(tk.getDiscType(), i, 4);
            tbl.setValueAt(tk.getDate(), i, 5);
            tbl.setValueAt(tk.getCustomer(), i,6);
            tbl.setValueAt(tk.getNIT(), i, 7);
            tbl.setValueAt(tk.getAddress(), i, 8);
            
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
