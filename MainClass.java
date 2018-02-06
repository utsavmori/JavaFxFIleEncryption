import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group; 
import javafx.scene.Scene; 
import javafx.scene.paint.Color; 
import javafx.stage.Stage;  
import javafx.scene.text.Font; 
import javafx.scene.text.Text; 
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;

public class MainClass extends Application { 

@Override     
   public void start(Stage primaryStage) throws Exception {     
	   
	   //create texts
	Text t0 = new Text(); 
	   Text t1 = new Text(); 
	   PasswordField pf=new PasswordField();
	   TextField hashField = new TextField();
	   Text t2 = new Text();
	   Text t3 = new Text();
	   Text t4 = new Text();
	   Text t5 = new Text();
	   Text t6 = new Text();
	   Button encryptbutton=new Button();
	   Button decryptbutton=new Button();
	   Button hashgenbut=new Button();
	   
	   
	   t0.setFont(new Font(22));
	   t0.setText("Hash Generation");
	   t0.setX(20); 
	      t0.setY(20); 
	      //Setting font to the text 
	      t1.setFont(new Font(15)); 
	       
	      //setting the position of the text 
	      t1.setX(50); 
	      t1.setY(60);          
	      
	      //Setting the text to be added. 
	      t1.setText("Enter Password"); 
	  
	      pf.setFocusTraversable(true);
	      pf.setPrefWidth(280);
	      pf.setLayoutX(180);
	      pf.setLayoutY(42);
	      
	     
	      hashgenbut.setOnAction(value ->  {
	    	  
	           try {
				hashField.setText(MainClass.GenerateMD5(pf.getText().toString()));
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        });
	      hashgenbut.setText("Generate Hash");
	      hashgenbut.setLayoutX(480);
	      hashgenbut.setLayoutY(42);
	      
	      
	      
	      /*
	       *Second Line Starts 
	       * */
	     
	       
	      //Setting font to the text 
	      t2.setFont(new Font(15)); 
	       
	      //setting the position of the text 
	      t2.setX(50); 
	      t2.setY(90);          
	      
	      //Setting the text to be added. 
	      t2.setText("Hash"); 
	     
	      //Setting the position of the text field 
	      hashField.setLayoutX(180); 
	      hashField.setLayoutY(70); 
	      hashField.setPrefWidth(280);
	     t3.setText("Encryption");
	     t3.setFont(new Font(22));
	     t3.setX(20);
	     t3.setY(140);
	   t4.setText("Below Button Encrypts all files in current Directory");
	   t4.setFont(new Font(12));
	   t4.setX(50);
	     t4.setY(160);
	   
	   encryptbutton.setText("Encrypt Now");
	   encryptbutton.setOnAction(value ->  {
		   try {
			   String hsh=MainClass.GenerateMD5(pf.getText().toString());
			hashField.setText(hsh);
			boolean x=MainClass.Encrypt(hsh);
			if(x==true) {
				Alert al=new Alert(AlertType.INFORMATION);
				al.setTitle("Encryption");
				al.setHeaderText("Encryption Completed");
				al.showAndWait();
			}
			else {
				Alert al=new Alert(AlertType.ERROR);
				al.setTitle("Encryption");
				al.setHeaderText("Encryption not Completed");
				al.showAndWait();
			}
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
           
        });
	   encryptbutton.setLayoutX(300);
	   encryptbutton.setLayoutY(180);
	   
	   //Decryption
	   
	   t5.setText("Decryption");
	     t5.setFont(new Font(22));
	     t5.setX(20);
	     t5.setY(230);
	   t6.setText("Below Button Decrypts all files in current Directory");
	   t6.setFont(new Font(12));
	   t6.setX(50);
	     t6.setY(250);
	   
	   decryptbutton.setText("Decrypt Now");
	   decryptbutton.setOnAction(value ->  {
		   try {
			   String hsh=MainClass.GenerateMD5(pf.getText().toString());
			hashField.setText(hsh);
			try {
				boolean x=MainClass.Decrypt(hsh);
				if(x==true) {
					Alert al=new Alert(AlertType.INFORMATION);
					al.setTitle("Decryption");
					al.setHeaderText("Decryption Completed");
					al.showAndWait();
				}
				else {
					Alert al=new Alert(AlertType.ERROR);
					al.setTitle("Decryption");
					al.setHeaderText("Decryption not Completed");
					al.showAndWait();
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
      });
	   decryptbutton.setLayoutX(300);
	   decryptbutton.setLayoutY(270);
	   
	   
	   
      //creating a Group object 
      Group root = new Group(); 
      
	ObservableList list = root.getChildren();
	list.add(t0);
      list.add(t1);
      list.add(pf);
      list.add(hashgenbut);
      list.add(t2);
      list.add(hashField);
      list.add(t3);
      list.add(t4);
      list.add(encryptbutton);
      list.add(t5);
      list.add(t6);
      list.add(decryptbutton);
      //Creating a Scene by passing the group object, height and width   
      Scene scene = new Scene(root ,700, 400); 
      
      //setting color to the scene 
      scene.setFill(Color.LIGHTGRAY);  
      
      //Setting the title to Stage. 
      primaryStage.setTitle("File Encryptor"); 
   
      //Adding the scene to Stage 
      primaryStage.setScene(scene); 
       
      //Displaying the contents of the stage 
      primaryStage.show(); 
   }    
   public static void main(String args[]){          
      launch(args);     
   }  
   public static String GenerateMD5(String passwd) throws NoSuchAlgorithmException{
	 if(passwd.equals("")) {return passwd;};
	   MessageDigest md = MessageDigest.getInstance("MD5");
       md.update(passwd.getBytes());
     
       return (new BigInteger(1,md.digest()).toString(16));
	   
   }
   public static boolean Encrypt(String hashed_passwd) {
	   //Get List of All files
	  
	   if(hashed_passwd.equals("")) {return false;}
	   File folder = new File(System.getProperty("user.dir"));
		File[] listOfFiles = folder.listFiles();

		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		        String fname=listOfFiles[i].getName();
		        if(fname.toCharArray()[0]=='.') {
		        	continue;
		        }
		       
		        String newfname=fname+".enc";
		        File inputFile = new File(fname);
		    	File encryptedFile = new File(newfname);
		    	Aes.fileProcessor(Cipher.ENCRYPT_MODE,hashed_passwd,inputFile,encryptedFile);
		    	inputFile.delete();
		      } 
		    }
		    File hshfile=new File("Hash.enc");
		    try {
				FileWriter fw=new FileWriter(hshfile);
				fw.write(hashed_passwd);
				fw.close();    
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return true;
		    
	   
   }
   public static boolean Decrypt(String hashed_passwd) throws IOException {
	   //Get List of All files
	  
	   if(hashed_passwd.equals("")) {return false;}
	   File hshfile=new File("Hash.enc");
	   if(hshfile.exists()) {
	   BufferedReader br = new BufferedReader(new FileReader(hshfile));
	   String hashtocompare=br.readLine();
	   br.close();
	   hshfile.delete();
	   if(hashtocompare.equals(hashed_passwd)) {
		   
		   File folder = new File(System.getProperty("user.dir"));
			File[] listOfFiles = folder.listFiles();

			    for (int i = 0; i < listOfFiles.length; i++) {
			      if (listOfFiles[i].isFile()) {
			        String fname=listOfFiles[i].getName();
			        if(fname.toCharArray()[0]=='.') {
			        	continue;
			        }
			       System.out.println(fname);
			        String[] temp=fname.split("\\.");
			       
			        String newfname="";
			        for(int j=0;j<temp.length-1;j++) {
			        	if(j==0) {
			        		newfname+=(temp[j]);
			        	}else {
			        	newfname+="."+temp[j];	
			        	}		        	
			        }
			        System.out.println(newfname);
			        File inputFile = new File(fname);
			    	File encryptedFile = new File(newfname);
			    	Aes.fileProcessor(Cipher.DECRYPT_MODE,hashed_passwd,inputFile,encryptedFile);
			    	inputFile.delete();
			      } 
			    }
			    
		   
	   }
	   }
	  
		 return true;
		    
	   
   }
} 