package com.omnichannel.app.model.extra;

import lombok.Data;

@Data
public class EmailEntity {
	
	private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;

}
