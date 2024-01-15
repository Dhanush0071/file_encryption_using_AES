package cryptograhy_AES;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
public class inputs {
	public static String[] readfile(String inp) throws FileNotFoundException
	{
		int n=inp.length()%16;
		for (int i=1;i<=16-n;i++)
		{
			inp=inp+" ";
		}
		int temp=0;
		String[] msgs=new String[inp.length()/16];
		for(int i=0;i<msgs.length;i++)
		{ 
	                String part = inp.substring(temp, temp+16);  
	                msgs[i] = part;  
	                temp+=16;  
	           
		}
		return msgs;
	}

}
