import objectdraw.*;
import java.awt.*;
import java.util.Scanner;
import java.io.File;

/**
 * Cryptography 
 * Erika Sklaver
 * Section 2
 * 04/10/15
 * 
 * The screen shows text reading Click to Load message and a shift counter equal to zero. When the user clicks on the Click to Load message
 * a message will appear. The user can then click on the shift button which will assign each letter in the message to a new letter in the
 * alphabet to create a code. Once the user decides how much they would like to shift, they may begin clicking on the message and start 
 * coding the message one letter at a time. 
 *
 */
public class Events extends FrameWindowController {
    //text location and size 
    private static final int TEXT_X = 25; 
    private static final int TEXT_Y = 50; 
    private static final int TEXT2_Y = 125;  
    private static final int FONT_SIZE = 24; 
    
    //text names 
    private Text firstLine; 
    private Text secondLine; 
    
    //the number of shifts 
    private int counter = 0; 
    
    //the string that will be coded 
    private String input; 
    
    //remembers whether the user has loaded the method or not
    private boolean messageLoaded = false; 
    
    //maximum amount of shifts 
    private static final int MAX_SHIFT = 25; 
    
    //remembers whether the user has changed the shift counter 
    private boolean shiftDone = false; 
    
    //allows the string to change 
    private char oldChar;
    private int intChar; 
    private String newString;
    
    //the index of the letter that is being coded 
    private int number = 0; 

    public void begin() {
       //creates the text that is displayed initially
       firstLine = new Text("Click to load message", TEXT_X, TEXT_Y, canvas);
       firstLine.setFontSize(FONT_SIZE); 
       secondLine = new Text("Shift = "+counter, TEXT_X, TEXT2_Y, canvas);
       secondLine.setFontSize(FONT_SIZE);
        
       //allows the text from the text window to be retrieved and displayed 
       input = "";
        try {
           input = new Scanner(new File("message.txt")).nextLine();
        } catch (Exception e){
            //could not read message.txt
            firstLine.setText("Invalid input, try again"); 
        }
       
    }

    public void onMousePress(Location point) {
        
       // when the user clicks on the first line and there is only spaces and upper case letters in the text, the secret
       // text will be displayed 
       if (firstLine.contains(point) && !shiftDone){
          if (isCorrectFormat(input)){
            firstLine.setText("Message: "+ input);
            messageLoaded = true; 
          } else {
            firstLine.setText("Invalid input, try again"); 
          }
       }
        
       //after the secret code has loaded the user can press on the shift button which will increase with every press until 
       //the user decides to stop or reaches 25
       if (secondLine.contains(point) && messageLoaded && counter < MAX_SHIFT && !shiftDone){
          counter++; 
          secondLine.setText("Shift = "+counter); 
       }
         
        //one the secret message has been loaded and the shift counter has been set the user can then begin to code the message
        //if the user clicks on the message, and there are still letters to be coded...
         if (firstLine.contains(point) && number < input.length() && counter >0){
         //if the first character is not a space...    
           if (input.charAt(number) != ' '){
           //finds the letter to be changed 
           oldChar = input.charAt(number); 
           //gives the intger value for that letter
           intChar = (int)oldChar;
           //the integer value of that letter subtracted by the integer value of A
           intChar = intChar - 'A'; 
           //adds the value of the shift counter 
           intChar = intChar + counter; 
           //divides that number by 26 to get a remainder that is a number from 0 to 25
           intChar = intChar%26; 
           //puts this number back in the range of capital numbers
           char c = (char) (intChar + 'A'); 
           
           //initial setting
           if (number == 0){
            newString = ""+c;
           }else{
            newString = newString + c; 
           }
           
           //creates the string with the changed letter
           firstLine.setText("Message: "+ newString + input.substring(number+1));
   
           //if the first character is a space...
         }else{
           //initial setting 
          if (number == 0){
            newString = ""+ '?'; 
          }else{
            newString = newString + '?';  
          }
          
          //creates the string with the changed letter
          firstLine.setText("Message: "+ newString + input.substring(number+1));
        
           } 
         //a letter has been changed so the shift counter cannot be changed anymore
         shiftDone = true; 
         //the next letter will be coded 
         number++;
       }
      
    } 
    
 
    // helper method:
    // returns whether the given string consists of upper-case letters and spaces.
    private boolean isCorrectFormat(String s) {
        //if the string is empty then it is not the correct format 
        if (s == ""){
         return false;
        }else{
            //if the string is not empty...
            int number = 0; 
            while (number< s.length()){
                //when the index that is being checked is less than the length of the string..
                if ((s.charAt(number) < 'A' || s.charAt(number)>'Z') && s.charAt(number) != ' '){
                 //if the string contains anything other than a capital letter or a space it is not in the correct format    
                 return false; 
                }
                number++; 
            }
        }
        //after every index has been checked and it hasn't been false, it is the correct format    
        return true; 
    
    }
    
}
