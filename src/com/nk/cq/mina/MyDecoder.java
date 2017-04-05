package com.nk.cq.mina;

import java.nio.ByteOrder;
import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.prefixedstring.PrefixedStringDecoder;

import com.nk.common.StaticVariable;

public class MyDecoder extends PrefixedStringDecoder{ //CumulativeProtocolDecoder

//	private PacketComponent packetComponent; //包解码器组件

	public MyDecoder(Charset charset){
		super(charset);
	}

	/**
	 * 这个方法的返回值是重点：
	 * 1、当内容刚好时，返回false，告知父类接收下一批内容
	 * 2、内容不够时需要下一批发过来的内容，此时返回false，这样父类 CumulativeProtocolDecoder会将内容放进IoSession中，等下次来数据后就自动拼装再交给本类的doDecode
	 * 3、当内容多时，返回true，因为需要再将本批数据进行读取，父类会将剩余的数据再次推送本类的doDecode
	 */
	@Override
	public boolean doDecode(IoSession session,IoBuffer in,ProtocolDecoderOutput out) throws Exception{
		in.order(ByteOrder.LITTLE_ENDIAN); //字节序采用小端存储，与c++保持一致
		if(in.remaining() > 0){ //有数据时，读取前4字节判断消息长度
			int packHeaderLen = StaticVariable.PROTOCOL_PREFIX_SIGN.length + StaticVariable.PROTOCOL_PREFIX_CONTENTLEN.length; //0x4E 0x4B 然后才是长度、json串
			byte[] headerBytes = new byte[packHeaderLen]; 
			in.mark(); //标记当前位置，以便reset
			in.get(headerBytes,0,packHeaderLen); //读取packHeaderLen个字节
			int size = Util.bytesToInt(headerBytes,StaticVariable.PROTOCOL_PREFIX_SIGN.length);
//			System.out.println("接收:"+size+"字节");
			if(size > in.remaining()){ //如果消息内容不够，则重置，相当于不读取size
				in.reset();
				return false; //父类接收新数据，以拼凑成完整数据
			}else{
				if(!(headerBytes[0] == 0x4E && headerBytes[1] == 0x4B)){
					int packHeaderFlagLen = StaticVariable.PROTOCOL_PREFIX_SIGN.length;
					in.reset();
					in.get(headerBytes,0,packHeaderFlagLen);
					return false;
				}
				byte[] bytes = new byte[size];
				in.get(bytes,0,size);
				String recvStr = new String(bytes,StaticVariable.CHARSET);
				out.write(recvStr);
				if(in.remaining() > 0){ //如果读取内容后还粘了包，就让父类再重读 一次，进行下一次解析
					return true;
				}
			}
		}
		return false; //处理成功，让父类进行接收下个包
	}
}



