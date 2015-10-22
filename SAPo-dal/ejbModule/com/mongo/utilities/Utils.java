package com.mongo.utilities;

import java.util.List;

import com.entities.mongo.Alert;
import com.entities.mongo.Spec;

public class Utils {
	
	public Utils() {
	}

	public int getSpecPosition(List<Spec> list, String name){
		for (Spec spec : list) 
		{
			if (spec.getName() == name){
				return list.indexOf(spec);
			}
		}	
		return -1;
	}
	
	public int getAlertPosition(List<Alert> list, String name){
		for (Alert alert : list) 
		{
			if (alert.getName() == name){
				return list.indexOf(alert);
			}
		}	
		return -1;
	}	
	
}
