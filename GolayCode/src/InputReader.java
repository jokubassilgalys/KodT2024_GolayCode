import java.util.Scanner;

public class InputReader {
    
    public static float probabilityReader(Scanner scanner) {

        for(;;){
            System.out.println("Enter the error probability of the channel (0 <= p <= 1): ");
            if(scanner.hasNextFloat()){
                float input = scanner.nextFloat();
                
                //Checking if input is within range

                if(input < 0 || input > 1){
                    System.out.println("Probability is not in the range of (0 <= p <= 1) ");
                }
                else {                 
                    //Flushing stdin
                    if(scanner.hasNextLine()){
                        scanner.nextLine();
                    }
                    return input;
                }
            }
            else {
                System.out.println("Bad input");
                scanner.next();
            }
        }
    }

    public static String vectorReader(Scanner scanner) {
        for(;;) {
            System.out.println("Input binary vector: ");
            if(scanner.hasNextLine()){
                
                String s = scanner.nextLine();

                //Clearing whitespaces from the input vector

                s = s.replaceAll("\\s+","");

                //Checking if input vector is binary

                if(!s.isEmpty() && isStringBinary(s)){
                    return s;
                }
                else{
                    System.out.println("Bad input");
                }
            }
        }
    }

    public static int choiceReader(Scanner scanner){
        //Choice for vector outputs 0, text 1

        for(;;) {
            System.out.println("What type of data will be sent through the channel (vector/text): ");
            if(scanner.hasNextLine()){
                
                String s = scanner.nextLine();
                s = s.trim().toLowerCase();

                switch (s) {
                    case "vector":
                        return 0;
                    case "text":
                        return 1;
                    default:
                        System.out.println("Bad input");
                        //scanner.next();
                        break;
                }
            }
        }
    }

    //Untested method :)
    public static String textReader(Scanner scanner) {
        for(;;) {
            System.out.println("Input text: ");
            if(scanner.hasNextLine()){
                
                String s = scanner.nextLine();
                
                String binaryText = "";
                for (char character : s.toCharArray()) {
                    
                    //Converting the character to its ASCII code
                    int ascii = (int) character;

                    //Converting the ASCII code to its binary representation and appending it

                    String binaryCharacter = Integer.toBinaryString(ascii);
                    binaryText += binaryCharacter;
                }

                //System.out.println(binaryText);
                return binaryText;
            }
        }
    }


    private static boolean isStringBinary(String str) {
        for(char c : str.toCharArray()){
            if(c != '0' && c != '1'){
                return false;
            }
        }
        return true;
    }
}
