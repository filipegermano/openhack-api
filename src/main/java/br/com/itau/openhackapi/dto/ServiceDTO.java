package br.com.itau.openhackapi.dto;

import lombok.Data;

@Data
public class ServiceDTO {
	
	private String name;
	
	private ServiceEndpointsDTO endpoints;

}
