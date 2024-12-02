import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        
        System.out.println("This is a program that encodes and decodes input using Golay Code");
        System.out.println("=================================================================");
        Scanner scanner = new Scanner(System.in);
    

        //Asking user for probability
        float errorProbability = InputReader.probabilityReader(scanner);

        //Asking user for input type
        int choice = InputReader.choiceReader(scanner);

        String inputVector = "";
        switch (choice) {
            case 0:
                //Asking user for vector input

                inputVector = InputReader.vectorReader(scanner);
                break;
            case 1:
                //Asking user for text input

                inputVector = InputReader.textReader(scanner);
                break;
            default:
                throw new AssertionError();
        }

        //Initializing coder, channel and lists to store their results

        Coder coder = new Coder();
        Channel channel = new Channel(errorProbability);
        List<String> codedInputVectorChunks = new ArrayList<String>();
        List<String> transmittedVectorChunks = new ArrayList<String>();

        
        //Calculating how many length 12 vectors will be coded

        int chunkCount = inputVector.length() / 12 + ((inputVector.length() % 12 == 0) ? 0 : 1);
        System.out.println("\nChunk count: "+ chunkCount);

        //Spliting vector into chunks of 12 length

        String inputVectorChunk;
        for(int i = 0; i < chunkCount; i++){
            if(i != chunkCount-1){
                inputVectorChunk = inputVector.substring(i * 12, ((i+1) * 12));
            }
            else{
                inputVectorChunk = inputVector.substring(i * 12);

                //If inputVectorChunk.length != 12 ==> pad out with 0s

                int missingSize = 12-inputVectorChunk.length();
                for(int j = 0; j < missingSize; j++){
                    inputVectorChunk += "0";
                }

            }
            System.out.println("Chunk "+ (i+1) +": \t\t"/*+ inputVectorChunk*/);

            //Coding the vector chunk
            codedInputVectorChunks.add(coder.VectorCoding(inputVectorChunk));

            //Transmitting the vector chunk through the channel
            
            String vectorAfterTransmittion = channel.AddErrors(codedInputVectorChunks.get(i));
            transmittedVectorChunks.add(vectorAfterTransmittion);

            System.out.println("After transmittion: \t"+ transmittedVectorChunks.get(i));
        }


        


        scanner.close();
    }
}
