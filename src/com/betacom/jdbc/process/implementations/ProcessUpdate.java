package com.betacom.jdbc.process.implementations;

import java.util.List;
import java.util.Map;

import com.betacom.jdbc.process.exceptions.AcademyException;
import com.betacom.jdbc.process.interfaces.SQLProcess;
import com.betacom.jdbc.singletone.SQLConfiguration;
import com.betacom.jdbc.utilities.DBUtilities;
import com.betacom.jdbc.utilities.SQLManager;

public class ProcessUpdate extends DBUtilities  implements SQLProcess{
	private boolean rc;
	private static SQLManager db = new SQLManager();
	
	
	@Override
	public boolean execute()  {
		System.out.println("Execute update process");
		rc = true;
		 try {
			 SQLConfiguration.getInstance().setAutoCommit();    // sett autocommit on
			 Map<Integer, Object> params = buildParameters(new Object[] {
					 "insert by java",
					 "11199933234",
					 "Via delle rose, 59 Novara",
					 "5589558558"
			 });
			 
			 int numero = db.update(SQLConfiguration.getInstance().getQuery("cliente.insert"), params);
			 
			 System.out.println("Dopo insert :" + numero + " row(s) inseriti");
			 
			 
			 List<Map<String,Object>> lU = db.list(SQLConfiguration.getInstance().getQuery("cliente.list"));
			 lU.forEach(c -> System.out.println(c));
			 
			 params = buildParameters(new Object[] {
					 "my update by java",    // denominazione -> row de fare update
					 "11199933234"           // piva -> univoco per fare il where
			 });

			 numero = db.update(SQLConfiguration.getInstance().getQuery("cliente.update"), params);
			 System.out.println("Dopo update :" + numero + " row(s) aggiornate");

			 params = buildParameters(new Object[] {
					 "11199933234"           // piva -> univoco per fare il where
			 });

			 numero = db.update(SQLConfiguration.getInstance().getQuery("cliente.delete"), params);
			 System.out.println("Dopo delete :" + numero + " row(s) cancellati");
			 
			 params = buildParameters(new Object[] {
					 "Tecnico",    // denominazione -> row de fare update
					 1900,
					 1500
			 });
			 
			 numero = db.update(SQLConfiguration.getInstance().getQuery("dipendente.update.mansione"), params);
			 System.out.println("Dopo update mansione :" + numero + " row(s) aggiornate");
			 
			 
			 
		} catch (Exception e) {
			System.out.println("errore:" + e.getMessage());
			rc=false;
		} 
		
		return rc;
	}

}
