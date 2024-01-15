package cryptograhy_AES;

import java.nio.charset.StandardCharsets;

public class convert {
    public static String textToHex(String text) {
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        StringBuilder hexStringBuilder = new StringBuilder();

        for (byte b : bytes) {
            hexStringBuilder.append(String.format("%02X", b));
        }

        return hexStringBuilder.toString();
    }
    public static String hexToText(String hexString) {
        byte[] bytes = hexStringToByteArray(hexString);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    private static byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                                 + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }
    public static String give_cipher(String[][] input) {
    	String out="";
    	for(int i=0;i<input.length;i++)
    	{
    		for(int j=0;j<input[0].length;j++)
    		{
    			out=out+input[j][i];
    		}
    	}
    	return out;
    }
    public static String give_text(String[][] input) {
    	String out="";
    	for(int i=0;i<input.length;i++)
    	{
    		for(int j=0;j<input[0].length;j++)
    		{
    			out=out+input[j][i];
    		}
    	}
    	return out;
    }
}
