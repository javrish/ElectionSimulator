package com.election;

import java.util.*;

public class ElectionSim {

	VoterDAO currentVoter;
	CandidateDB candidates;
	// CandidateDAO[] candidates;
	VoterDB voterDB;
	VoteDB caster;
	boolean loginStatus = false;

	public ElectionSim(VoterDB voters, CandidateDB candidates) {
		this.voterDB = voters;
		this.candidates = candidates;
		 // check the input source, inmem or hibernate
			caster = new InMemoryCastVote(candidates.getCandidates());
		
	}

	public String commandExecuter(String command) {
		String[] commandArgs = command.split(" ");
		// check if no. of commands is greater than 2 ie. "login" login id, pass only

		switch (commandArgs[0]) {
		case "login": {

			if (commandArgs.length != 3) {
				return "\nIncorrect Syntax! \n use \"login <username> <password>\"\n";
			}

			if (loginStatus) {
				return currentVoter.getName()+" already logged in";
			}

			String voterName = commandArgs[1];
			String voterPassword = commandArgs[2];

			VoterDAO voter = voterDB.getVoterByName(voterName);
			if (voter == null) {
				return "Invalid Credentials!\n";
			}

			if (voterPassword.equals(voter.getPassword())) {
				currentVoter = voter;
				loginStatus = true;
				return currentVoter + " logged in successfully\n";
			} else
				return "Invalid Credentials!\n";
		}

		case "cast": {
			if (commandArgs.length != 1)
				return "\nIncorrect Syntax for cast! \n use \"cast\"\n";
			
			if (!loginStatus) {
				return "No user logged in.\n Please login first";
			}
			
			if(currentVoter.partyVoted() != null) {
				return "Voter already casted vote to "+currentVoter.partyVoted();
			}
			String result = "";
			String candidateState = currentVoter.getState();
			int candidateIndex = 1;

			List<CandidateDAO> candidatesByState = candidates.getCandidatesByState(candidateState);

			if (candidatesByState == null)
				return "No candidate from your State";
			for (CandidateDAO candidate : candidatesByState) {
				result += candidateIndex++ + ". " + candidate + "\n";
			}
			return result;
		}

		case "vote": {
			if(commandArgs.length != 2) {
				return "Invalid vote Syntax\nUse:\nvote <party name>";
			}
			
			if (!loginStatus) {
				return "No user logged in.\n Please login first";
			}
			
			if(currentVoter.partyVoted() != null) {
				return "Voter just casted vote to "+currentVoter.partyVoted();
			}
			
			String partyName = commandArgs[1];
			String voterState = currentVoter.getState();
			boolean votable = false;
			List<CandidateDAO> candidatesByVotersState = candidates.getCandidatesByState(voterState);
			
			//has voter entered correct party name from his state
			for (CandidateDAO candidate : candidatesByVotersState) {
				if(partyName.equals(candidate.getParty())) {
					votable = true;
				}
			}
			
			//if not eligible to vote that party
			if(!votable)
				return "Wrong Party Name, Please re cast.";
			
			String votedParty = caster.voteUpdate(partyName);
			
			if (votedParty.equals(partyName)) {
				currentVoter.setVoteCasted(partyName);
				return "Vote Casted to " + votedParty;
				// currentVoter.getState();
			} else
				return "vote cast failed!";
			
		}

		case "logout": {

			if (commandArgs.length != 1) {
				return "Incorrect Command\nPlease use:\n \"logout\" \nTo Logout";
			}

			if (!loginStatus) {
				return "No user logged in.\n Please login first";
			}

			String voterName = currentVoter.getName();
			currentVoter = null;
			loginStatus = false;

			return voterName + " logged out";
		}
		
		case "status": {
			
			if(commandArgs.length != 1) {
				return "Incorrect syntax.\n Use:\n \"status \" ";
			}
			
			Hashtable<String, Integer> voteTable = caster.getVoteTable();
			/*
			CandidateDAO[] candidateDAOs = candidates.getCandidates();
			for(CandidateDAO candidate:candidateDAOs) {
				if(voteTable.get(candidate.getParty()) != null) {
					voteTable.get(candidate.getParty());
				}
				*/
			String result = "";
			Iterator<?> iterator = voteTable.entrySet().iterator();
			int partyIndex = 0;
			while(iterator.hasNext()) {
				@SuppressWarnings("unchecked")
				Map.Entry<String, Integer> partyWithVote = (Map.Entry<String, Integer>)iterator.next();
				int votes = (int)partyWithVote.getValue();
				result += ++partyIndex+". "+partyWithVote.getKey() +" Votes Recieved = "+votes+"\n";
																	
			}
			return result;
			
			}
		
		
		default : {
			break;
		}
		}
		return "Invalid Command!\n";
	}
}
