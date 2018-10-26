package br.com.itau.openhackapi.model;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class ServiceItem {
	
	private ServiceMetadata metadata;
	
	private Map<String, String> labels;
	
	private ServiceSpec spec;
	
	private ServiceStatus status;
	
	private String timestamp;
	
	private String window;
	
	private List<ServiceContainer> containers;

}
