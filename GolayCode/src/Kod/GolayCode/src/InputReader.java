import java.util.Scanner;

public class InputReader {
    
    public static float probabilityReader(Scanner scanner, String msg) {

        for(;;){
            System.out.println(msg);
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

    public static String vectorReader(Scanner scanner, String msg) {
        for(;;) {
            System.out.println(msg);
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

    public static int choiceReader(Scanner scanner, String msg){
        //Choice for vector outputs 0, text 1

        for(;;) {
            System.out.println(msg);
            if(scanner.hasNextLine()){
                
                String s = scanner.nextLine();

                //Input cleanup
                s = s.trim().toLowerCase();

                switch (s) {
                    case "vector", "v":
                        return 0;
                    case "text", "t":
                        return 1;
                    /*case "image", "i":
                        return 2;*/
                    default:
                        System.out.println("Bad input");
                        break;
                }
            }
        }
    }

    //Untested method :)
    public static String textReader(Scanner scanner, String msg) {
        for(;;) {
            System.out.println(msg);
            if(scanner.hasNextLine()){
                
                String s = scanner.nextLine();
                
                String binaryText = "";
                for (char character : s.toCharArray()) {
                    
                    //Converting the character to its ASCII code
                    int ascii = (int) character;

                    //Converting the ASCII code to its binary representation and appending it

                    String binaryCharacter = Integer.toBinaryString(ascii);
                    binaryCharacter = String.format("%8s", binaryCharacter).replace(' ', '0');
                    binaryText += binaryCharacter;

                    //System.out.println("c:"+character+" ascii:"+ascii+" bchar:"+binaryCharacter);
                }

                
                return binaryText;
            }
        }
    }

    public static Boolean boolReader(Scanner scanner, String msg){
        for(;;) {
            System.out.println(msg);
            if(scanner.hasNextLine()){
                
                String s = scanner.nextLine();

                //Input cleanup
                s = s.trim().toLowerCase();

                switch (s) {
                    case "no", "n":
                        return false;
                    case "yes", "y":
                        return true;
                    default:
                        System.out.println("Bad input");
                        break;
                }
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
