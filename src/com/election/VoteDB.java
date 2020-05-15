package com.election;

import java.util.Hashtable;

public interface VoteDB {

	String voteUpdate(String partyName);
	public Hashtable<String, Integer> getVoteTable();
}
