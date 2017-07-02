package app.comprobar;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import app.domain.Artefacto;
import app.service.impl.ArtefactoServiceImpl;
import app.web.rest.ArtefactoResource;

public class comprobarMaven {
	private final Logger log = LoggerFactory.getLogger(ArtefactoServiceImpl.class);
	
@SuppressWarnings("finally")
public Artefacto comprobar(Artefacto artefacto){
	artefacto.setComprobado(true);
	artefacto.setExiste(false);
	//http://search.maven.org/solrsearch/select?q=g:%22org.kurento%22+AND+a:%22kurento-utils-js%22&rows=20&core=gav
String path="http://search.maven.org/solrsearch/select?q=g:\""+artefacto.getGrupo()+"\" AND a:\""+artefacto.getNombre()+"\"&rows=20&core=gav";
//	String path="http://search.maven.org/solrsearch/select?q=g:\""+artefacto.getGrupo()+"\" AND a:\""+artefacto.getNombre()+"\" AND v:\""+artefacto.getVersiona()+"\" OR l:\"javadoc\" OR l:\"jar\"&rows=20&wt=json";
	//"response\":{\"numFound\":"
	//http://search.maven.org/solrsearch/select? q=g:"org.kurento"%20AND%20a:"kms-api-filters"%20AND%20v:"6.5.0" %20AND%20l:"javadoc"%20AND%20p:"jar"&rows=20&wt=json"
	try {			
		URL url=new URL(path);
		String nullFragment = null;			
		URI uri=new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(),nullFragment );
		RestTemplate resttemplate=new RestTemplate();
		String res=resttemplate.getForObject(uri, String.class);	
		 log.debug("AQUI"+res);
		 
		 log.debug((":"+artefacto.getVersiona()+"\","+"\""+"g"));
		
		if((res.contains(":"+artefacto.getVersiona()+"\","+"\""+"g"))){
//		if((res.contains("numFound\\"))){
//		if((res.contains("\""+"numFound"+"\""+":0"))){
			artefacto.setExiste(true);
			
//			":{"numFound":1
			
			if ((res.contains(artefacto.getNombre())) && (res.contains(artefacto.getVersiona()))){
				artefacto.setExiste(true);
			}
			else{
				artefacto.setExiste(false);
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
