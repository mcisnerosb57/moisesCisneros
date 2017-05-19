package app.comprobar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import app.domain.Artefacto;

public class comprobarBower {
	
	public Artefacto comprobar(Artefacto artefacto){
		String cmd="bower.cmd info "+ artefacto.getNombre() + "#" + artefacto.getVersiona();
		//String cmd="npm.cmd view  kurento-client-core ";
		Process process=null;
		InputStream is=null;	
		
	    try {
	    	
	    	
	    	process=Runtime.getRuntime().exec(cmd);	
			is=process.getInputStream();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			System.out.println(reader); 
		    StringBuilder out = new StringBuilder();
		    String line;
		    String json="";
			while ((line = reader.readLine()) != null) {
			        out.append(line);			       
			}
			json=json+out.toString();
			reader.close();
			// Disable comment if command output has to be shown on screen
			System.out.println(json); 
		    
			if (json.equals("")){
			    
				// This covers those cases in which the artifact specified is incorrect (Bower returns an empty output)
				artefacto.setComprobado(true);
				artefacto.setExiste(false);
		
				 
			
			} else {
				
				if(!(json.contains("{") & json.contains("}"))){
					artefacto.setComprobado(true);
					artefacto.setExiste(false);
					
	
			    }
				
				else {
					artefacto.setComprobado(true);
					artefacto.setExiste(true);			
		    	}		    		    	
		    }
		} catch (IOException e) {
			
			e.printStackTrace();
		}	
		
		return artefacto;
	}

}