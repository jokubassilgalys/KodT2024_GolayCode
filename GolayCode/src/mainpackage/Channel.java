package mainpackage;
import java.util.regex.Pattern;

public class Channel {

    float errorProbability;
    int errorsMade;

    public Channel(float p) {
        errorProbability = p;
        errorsMade = 0;
    }
    //Error probability precision is up to 0.00000001

    public String AddErrors(String vector){
        String transmittedVector = "";
        float randomNumber;

        //Making sure the vector is binary and of the right length
        if(Pattern.matches("[01]{12}", vector)){
            System.out.println("Vector "+vector+" length is incorrect and cannot be transmitted through the channel");
            return "";
        }

        for(int i = 0; i < 23; i++){
            randomNumber = (float) Math.random();
            //System.out.println(randomNumber);

            char currentBit = vector.charAt(i);

            if(randomNumber < errorProbability){
                transmittedVector += Flip(currentBit);
                errorsMade++;
            }
            else {
                transmittedVector += currentBit;
            }
        }

        return transmittedVector;
    }

    private char Flip(char c){
        return (c == '0' ? '1' : '0'); 
    }

    public int getErrorsMade(){
        return errorsMade;
    }
}
