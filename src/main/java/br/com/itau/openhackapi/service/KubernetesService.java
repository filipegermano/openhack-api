package br.com.itau.openhackapi.service;

import java.net.Socket;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.itau.openhackapi.dto.ServiceDTO;
import br.com.itau.openhackapi.dto.ServiceEndpointsDTO;
import br.com.itau.openhackapi.model.AttachmentSlack;
import br.com.itau.openhackapi.model.ServiceItem;
import br.com.itau.openhackapi.model.ServicePort;
import br.com.itau.openhackapi.model.TemplateSlack;

@Service
public class KubernetesService {
	
	public String delete(String text) {
		
		trustAllHosts();
		String urlDeploy = "https://team1-f86b34ad.hcp.eastus.azmk8s.io:443/api/v1/namespaces/default/services/[NOME]".replace("[NOME]", text);
		
		RestTemplate restTemplated = new RestTemplate();
		HttpHeaders headersDeploy = new HttpHeaders();
		headersDeploy.set("Authorization", "Bearer 461316bf132e9c32ef4e9c1deb89b9cf");
		headersDeploy.set("Content-Type", "application/json");
		
		HttpEntity<String> entityDeploy = new HttpEntity<String>(null, headersDeploy);
		
		restTemplated.exchange(urlDeploy, HttpMethod.DELETE, entityDeploy, String.class);
		
		String url = "https://team1-f86b34ad.hcp.eastus.azmk8s.io:443/apis/extensions/v1beta1/namespaces/default/deployments/[NOME]".replace("[NOME]", text);;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer 461316bf132e9c32ef4e9c1deb89b9cf");
		headers.set("Content-Type", "application/json");
		
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		
		restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
		
		return "Success";
	}
	
	public String createServer() {
		
		UUID uuid = UUID.randomUUID();
		String name = uuid.toString();
		
		trustAllHosts();
		String urlDeploy = "https://team1-f86b34ad.hcp.eastus.azmk8s.io:443/apis/apps/v1/namespaces/default/deployments";
		RestTemplate restTemplated = new RestTemplate();
		HttpHeaders headersDeploy = new HttpHeaders();
		headersDeploy.set("Authorization", "Bearer 461316bf132e9c32ef4e9c1deb89b9cf");
		headersDeploy.set("Content-Type", "application/json");
		
		String bodyDeploy = "{\r\n    \"apiVersion\": \"apps/v1\",\r\n    \"kind\": \"Deployment\",\r\n    \"metadata\": {\r\n        \"name\": \"minecraft-[NOME]\",\r\n        \"namespace\": \"default\"\r\n    },\r\n    \"spec\": {\r\n        \"replicas\": 1,\r\n        \"selector\": {\r\n            \"matchLabels\": {\r\n                \"app\": \"minecraft-[NOME]\"\r\n            }\r\n        },\r\n        \"template\": {\r\n            \"metadata\": {\r\n                \"labels\": {\r\n                    \"app\": \"minecraft-[NOME]\"\r\n                }\r\n            },\r\n            \"spec\": {\r\n                \"containers\": [\r\n                    {\r\n                        \"env\": [\r\n                            {\r\n                                \"name\": \"EULA\",\r\n                                \"value\": \"TRUE\"\r\n                            }\r\n                        ],\r\n                        \"image\": \"openhack/minecraft-server:2.0\",\r\n                        \"name\": \"minecraft-[NOME]\",\r\n                        \"ports\": [\r\n                            {\r\n                                \"containerPort\": 25565,\r\n                                \"name\": \"game\"\r\n                            },\r\n                            {\r\n                                \"containerPort\": 25575,\r\n                                \"name\": \"rcon\"\r\n                            }\r\n                        ],\r\n                        \"volumeMounts\": [\r\n                            {\r\n                                \"mountPath\": \"/data\",\r\n                                \"name\": \"server-state\",\r\n                                \"subPath\": \"minecraft-[NOME]\"\r\n                            }\r\n                        ]\r\n                    }\r\n                ],\r\n                \"volumes\": [\r\n                    {\r\n                        \"persistentVolumeClaim\": {\r\n                            \"claimName\": \"azurefile\"\r\n                        },\r\n                        \"name\": \"server-state\"\r\n                    }\r\n                ]\r\n            }\r\n        }\r\n    }\r\n}";
		bodyDeploy = bodyDeploy.replace("[NOME]", name);
		HttpEntity<String> entityDeploy = new HttpEntity<String>(bodyDeploy, headersDeploy);
		
		restTemplated.exchange(urlDeploy, HttpMethod.POST, entityDeploy, String.class);
		
		String url = "https://team1-f86b34ad.hcp.eastus.azmk8s.io:443/api/v1/namespaces/default/services";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer 461316bf132e9c32ef4e9c1deb89b9cf");
		headers.set("Content-Type", "application/json");
		
		String body = "{\r\n    \"apiVersion\": \"v1\",\r\n    \"kind\": \"Service\",\r\n    \"metadata\": {\r\n        \"name\": \"minecraft-[NOME]\",\r\n        \"namespace\": \"default\"\r\n    },\r\n    \"spec\": {\r\n        \"ports\": [\r\n            {\r\n                \"name\": \"game\",\r\n                \"port\": 25565,\r\n                \"protocol\": \"TCP\",\r\n                \"targetPort\": 25565\r\n            },\r\n            {\r\n                \"name\": \"rcon\",\r\n                \"port\": 25575,\r\n                \"protocol\": \"TCP\",\r\n                \"targetPort\": 25575\r\n            }\r\n        ],\r\n        \"selector\": {\r\n            \"app\": \"minecraft-[NOME]\"\r\n        },\r\n        \"type\": \"LoadBalancer\"\r\n    }\r\n}";
		body = body.replace("[NOME]", name);
		HttpEntity<String> entity = new HttpEntity<String>(body, headers);
		
		restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		
		return "Success";
	}
	
	
	public List<ServiceDTO> findAllServices() {
		
		trustAllHosts();
		String url = "https://team1-f86b34ad.hcp.eastus.azmk8s.io:443/api/v1/namespaces/default/services";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer 461316bf132e9c32ef4e9c1deb89b9cf");
		
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		
		ResponseEntity<br.com.itau.openhackapi.model.Service> response = restTemplate.exchange(url, HttpMethod.GET, entity, br.com.itau.openhackapi.model.Service.class);
		
		List<ServiceDTO> serviceDTOs = new ArrayList<>();
		for (ServiceItem item : response.getBody().getItems()) {
			
			if (item.getMetadata().getName().contains("minecraft")) {
				
				ServiceDTO serviceDTO = new ServiceDTO();
				String name = item.getSpec().getSelector().get("app");
				
				if (name == null) {
					name = item.getSpec().getSelector().get("app.kubernetes.io/name");
				}
				
				serviceDTO.setName(name);
				
				String ip = null;
				try {
					ip = item.getStatus().getLoadBalancer().getIngress().get(0).getIp();
				} catch (Exception e) {
					System.out.println("IP Ainda pendente");
					// TODO: handle exception
				}
				
				ServiceEndpointsDTO serviceEndpointsDTO = new ServiceEndpointsDTO();
				for (ServicePort port : item.getSpec().getPorts()) {
					
					
					if (port.getName().startsWith("game")) {
						
						serviceEndpointsDTO.setMinecraft(ip + ":" + port.getPort());
						
					}
					
					if (port.getName().startsWith("rcon")) {
						
						serviceEndpointsDTO.setRcon(ip + ":" + port.getPort());;
					}
				}
				
				serviceDTO.setEndpoints(serviceEndpointsDTO);
				serviceDTOs.add(serviceDTO);
				
			}
			
		}
		
		return serviceDTOs;
		
	}
	
