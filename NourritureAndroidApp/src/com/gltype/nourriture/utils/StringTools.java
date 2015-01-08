package com.gltype.nourriture.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StringTools {
	public static String getInputStream(InputStream is){
		try {
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			int len = 0;
			byte[] buff =new byte[1024];
			while((len = is.read(buff))!=-1){
				baos.write(buff, 0, len);
			}
			is.close();
			baos.close();
			byte[] result = baos.toByteArray();
			String temp = new String(result);
			return temp;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Fail to get string";
		}
	}
	public static String dateFormat(String date){
		String newDate="";
		date = date.replace("T"," ");
        date = date.substring(0,19);
        newDate=date;
		return newDate;
	}
}
