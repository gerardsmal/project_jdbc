package com.betacom.jdbc.utilities;

import java.util.HashMap;
import java.util.Map;

public class DBUtilities {

	/*
	 * build parameters map for SQLManager
	 * key = object position (progressif)
	 * value = parameter value
	 */
	public Map<Integer, Object> buildParameters(Object[] p){
		Map<Integer, Object> params = new HashMap<Integer, Object>(); // result definition
		int pIdx = 1; // init position
		for (Object o:p) {
			params.put(pIdx++, o); // put into mao
		}
		return params;  // {1,param1}, {2,param2} ....
	}
	
}
