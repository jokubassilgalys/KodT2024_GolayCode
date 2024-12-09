package mainpackage;
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

        String userInput = "";
        switch (choice) {
            case 0:
                //Asking user for vector input

                userInput = InputReader.vectorReader(scanner, "Input binary vector: ");
                break;
            case 1:
                //Asking user for text input

                userInput = InputReader.textReader(scanner, "Input text: ");
                break;
            default:
                throw new AssertionError();
        }

        //Asking user for manual error input
        Boolean manualErrorInput = InputReader.boolReader(scanner, "Do you want to input transmittion errors yourself? (yes/no): ");


        //Initializing coder, decoder, channel and lists to store their results
        //
        //inputVectorChunks - user input split into binary vectors with length of 12
        //codedVectorChunks - binary vectors after coding (length 23)
        //transmittedVectorChunks - coded vectors with errors recieved from the channel (length 23)
        //decodedVectorChunks - decoded and error corrected vectors (length 12)

        Coder coder = new Coder();
        Channel channel = new Channel(errorProbability);
        Decoder decoder = new Decoder();
        List<String> inputVectorChunks = new ArrayList<>();
        List<String> codedVectorChunks = new ArrayList<>();
        List<String> transmittedVectorChunks = new ArrayList<>();
        List<String> decodedVectorChunks = new ArrayList<>();

        //Spliting the user input to chunks of length 12
        inputVectorChunks = ListHelper.SplitToChunks(userInput, 12);
        

        //Here every message chunk will be coded and either sent through the noisy channel
        //or the user is asked to reinput the coded vector 

        int currentChunk = 0;
        if(manualErrorInput){
            //Coding all chunks
            for (String chunk : inputVectorChunks) {
                System.out.println("Chunk "+ (currentChunk+1) +":");
                codedVectorChunks.add(coder.VectorCoding(chunk));
                currentChunk++;
            }

            ListHelper.printList(codedVectorChunks, "\nCoded message: \t\t");

            //Ask user for manual error input instead of seading it through the channel
            String messageWithManualErrors = InputReader.vectorReader(scanner, "Enter the your message with errors: ");
            transmittedVectorChunks = ListHelper.SplitToChunks(messageWithManualErrors, 23);
        }
        else {    
            //Coding all chunks
            for (String chunk : inputVectorChunks) {
                System.out.println("Chunk "+ (currentChunk+1) +":");
                codedVectorChunks.add(coder.VectorCoding(chunk));

                //Sending chunk through channel
                String vectorAfterTransmittion = channel.AddErrors(codedVectorChunks.get(currentChunk));
                transmittedVectorChunks.add(vectorAfterTransmittion);

                System.out.println("After transmittion: \t"+ transmittedVectorChunks.get(currentChunk));
                currentChunk++;
            }
        }

        //Printing full messages before and after distortion from the channel
        //and then trying to correct errors

        System.out.println("\n=================================================================");
        ListHelper.printList(codedVectorChunks, "Full coded message: \t\t");
        ListHelper.printList(transmittedVectorChunks, "Full transmitted message: \t");
        if(!manualErrorInput){
            System.out.println("Errors made: "+channel.getErrorsMade()+"\n");
        }
        

        //Every chunk of the message is sent to the decoder
        for(String transmittedChunk : transmittedVectorChunks){
            decodedVectorChunks.add(decoder.Decode(transmittedChunk));
        }   

        //Remove the padding added before
        ListHelper.removePadding(decodedVectorChunks, 12);
        ListHelper.removePadding(inputVectorChunks, 12);

        if(choice == 0){
            //Printing the result of the corrected message
            ListHelper.printList(decodedVectorChunks, "Decoded message: \t");
            ListHelper.printList(inputVectorChunks, "Original message: \t");
        }
        else if(choice == 1){
            //If text was inputed before binary needs to be displayed as letters
            System.out.println("Decoded message: "+ ListHelper.binaryListToString(decodedVectorChunks));
            System.out.println("Original message: "+ ListHelper.binaryListToString(inputVectorChunks));
        }

        scanner.close();
        //TODO: experimetas
        //TODO: be klaidu siuntimas
    }
}
