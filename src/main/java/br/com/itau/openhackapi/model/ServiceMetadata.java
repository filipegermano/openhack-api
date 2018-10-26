package br.com.itau.openhackapi.model;

import java.util.Map;

import lombok.Data;

@Data
public class ServiceMetadata {
	
	private String selfLink;
	
	private String resourceVersion;
	
	//--
	private String name;
	
	private String namespace;
	
	private String uid;
	
	private String creationTimestamp;
	
	private Map<String, String> annotations;

}
