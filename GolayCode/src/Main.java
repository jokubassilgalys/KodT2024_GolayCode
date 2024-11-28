import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        
        System.out.println("This is a program that encodes and decodes input using Golay Code");
        System.out.println("=================================================================");

        Scanner scanner = new Scanner(System.in);

        //Asking user for probability
        float p = InputReader.probabilityReader(scanner);

        //Asking user for vector input
        String inputVector = InputReader.vectorReader(scanner);

        Coder coder = new Coder();
        List<String> codedInputVectorChunks = new ArrayList<String>();

        
        //Calculating how many length 12 vectors will be coded
        int chunkCount = inputVector.length() / 12 + ((inputVector.length() % 12 == 0) ? 0 : 1);
        System.out.println("Chunk count: "+ chunkCount);


        //Spliting vector into chunks of 12 length
        String inputVectorChunk;
        for(int i = 0; i < chunkCount; i++){
            if(i != chunkCount-1){
                inputVectorChunk = inputVector.substring(i * 12, ((i+1) * 12));
            }
            else{
                inputVectorChunk = inputVector.substring(i * 12);
                //if inputVectorChunk.length != 12 ==> padd out with 0s
            }
            System.out.println("Chunk "+ i +": "+ inputVectorChunk);

            //Coding the vector chunk
            codedInputVectorChunks.add(coder.VectorCoding(inputVectorChunk));
        }


        


        scanner.close();
    }
}
