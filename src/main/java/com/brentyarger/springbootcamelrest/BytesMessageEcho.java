package com.brentyarger.springbootcamelrest;

import org.apache.camel.Body;
import org.apache.camel.Exchange;

public class BytesMessageEcho {

	public byte[] returnBytes(@Body byte[] bytes, Exchange exchange) {
		
		exchange.getIn().setHeader("ProcessedHeader", "processed!");
		
		return bytes;
	}
}
