//Personal.java

package personal;

public class Personal {
    
    private String name;
    private String address;
    private String telephoneNumber;
    private String emailAddress;
    
    public Personal(){
        this.name="";
        this.address="";
        this.telephoneNumber="";
        this.emailAddress="";        
    }
    
    public Personal(String name, String address, String telephoneNumber, String emailAddress){
        this.name = name;
        this.address=address;
        this.telephoneNumber=telephoneNumber;
        this.emailAddress=emailAddress; 
    }
    
    public boolean isEqual(Personal other){
        boolean ret = false;
        
        if(this.name.compareTo(other.name)==0 && this.address.compareTo(other.address)==0 && this.telephoneNumber.compareTo(
        other.address)==0 && this.emailAddress.compareTo(other.address)==0){
            ret=true;
        }
        return ret;
    }
    
    public boolean isNotEqual(Personal other){
        boolean ret=false;
        
        if(this.name.compareTo(other.name)!=0 || this.address.compareTo(other.address)!=0 || this.telephoneNumber.compareTo(other.telephoneNumber)!=0
                || this.emailAddress.compareTo(other.emailAddress)!=0){
            ret=true;
        }
        return ret; 
    }
    
    public String getName(){
        String name=new String(this.name);
        return name;
    }
    
    public String getAddress(){
        String address=new String(this.address);
        return address;
    }
    
    public String getTelephoneNumber(){
        String telephoneNumber=new String(this.telephoneNumber);
        return telephoneNumber;
    }
    
    public String getEmailAddress(){
        String emailAddress=new String(this.emailAddress);
        return emailAddress; 
    }
        public static void main(String[] args) {        
    }
}
