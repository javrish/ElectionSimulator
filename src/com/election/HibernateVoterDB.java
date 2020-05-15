package com.election;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateVoterDB implements VoterDB {

	VoterDAO[] voters;

	public HibernateVoterDB() {
		// TODO Auto-generated constructor stub
	}

	public HibernateVoterDB(VoterDAO[] voters) {
		this.voters = voters;
	}

	@Override
	public VoterDAO getVoterByName(String voterName) {

		Session session = ElectionSimCLI.sessionFactoryObj.openSession();
        Transaction t = session.beginTransaction();

		for (VoterDAO voter : voters) {
			session.save(voter);
		}

		for (VoterDAO voter : voters) {
			if (voter.getName().equals(voterName))
				return voter;
		}
		
		t.commit();

		session.close();
		return null;
	}
}
