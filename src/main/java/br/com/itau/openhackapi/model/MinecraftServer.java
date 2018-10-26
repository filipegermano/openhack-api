package br.com.itau.openhackapi.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class MinecraftServer implements Serializable {
	
	private static final long serialVersionUID = 1097544086587334652L;

	private String name;
	
	private Integer protocol;

}
