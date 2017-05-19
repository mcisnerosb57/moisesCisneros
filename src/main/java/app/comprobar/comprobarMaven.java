package app.comprobar;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import app.domain.Artefacto;
import app.web.rest.ArtefactoResource;

public class comprobarMaven {
	
@SuppressWarnings("finally")
public Artefacto comprobar(Artefacto artefacto){
	artefacto.setComprobado(true);
	artefacto.setExiste(false);
	
	String path="http://search.maven.org/solrsearch/select?q=g:\""+artefacto.getGrupo()+"\" AND a:\""+artefacto.getNombre()+"\" AND v:\""+artefacto.getVersiona()+"\" OR l:\"javadoc\" OR l:\"jar\"&rows=20&wt=json";
	//"response\":{\"numFound\":"
	
	try {			
		URL url=new URL(path);
		String nullFragment = null;			
		URI uri=new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(),nullFragment );
		RestTemplate resttemplate=new RestTemplate();
		String res=resttemplate.getForObject(uri, String.class);	
		artefacto.setNombre(res.substring(255, 285));
		
		
		if((res.contains("numFound\\"))){
			
			if ((res.contains(artefacto.getNombre())) && (res.contains(artefacto.getVersiona()))){
				artefacto.setExiste(true);
				artefacto.setNombre("moises");
			}
			else{
				artefacto.setExiste(false);
				artefacto.setNombre("fail");
			}
			    
			}
	
	
		}
		
		catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	finally{
	
	return artefacto;}
	

}

}
