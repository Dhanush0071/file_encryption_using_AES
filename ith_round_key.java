package cryptograhy_AES;

public class ith_round_key{
	
	static String [][]sbox=s_box.convertHexToString(s_box.sbox);
	public static int[][] round_constants= {{0x01,0x00,0x00,0x00},{0x02,0x00,0x00,0x00},{0x04,0x00,0x00,0x00},{0x08,0x00,0x00,0x00},{0x10,0x00,0x00,0x00},{0x20,0x00,0x00,0x00},{0x40,0x00,0x00,0x00},{0x80,0x00,0x00,0x00},{0x1b,0x00,0x00,0x00},{0x36,0x00,0x00,0x00}};
	public static String[][]generate_key(String [][]key,int round_no)
	{
		int[][]output_key=new int[4][4];
		String[] third_column=arrays.get_col(key, 3);
		String[] shifted_word=g(third_column);
		int[] substituted=Substitute(shifted_word);
		int[] g=new int[substituted.length];
		int[]constant=arrays.get_row(round_constants,round_no-1);
		for(int i=0;i<4;i++)
		{
			g[i]=substituted[i]^constant[i];
		}
		int[][]integerKey=s_box.convertStringToHex(key);
		int[] temp=g;
		for(int i=0;i<key.length;i++)
		{
			int []temp2=arrays.get_col(integerKey, i);
			for(int j=0;j<key[0].length;j++)
			{
				output_key[j][i]=temp[j]^temp2[j];
				temp[j]=output_key[j][i];
			}
		}
		String[][] o_key=s_box.convertHexToString(output_key);
		return o_key;	
		
	}
	public static String[] g(String[]word)
	{
		String temp=word[0];
		word[0]=word[1];
		word[1]=word[2];
		word[2]=word[3];
		word[3]=temp;
		
		return word;
	}
	public static int[] Substitute(String [] input)
	{
		int[]output=new int[input.length];
		String s_box_index="0123456789ABCDEF";
		for(int i=0;i<input.length;i++)
		{
			String[]indexes=input[i].split("");
			int row=s_box_index.indexOf(indexes[0]);
			int col=s_box_index.indexOf(indexes[1]);
			output[i]=s_box.sbox[row][col];
		}
		return output;
	}
	public static String[][] get_key(String[][][]keys,int round)
	{
		String[][]output=new String[4][4];
		for(int i=0;i<4;i++)
		{
			for(int j=0;j<4;j++)
			{
				output[i][j]=keys[i][j][round];
			}
		}
		return output;
	}
	public static void main(String[]args)
	{
		String key="Thats My Kung Fu";
		
		
	}
}
