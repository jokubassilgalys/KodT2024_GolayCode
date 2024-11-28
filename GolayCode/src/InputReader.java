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
                    /*if(s.length() == 12){
                        return s;
                    }
                    else {
                        System.out.println("Vector length is not 12");
                    }*/
                    return s;
                }
                else{
                    System.out.println("Bad input");
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
