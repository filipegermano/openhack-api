package br.com.itau.openhackapi.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.itau.openhackapi.model.MinecraftStatus;

@Service
public class MetricsMinecraftService {
	
	public MinecraftStatus findMinecraftMetrics() {
		
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<MinecraftStatus> minecraftStatus = restTemplate.getForEntity("https://mcapi.us/server/status?ip=168.62.175.52&port=25565", MinecraftStatus.class);
		
		return minecraftStatus.getBody();
	}

}
