package spaceproject;

// @author Duncan Walker

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

 
public class ECFile2022 
{

 public ECFile2022() {}
    
 public Cargo[] loadData(String filename)
  {
    File myFile = new File(filename);
    ArrayList<Cargo> arrList = new ArrayList<>();
    
    try 
     {
       FileReader fr = new FileReader(myFile);
       BufferedReader br = new BufferedReader(fr);
       
       String data="";
       
       while( (data = br.readLine())  != null)
       {
         StringTokenizer st = new StringTokenizer(data,","); 
         
         while (st.hasMoreTokens())
          {
            Cargo cargo = new Cargo();
            
            cargo.setId(st.nextToken().trim());
            cargo.setWeight(Integer.parseInt(st.nextToken().trim()));
            cargo.setContent(st.nextToken().trim());
             
            arrList.add(cargo);
  
          }         
       }
       br.close();
       
       
     }
    catch(IOException e)
     {
         System.out.println("Error loading data");
         System.out.println(e);

     }
      
    System.out.println(arrList.size()+ " Cargo items on file ");
    
   Cargo result[] = new Cargo[arrList.size()];
    result = arrList.toArray(result);
    
    return result;
  }
 
}
  
 

