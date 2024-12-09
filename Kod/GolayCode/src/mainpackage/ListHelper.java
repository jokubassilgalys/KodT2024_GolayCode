package mainpackage;
import java.util.ArrayList;
import java.util.List;

public class ListHelper {
    static int paddingSize;

    //Splitting a long binary string into a list of string of specified length
    public static List<String> SplitToChunks(String inputVector, int chunkSize){
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
                paddingSize = chunkSize-inputVectorChunk.length();
                for(int j = 0; j < paddingSize; j++){
                    inputVectorChunk += "0";
                }

            }
            result.add(inputVectorChunk);
        }

        return result;
    }

    //Printing the list
    public static void printList(List<String> list, String msg){
        System.out.print(msg);
        for (String vector : list) {
            System.out.print(vector + " ");
        }
        System.out.print("\n");
    }

    //Removing the padding added to the last vector of the list
    public static void removePadding(List<String> list, int chunkSize){
        String lastVector = list.get(list.size() - 1);
        list.set(list.size() - 1, lastVector.substring(0, lastVector.length() - paddingSize));

    }

    //In scenarios where input was text the binary list needs to be converted back to text
    public static String binaryListToString(List<String> list){
        List<String> tmpList = list;

        String binaryMessage = "";
        for (String string : tmpList) {
            binaryMessage += string;
        }

        tmpList = ListHelper.SplitToChunks(binaryMessage, 8);

        String message = "";
        for (String binaryString : tmpList) {
            int decimalValue = Integer.parseInt(binaryString, 2);
            message += (char) decimalValue;
        }

        return message;
    } 
}
