package br.com.sgo.infra;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import br.com.caelum.vraptor.Convert;
import br.com.caelum.vraptor.Converter;

@Convert(Date.class)  
public class DateConverter implements Converter<Date> {  
  
    @Override  
    public Date convert(String value, Class<? extends Date> arg1, ResourceBundle arg2) {  

    	SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    	SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm");
    	
    	if(value.trim().length() == dateFormat1.toPattern().length()){
    		
    		try {  
                return new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(value);  
            } catch (ParseException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
                return null;  
            }
    		
    	} else if (value.trim().length() == dateFormat2.toPattern().length()){
    		
    		try {  
                return new SimpleDateFormat("HH:mm").parse(value);  
            } catch (ParseException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
                return null;  
            }
    		
    	} else  {
    		
    		try {  
                return new SimpleDateFormat("HH:mm").parse(value);  
            } catch (ParseException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
                return null;  
            }
    		
    	}
  	
    	  
    }  
  
}  
