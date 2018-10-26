package br.com.itau.openhackapi.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class MinecraftPlayers implements Serializable {

	private static final long serialVersionUID = -7478811945253697545L;

	private Integer max;

	private Integer now;

}
