package command;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

/**
 * @author kinsoku
 */
public class ChecksumCalculator {

    //Getting the decimal equivalent of the calculated HEX value
    public static int hex_to_decimal(String s) {
        String digits = "0123456789ABCDEF";
        s = s.toUpperCase();
        int val = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int d = digits.indexOf(c);
            val = 16 * val + d;
        }
        return val;
    } //end of hex_to_decimal

    public String checksumValue(String commandString) {

        //check the number of command characters, if it only contains 1 character, add two zero prefix
        if (commandString.length() == 1) {
            commandString = ("000" + commandString).substring(commandString.length());
        }

        //Getting the hex values
        ArrayList hexdec_num = new ChecksumCalculator().hexCalc(commandString);

        //Getting the binary list
        //System.out.println("Equivalent Binaries: ");
        List<ArrayList> binList = new ChecksumCalculator().binCalc(hexdec_num);

        //Getting the BCC value
        //System.out.println("BCC Calculation:");
        int bcc = new ChecksumCalculator().bccCalc(binList);

        //Output the BCC calculation result
        NumberFormat formatBCC = new DecimalFormat("000");
        String bccOutput = formatBCC.format(bcc);
        //System.out.println("BCC_Calc: " + bccOutput);

        //Getting the complete command syntax
        String command = commandString + bccOutput;
        //System.out.println("Command: " + command.replaceFirst("^0+(?!$)", ""));
        return command.replaceFirst("^0+(?!$)", "");
    } //end of main

    //Getting the HEX equivalent of the inputted command code (ascii)
    public ArrayList hexCalc(String command) {
        //To list up all the HEX value
        ArrayList<String> hexValue = new ArrayList<>();

        // Step-1 - Convert ASCII binString to char array
        List<String> hexList = new ArrayList<>(Arrays.asList(command.split("")));

        for (String hexString : hexList) {
            char[] hexChar = hexString.toCharArray();

            // Step-2 Iterate over char array and cast each element to Integer.
            StringBuilder buildHex = new StringBuilder();
            for (char c : hexChar) {
                int x = c;

                // Step-3 Convert integer value to hex using toHexString() method.
                buildHex.append(Integer.toHexString(x).toUpperCase());
                hexValue.add(buildHex.toString());
            }
        }
        //System.out.println("Hex Value: " + hexValue);
        return hexValue;
    } //end of hexCalc

    //Getting the binary values:
    public List<ArrayList> binCalc(ArrayList<String> hexVal) {
        //The string data type of the binaries
        List<String> binaryStringList = new ArrayList<>(Collections.emptyList());

        //The integer data type of the binaries
        List<ArrayList> binaryIntList = new ArrayList<>(Collections.emptyList());

        //Getting the binary values:
        //The number of iterations of binaries (will vary depends on the inputted command)
        for (Object hex : hexVal) {
            int dec_num, i = 1, j;
            int[] bin_num = new int[100];

            //Converting hexadecimal to decimal
            dec_num = hex_to_decimal(hex.toString());

            //Converting decimal to binary
            while (dec_num != 0) {
                bin_num[i++] = dec_num % 2;
                dec_num = dec_num / 2;
            }

            //Getting the binary string
            StringBuilder bin = new StringBuilder();
            for (j = i - 1; j > 0; j--) {
                bin.append(bin_num[j]);
            }

            //Formatting the binary into 8 bit as string array
            NumberFormat formatBin = new DecimalFormat("00000000");
            String binVal = formatBin.format(Integer.parseInt(bin.toString()));
            binaryStringList.add(binVal);
        }

        //Converting binary string array into int array	
        for (String binString : binaryStringList) {
            int binArrNum = 0;
            ArrayList<Integer> binArrayList = new ArrayList<>();

            List<String> binArray = new ArrayList<>(Arrays.asList(binString.split("")));
            for (String binChar : binArray) {
                char[] ch = binChar.toCharArray();
                StringBuilder builder = new StringBuilder();
                for (char c : ch) {
                    binArrayList.add(Integer.parseInt(builder.append(c).toString()));
                }
            }
            //System.out.println("For HEX_" + hexVal.get(binArrNum) + "H: " + binArrayList);
            binArrNum++;
            binaryIntList.add(binArrayList);
        }
        return binaryIntList;
    }

    //XOR Gate and BCC calculation
    public int bccCalc(List<ArrayList> binList) {
        ArrayList<Integer> bccVal = new ArrayList<>();
        ArrayList<Integer> xorComp = new ArrayList<>();

        for (ArrayList binArr : binList) {
            if (xorComp.size() == 0) {
                for (int i = 0; i < binArr.size(); i++) {
                    xorComp.add((Integer) binArr.get(i));
                }
            } else {
                bccVal.clear();
                for (int i = 0; i < binArr.size(); i++) {
                    if (xorComp.get(i) == binArr.get(i)) {
                        bccVal.add(0);
                    } else {
                        bccVal.add(1);
                    }
                }

                //Updating the BCC value
                xorComp.clear();
                for (int i = 0; i < bccVal.size(); i++) {
                    xorComp.add(bccVal.get(i));
                }
            }
            if (bccVal.isEmpty()) {
                //System.out.println("BCC: " + "[0, 0, 0, 0, 0, 0, 0, 0]");
            } else {
                //System.out.println("BCC: " + bccVal);
            }
        }

        //Converting BCC to 3 digit decimal
        HashMap<Integer, Integer> hm = new HashMap<>();
        hm.put(0, 128);
        hm.put(1, 64);
        hm.put(2, 32);
        hm.put(3, 16);
        hm.put(4, 8);
        hm.put(5, 4);
        hm.put(6, 2);
        hm.put(7, 1);

        int bccSum = 0;
        try {
            for (Map.Entry m : hm.entrySet()) {
                if (bccVal.get((int) m.getKey()) != 0) {
                    bccSum = bccSum + (int) m.getValue();
                }
            }
        } catch (Exception e) {
            //System.out.println("INFO: " + e.getMessage());
        }
        return bccSum;
    } //end of bccCalc

} //end of class
