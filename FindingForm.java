//FindingForm.java

package findingForm;

import addressBookForm.AddressBookForm;
import javax.swing.table.DefaultTableModel;
import personal.Personal;


public class FindingForm extends javax.swing.JFrame {
    private AddressBookForm addressBookForm;
    private int[] indexes;
    
    public FindingForm(AddressBookForm addressBookForm) {
        initComponents();
        this.addressBookForm=addressBookForm;
        this.indexes=null;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jInternalFrame1 = new javax.swing.JInternalFrame();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        NAME = new javax.swing.JTextField();
        FIND = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        PERSONALS = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jInternalFrame1.setClosable(true);
        jInternalFrame1.setIconifiable(true);
        jInternalFrame1.setMaximizable(true);
        jInternalFrame1.setResizable(true);
        jInternalFrame1.setTitle("찾기");
        jInternalFrame1.setVisible(true);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("조건"));

        jLabel1.setText("성명");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NAME, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(NAME, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        FIND.setText("찾기");
        FIND.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FINDActionPerformed(evt);
            }
        });

        PERSONALS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "번호", "성명", "주소", "전화번호", "이메일주소"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        PERSONALS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PERSONALSMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(PERSONALS);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(FIND, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(FIND))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

  
    private void FINDActionPerformed(java.awt.event.ActionEvent evt) {                                     
        String name;
        DefaultTableModel model;
        int i=0;
        Personal personal;
        String address;
        String telephoneNumber;
        String emailAddress;
        Object[] personals=new Object[5];
        
        //1. 찾기 버튼을 클릭했을 때
          //1.1. 성명을 읽는다.
          name=NAME.getText();
          
          //1.2. 주소록 윈도우를 찾는다.
          //1.3. 주소록 윈도우의 주소록에서 찾는다.
          this.indexes=this.addressBookForm.addressBook.find(name);
          
          //1.4. JTable의 모든 항목을 지운다.
          model=(DefaultTableModel)PERSONALS.getModel();
          model.setNumRows(0);
          
          //1.5 찾은 개수만큼 JTable에 항목을 추가한다.
          while(i<this.indexes.length){
              personal=this.addressBookForm.addressBook.getAt(this.indexes[i]);
              
              name = personal.getName();
              address=personal.getAddress();
              telephoneNumber=personal.getTelephoneNumber();
              emailAddress=personal.getEmailAddress();
              
              personals[0]=i+1;
              personals[1]=name;
              personals[2]=address;
              personals[3]=telephoneNumber;
              personals[4]=emailAddress;
              
              model.addRow(personals);
              i++;           
          } 
    }                                    

    private void PERSONALSMouseClicked(java.awt.event.MouseEvent evt) {                                       
        int index;
        String name;
        String address;
        String telephoneNumber;
        String emailAddress;
        
        //2. 항목을 더블클릭 했을 때
          //2.1 JTable의 번호를 읽는다.
          index=PERSONALS.getSelectedRow();
          
          //2.2 읽은 JTable 항목의 성명, 주소, 전화번호, 이메일 주소를 읽는다.
          name=(String)PERSONALS.getValueAt(index,1);
          address=(String)PERSONALS.getValueAt(index, 2);
          telephoneNumber=(String)PERSONALS.getValueAt(index, 3);
          emailAddress=(String)PERSONALS.getValueAt(index, 4);
          
          //2.3 주소록 윈도우를 찾는다.
          //2.4 주소록 윈도우의 개인에 성명, 주소, 전화번호, 이메일 주소를 적는다.
          this.addressBookForm.NAME.setText(name);
          this.addressBookForm.ADDRESS.setText(address);
          this.addressBookForm.TELEPHONENUMBER.setText(telephoneNumber);
          this.addressBookForm.EMAILADDRESS.setText(emailAddress);
          
          //2.5 주소록 윈도우의 JTable에서 해당 항목을 선택한다.
          this.addressBookForm.PERSONALS.setRowSelectionInterval(this.indexes[index], this.indexes[index]);
          
          //2.6 윈도우를 종료한다.
          dispose();   
    }                                      

    
    
   

    // Variables declaration - do not modify                     
    private javax.swing.JButton FIND;
    private javax.swing.JTextField NAME;
    private javax.swing.JTable PERSONALS;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration                   
}
