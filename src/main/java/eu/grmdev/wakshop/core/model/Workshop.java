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

import eu.grmdev.wakshop.core.net.ClientService;
import eu.grmdev.wakshop.core.net.ConnectionMember;
import eu.grmdev.wakshop.core.net.Server;
import eu.grmdev.wakshop.utils.UtilsHelper;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@Table(name = "workshops")
@Data
public class Workshop implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "f_title", unique = true, nullable = false)
	private String title;
	@Column(name = "f_archieved")
	private boolean archieved;
	@Column(name = "f_chat_on")
	private boolean chatEnabled;
	@Column(name = "f_file_transfer_on")
	private boolean fileTransferEnabled;
	
	@Transient
	private transient ConnectionMember host;
	@Setter(value = AccessLevel.PRIVATE)
	@Transient
	private transient List<ClientService> members;
	
	public void setHost(Server s) {
		if (s != null) {
			this.host = s;
		}
	}
	
	public void addMember(ClientService client) {
		if (members == null) {
			members = new ArrayList<>();
		}
		members.add(client);
		notifyMembers();
	}
	
	private void notifyMembers() {
		for (Iterator<ClientService> it = members.iterator(); it.hasNext();) {
			ClientService cs = it.next();
			try {
				cs.workshopUpdated(this);
			}
			catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void notifyObservers() {
		// TODO Auto-generated method stub
		
	}
	
	public void setReorganizedMembers(List<ClientService> members) {
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
}
