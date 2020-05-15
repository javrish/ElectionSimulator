package com.election;

import java.util.Hashtable;

public class InMemoryCastVote implements VoteDB{

	Hashtable<String, Integer> voteTable = new Hashtable<String, Integer>();
	
	public Hashtable<String, Integer> getVoteTable() {
		return voteTable;
	}

	CandidateDB candidates;

	public InMemoryCastVote(CandidateDAO[] candidateDAOs) {
		
		for(CandidateDAO candidate:candidateDAOs) {
			if(voteTable.get(candidate.getParty()) == null) {
				voteTable.put(candidate.getParty(),0);
			}
		}
	}
	
	@Override
	public String voteUpdate(String partyName) {
		int currentVoteCount = voteTable.get(partyName);
		voteTable.put(partyName, ++currentVoteCount);
		return partyName;
	}

}
