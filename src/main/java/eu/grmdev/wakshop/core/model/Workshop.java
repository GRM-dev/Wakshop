package eu.grmdev.wakshop.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "workshops")
@Data
public class Workshop {
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
}
