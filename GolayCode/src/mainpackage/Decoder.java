package mainpackage;

public class Decoder {
    
    //   24x12 parity matrix used:
    //
    //   1 0 0 0 0 0 0 0 0 0 0 0  
    //   0 1 0 0 0 0 0 0 0 0 0 0  
    //   0 0 1 0 0 0 0 0 0 0 0 0  
    //   0 0 0 1 0 0 0 0 0 0 0 0  
    //   0 0 0 0 1 0 0 0 0 0 0 0  
    //   0 0 0 0 0 1 0 0 0 0 0 0  
    //   0 0 0 0 0 0 1 0 0 0 0 0   
    //   0 0 0 0 0 0 0 1 0 0 0 0  
    //   0 0 0 0 0 0 0 0 1 0 0 0  
    //   0 0 0 0 0 0 0 0 0 1 0 0  
    //   0 0 0 0 0 0 0 0 0 0 1 0  
    //   0 0 0 0 0 0 0 0 0 0 0 1  
    //
    //   1 1 0 1 1 1 0 0 0 1 0 1
    //   1 0 1 1 1 0 0 0 1 0 1 1
    //   0 1 1 1 0 0 0 1 0 1 1 1
    //   1 1 1 0 0 0 1 0 1 1 0 1
    //   1 1 0 0 0 1 0 1 1 0 1 1
    //   1 0 0 0 1 0 1 1 0 1 1 1
    //   0 0 0 1 0 1 1 0 1 1 1 1
    //   0 0 1 0 1 1 0 1 1 1 0 1
    //   0 1 0 1 1 0 1 1 1 0 0 1
    //   1 0 1 1 0 1 1 1 0 0 0 1
    //   0 1 1 0 1 1 1 0 0 0 1 1
    //   1 1 1 1 1 1 1 1 1 1 1 0
    //
    //   second half of this matrix is also used as matrix B

    int[] parityMatrix;
    int[] bMatrix;

    public Decoder(){
        parityMatrix = new int[12];
        bMatrix = new int[12];

        parityMatrix[0]  = Integer.parseInt("100000000000"+"11011100010"+"1",2);
        parityMatrix[1]  = Integer.parseInt("010000000000"+"10111000101"+"1",2);
        parityMatrix[2]  = Integer.parseInt("001000000000"+"01110001011"+"1",2);
        parityMatrix[3]  = Integer.parseInt("000100000000"+"11100010110"+"1",2);
        parityMatrix[4]  = Integer.parseInt("000010000000"+"11000101101"+"1",2);
        parityMatrix[5]  = Integer.parseInt("000001000000"+"10001011011"+"1",2);
        parityMatrix[6]  = Integer.parseInt("000000100000"+"00010110111"+"1",2);
        parityMatrix[7]  = Integer.parseInt("000000010000"+"00101101110"+"1",2);
        parityMatrix[8]  = Integer.parseInt("000000001000"+"01011011100"+"1",2);
        parityMatrix[9]  = Integer.parseInt("000000000100"+"10110111000"+"1",2);
        parityMatrix[10] = Integer.parseInt("000000000010"+"01101110001"+"1",2);
        parityMatrix[11] = Integer.parseInt("000000000001"+"11111111111"+"0",2);

        bMatrix[0]  = Integer.parseInt("11011100010"+"1", 2);
        bMatrix[1]  = Integer.parseInt("10111000101"+"1", 2);
        bMatrix[2]  = Integer.parseInt("01110001011"+"1", 2);
        bMatrix[3]  = Integer.parseInt("11100010110"+"1", 2);
        bMatrix[4]  = Integer.parseInt("11000101101"+"1", 2);
        bMatrix[5]  = Integer.parseInt("10001011011"+"1", 2);
        bMatrix[6]  = Integer.parseInt("00010110111"+"1", 2);
        bMatrix[7]  = Integer.parseInt("00101101110"+"1", 2);
        bMatrix[8]  = Integer.parseInt("01011011100"+"1", 2);
        bMatrix[9]  = Integer.parseInt("10110111000"+"1", 2);
        bMatrix[10] = Integer.parseInt("01101110001"+"1", 2);
        bMatrix[11] = Integer.parseInt("11111111111"+"0", 2);

    }


    public String Decode(String codedVector){
        String w0 = codedVector;
        int w0Decimal = Integer.parseInt(w0,2);

        if(Integer.bitCount(w0Decimal) % 2 == 0){
            w0 += "1";
        }
        else {
            w0 += "0";
        }


        String syndrome = "";
        w0Decimal = Integer.parseInt(w0,2);

        for (int column : parityMatrix) {
            //Could build int instead of string here to avoid conversion between types
            syndrome += Integer.bitCount(w0Decimal & column) % 2;
        }

        String u = findMistakeVectorU(syndrome);


        if(!u.equals("")){
            int anws = w0Decimal ^ Integer.parseInt(u,2);
            String decodedVector = String.format("%24s", Integer.toBinaryString(anws)).replace(' ', '0');
            return decodedVector.substring(0, 12);
        }
        else {
            return "";
        }
    }

    private String findMistakeVectorU(String syndrome){
        int intSyndrome = Integer.parseInt(syndrome,2);
        String e = "000000000000";

        //u = [s, 0]
        if(Integer.bitCount(intSyndrome) <= 3){
            return syndrome + e;
        }
        else{
            int bMatrixRowCount = bMatrix.length;
            int tmp; 

            for(int i = 0; i < bMatrixRowCount; i++){
                //tmp = s + bi
                tmp = intSyndrome ^ bMatrix[i];
                
                //u = [s + bi, ei]
                if(Integer.bitCount(tmp) <= 2){
                    e = e.substring(0, i) + '1' + e.substring(i + 1);

                    String stringTmp = String.format("%12s", Integer.toBinaryString(tmp)).replace(' ', '0');
                    return stringTmp + e;
                }
            }

            //This part of the code is only reached if U hasn't been found yet

            //Building new syndrome sB
            String newSyndrome = "";
            for (int col : bMatrix) {
                newSyndrome += Integer.bitCount(intSyndrome & col) % 2;
            }

            int newIntSyndrome = Integer.parseInt(newSyndrome,2);

            //u = [0, sB]
            if(Integer.bitCount(newIntSyndrome) <= 3){
                return e + newSyndrome;
            }
            else{
                for(int i = 0; i < bMatrixRowCount; i++){
                    //tmp = sB + bi
                    tmp = newIntSyndrome ^ bMatrix[i];
                    
                    //u = [ei, sB + bi]
                    if(Integer.bitCount(tmp) <= 2){
                        e = e.substring(0, i) + '1' + e.substring(i + 1);
    
                        String stringTmp = String.format("%12s", Integer.toBinaryString(tmp)).replace(' ', '0');
                        return e + stringTmp;
                    }
                }
            }

            //If U still hasnt been found we return an empty string
            return "";
        }
    }

}
