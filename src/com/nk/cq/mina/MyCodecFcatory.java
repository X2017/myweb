package com.nk.cq.mina;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class MyCodecFcatory implements ProtocolCodecFactory{

	private ProtocolEncoder encoder = null;
	private ProtocolDecoder decoder = null;

	public MyCodecFcatory(ProtocolEncoder encoder,ProtocolDecoder decoder){
		this.encoder = encoder;
		this.decoder = decoder;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception{
		return this.encoder;
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception{
		return this.decoder;
	}
}
