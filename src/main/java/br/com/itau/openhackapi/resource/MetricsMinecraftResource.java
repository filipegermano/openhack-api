package br.com.itau.openhackapi.resource;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.itau.openhackapi.service.MetricsMinecraftService;

@RestController
public class MetricsMinecraftResource {
	
	@Autowired
	private MetricsMinecraftService metricsMinecraftService;
	
	@PostMapping("/mc-commands")
	public @ResponseBody ResponseEntity<?> findAllCommands() {

		String commands = "mc-commands -> List all commands\n "
				+ "mc-metrics -> All metrics\n"
				+ "mc-status -> Status service\n "
				+ "mc-players -> Online players\n ";
		
		return ResponseEntity.ok(commands);
	}
	
	@PostMapping("/mc-metrics")
	public @ResponseBody ResponseEntity<?> findMetrics() {

		return ResponseEntity.ok(metricsMinecraftService.findMinecraftMetrics());
	}
	
	@PostMapping("/mc-status")
	public @ResponseBody ResponseEntity<?> findStatus() {

		return ResponseEntity.ok(metricsMinecraftService.findMinecraftMetrics().getStatus());
	}
	
	@PostMapping("/mc-players")
	public @ResponseBody ResponseEntity<?> findOnlinePlayers() {

		return ResponseEntity.ok(metricsMinecraftService.findMinecraftMetrics().getPlayers().getNow().toString());
	}

}
