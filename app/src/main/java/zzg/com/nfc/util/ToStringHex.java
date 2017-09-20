package zzg.com.nfc.util;

import java.io.ByteArrayOutputStream;

public class ToStringHex {
	
	// ���ַ��������16��������,�����������ַ����������ģ� 
	private static String hexString="0123456789ABCDEF"; 
	/* 
	* ���ַ��������16��������,�����������ַ����������ģ� 
	*/ 
	public static String encode(String str) 
	{ 
	//����Ĭ�ϱ����ȡ�ֽ����� 
	byte[] bytes=str.getBytes(); 
	StringBuilder sb=new StringBuilder(bytes.length*2); 
	//���ֽ�������ÿ���ֽڲ���2λ16�������� 
	for(int i=0;i<bytes.length;i++) 
	{ 
	sb.append(hexString.charAt((bytes[i]&0xf0)>>4)); 
	sb.append(hexString.charAt((bytes[i]&0x0f)>>0)); 
	} 
	return sb.toString(); 
	} 

//��16�������ֽ�����ַ���,�����������ַ����������ģ� 
	public static String decode(String bytes) 
	{ 
	ByteArrayOutputStream baos=new ByteArrayOutputStream(bytes.length()/2); 
	//��ÿ2λ16����������װ��һ���ֽ� 
	for(int i=0;i<bytes.length()-1;i+=2)
	baos.write((hexString.indexOf(bytes.charAt(i))<<4 |hexString.indexOf(bytes.charAt(i+1)))); 
	return new String(baos.toByteArray()); 
	} 
	
	//ת��ʮ�����Ʊ���ΪASCll�ַ��� 
	 public static String toStringHex(String s) 
 	     { 
 	       byte[] baKeyword = new byte[s.length()/2]; //------------------
 	       for(int i = 0; i < baKeyword.length; i++) 
 	       { 
 	    	   try 
 	    	   { 
 	    		   baKeyword[i] = (byte)(0xff & Integer.parseInt(s.substring(i*2, i*2+2),16)); 
 	    	   } 
 	    	   catch(Exception e) 
 	    	   { 
 	    		   e.printStackTrace(); 
 	    	   } 
 	       } 
 	       try 
 	       { 
 	    	   s = new String(baKeyword, "utf-8");//UTF-16le:Not 
 	       } 
 	       catch (Exception e1) 
 	       { 
 	    	   e1.printStackTrace(); 
 	       } 
 	       return s; 
 	     }
}
