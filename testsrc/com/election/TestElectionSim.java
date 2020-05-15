package com.election;

public class TestElectionSim {

	private VoterDAO[] voters = new VoterDAO[] { new VoterDAO("scott", "tiger", "Gujarat"),
			new VoterDAO("admin", "admin", "WestBengal") };

	private CandidateDAO[] candidates = new CandidateDAO[] { new CandidateDAO("Narendra Modi", "Gujarat", "BJP"),
			new CandidateDAO("Rahul Gandhi", "Gujarat", "INC"), new CandidateDAO("Mamta Banerjee", "WestBengal", "TMC") };
//**********************************************************************

	public static void main(String[] args) {

		TestElectionSim test = new TestElectionSim();

		test.loginViaAdminIsValid();
		test.loginWhenARegisteredVoterisValid();
		test.loginWithInvalidCredentialsFails();
		test.loginWithIncorrectLoginCommand();
		test.InvalidCommandEntered();
		test.castShowsCandidatesFromVotersState();
		test.castWithIncorrectCommand();		
		test.voteWithWrongPartyNameFailsTest();
		test.voteFromCastList();
		test.multipleLoginNotAllowed();
		test.logoutMakesCurrentUserLogoutTest();
		test.logoutWithoutLoginTest();
		test.logoutWithInavlidCommandSyntaxIsInvalid();
		test.reCastWhenWrongPartyNameEntered();
		test.voterCantCastMultipleVotes();
		
		test.getStatusOfElection();
		
		System.out.println("You got this brother <3 ! \n\nAll test cases passed!");
	}

	private void getStatusOfElection() {
		ElectionSim simulator = createElectionSim();
		
		simulator.commandExecuter("login admin admin");
		simulator.commandExecuter("cast");
		simulator.commandExecuter("vote TMC");
		
		check(simulator.commandExecuter("status"),"1. BJP Votes Recieved = 0\n2. TMC Votes Recieved = 0\n3. INC Votes Recieved = 0\n");
	}	

	private void voterCantCastMultipleVotes() {
		ElectionSim simulator = createElectionSim();
		
		simulator.commandExecuter("login admin admin");
		simulator.commandExecuter("cast");
		simulator.commandExecuter("vote TMC");
		check(simulator.commandExecuter("vote TMC"),"Voter just casted vote to TMC");
	}

	private void reCastWhenWrongPartyNameEntered() {
		ElectionSim simulator = createElectionSim();
		simulator.commandExecuter("login admin admin");
		simulator.commandExecuter("cast");
		simulator.commandExecuter("vote BJP");
		
		check(simulator.commandExecuter("vote TMC"),"Voter just casted vote to TMC");	
	}


	private void logoutWithInavlidCommandSyntaxIsInvalid() {
		ElectionSim simulator = createElectionSim();
		
		check(simulator.commandExecuter("logout admin"),"Incorrect Command\nPlease use:\n \"logout\" \nTo Logout");
		
	}

	private void logoutWithoutLoginTest() {
		ElectionSim simulator = createElectionSim();
		
		check(simulator.commandExecuter("logout"),"No user logged in.\n Please login first");
}

	private void logoutMakesCurrentUserLogoutTest() {
		ElectionSim simulator = createElectionSim();
		
		simulator.commandExecuter("login admin admin");
		check(simulator.commandExecuter("logout"),"admin logged out");
	}

	private void multipleLoginNotAllowed() {
		ElectionSim simulator = createElectionSim();
		
		simulator.commandExecuter("login admin admin");
		
		check(simulator.commandExecuter("login admin admin"),"admin already logged in");
}

	private void voteFromCastList() {
		ElectionSim simulator = createElectionSim();
		
		simulator.commandExecuter("login admin admin");
		simulator.commandExecuter("cast");
		
		check(simulator.commandExecuter("vote TMC"),"Vote Casted to TMC");
	}

	private void voteWithWrongPartyNameFailsTest() {
		ElectionSim simulator = createElectionSim();
		simulator.commandExecuter("login admin admin");
		simulator.commandExecuter("cast");
		
		check(simulator.commandExecuter("vote BJP"),"Wrong Party Name, Please re cast.");
	}
	
	private void castWithIncorrectCommand() {
		ElectionSim simulator = createElectionSim();

		check(simulator.commandExecuter("cast vote"), "\nIncorrect Syntax for cast! \n use \"cast\"\n");
	}

	private void castShowsCandidatesFromVotersState() {
		ElectionSim simulator = createElectionSim();

		simulator.commandExecuter("login scott tiger");
		check(simulator.commandExecuter("cast"), "1. Narendra Modi BJP\n2. Rahul Gandhi INC\n");

	}

	private void InvalidCommandEntered() {
		ElectionSim simulator = createElectionSim();

		check(simulator.commandExecuter("signin admin admin"), "Invalid Command!\n");
	}

	private void loginWithIncorrectLoginCommand() {
		ElectionSim simulator = createElectionSim();

		check(simulator.commandExecuter("login user pass gujrat"),
				"\nIncorrect Syntax! \n use \"login <username> <password>\"\n");
	}

	private void loginWithInvalidCredentialsFails() {
		ElectionSim simulator = createElectionSim();

		check(simulator.commandExecuter("login wrong user"), "Invalid Credentials!\n");

	}

	private void loginWhenARegisteredVoterisValid() {
		ElectionSim simulator = createElectionSim();

		check(simulator.commandExecuter("login scott tiger"), "scott logged in successfully\n");
	}

	private ElectionSim createElectionSim() {
		return new ElectionSim(new InMemoryVoterDB(voters), new InMemoryCandidateDB(candidates));
	}

	private void loginViaAdminIsValid() {
		ElectionSim simulator = createElectionSim();
		String command = "login admin admin";

		check(simulator.commandExecuter(command), "admin logged in successfully\n");
	}

//**********************************************************************	

	private static void check(String actual, String expected) {
		if (!actual.equals(expected)) {
			throw new RuntimeException(String.format("Recieved: \"" + actual + "\" Expected: \"" + expected + "\""));
		}
	}
	
}