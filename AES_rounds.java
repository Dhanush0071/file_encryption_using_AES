package cryptograhy_AES;
class AES_rounds{
	
	static final int[][] column_shuffle_matrix= {{0x02,0x03,0x01,0x01},{0x01,0x02,0x03,0x01},{0x01,0x01,0x02,0x03},{0x03,0x01,0x01,0x02}};
	static final int[][] inverse_column_shuffle_matrix= {{0x0e,0x0b,0x0d,0x09},{0x09,0x0e,0x0b,0x0d},{0x0d,0x09,0x0e,0x0b},{0x0b,0x0d,0x09,0x0e}};
	static String[][] sbox=s_box.convertHexToString(s_box.sbox);
	static String[][] inv_sbox=s_box.convertHexToString(s_box.invsbox);
	public static String[][] SubstituteBytes(String[][]plain_text)
	{
		String[][]substituted_text=new String[plain_text.length][plain_text[0].length];
		String s_box_index="0123456789ABCDEF";
		for(int i=0;i<substituted_text.length;i++)
		{
			for(int j=0;j<substituted_text[0].length;j++)
			{
				String[]indexes=plain_text[i][j].split("");
				int row=s_box_index.indexOf(indexes[0]);
				int col=s_box_index.indexOf(indexes[1]);
				substituted_text[i][j]=sbox[row][col];
			}
		}
		return substituted_text;
	}
	
	public static String[][] ShiftRows(String[][]substituted_text)
	{
		String[][]row_shifted_text=new String[substituted_text.length][substituted_text[0].length];
		for(int i=0;i<substituted_text.length;i++)
		{
			for(int j=0;j<substituted_text[0].length;j++)
			{
			row_shifted_text[i][(j-i+4)%(4)]=substituted_text[i][j];	
			}
		}
		return row_shifted_text;
	}
	
	public static String[][] MixColumns(String[][]row_shifted_text){
			int[][]intermediate_output=new int[4][4];
			int[][]integer_row_shifted_text=s_box.convertStringToHex(row_shifted_text);
			for(int i=0;i<4;i++)
			{
				int[]mixrow=arrays.get_row(column_shuffle_matrix, i);
				for(int j=0;j<4;j++)
				{
					String[]mixcol=arrays.get_col(row_shifted_text, j);
					for(int k=0;k<4;k++)
					{
						String s_box_index="0123456789ABCDEF";
						String[]indexes=mixcol[k].split("");
						int row=s_box_index.indexOf(indexes[0]);
						int col=s_box_index.indexOf(indexes[1]);
						if(mixrow[k]==0x02)
						{
							intermediate_output[i][j]=intermediate_output[i][j]^MixTables.mc2[row][col];
						}
						else if(mixrow[k]==0x03)
						{
							intermediate_output[i][j]=intermediate_output[i][j]^MixTables.mc3[row][col];
						}
						else
						{
							intermediate_output[i][j]=intermediate_output[i][j]^Integer.parseInt(mixcol[k], 16);
						}
					}
				}
			}
			String[][]output=s_box.convertHexToString(intermediate_output);
			return output;
	}
	
	public static String[][] AddRoundKey(String[][]column_mixed_text,String[][]key)
	{
		int[][]intcolumn_mixed_text=s_box.convertStringToHex(column_mixed_text);
		int[][]intkey=s_box.convertStringToHex(key);
		int[][]intermediate_output=new int[4][4];
		for(int i=0;i<key.length;i++)
		{
			for(int j=0;j<key[0].length;j++)
			{
				intermediate_output[i][j]=intkey[i][j]^intcolumn_mixed_text[i][j];
			}
		}
		String[][] output=s_box.convertHexToString(intermediate_output);
		return output;
	}
	
	public static String[][] InverseSubstituteBytes(String[][]cipher_text)
	{
		String[][]inverse_substituted_text=new String[cipher_text.length][cipher_text[0].length];
		String inv_s_box_index="0123456789ABCDEF";
		for(int i=0;i<inverse_substituted_text.length;i++)
		{
			for(int j=0;j<inverse_substituted_text[0].length;j++)
			{
				String[]indexes=cipher_text[i][j].split("");
				int row=inv_s_box_index.indexOf(indexes[0]);
				int col=inv_s_box_index.indexOf(indexes[1]);
				inverse_substituted_text[i][j]=inv_sbox[row][col];
			}
		}
		return inverse_substituted_text;
	}
	
	public static String[][] InverseShiftRows(String[][]inverse_substituted_text)
	{
		String[][]inverse_row_shifted_text=new String[inverse_substituted_text.length][inverse_substituted_text[0].length];
		for(int i=0;i<inverse_substituted_text.length;i++)
		{
			for(int j=0;j<inverse_substituted_text[0].length;j++)
			{
			inverse_row_shifted_text[i][(j+i+4)%(4)]=inverse_substituted_text[i][j];	
			}
		}
		return inverse_row_shifted_text;
	}
	
	public static String[][] InverseMixColumns(String[][]Add_round_key_text)
	{
		int[][]intermediate_output=new int[4][4];
		int[][]integer_add_round_text=s_box.convertStringToHex(Add_round_key_text);
		
		for(int i=0;i<4;i++)
		{
			int[]invmixrow=arrays.get_row(inverse_column_shuffle_matrix, i);
			for(int j=0;j<4;j++)
			{
				String[]invmixcol=arrays.get_col(Add_round_key_text, j);
				for(int k=0;k<4;k++)
				{
					String s_box_index="0123456789ABCDEF";
					String[]indexes=invmixcol[k].split("");
					int row=s_box_index.indexOf(indexes[0]);
					int col=s_box_index.indexOf(indexes[1]);
					if(invmixrow[k]==0x0e)
					{
						intermediate_output[i][j]=intermediate_output[i][j]^MixTables.mc14[row][col];
					}
					else if(invmixrow[k]==0x0b)
					{
						intermediate_output[i][j]=intermediate_output[i][j]^MixTables.mc11[row][col];
					}
					else if(invmixrow[k]==0x0d)
					{
						intermediate_output[i][j]=intermediate_output[i][j]^MixTables.mc13[row][col];
					}
					else
					{
						intermediate_output[i][j]=intermediate_output[i][j]^MixTables.mc9[row][col];
					}
				}
			}
		}
		String[][]output=s_box.convertHexToString(intermediate_output);
		return output;
	}
}