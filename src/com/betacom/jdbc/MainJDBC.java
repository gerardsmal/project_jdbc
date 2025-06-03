package com.betacom.jdbc;

import com.betacom.jdbc.process.exceptions.AcademyException;
import com.betacom.jdbc.process.implementations.ProcessQuery;
import com.betacom.jdbc.process.implementations.ProcessTransaction;
import com.betacom.jdbc.process.implementations.ProcessUpdate;
import com.betacom.jdbc.process.interfaces.SQLProcess;
import com.betacom.jdbc.singletone.SQLConfiguration;

public class MainJDBC {

	public static void main(String[] args) {
		System.out.println("Begin MainJDBC");
		SQLProcess run = null;
		
		try {
			SQLConfiguration.getInstance().getConnection();   // autocommit = true
			System.out.println("Process Query *******************");
			run = new ProcessQuery();
			if (!run.execute())
				System.out.println("Error found...");  // run ProcessQuery

			System.out.println("Process Update *******************");
			run = new ProcessUpdate();
			if (!run.execute())
				System.out.println("Error found...");  // run ProcessQuery
			
			System.out.println("Process Transaction *******************");			
			run = new ProcessTransaction();
			if (!run.execute())
				System.out.println("Error found...");
			
		
		} catch (AcademyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
