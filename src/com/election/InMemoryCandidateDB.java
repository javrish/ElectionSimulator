package com.election;

import java.util.ArrayList;

public class InMemoryCandidateDB implements CandidateDB{
	CandidateDAO[] candidates;

	public CandidateDAO[] getCandidates() {
		return candidates;
	}

	public InMemoryCandidateDB(CandidateDAO[] candidates) {
		this.candidates = candidates;
	}
	
	@Override
	public ArrayList<CandidateDAO> getCandidatesByState(String candidateState) {
		
		ArrayList<CandidateDAO> candidatesByState = new ArrayList<>();
		for (CandidateDAO candidateRecord : candidates) {
			if (candidateRecord.getState().equals(candidateState)) {
				candidatesByState.add(candidateRecord);
			}
		}
		return candidatesByState;
	}	
}
