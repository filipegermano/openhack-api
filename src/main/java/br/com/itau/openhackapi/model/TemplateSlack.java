package br.com.itau.openhackapi.model;

import java.util.List;

import lombok.Data;

@Data
public class TemplateSlack {
	
	private List<AttachmentSlack> attachments;

}
