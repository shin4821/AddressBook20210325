//AddressBookForm.java

package addressBookForm;

import addressbook.AddressBook;
import javax.swing.*;
import javax.swing.event.*;
import findingForm.*;
import javax.swing.table.DefaultTableModel;
import java.lang.Integer;
import personal.Personal;
import java.sql.*;
import javax.sql.*;
import java.lang.Throwable;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AddressBookForm extends javax.swing.JFrame {

    public final AddressBook addressBook;

    public void load() throws SQLException{
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ADDRESSBOOK", "Sydney","4821");
        Statement stmt = con.createStatement();
        ResultSet rs=stmt.executeQuery("SELECT Personal.name, Personal.address, Personal.telephoneNumber, Personal.emailAddress FROM Personal;");
       
        try {
            String name = null;
            String address=null;
            String telephoneNumber=null;
            String emailAddress=null;

            while(rs.next()){
                name=rs.getString("name");
                address=rs.getString("address");
                telephoneNumber=rs.getString("telephoneNumber");
                emailAddress=rs.getString("emailAddress");
                
                this.addressBook.record(name, address, telephoneNumber, emailAddress);                
            }
            
        }     
        catch(NullPointerException e){
            System.out.println("실행중 예외발생1");
        }
        finally{
            if(rs!=null){
               try{
                   rs.close();
               }
               catch(SQLException e){
                   System.out.println("실행중 예외발생2");
               }    
            }
           
            if(stmt!=null){
                   try{
                   stmt.close();
               }
               catch(SQLException e){
                   System.out.println("실행중 예외발생3");
               }   
            }
            if(con!=null){
                   try{
                   con.close();
               }
               catch(SQLException e){
                   System.out.println("실행중 예외발생4");
               } 
            } 
            
        }   
    }
    
    public void insert(int index) throws SQLException{
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ADDRESSBOOK", "Sydney","4821");
        Statement stmt = con.createStatement();
        
        try{
            
            String name=null;
            String address=null;
            String telephoneNumber=null;
            String emailAddress=null;
            String code=null;
            String sql=null;
            Personal personal=this.addressBook.getAt(index); 
            
            code=getCode();   
            name=personal.getName();
            address=personal.getAddress();
            telephoneNumber=personal.getTelephoneNumber();
            emailAddress=personal.getEmailAddress();
            
            
            sql=String.format("INSERT INTO Personal(code, name, address, telephoneNumber, emailAddress) VALUES('%s','%s','%s','%s','%s');",
                    code, name, address, telephoneNumber, emailAddress);
            
            stmt.executeUpdate(sql);     

        }
        catch(NullPointerException e){
            System.out.println("실행중 예외발생 1");
        }
        finally{
            if(stmt!=null){
                try{
                    stmt.close();
                }
                catch(SQLException e){
                   System.out.println("실행중 예외발생 2");
               }   
            }
            if(con!=null){
                try{
                    con.close();
                }
                catch(SQLException e){
                    System.out.println("실행중 예외발생 3");
                }
            } 
        }  
    }
    
    public void modify(int index) throws SQLException{
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ADDRESSBOOK","Sydney","4821");
        Statement stmt=con.createStatement();
        ResultSet rs=stmt.executeQuery("SELECT Personal.code FROM Personal;");
        
        try{
            int i=0;
            Personal personal=this.addressBook.getAt(index);
            
            String code=null;
            String sql=null;
            String address=null;
            String telephoneNumber=null;
            String emailAddress=null;

            while(rs.next() && i<=index){
                code=rs.getString("code");
                i++;
            }

            address=personal.getAddress();
            telephoneNumber=personal.getTelephoneNumber();
            emailAddress=personal.getEmailAddress();
            
            sql=String.format("UPDATE Personal SET address='%s', telephoneNumber='%s', emailAddress='%s' WHERE code='%s';",
                    address, telephoneNumber, emailAddress, code);
            stmt.executeUpdate(sql);   
        }
        catch(NullPointerException e){
            System.out.println("실행중 예외발생");
        }
         finally{
            if(stmt!=null){
                try{
                    stmt.close();
                }
                catch(SQLException e){
                   System.out.println("실행중 예외발생");
               }   
            }
            if(con!=null){
                try{
                    con.close();
                }
                catch(SQLException e){
                    System.out.println("실행중 예외발생");
                }
            } 
        }        
    }
    
    public void delete(int index) throws SQLException{
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/ADDRESSBOOK","Sydney","4821");
        Statement stmt=con.createStatement();
        ResultSet rs=stmt.executeQuery("SELECT Personal.code FROM Personal;");
        
        try{
            int i=0;
            String code=null;
            String sql;
            while(rs.next() && i<=index){
                code=rs.getString("code");
                i++;
            }

            
            sql=String.format("DELETE FROM Personal WHERE code='%s';", code);
            stmt.executeUpdate(sql); 
        }
        catch(NullPointerException e){
            System.out.println("실행중 예외발생");
        }
         finally{
            if(stmt!=null){
                try{
                    stmt.close();
                }
                catch(SQLException e){
                   System.out.println("실행중 예외발생");
               }   
            }
            if(con!=null){
                try{
                    con.close();
                }
                catch(SQLException e){
                    System.out.println("실행중 예외발생");
                }
            } 
        } 
    }
    
    public String getCode() throws SQLException{
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/ADDRESSBOOK","Sydney","4821");
        Statement stmt = con.createStatement();
        ResultSet rs=stmt.executeQuery("SELECT Personal.code FROM Personal ORDER BY code DESC");
        String code=null;

        try{
            int number;

            //1. 제일 큰 코드를 찾는다.
            if(rs.next()){
                code=rs.getString("code");
            }
             
            //2. 코드가 없으면 기본 코드를 매긴다.
            else{
                code="P0000";
            }
            //3. 코드(문자열)의 숫자부분만 분리한다.
            code=code.substring(1);
            
            //4. 숫자부분을 숫자로 표현한다.
            number=Integer.parseInt(code);
            
            //5. 숫자를 늘린다.
            number++;         
            
            //6. 해당 정수를 문자열로 반환한다.
            //7. 알파벳과 숫자를 합쳐 코드를 만든다.

            code= String.format("P%04d", number); 

        }
        catch(NullPointerException e){
             System.out.println("실행중 예외발생 -getcode 1");
        }
         finally{
            if(stmt!=null){
                try{
                    stmt.close();
                }
                catch(SQLException e){
                   System.out.println("실행중 예외발생 -getcode 2");
               }   
            }
            if(con!=null){
                try{
                    con.close();
                }
                catch(SQLException e){
                    System.out.println("실행중 예외발생 -getcode 3");
                }
            } 
        } 
        //8. 코드를 출력한다.
        return code;
        
    }
    
    public void save() throws SQLException{
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ADDRESSBOOK", "Sydney","4821");
        Statement stmt=con.createStatement();
        Statement stmt_=con.createStatement();
        ResultSet rs=stmt.executeQuery("SELECT Personal.code FROM Personal");
        
        try{
            Personal personal;
            int i=0;
            String code=null;
            String sql=null;
            String name=null;
            String address=null;
            String telephoneNumber=null;
            String emailAddress=null;
            
            //1. db의 전체데이터를 삭제한다.
            stmt_.executeUpdate("DELETE FROM Personal;");
            
            //2. length보다 작을 동안 반복한다.
            while(rs.next() && i<this.addressBook.getLength()){
                //2.1. 코드를 찾는다,
                code=rs.getString("code");
                
                //2.2. i번째 personal을 db에 입력한다.
                personal=this.addressBook.getAt(i);
                
                name=personal.getName();
                address=personal.getAddress();
                telephoneNumber=personal.getTelephoneNumber();
                emailAddress=personal.getEmailAddress();
                
                sql=String.format("INSERT INTO Personal(code,name, address, telephoneNumber, emailAddress) VALUE('%s','%s','%s','%s','%s');",
                        code, name, address, telephoneNumber, emailAddress);
                stmt_.executeUpdate(sql);
                
                i++;     
            }  
        }
        catch(NullPointerException e){
            System.out.println("실행중 예외발생");
        }
         finally{
            if(stmt!=null){
                try{
                    stmt.close();
                }
                catch(SQLException e){
                   System.out.println("실행중 예외발생");
               }   
            }
            if(con!=null){
                try{
                    con.close();
                }
                catch(SQLException e){
                    System.out.println("실행중 예외발생");
                }
            } 
        }   
    }

    public AddressBookForm() throws SQLException {
        initComponents();
        String name;
        String address;
        String telephoneNumber;
        String emailAddress;
        DefaultTableModel model;
        Personal personal;
        int i=0;
        Object[]personals=new Object[5];
        
        //1. 윈도우가 생성될 때
          //1.1 주소록을 만든다.
           this.addressBook = new AddressBook(3);
           
          //1.2 적재한다.
         try {
           load();
          } catch (SQLException ex) {
            Logger.getLogger(AddressBookForm.class.getName()).log(Level.SEVERE, null, ex);
          }
           
           //1.3 적재된 개수만큼 JTable에 항목을 추가한다,
           model=(DefaultTableModel)PERSONALS.getModel();
           while(i<this.addressBook.getLength()){
               personal=this.addressBook.getAt(i);
               name=personal.getName();
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        RECORD = new javax.swing.JButton();
        FIND = new javax.swing.JButton();
        CORRECT = new javax.swing.JButton();
        ERASE = new javax.swing.JButton();
        ARRANGE = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        NAME = new javax.swing.JTextField();
        ADDRESS = new javax.swing.JTextField();
        TELEPHONENUMBER = new javax.swing.JTextField();
        EMAILADDRESS = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        PERSONALS = new javax.swing.JTable();

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
        jScrollPane2.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jInternalFrame1.setClosable(true);
        jInternalFrame1.setIconifiable(true);
        jInternalFrame1.setMaximizable(true);
        jInternalFrame1.setResizable(true);
        jInternalFrame1.setTitle("주소록");
        jInternalFrame1.setToolTipText("");
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

        RECORD.setText("기재하기");
        RECORD.setToolTipText("");
        RECORD.setName(""); // NOI18N
        RECORD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RECORDActionPerformed(evt);
            }
        });

        FIND.setText("찾기");
        FIND.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FINDActionPerformed(evt);
            }
        });

        CORRECT.setText("고치기");
        CORRECT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CORRECTActionPerformed(evt);
            }
        });

        ERASE.setText("지우기");
        ERASE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ERASEActionPerformed(evt);
            }
        });

        ARRANGE.setText("정리하기");
        ARRANGE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ARRANGEActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("개인"));

        jLabel1.setText("성명");

        jLabel2.setText("주소");

        jLabel3.setText("전화번호");

        jLabel4.setText("이메일주소");

        NAME.setActionCommand("<Not Set>");

        ADDRESS.setToolTipText("");
        ADDRESS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ADDRESSActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(20, 20, 20)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(EMAILADDRESS)
                    .addComponent(NAME, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ADDRESS, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TELEPHONENUMBER, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(9, 9, 9))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(NAME, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(ADDRESS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(TELEPHONENUMBER, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(EMAILADDRESS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        PERSONALS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "번호", "성명", "주소", "전화번호", "이메일주소"
            }
        ));
        PERSONALS.setToolTipText("");
        PERSONALS.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        PERSONALS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PERSONALSMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(PERSONALS);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 1, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(RECORD, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FIND, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CORRECT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ERASE, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ARRANGE, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(RECORD)
                        .addGap(6, 6, 6)
                        .addComponent(FIND)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CORRECT)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ERASE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ARRANGE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    private void formWindowOpened(java.awt.event.WindowEvent evt) {                                  

    }                                 

    private void RECORDActionPerformed(java.awt.event.ActionEvent evt) {                                       
        String name;
        String address;
        String telephoneNumber;
        String emailAddress;
        DefaultTableModel model;
        int index;
        int i=0;
        Personal personal = new Personal();
        Object[] personals = new Object[5];
        
        //2. 기재하기 버튼을 클릭했을 때
          //2.1 성명, 주소, 전화번호, 이메일주소를 읽는다.
          name=NAME.getText();
          address=ADDRESS.getText();
          telephoneNumber=TELEPHONENUMBER.getText();
          emailAddress=EMAILADDRESS.getText();
          
          //2.2 주소록에 기재한다.
          index=this.addressBook.record(name, address, telephoneNumber, emailAddress);
          
          // db에 insert한다.(3/25 추가)
          try {
            insert(index);
          } catch (SQLException ex) {
            Logger.getLogger(AddressBookForm.class.getName()).log(Level.SEVERE, null, ex);
          }

          
          personal=this.addressBook.getAt(index);
          
          name=personal.getName();
          address=personal.getAddress();
          telephoneNumber=personal.getTelephoneNumber();
          emailAddress=personal.getEmailAddress();
                 
          personals[0]=index+1;
          personals[1]=name;
          personals[2]=address;
          personals[3]=telephoneNumber;
          personals[4]=emailAddress;
          
          //2.3 JTable에 항목을 추가한다.
          model=(DefaultTableModel)PERSONALS.getModel(); 
          model.addRow(personals);
    }                                      

    private void FINDActionPerformed(java.awt.event.ActionEvent evt) {                                     
          //3. 찾기 버튼을 클릭했을 때
            //3.1 찾기 윈도우를 출력한다.
            FindingForm findingForm= new FindingForm(this);
            findingForm.setVisible(true);
    }                                    

    private void CORRECTActionPerformed(java.awt.event.ActionEvent evt) {                                        
         String address;
         String telephoneNumber;
         String emailAddress;
         int index;
         Personal personal;
         
         //4. 고치기 버튼 클릭했을 때
          //4.1 주소, 전화번호, 이메일 주소를 읽는다.
          address=ADDRESS.getText();
          telephoneNumber=TELEPHONENUMBER.getText();
          emailAddress=EMAILADDRESS.getText();
          
          //4.2 JTable의 번호를 읽는다.
          index=PERSONALS.getSelectedRow();
          
          //4.3 주소록에서 고친다.
          index= this.addressBook.correct(index, address, telephoneNumber, emailAddress);
          
          //(3/26추가) db에서 고친다.
          try {
            modify(index);
          } catch (SQLException ex) {
            Logger.getLogger(AddressBookForm.class.getName()).log(Level.SEVERE, null, ex);
          }
          
          //4.4 JTable에서 항목을 고친다.
          personal=this.addressBook.getAt(index);
          
          address=personal.getAddress();
          PERSONALS.setValueAt(address, index, 2);
          
          telephoneNumber=personal.getTelephoneNumber();
          PERSONALS.setValueAt(telephoneNumber, index, 3);
          
          emailAddress=personal.getEmailAddress();
          PERSONALS.setValueAt(emailAddress, index, 4);
          
    }                                       

    private void PERSONALSMouseClicked(java.awt.event.MouseEvent evt) {                                       
        int index;
        String name;
        String address;
        String telephonenNumber;
        String emailAddress;
        
        //4. 항목을 더블클릭 했을 때
         //4.1 JTable의 번호를 읽는다.
         index=PERSONALS.getSelectedRow();
         
         //4.2 해당 항목의 성명, 주소, 전화번호, 이메일 주소를 읽는다.
         name=(String)PERSONALS.getValueAt(index, 1);
         address=(String)PERSONALS.getValueAt(index,2);
         telephonenNumber=(String)PERSONALS.getValueAt(index,3);
         emailAddress=(String)PERSONALS.getValueAt(index, 4);
         
         //4.3 개인에 성명, 주소, 전화번호, 이메일 주소를 적는다.
         NAME.setText(name);
         ADDRESS.setText(address);
         TELEPHONENUMBER.setText(telephonenNumber);
         EMAILADDRESS.setText(emailAddress);
        
    }                                      
    
    
    private void ERASEActionPerformed(java.awt.event.ActionEvent evt) {                                      
       int index;
       DefaultTableModel model;
       
       //6. 지우기 버튼 클릭했을 때
        //6.1 JTable의 번호를 읽는다.
        index=PERSONALS.getSelectedRow();
        
        //6.2 주소록에서 지운다.
        addressBook.erase(index);
        
        //db에서 지운다.
          try {
            delete(index);
          } catch (SQLException ex) {
            Logger.getLogger(AddressBookForm.class.getName()).log(Level.SEVERE, null, ex);
          }
        
        //6.3 JTable에서 항목을 지운다.
        model=(DefaultTableModel)PERSONALS.getModel();
        model.removeRow(index);
        
        //6.4 지운 JTable 번호의 하위번호에 대하여 1씩 빼서 적는다.
        while(index<addressBook.getLength()){
            PERSONALS.setValueAt(index+1, index, 0);
            index++;
        } 
    }                                     

    private void ARRANGEActionPerformed(java.awt.event.ActionEvent evt) {                                        
        DefaultTableModel model;
        int length;
        int i=0;
        String name;
        String address;
        String telephoneNumber;
        String emailAddress;
        Personal personal;
        Object[] personals=new Object[5];
        
        //7. 정리하기 버튼을 클릭했을 때
         //7.1 주소록을 정리한다.
         this.addressBook.arrange();
         
         //7.2 JTable의 모든 항목을 지운다.
         model=(DefaultTableModel)PERSONALS.getModel();
         model.setNumRows(0);
         
         //7.3. JTable에 주소록의 length만큼 항목을 추가한다.
         while(i<this.addressBook.getLength()){
             personal=this.addressBook.getAt(i);
             
             personals[0]=i+1;
             
             name=personal.getName();
             personals[1]=name;
             
             address=personal.getAddress();
             personals[2]=address;
             
             telephoneNumber=personal.getTelephoneNumber();
             personals[3]=telephoneNumber;
             
             emailAddress=personal.getEmailAddress();
             personals[4]=emailAddress;
             
             model.addRow(personals);
             i++;            
         } 
    }                                       

    private void formWindowClosing(java.awt.event.WindowEvent evt) {                                   
          
        try {
            //8. 닫기 버튼을 클릭했을때(윈도우가 닫힐때)
            //8.1. 저장한다.
            save();
        } catch (SQLException ex) {
            Logger.getLogger(AddressBookForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        //8.2 종료한다.
        System.exit(0);  
    }                                  

    private void ADDRESSActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // TODO add your handling code here:
    }                                       


    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AddressBookForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddressBookForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddressBookForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddressBookForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                
                try {
                    new AddressBookForm().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(AddressBookForm.class.getName()).log(Level.SEVERE, null, ex);
                }       
            }  
        });
        
    }
    
    

    // Variables declaration - do not modify                     
    private javax.swing.JTextField ADDRESS;
    private javax.swing.JButton ARRANGE;
    private javax.swing.JButton CORRECT;
    private javax.swing.JTextField EMAILADDRESS;
    private javax.swing.JButton ERASE;
    private javax.swing.JButton FIND;
    private javax.swing.JTextField NAME;
    private javax.swing.JTable PERSONALS;
    private javax.swing.JButton RECORD;
    private javax.swing.JTextField TELEPHONENUMBER;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    // End of variables declaration                   
}
