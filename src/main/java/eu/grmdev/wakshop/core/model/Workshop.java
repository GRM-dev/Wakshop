package eu.grmdev.wakshop.core.model;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import eu.grmdev.wakshop.core.model.misc.NotifyEvent;
import eu.grmdev.wakshop.core.model.misc.NotifyEvent.NotEventType;
import eu.grmdev.wakshop.core.net.ClientService;
import eu.grmdev.wakshop.core.net.ConnectionMember;
import eu.grmdev.wakshop.core.net.Server;
import eu.grmdev.wakshop.utils.UtilsHelper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "workshops")
public class Workshop implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter
	@Setter
	private int id;
	@Getter
	@Column(name = "f_title", unique = true, nullable = false)
	private String title;
	@Getter
	@Column(name = "f_archieved")
	private boolean archieved;
	@Getter
	@Column(name = "f_chat_on")
	private boolean chatEnabled;
	@Getter
	@Column(name = "f_file_transfer_on")
	private boolean fileTransferEnabled;
	
	@Transient
	@Getter
	private transient ConnectionMember host;
	@Setter(value = AccessLevel.PRIVATE)
	@Transient
	private transient List<ClientService> members;
	
	public void addMember(ClientService client) {
		if (members == null) {
			members = new ArrayList<>();
		}
		members.add(client);
		notifyMembers(new NotifyEvent(NotEventType.MEMBER_NEW, this, client));
	}
	
	public void captureEvent(NotifyEvent event) {
		if (event == null) { return; }
		switch (event.getEventType()) {
			case CHAT_STATE_CHANGED :
				break;
			case FILE_TRANSFER_STATE_CHANGED :
				break;
			case MEMBER :
				this.setReorganizedMembers(event.getSource().getMembers());
				break;
			case MEMBER_LEAVE :
				break;
			case MEMBER_NEW :
				break;
			case TITLE_CHANGED :
				title = (String) event.getArgs()[0];
				break;
			default :
				break;
		}
		updateView();
	}
	
	private void updateView() {
		// TODO Auto-generated method stub
		
	}
	
	private void notifyMembers(NotifyEvent event) {
		for (Iterator<ClientService> it = members.iterator(); it.hasNext();) {
			ClientService cs = it.next();
			try {
				cs.workshopUpdated(event);
			}
			catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setHost(Server s) {
		if (s != null) {
			this.host = s;
		}
	}
	
	private void setReorganizedMembers(List<ClientService> members) {
		if (members != null) {
			if (this.members == null) {
				this.members = members;
			}
			else {
				this.members = UtilsHelper.combineSubtractArrays(this.members, members);
			}
		}
	}
	
	public List<ClientService> getMembers() {
		if (members == null) {
			members = new ArrayList<>();
		}
		return members;
	}
	
	public void setTitle(String title) {
		if (this.title == title) { return; }
		this.title = title;
		notifyMembers(new NotifyEvent(NotEventType.TITLE_CHANGED, this, title));
	}
	
	public void setChatEnabled(boolean chatEnabled) {
		if (this.chatEnabled == chatEnabled) { return; }
		this.chatEnabled = chatEnabled;
		notifyMembers(new NotifyEvent(NotEventType.CHAT_STATE_CHANGED, this, chatEnabled));
	}
	
	public void setFileTransferEnabled(boolean fileTransferEnabled) {
		if (this.fileTransferEnabled == fileTransferEnabled) { return; }
		this.fileTransferEnabled = fileTransferEnabled;
		notifyMembers(new NotifyEvent(NotEventType.FILE_TRANSFER_STATE_CHANGED, this, fileTransferEnabled));
	}
}
