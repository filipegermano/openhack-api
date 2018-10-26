package br.com.itau.openhackapi.model;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class ServiceSpec {
	
	private List<ServicePort> ports;
	
	private String clusterIP;
	
	private String type;
	
	private String sessionAffinity;
	
	private Map<String, String> selector;
	
	private String externalTrafficPolicy;

}
