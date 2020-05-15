package com.election;

import java.util.List;

public interface CandidateDB {
	List<CandidateDAO> getCandidatesByState(String candidateState);
	CandidateDAO[] getCandidates();
	
	
}
