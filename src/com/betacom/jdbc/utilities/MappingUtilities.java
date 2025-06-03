package com.betacom.jdbc.utilities;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.betacom.jdbc.model.Cliente;
import com.betacom.jdbc.model.Dipendente;

public class MappingUtilities {
	/*
	 * convert result (List<Map<String,Object>>) into List<Dipendente>
	 */
	public static List<Dipendente> buildResultSetDipendente(List<Map<String,Object>> inp){
		return inp.stream()
				 .map(row -> new Dipendente(
						 (Integer)row.get("id_dipendente"),
						 row.get("nome")+"",
						 row.get("cognome")+"",
						 (Date)row.get("data_assunzione"),
						 (Integer)row.get("id_ufficio"),
						 row.get("telefono")+"",
						 row.get("mansione")+"",
						 (BigDecimal)row.get("stipendio")))
				 .collect(Collectors.toList());
	}

	public static  Dipendente buildResultSetDipendente(Map<String,Object> row){
		return	 new Dipendente(
						 (Integer)row.get("id_dipendente"),
						 row.get("nome")+"",
						 row.get("cognome")+"",
						 (Date)row.get("data_assunzione"),
						 (Integer)row.get("id_ufficio"),
						 row.get("telefono")+"",
						 row.get("mansione")+"",
						 (BigDecimal)row.get("stipendio"));
				 
	}

	public static Cliente tranformMapCliente(Map<String,Object> c) {
		return new Cliente((Integer)c.get("id_cliente"),
				c.get("denominazione").toString(),
				c.get("p_iva").toString(), 
				c.get("inidirizzo").toString(),
				c.get("telefono").toString());
	}

}
