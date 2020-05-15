package com.election;

import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class ElectionSimCLI {
	public static SessionFactory sessionFactoryObj;

	public ElectionSimCLI() {

		Configuration configurationObj = new Configuration();
		configurationObj.configure("hibernate.cfg.xml");

		sessionFactoryObj = configurationObj.buildSessionFactory();
	}

	private VoterDAO[] voters = new VoterDAO[] { new VoterDAO("scott", "tiger", "Gujarat"),
			new VoterDAO("admin", "admin", "WestBengal"), new VoterDAO("vaibhav", "vb12", "Gujarat"),
			new VoterDAO("prateek", "pg13", "Gujarat") };

	private CandidateDAO[] candidates = new CandidateDAO[] { new CandidateDAO("Narendra Modi", "Gujarat", "BJP"),
			new CandidateDAO("Rahul Gandhi", "Gujarat", "INC"),
			new CandidateDAO("Mamta Banerjee", "WestBengal", "TMC") };

	public static void main(String[] args) {
		ElectionSimCLI cli = new ElectionSimCLI();

		ElectionSim simulator = cli.createElectionSim();
		ElectionSim simulatorHB = cli.createElectionSimHibernate();

		Scanner sc = new Scanner(System.in);
		String command;
		while (!(command = sc.nextLine()).equals("done")) {
			System.out.println(simulatorHB.commandExecuter(command));
		}

		sc.close();
	}

	private ElectionSim createElectionSimHibernate() {

		System.out.println(".......ENGINE START.......");
		System.out.println(".. ONE TO ONE ANNOTATION INSERT LESSON ..");

		Session session = sessionFactoryObj.openSession();

		Transaction t = session.beginTransaction();

		for (VoterDAO voter : voters) {
			session.save(voter);
		}

		for (CandidateDAO candidate : candidates) {
			session.save(candidate);
		}

		t.commit();

		session.close();

		return new ElectionSim(new HibernateVoterDB(voters), new HibernateCandidateDB(candidates));
	}

	private ElectionSim createElectionSim() {
		return new ElectionSim(new InMemoryVoterDB(voters), new InMemoryCandidateDB(candidates));
	}
}