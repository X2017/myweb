package com.nk.cq.mina;

import java.io.UnsupportedEncodingException;

public class Util{
	public static String html(String content){
		if(content==null){
			return "";
		}
		String html = content;
		html = html.replaceAll("&","&amp;");
		html = html.replace("\"","&quot;");
		html = html.replace("\t","&nbsp;&nbsp;");
		html = html.replace(" ","&nbsp;");
		html = html.replace("<","&lt;");
		html = html.replaceAll(">","&gt;");
		return html;
	}

	public static String getSpaceHexString(byte[] src){
		String retString = new String();
		try{
			for(int i = 0; i<src.length; i++)
				if(i!=src.length-1)
					retString = retString+String.format("%02X ",new Object[]{Integer.valueOf(src[i]&0xFF)});
				else
					retString = retString+String.format("%02X",new Object[]{Integer.valueOf(src[i]&0xFF)});
		}catch(Exception e){
			e.printStackTrace();
		}
		return retString.trim();
	}
	
	public static String getSpaceHexString(byte[] src,int printLen){
		String retString = new String();
		try{
			for(int i = 0; i<printLen && printLen<=src.length; i++)
				if(i!=src.length-1)
					retString = retString+String.format("%02X ",new Object[]{Integer.valueOf(src[i]&0xFF)});
				else
					retString = retString+String.format("%02X",new Object[]{Integer.valueOf(src[i]&0xFF)});
		}catch(Exception e){
			e.printStackTrace();
		}
		return retString.trim();
	}

	public static byte[] gbSetCs(byte[] src){
		if(src.length<=8){
			return null;
		}
		byte[] retByteArray = new byte[src.length];
		System.arraycopy(src,0,retByteArray,0,src.length);
		try{
			int cs = 0;
			for(int i = 6; i<src.length-2; i++){
				cs += src[i]%256;
				cs %= 256;
			}
			retByteArray[(src.length-2)] = (byte)cs;
		}catch(Exception e){
			e.printStackTrace();
		}
		return retByteArray;
	}

	public static String getNoSpaceHexUnsignedByteString(String hexUnsignedByteString){
		String hexUnsignedByteStringNoSpace = "";
		for(int i = 0; i<hexUnsignedByteString.length(); i += 3){
			if(i+2<=hexUnsignedByteString.length()){
				hexUnsignedByteStringNoSpace = hexUnsignedByteStringNoSpace+hexUnsignedByteString.substring(i,i+2);
			}
		}

		return hexUnsignedByteStringNoSpace;
	}

	public static String getNoSpaceHexSignedByteString(String hexUnsignedByteStringNoSpace){
		String hexSignedByteStringNoSpace = "";
		for(int i = 0; i<hexUnsignedByteStringNoSpace.length(); i += 2){
			if(i+2<=hexUnsignedByteStringNoSpace.length()){
				short tempShortValue = Short.parseShort(hexUnsignedByteStringNoSpace.substring(i,i+2),16);
				byte byteValue = (byte)tempShortValue;
				hexSignedByteStringNoSpace = hexSignedByteStringNoSpace+String.format("%02X",new Object[]{Byte.valueOf(byteValue)});
			}
		}
		return hexSignedByteStringNoSpace;
	}

	public static String getRealAsciiStringByNoSpaceHexString(String noSpaceHexString){
		String realString = "";
		int byteNum = noSpaceHexString.length()/2;
		byte[] byteArray = new byte[byteNum];
		int byteArrayIndex = 0;
		for(int i = 0; i<noSpaceHexString.length(); i += 2)
			if(i+2<=noSpaceHexString.length()){
				short tempShortValue = Short.parseShort(noSpaceHexString.substring(i,i+2),16);
				byte byteValue = (byte)tempShortValue;
				byteArray[byteArrayIndex] = byteValue;
				byteArrayIndex++;
			}
		try{
			realString = new String(byteArray,"gb2312");
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}

		return realString;
	}

	public static byte[] getRealByteArrayByNoSpaceHexString(String noSpaceHexString){
		if(noSpaceHexString.length()%2!=0){
			noSpaceHexString = noSpaceHexString.substring(0,noSpaceHexString.length()-1);
		}
		int length = noSpaceHexString.length()/2;
		byte[] returnByteArray = new byte[length];
		int index = 0;
		for(int i = 0; i<noSpaceHexString.length(); i += 2){
			if(i+2<=noSpaceHexString.length()){
				returnByteArray[index] = (byte)Short.parseShort(noSpaceHexString.substring(i,i+2),16);
				index++;
			}
		}
		return returnByteArray;
	}

	public static String getSpaceHexString(long value,int bytes){
		int tempValue = (int)value;
		String reqString = "";
		for(int i = 0; i < bytes; i++){
			reqString = reqString+String.format("%02X ",(byte)(tempValue>>(8*i))&0x000000FF);
		}
		return reqString.trim();
	}
	
	public static String getNoSpaceHexString(long value,int bytes){
		int tempValue = (int)value;
		String reqString = "";
		for(int i = 0; i < bytes; i++){
			reqString = reqString+String.format("%02X",(byte)(tempValue>>(8*i))&0x000000FF);
		}
		return reqString.trim();
	}
	
//	public static String getRealStringByValue(long value,int bytes){
//		int tempValue = (int)value;
//		System.out.println("tempValue:"+tempValue);
//		String reqString = "";
//		for(int i = 0; i < bytes; i++){
//			System.out.println(String.format("%02X",(byte)(tempValue>>(8*i))&0x000000FF));
//			reqString = reqString+String.format("%c",(char)(tempValue>>(8*i))&0x000000FF); //这里转换有问题
//			System.out.println("reqString:"+reqString);
//		}
//		return reqString;
//	}
	
	public static byte[] intToBytes(int value){
		byte[] src = new byte[4];
		src[3] = (byte)((value>>24)&0xFF);
		src[2] = (byte)((value>>16)&0xFF);
		src[1] = (byte)((value>>8)&0xFF);
		src[0] = (byte)(value&0xFF);
		return src;
	}
	
	public static int bytesToInt(byte[] src,int offset){
		int value;
		value = (int)((src[offset]&0xFF)|((src[offset+1]&0xFF)<<8)|((src[offset+2]&0xFF)<<16)|((src[offset+3]&0xFF)<<24));
		return value;
	}
}











