package com.betacom.jdbc.process.implementations;

import java.util.List;
import java.util.Map;

import com.betacom.jdbc.model.Cliente;
import com.betacom.jdbc.model.Dipendente;
import com.betacom.jdbc.process.exceptions.AcademyException;
import com.betacom.jdbc.process.interfaces.SQLProcess;
import com.betacom.jdbc.singletone.SQLConfiguration;
import com.betacom.jdbc.utilities.DBUtilities;
import com.betacom.jdbc.utilities.SQLManager;
import static com.betacom.jdbc.utilities.MappingUtilities.tranformMapCliente;
import static com.betacom.jdbc.utilities.MappingUtilities.buildResultSetDipendente;



public class ProcessTransaction extends DBUtilities  implements SQLProcess{
	private boolean rc;
	private static SQLManager db = new SQLManager();

	@Override
	public boolean execute() {
		System.out.println("Begin process transaction");
		try {
			SQLConfiguration.getInstance().setTransaction();   // set transaction on
			
			 Map<Integer, Object> params = buildParameters(new Object[] {
					 "transaction by java",
					 "22299933234",
					 "Via delle Transaction, 70 Trapani",
					 "6689558558"
			 });
			 
			 int numero = db.update(SQLConfiguration.getInstance().getQuery("cliente.insert"), params);
			 
			 params = buildParameters(new Object[] {
					 "22299933234",
			 });
			 
			 Cliente c = tranformMapCliente(db.get(SQLConfiguration.getInstance().getQuery("cliente.byPIva"), params));
			 System.out.println(c);

			 params = buildParameters(new Object[] {
					 "Alice",
					 "Due"
			 });
			 

			 Dipendente d = buildResultSetDipendente(db.get(SQLConfiguration.getInstance().getQuery("dipendente.nomeCognome"), params));
			 System.out.println(d);
			 
			 params = buildParameters(new Object[] {
					 "Rapporto Java",
					 c.getId(),
					 d.getId()
			 });
			 
			 numero = db.update(SQLConfiguration.getInstance().getQuery("raaporto.insert"), params);
			 System.out.println("Rapporto cliente inserito :" + numero + " record(s)");

			 
			 
			 int i = 0;
			 
			 int j = 20 /i;
			 
			 System.out.println("Execute commit ......");
			 db.commmit();
		} catch (Exception e) {
			System.out.println("errore:" + e.getMessage());
			rc=false;
			try {
				System.out.println("Execute rollback ......");
				db.rollBack();
			} catch (AcademyException e1) {
				System.out.println("Errore nel rollback :" + e.getMessage());
			}
		} 
		
		
		return true;
	}
	
	

}
