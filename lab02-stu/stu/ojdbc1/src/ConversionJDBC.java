//Prepared by Terri Wong

import java.io.*;
import java.sql.*;

public class ConversionJDBC {
  public void convertData() throws Exception {

      //Establish the data file connection
    	File file = new File("student.txt");
    	InputStream is = new FileInputStream(file);
    	DataInputStream dis = new DataInputStream(is);
    	
      //Perform read operation
        BufferedReader br = new BufferedReader(new InputStreamReader(dis));    

      //Establish the Oracle DB connection
    	Class.forName ("oracle.jdbc.driver.OracleDriver");
        Connection cn = DriverManager.getConnection
                  
      //Replace your username and password
        ("jdbc:oracle:thin:@oracleacademy.ouhk.edu.hk:8998:db1011", "s1140770", "11407708");

      //Create an object statement to execute SQL update statements i.e. Create and Insert
    	Statement st = cn.createStatement();
        
      //create table student2 using the method of executeUpdate() came from the object Statement
      try{
        try{  
        st.executeUpdate("create table student3 "+
                     "(studID char(8), studName varchar(20), age int, phoneNumber int)");

        }catch (Exception e2){
            st.executeUpdate("delete from student3");
        }
        //declear variables to hold the data from the text file
        Boolean beof = false;
        String stuID = null;
        String stuName = null;
        Integer stuAge = null;
        Integer stuNum = null;
        //print out the data read from the text file and then inserted into table student2

        System.out.println ("Data inserted into the table student3 as:\n\n"); 

        //set up a loop to continuing reading records 
        //i.e. StuID, StuName, and StuAge from the data file  
        do {
          stuID = null;
          stuName = null;
          stuAge = null;
          stuNum = null;
          stuID = formatStudentID(br.readLine());

          if (stuID != null){
                  stuName = formatStudentName(br.readLine());
          }
          else
                  beof = true;

          if (stuName != null){        	
                  stuAge = Integer.parseInt(br.readLine());
          }
          else
                  beof = true;

          if (stuAge != null)
                  stuNum = Integer.parseInt(br.readLine());
          else
                  beof = true;
          
          if (stuNum == null)
                  beof = true;
          
        if (beof != true) {
          //print out the data
          System.out.println (" "+stuID+",  "+stuName+" ,  "+stuAge+" ,  "+stuNum+"\n");  
          
          //commit the transaction
          st.executeUpdate("insert into student3 values ('"+stuID+"', '"+stuName+"', "+stuAge+", "+stuNum+")");
         }
      } while (beof != true);

    } catch (Exception e1) {
      System.err.println("Caught Exception: " + e1.getMessage());
    }

    dis.close();
    cn.close();	
  }
  
  
  //Formatting function for student ID
  public String formatStudentID(String ID){
      String NewID = ID;
      if (ID.length()<8){
          for (int i = ID.length(); i < 8 ; i++){
              NewID = "0" + NewID;
          }
      }
      if (ID.length()>8){
          NewID = ID.substring(0,8);
      }
      return NewID;
  }
  
  //Formatting function for student Name
  public String formatStudentName(String name){
      String fort = name;
      int index;
      if(name.length()>20){
          index = name.indexOf(" ");
          fort = name.substring(0,1)+name.substring(index);
      }
      return fort;
  }
  
  public static void main(String[] args) throws Exception {
    ConversionJDBC student = new ConversionJDBC();
    student.convertData();
  }
}
