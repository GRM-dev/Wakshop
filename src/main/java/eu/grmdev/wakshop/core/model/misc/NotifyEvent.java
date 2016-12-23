package eu.grmdev.wakshop.core.model.misc;

import java.io.Serializable;

import eu.grmdev.wakshop.core.model.Workshop;
import lombok.Getter;

public class NotifyEvent implements Serializable {
	private static final long serialVersionUID = 1L;
	@Getter
	private NotEventType eventType;
	@Getter
	private Workshop source;
	@Getter
	private Object[] args;
	
	public NotifyEvent(NotEventType eventType, Workshop source, Object... args) {
		this.eventType = eventType;
		this.source = source;
		this.args = args;
	}
	
	public enum NotEventType {
		MEMBER_NEW ,
		MEMBER_LEAVE ,
		MEMBER ,
		TITLE_CHANGED ,
		CHAT_STATE_CHANGED ,
		FILE_TRANSFER_STATE_CHANGED
	}
}
