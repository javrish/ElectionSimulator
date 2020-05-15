package com.election;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;



public class HibernateCandidateDB implements CandidateDB {
	
	private CandidateDAO[] candidates;
	
	public HibernateCandidateDB(CandidateDAO[] candidates) {
		this.candidates = candidates;
		
	}
	
	@Override
	public List<CandidateDAO> getCandidatesByState(String candidateState) {
		
		List<CandidateDAO> candidatesByState = new ArrayList<CandidateDAO>();
		Session session = ElectionSimCLI.sessionFactoryObj.openSession();
		Criteria c = session.createCriteria(CandidateDAO.class);
		c.add(Restrictions.eq("state", candidateState));
		candidatesByState = c.list();	
		System.out.println("hello" +candidatesByState);

		return candidatesByState;
	}

	@Override
	public CandidateDAO[] getCandidates() {
		
			Session session = ElectionSimCLI.sessionFactoryObj.openSession();
			List<CandidateDAO> candidateList = (ArrayList<CandidateDAO>) session.createCriteria(CandidateDAO.class).list();
			CandidateDAO[] candidates = new CandidateDAO[candidateList.size()];
			int i = 0;			
			for(CandidateDAO c : candidateList)
			{
				candidates[i] = c;
				i++;
			}	
			return candidates;		
	}

}
