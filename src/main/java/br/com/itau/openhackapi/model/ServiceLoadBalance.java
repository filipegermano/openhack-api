package br.com.itau.openhackapi.model;

import java.util.List;

import lombok.Data;

@Data
public class ServiceLoadBalance {
	
	private List<ServiceIngress> ingress;

}
