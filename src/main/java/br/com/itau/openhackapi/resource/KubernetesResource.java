package br.com.itau.openhackapi.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import br.com.itau.openhackapi.service.KubernetesService;

@RestController
public class KubernetesResource {
	
	@Autowired
	private KubernetesService kubernetesService;
	
	@PostMapping("/kb-services")
	public @ResponseBody ResponseEntity<?> findAllServices() throws JsonProcessingException {

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(kubernetesService.findAllServices());
		
		return ResponseEntity.ok(json);
	}

	
	@PostMapping("/kb-metrics")
	public @ResponseBody ResponseEntity<?> findTemplate() {
		
		return ResponseEntity.ok(kubernetesService.findMetricsServer());
	}
	
	@PostMapping("/kb-create")
	public @ResponseBody ResponseEntity<?> create() {
		
		return ResponseEntity.ok(kubernetesService.createServer());
	}
	
	@PostMapping("/kb-delete")
	public @ResponseBody ResponseEntity<?> delete(@ModelAttribute("text") String text) {
		
		System.out.println("Aquio texto >>>" + text);
		
		return ResponseEntity.ok(kubernetesService.delete(text));
	}

}
