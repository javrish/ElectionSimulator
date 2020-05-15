package com.election;

public class InMemoryVoterDB implements VoterDB{
	
	VoterDAO[] voters;

	//this will read file and pass to interface VoterDB

	public InMemoryVoterDB(VoterDAO[] voters) {
		this.voters = voters;
	}
	
	@Override
	public VoterDAO getVoterByName(String voterName) {
		for (VoterDAO voterRecord : voters) {
			if (voterRecord.getName().equals(voterName)) {
				return voterRecord;
			}
		}
		return null;
	}

	
}