package br.com.itau.openhackapi.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MinecraftStatus implements Serializable {

	private static final long serialVersionUID = 7488806301812561408L;

	private String status;
	
	private Boolean online;
	
	private String motd;
	
	private String error;

	private MinecraftPlayers players;

	private MinecraftServer server;

	@JsonProperty(value = "last_online")
	private String lastOnline;
	
	@JsonProperty(value = "last_updated")
	private String lastUpdated;
	
	private Long duration;

}
