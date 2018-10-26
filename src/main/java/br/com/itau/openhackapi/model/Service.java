package br.com.itau.openhackapi.model;

import java.util.List;

import lombok.Data;

@Data
public class Service {
	
	private String kind;
	
	private String apiVersion;
	
	private ServiceMetadata	metadata;
	
	private List<ServiceItem> items;

}
