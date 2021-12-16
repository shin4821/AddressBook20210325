//AddressBook.java

package addressbook;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import personal.*;

public class AddressBook {
    
    private ArrayList<Personal> personals;
    private int capacity;
    private int length;
    
    
    public AddressBook(int capacity){
        this.personals= new ArrayList<>(capacity);
        this.capacity=capacity;
        this.length=0; 
    }
    

    
    public int record(String name, String address, String telephoneNumber, String emailAddress){
        
        //1. 이름, 주소, 전화번호, 이메일 주소를 입력받는다.
        //2. personal 객체를 만든다.
        Personal personal= new Personal(name, address, telephoneNumber, emailAddress);
        
        //3. length가 capacity보다 작으면 add를 호출한다.
        if(this.length<this.capacity){
            this.personals.add(this.length, personal);
        }
        //4. length가 capacity보다 크거나 같으면 add를 호출한다.
        else if(this.length>=this.capacity){
            this.personals.add(personal);
            this.capacity++;
        }
        this.length++;
        
        return this.length-1; 
    }
    
    public int[] find(String name){
        
        Personal personal;      
        int i=0;
        int j=0;
        int count=0;
        
        while(i<this.length){
            personal=this.personals.get(i);
            if(personal.getName().compareTo(name)==0){
                count++;
            }
            i++;
        }
       
        int[] indexes = new int[count];
        
        i=0;         
        while(i<this.length){
            personal=this.personals.get(i);
            if(personal.getName().compareTo(name)==0){
                indexes[j]=i;
                j++;
            }
            i++;
        }
       return indexes;
    }
    
    public int correct(int index, String address, String telephoneNumber, String emailAddress){
        Personal personal;
        
        //1. index, address, telephoneNumber, emailAddress를 입력받는다.
        //2. index번째의 personal을 찾는다.
        personal=this.personals.get(index);
        
        //3. personal를 수정한다.
        Personal personal_=new Personal(personal.getName(), address, telephoneNumber, emailAddress);     
        this.personals.set(index, personal_);
        
        //4. index를 출력한다.
        return index;
    }
    
    public long erase(int index){
        long index_;
        
        this.personals.remove(index);
        this.capacity--;
        this.length--;
        
        index_=-1;
        return index_;        
    }
    
public void arrange(){
    Collections.sort(this.personals, new Comparator<Personal>(){
        @Override
        public int compare(Personal one, Personal other){
            return one.getName().compareTo(other.getName());
        }
    });   
}
    
    
    
    public Personal getAt(int index){
        Personal personal;
        
        personal= this.personals.get(index);
        return personal;

    }
    
    public int getCapacity(){
        return this.capacity;
    }
    
    public int getLength(){
        return this.length;
    }
    
    public static void main(String[] args) {
        AddressBook addressBook = new AddressBook(3);
        Personal personal = new Personal();
        int[] indexes = null;
        int index;
        long index_;
        int i;

        index=addressBook.record("고길동", "서울시 서초구", "01011112222", "ko@");
        personal=addressBook.getAt(index);
        System.out.printf("%s %s %s %s\n", personal.getName(), personal.getAddress(), personal.getTelephoneNumber(), personal.getEmailAddress());
     
        index=addressBook.record("박길동", "서울시 마포구", "01022223333", "park@");
        personal=addressBook.getAt(index);
        System.out.printf("%s %s %s %s\n", personal.getName(), personal.getAddress(), personal.getTelephoneNumber(), personal.getEmailAddress());
        
        index=addressBook.record("나길동", "인천시 연수구", "01033334444", "Na@");
        personal=addressBook.getAt(index);
        System.out.printf("%s, %s %s %s\n ", personal.getName(), personal.getAddress(), personal.getTelephoneNumber(), personal.getEmailAddress());
        
        index=addressBook.record("박길동", "부산시 해운대구", "01044445555", "park@");
        personal=addressBook.getAt(index);
        System.out.printf("%s, %s %s %s\n ", personal.getName(), personal.getAddress(), personal.getTelephoneNumber(), personal.getEmailAddress());
        
        indexes = addressBook.find("박길동");
        i=0;
        while(i< indexes.length){
            personal=addressBook.getAt(indexes[i]);
            System.out.printf("%s, %s %s %s\n ", personal.getName(), personal.getAddress(), personal.getTelephoneNumber(), 
                    personal.getEmailAddress());
            i++;
        }
        
        System.out.printf("arrange 출력\n");
        addressBook.arrange();
        i=0;
        while(i<addressBook.getLength()){
                personal=addressBook.getAt(i);
                System.out.printf("%s, %s %s %s\n ", personal.getName(), personal.getAddress(), personal.getTelephoneNumber(), 
                    personal.getEmailAddress());
                i++;
        }
        
        

        
        

        

 
        
        
        

    } 
}
