import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args){

        System.out.println("This is a program that encodes and decodes input using Golay Code");
        System.out.println("=================================================================");
        Scanner scanner = new Scanner(System.in, "utf-8");

        //Asking user for probability
        float errorProbability = InputReader.probabilityReader(scanner, "Enter the error probability of the channel (0 <= p <= 1): ");

        //Asking user for input type
        int choice = InputReader.choiceReader(scanner, "What type of data will be sent through the channel (vector/text): ");

        String inputVector = "";
        switch (choice) {
            case 0:
                //Asking user for vector input

                inputVector = InputReader.vectorReader(scanner, "Input binary vector: ");
                break;
            case 1:
                //Asking user for text input

                inputVector = InputReader.textReader(scanner, "Input text: ");
                break;
            default:
                throw new AssertionError();
        }

        //Asking user for manual error input
        Boolean manualErrorInput = InputReader.boolReader(scanner, "Do you want to input transmittion errors yourself? (yes/no): ");


        //Initializing coder, decoder, channel and lists to store their results

        Coder coder = new Coder();
        Channel channel = new Channel(errorProbability);
        Decoder decoder = new Decoder();
        List<String> inputVectorChunks = new ArrayList<>();
        List<String> codedInputVectorChunks = new ArrayList<>();
        List<String> transmittedVectorChunks = new ArrayList<>();
        List<String> decodedVectorChunks = new ArrayList<>();

        inputVectorChunks = SplitToChunks(inputVector, 12);
        
        int tmp = 0;
        if(manualErrorInput){
            //Coding all chunks
            for (String chunk : inputVectorChunks) {
                System.out.println("Chunk "+ (tmp+1) +":");
                codedInputVectorChunks.add(coder.VectorCoding(chunk));
                tmp++;
            }

            PrintList(codedInputVectorChunks, "\nCoded message: \t\t");

            String messageWithManualErrors = InputReader.vectorReader(scanner, "Enter the your message with errors: ");
            //TODO?: needs spliting into chunks

            transmittedVectorChunks = SplitToChunks(messageWithManualErrors, 23);
        }
        else {    
            //Coding all chunks
            for (String chunk : inputVectorChunks) {
                System.out.println("Chunk "+ (tmp+1) +":");
                codedInputVectorChunks.add(coder.VectorCoding(chunk));

                //Sending chunk through channel
                String vectorAfterTransmittion = channel.AddErrors(codedInputVectorChunks.get(tmp));
                transmittedVectorChunks.add(vectorAfterTransmittion);

                System.out.println("After transmittion: \t"+ transmittedVectorChunks.get(tmp));
                tmp++;
            }
        }

        System.out.println("\n=================================================================");
        PrintList(codedInputVectorChunks, "Full coded message: \t\t");
        PrintList(transmittedVectorChunks, "Full transmitted message: \t");
        System.out.println("Errors made: "+channel.getErrorsMade());

        for(String transmittedChunk : transmittedVectorChunks){
            decodedVectorChunks.add(decoder.Decode(transmittedChunk));
        }   


        //TODO: recode letters
        //TODO: clean up and commenting
        //TODO: doc
        PrintList(decodedVectorChunks, "Decoded message:");



        scanner.close();
    }



    private static List<String> SplitToChunks(String inputVector, int chunkSize){
        List<String> result = new ArrayList<>();
        
        //Calculating how many length 12 vectors will be coded
        int chunkCount = inputVector.length() / chunkSize + ((inputVector.length() % chunkSize == 0) ? 0 : 1);

        //Spliting vector into chunks of 12 length
        String inputVectorChunk;
        for(int i = 0; i < chunkCount; i++){
            if(i != chunkCount-1){
                inputVectorChunk = inputVector.substring(i * chunkSize, ((i+1) * chunkSize));
            }
            else{
                inputVectorChunk = inputVector.substring(i * chunkSize);

                //If inputVectorChunk.length != 12 ==> pad out with 0s
                //TODO: remove padding
                int missingSize = chunkSize-inputVectorChunk.length();
                for(int j = 0; j < missingSize; j++){
                    inputVectorChunk += "0";
                }

            }
            result.add(inputVectorChunk);
        }

        return result;
    }

    private static void PrintList(List<String> list, String msg){
        System.out.print(msg);
        for (String vector : list) {
            System.out.print(vector + " ");
        }
        System.out.print("\n");
    }

}
