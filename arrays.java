package cryptograhy_AES;

public class arrays {
	public static String[] get_col(String[][] arr, int col_no)
	{
		String[]out=new String[arr.length];
		for(int i=0;i<arr.length;i++)
		{
			out[i]=arr[i][col_no];
		}
		return out;
	}
	public static int[] get_col(int[][] arr, int col_no)
	{
		int[]out=new int[arr.length];
		for(int i=0;i<arr.length;i++)
		{
			out[i]=arr[i][col_no];
		}
		return out;
	}
	
	public static int[] get_row(int[][] arr, int row_no)
	{
		int[]out=new int[arr[0].length];
		for(int i=0;i<arr[0].length;i++)
		{
			out[i]=arr[row_no][i];
		}
		return out;
	}
	public static void printarray(String[][]arr)
    {
    	for(int i=0;i<arr.length;i++)
    	{
    		for(int j=0;j<arr[0].length;j++)
    		{
    			System.out.print(arr[i][j]+" ");
    		}
    		System.out.println();
    	}
    	
    }
    public static String[][] get_nibble_array(String[]arr)
    {
    	String[][]nibble=new String[4][4];
    	int index=0;
    	for(int i=0;i<nibble.length;i++)
    	{
    		for(int j=0;j<nibble[0].length;j++)
    		{
    			nibble[j][i]=arr[index];
    			index++;
    		}
    	}
    	return nibble;
    }
    public static String[] give_hex_array(String input_hex)
    {
		String [] arr=new String[16];
		int index=0;
		for(int i=0;i<input_hex.length();i=i+2)
    		{ 
				String part = input_hex.substring(i, i+2);  
    	        arr[index] = part;  
    	        index++;
    		}	
		return arr;
    }
}