	public TemplateSlack findMetricsServer() {
		
		trustAllHosts();
		String url = "https://team1-f86b34ad.hcp.eastus.azmk8s.io:443/apis/metrics.k8s.io/v1beta1/namespaces/default/pods";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer 461316bf132e9c32ef4e9c1deb89b9cf");
		
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		
		ResponseEntity<br.com.itau.openhackapi.model.Service> response = restTemplate.exchange(url, HttpMethod.GET, entity, br.com.itau.openhackapi.model.Service.class);
		
		TemplateSlack templateSlack = new TemplateSlack();
		List<AttachmentSlack> attachments = new ArrayList<>();
		AttachmentSlack attachmentCPU = new AttachmentSlack();
		attachmentCPU.setText("CPU: " + response.getBody().getItems().get(0).getContainers().get(0).getUsage().getCpu());
		attachments.add(attachmentCPU);
		
		AttachmentSlack attachmentMemory = new AttachmentSlack();
		attachmentMemory.setText("Memory: " + response.getBody().getItems().get(0).getContainers().get(0).getUsage().getMemory());
		attachments.add(attachmentMemory);
		
		templateSlack.setAttachments(attachments);
		
		return templateSlack;
		
	}
	
	public static void main(String[] args) {
		
		
		trustAllHosts();
		String url = "https://team1-f86b34ad.hcp.eastus.azmk8s.io:443/api/v1/namespaces/default/services";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer 461316bf132e9c32ef4e9c1deb89b9cf");
		
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		
		ResponseEntity<br.com.itau.openhackapi.model.Service> response = restTemplate.exchange(url, HttpMethod.GET, entity, br.com.itau.openhackapi.model.Service.class);
		
		List<ServiceDTO> serviceDTOs = new ArrayList<>();
		for (ServiceItem item : response.getBody().getItems()) {
			
			if (item.getMetadata().getName().contains("minecraft")) {
				
				ServiceDTO serviceDTO = new ServiceDTO();
				String name = item.getSpec().getSelector().get("app");
				
				if (name == null || name.equals("")) {
					name = item.getSpec().getSelector().get("app.kubernetes.io/name");
				}
				
				serviceDTO.setName(name);
				
				
				String ip = item.getStatus().getLoadBalancer().getIngress().get(0).getIp();
				ServiceEndpointsDTO serviceEndpointsDTO = new ServiceEndpointsDTO();
				for (ServicePort port : item.getSpec().getPorts()) {
					
					
					if (port.getName().startsWith("game")) {
						
						serviceEndpointsDTO.setMinecraft(ip + ":" + port.getPort());
						
					}
					
					if (port.getName().startsWith("rcon")) {
						
						serviceEndpointsDTO.setRcon(ip + ":" + port.getPort());;
					}
				}
				
				serviceDTO.setEndpoints(serviceEndpointsDTO);
				serviceDTOs.add(serviceDTO);
				
			}
			
		}
		
		
	
		
		System.out.println(serviceDTOs);
	}
	
	public static void trustAllHosts()
    {
        try
        {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509ExtendedTrustManager()
                    {
                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers()
                        {
                            return null;
                        }

                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
                        {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
                        {
                        }

                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] xcs, String string, Socket socket) throws CertificateException
                        {

                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] xcs, String string, Socket socket) throws CertificateException
                        {

                        }

                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] xcs, String string, SSLEngine ssle) throws CertificateException
                        {

                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] xcs, String string, SSLEngine ssle) throws CertificateException
                        {

                        }

                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new  HostnameVerifier()
            {
                @Override
                public boolean verify(String hostname, SSLSession session)
                {
                    return true;
                }
            };
            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

}
