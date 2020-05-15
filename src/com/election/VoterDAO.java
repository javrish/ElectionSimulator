package com.election;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="VoterDAO")
public class VoterDAO {
	
	private String name;
	private String password;
	private String state;
	private String partyVoted;
	
	public VoterDAO() {
		// TODO Auto-generated constructor stub
	}
	public VoterDAO(String name, String password, String state) {
		this.name = name;
		this.password = password;
		this.state = state;
		this.partyVoted = null;
	}
	
	public String partyVoted() {
		return partyVoted;
	}

	public void setVoteCasted(String partyVoted) {
		this.partyVoted = partyVoted;
	}

	@Id
	@Column(name="Name")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="Password")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name="State")
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return name;
	}
}
