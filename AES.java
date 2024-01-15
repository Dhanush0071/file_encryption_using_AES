package cryptograhy_AES;
import java.nio.charset.StandardCharsets;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class AES 
{     
	static PrintWriter write_plain_text;
	static String[] msgs;
	static PrintWriter write_cipher;
	public static void encrypt(String[]msgs,String key)
	{
		System.out.println("ENCRYPTION====================Done");
		for(int t=0;t<msgs.length;t++)
		{
			//System.out.println(msgs[t]);
			String inp=msgs[t];
			String input_hex=convert.textToHex(inp);
			String key_hex=convert.textToHex(key);
			String[][] nbl=arrays.get_nibble_array(arrays.give_hex_array(input_hex));
			String[][] key0=arrays.get_nibble_array(arrays.give_hex_array(key_hex));
			//String [][]x=ith_round_key.generate_key(key0, 1);
			
			//round 0
			String[][] r0_text=AES_rounds.AddRoundKey(nbl, key0);
			String [][]previous_key=key0;
			String [][]previous_output=r0_text;
			
			//round 1 to N-1
			for(int i=1;i<10;i++)
			{
				String[][] present_key=ith_round_key.generate_key(previous_key,i);
				String[][]substituted_bytes_text=AES_rounds.SubstituteBytes(previous_output);
				String[][] row_shifted_text=AES_rounds.ShiftRows(substituted_bytes_text);
				String[][] Mix_columns_text=AES_rounds.MixColumns(row_shifted_text);
				previous_output=AES_rounds.AddRoundKey(Mix_columns_text, present_key);
				previous_key=present_key;
			}
			
			//round 10
			String[][] present_key=ith_round_key.generate_key(previous_key,10);
			String[][]substituted_bytes_text=AES_rounds.SubstituteBytes(previous_output);
			String[][] row_shifted_text=AES_rounds.ShiftRows(substituted_bytes_text);
			previous_output=AES_rounds.AddRoundKey(row_shifted_text, present_key);
			
			String cipher_text=convert.give_cipher(previous_output);
			write_cipher.println(cipher_text);
		}
		write_cipher.close();
	}
	
	public static void decrypt(String[]cipher_text,String key)
	{
		String output="";
		System.out.println("DECRYPTION====================Done");
		String key_hex=convert.textToHex(key);
		String[][] key0=arrays.get_nibble_array(arrays.give_hex_array(key_hex));
		String [][][]keys=new String[4][4][11];
		String[][]p_k=key0;
		
	//key generation
		for(int i=0;i<11;i++)
		{
			for(int row=0;row<4;row++)
			{
				for(int col=0;col<4;col++)
				{
					keys[row][col][i]=p_k[row][col];
				}
			}
			if(i<=9)
			{
			p_k=ith_round_key.generate_key(p_k, i+1);
			}
		}
		
		
		for (int t=0;t<cipher_text.length;t++)
		{
			//System.out.println(cipher_text[t]);
			String inp=cipher_text[t];
			String input_hex=convert.textToHex(inp);
//			String key_hex=convert.textToHex(key);
			String[][] nbl=arrays.get_nibble_array(arrays.give_hex_array(inp));
//			String[][] key0=arrays.get_nibble_array(arrays.give_hex_array(key_hex));
			String [][]x=ith_round_key.generate_key(key0, 1);
			
			
			//round 0
			String [][]previous_key=ith_round_key.get_key(keys,10);
			String[][] r0_cipher_text=AES_rounds.AddRoundKey(nbl, previous_key);
			String [][]previous_output=r0_cipher_text;
			
			//arrays.printarray(previous_output);
			
			//round 1 to N-1
			for(int i=9;i>0;i--)
			{
				String[][] present_key=ith_round_key.get_key(keys,i);
				String[][]inverse_shift_rows=AES_rounds.InverseShiftRows(previous_output);
				String[][] inverse_sub_bytes=AES_rounds.InverseSubstituteBytes(inverse_shift_rows);
				String[][] add_rnd_key=AES_rounds.AddRoundKey(inverse_sub_bytes,present_key);
				previous_output=AES_rounds.InverseMixColumns(add_rnd_key);
				
			}
			
			//arrays.printarray(previous_output);
			
			
			//round 10
			String[][] present_key=ith_round_key.get_key(keys,0);
			String[][] inverse_shift_rows=AES_rounds.InverseShiftRows(previous_output);
			String[][] inverse_sub_bytes=AES_rounds.InverseSubstituteBytes(inverse_shift_rows);
			previous_output=AES_rounds.AddRoundKey(inverse_sub_bytes, present_key);
			
			String plain_text=convert.give_text(previous_output);
			//System.out.println("plain text : "+plain_text);
			output=output+convert.hexToText(plain_text);
		}
		
//		System.out.println(output);
		String[]plain_text=output.split("[.]");
//		System.out.println(plain_text.length);
		for(int i=0;i<plain_text.length;i++)
		{
			write_plain_text.println(plain_text[i]);	
		}
		write_plain_text.close();		
	}
        public static void main(String[] args) throws FileNotFoundException {
    		String key="Thats my Kung Fu";
    		Scanner sc=new Scanner(System.in);
    		System.out.println("enter the format, format should be like <input file directory> <output file directory> : ");
    		String command=sc.nextLine();
    		String[]commands=command.split(" ");
    		String[]kargs=command.split(" ");
    		String inp="";
    		
    		
    		for(int i=0;i<kargs.length;i++)
    		{
    			System.out.println(kargs[i]);
    		}
    		
    		
    		File getfile=new File(kargs[kargs.length-3]);
    		Scanner read=new Scanner(getfile);
    		
    		
    		while(read.hasNextLine())
    		{
    			String temp=read.nextLine();
    			inp=inp+temp;
    		}
    		msgs=inputs.readfile(inp);
    		 write_cipher = new PrintWriter (kargs[kargs.length-2]);
    		 encrypt(msgs,key);
    		 
    		 
    		 File encrypted_file=new File(kargs[kargs.length-2]);
    		 Scanner scan=new Scanner(encrypted_file);
    		 String[] cipher_text=new String[msgs.length];
    		 int index=0;
    		 while(scan.hasNextLine())
    		 {
    			 cipher_text[index]=scan.nextLine();
    			 index++;
    		 }
    		 write_plain_text = new PrintWriter (kargs[kargs.length-1]);
    		 decrypt(cipher_text,key);
    		 
    		 

        }

}//C:\\Users\\ASUS\\Desktop\\message.txt C:\\Users\\ASUS\\Desktop\\ciher_text.txt C:\\Users\\ASUS\\Desktop\\plan.txt
