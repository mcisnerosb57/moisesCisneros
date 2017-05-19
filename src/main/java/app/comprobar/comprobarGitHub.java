package app.comprobar;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.web.client.RestTemplate;

import app.domain.Artefacto;

public class comprobarGitHub {
	public Artefacto comprobar(Artefacto artefacto){
		artefacto.setExiste(false);
		artefacto.setComprobado(true);
		String url="https://api.github.com/repos/"+artefacto.getGrupo() + "/" + artefacto.getNombre() + "/git/refs/tags/" + artefacto.getVersiona();
		//String url="https://api.github.com/repos/kurento2/kurento-client-filters-js/git/refs/tags/6.5.0";
		
		try {
			
			//String reference;
			URI uri=new URI(url);
			RestTemplate resttemplate=new RestTemplate();
			
			String res=resttemplate.getForObject(uri, String.class);
			
			
			
			 if (res.contains("object")) {
				 artefacto.setExiste(true);
				 
			 }
			 
			
			//ObjectMapper mapper = new ObjectMapper();
		} 
		finally{
			
			return artefacto;
		}
		
		//return artefacto;
	}

}
