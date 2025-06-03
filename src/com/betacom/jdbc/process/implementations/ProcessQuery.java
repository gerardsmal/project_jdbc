package com.betacom.jdbc.process.implementations;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.betacom.jdbc.model.Dipendente;
import com.betacom.jdbc.process.exceptions.AcademyException;
import com.betacom.jdbc.process.interfaces.SQLProcess;
import com.betacom.jdbc.singletone.SQLConfiguration;
import com.betacom.jdbc.utilities.DBUtilities;
import com.betacom.jdbc.utilities.SQLManager;
import com.mysql.cj.protocol.a.SqlDateValueEncoder;

import static com.betacom.jdbc.utilities.MappingUtilities.buildResultSetDipendente;


public class ProcessQuery extends DBUtilities  implements SQLProcess{
	
	private SQLManager db = new SQLManager();
	private String qry = null;

	@Override
	public boolean execute()  {
		
		boolean rc = true;
		try {
			
			 SQLConfiguration.getInstance().setAutoCommit();  // set autocommmit on
			 List<String> lT = db.listOfTable("db_academy");
			 
			 lT.forEach(t -> System.out.println(t));
			 
			 /*
			  * Parametri sono indicate con ?
			  */
			 System.out.println("********* query dipendente.mansione ***************");
			 qry = SQLConfiguration.getInstance().getQuery("dipendente.mansione");

			 Map<Integer, Object> params = buildParameters(new Object[] {
					 "impiegato"
			 });
			 
			 List<Dipendente> ld = buildResultSetDipendente(db.list(qry, params));  // execute query
			 ld.forEach(d -> System.out.println(d));
			 /*
			  * Query with two parameters
			  */
			 System.out.println("********* query dipendente.mansione.stipendio ************");
			 qry = SQLConfiguration.getInstance().getQuery("dipendente.mansione.stipendio");
			 params = buildParameters(new Object[] {
				"impiegato",
				1500
			 });
			 
			 ld = buildResultSetDipendente(db.list(qry, params));  // execute query
			 ld.forEach(d -> System.out.println(d));
			 /*
			  * Query without parameters 
			  */
			 System.out.println("********* query dipendente ************");
			 qry = SQLConfiguration.getInstance().getQuery("dipendente");
			 ld = buildResultSetDipendente(db.list(qry));  // execute query
			 ld.forEach(d -> System.out.println(d));
			 /*
			  * Query with on row 
			  */			 
			 qry = SQLConfiguration.getInstance().getQuery("dipendente.byId");
			 params = buildParameters(new Object[] {9});
			 System.out.println("********* query dipendente byId ************");
			 Dipendente d = buildResultSetDipendente(db.get(qry, params));
			 System.out.println(d);
			 System.out.println("********* Count ************");

			 params = buildParameters(new Object[] {"impiegato"});
			 Long count = db.count(SQLConfiguration.getInstance().getQuery("dipendente.mansione"), params);
			 System.out.println("Count:" + count);
			 
			 
			 
		} catch (Exception e) {
			System.out.println(e.getMessage());
			rc = false;
		} 
		
		return rc;
		
	}

	
}
