package com.election;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CandidateDAO")
public class CandidateDAO {

	private String name;
	private String state;
	private String party;
	
	public CandidateDAO(String name, String state, String party) {
		this.name = name;
		this.state = state;
		this.party = party;
	}
	
	public CandidateDAO() {
		// TODO Auto-generated constructor stub
	}
	
	@Id
	@Column(name="Name")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="Party")
	public String getParty() {
		return party;
	}

	public void setParty(String party) {
		this.party = party;
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
		return name +" " +party;
	}
}
