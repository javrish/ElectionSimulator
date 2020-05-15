package com.election;

public interface VoterDB {
	VoterDAO getVoterByName(String voterName);
}
