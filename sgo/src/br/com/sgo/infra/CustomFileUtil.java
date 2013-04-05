package br.com.sgo.infra;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class CustomFileUtil {

	public static byte[] fileToByte(File pdf) throws Exception {   
	    FileInputStream fis = new FileInputStream(pdf);   
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();   
	    byte[] buffer = new byte[8192];   
	    int bytesRead = 0;   
	    while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {   
	        baos.write(buffer, 0, bytesRead);   
	    }   
	    return baos.toByteArray();   
	}

	public static void extraiZip(File origem, File destino) throws ZipException, IOException {     
	
		ZipFile zip = null;     
		File arquivo = null;     
		InputStream is = null;     
		OutputStream os = null;     
	
	    byte[] buffer = new byte[1024];     
	
	    try {     
	      if(!destino.exists()) {     
	        destino.mkdirs();     
	      }
	
	      if(!destino.exists() || !destino.isDirectory()) {     
	        throw new IOException("Informe um diretório válido");     
	      }     
	
	      zip = new ZipFile(origem);     
	
	      Enumeration<?> e = zip.entries();   
	      while(e.hasMoreElements()) {     
	        ZipEntry entrada = (ZipEntry) e.nextElement();     
	        arquivo = new File(destino, entrada.getName());     
	        if(entrada.isDirectory() && !arquivo.exists()) {     
	          arquivo.mkdirs();     
	          continue;     
	        }     
	        if(!arquivo.getParentFile().exists()) {     
	          arquivo.getParentFile().mkdirs();     
	        }   
	
	        is = zip.getInputStream(entrada);   
	        os = new FileOutputStream(arquivo);   
	        int bytesLidos = 0;   
	        if (is == null) {   
	            throw new ZipException("Erro ao ler a entrada do zip: " + entrada.getName());   
	        }   
	
	        while ((bytesLidos = is.read(buffer)) > 0) {   
	            os.write(buffer, 0, bytesLidos);   
	        }   
	    }   

	    } catch(Exception e){   

	    }finally {     
	        if (os != null){   
	            os.close();   
	        }   

	        if (is != null){   
	            is.close();   
	        }   

	        if (zip != null){   
	            zip.close();   
	        }     
	    }     
	}
	
	public static void deleteFile(File f){

		if (!f.exists())
		      throw new IllegalArgumentException(
		          "Delete: no such file or directory: " + f.getPath());

		if (!f.canWrite())
		      throw new IllegalArgumentException("Delete: write protected: "
		          + f.getPath());

		boolean success = f.delete();

		if (!success)
		      throw new IllegalArgumentException("Delete: deletion failed");

	}
}
