package br.com.itau.openhackapi.model;

import lombok.Data;

@Data
public class ServicePort {

	private String name;
	
	private String protocol;
	
	private Integer port;
	
	private String targetPort;
	
	private Integer nodePort;
	
}
