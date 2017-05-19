package app.comprobar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import app.domain.Artefacto;

public class comprobarNpm {
	
	
	public Artefacto comprobar(Artefacto artefacto){
		
		String cmd="npm.cmd view "+ artefacto.getNombre();
		//String cmd="npm.cmd view  kurento-client-core ";
		Process process=null;
		InputStream is=null;
		
		
try {
	    	
	    	process=Runtime.getRuntime().exec(cmd);	
			is=process.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		    StringBuilder out = new StringBuilder();
		    String line;
		    String json="";
			while ((line = reader.readLine()) != null) {
			        out.append(line.trim());			       
			}
			json=json+out.toString();
			reader.close();
			
			if(json.equals("")){
				
				// In case the artifact specified is not found in the npm package registry, the output string will be empty
				artefacto.setComprobado(true);
				artefacto.setExiste(false);
				
			}else {
				
				// If the artifact is found, npm view will provide us with all related information. In particular,
				// we will get the versions available for the artifact. By using some String processing, we are able
				// to determine whether the version under study is in the npm registry.
				
				// Disable comment if ouput has to be shown on screen (prints the string content read from the input stream)
				//System.out.println(json);  
				
				// Create the substring with the data to analyze 
				
		   	    String json2=(json.substring((json.indexOf("versions:"))+10,(json.indexOf("maintainers:"))-1));
		   	    
		   	    // Disable comment if ouput has to be shown on screen
		   	    //System.out.println(json2);
		   	    
		   	    if (json2.contains(artefacto.getVersiona())) {
		   	    	
		   	    	artefacto.setComprobado(true);
					artefacto.setExiste(true);
		   	    } else {
		   	    	artefacto.setComprobado(true);
					artefacto.setExiste(false);
		   	    }
		   	    
			}
		    
		    
		} catch (IOException e) {
			
			e.printStackTrace();
		}	
		
		
		
		return artefacto;
		
	}






}
