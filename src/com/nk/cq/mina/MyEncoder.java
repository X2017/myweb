package com.nk.cq.mina;

import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.nk.common.StaticVariable;

public class MyEncoder extends ProtocolEncoderAdapter{
	private final Charset charset;
	public MyEncoder(Charset charset){
//		super(charset);
		this.charset = charset;
	}
	
	@Override
	public void encode(IoSession session,Object message,ProtocolEncoderOutput out) throws Exception{
		String messageStr = message.toString();
//		System.out.println("发送的消息:"+messageStr);
		String value = messageStr;
		int valueLen = value.getBytes(StaticVariable.CHARSET_NAME).length;
		IoBuffer buffer = IoBuffer.allocate(valueLen+6).setAutoExpand(true);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		CharsetEncoder ce = charset.newEncoder();
		buffer.putString("NK",ce);
		byte lenBytes[] = Util.intToBytes(valueLen);
		buffer.put(lenBytes);
		buffer.putString(value,ce);
		buffer.flip();
//		System.out.println("发送报文:"+buffer.getHexDump());
		out.write(buffer);
		
//		if(buffer.position() > getMaxDataLength()){
//			throw new IllegalArgumentException((new StringBuilder()).append("Data length: ").append(buffer.position()).toString());
//		}else{
//			buffer.flip();
//			out.write(buffer);
//			return;
//		}
		
//		int sendLen = messageStr.length();
//		String sendvDataLenHexStr = Util.getNoSpaceHexString(sendLen,4);
//		System.out.println("sendvDataLenHexStr:"+sendvDataLenHexStr);
//		byte lenByteOrder[] = new byte[4];
//		lenByteOrder[0] = Byte.parseByte(sendvDataLenHexStr.substring(0,2),16);
//		lenByteOrder[1] = Byte.parseByte(sendvDataLenHexStr.substring(2,4),16);
//		lenByteOrder[2] = Byte.parseByte(sendvDataLenHexStr.substring(4,6),16);
//		lenByteOrder[3] = Byte.parseByte(sendvDataLenHexStr.substring(6,8),16);
//		PackageProtocol p = new PackageProtocol();
//		p.setPackLen(lenByteOrder);
//		p.setJsonStr(message.toString());
//		out.write(p);
//		out.flush();
	}
}





