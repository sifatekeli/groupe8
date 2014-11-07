/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.Part;

import model.Item;

@ManagedBean
@SessionScoped
public class ajouter {

    @EJB
    private String titre;
    private String texte;
    private Part file;
    
    public String upload(titre , texte) throws IOException {
        InputStream inputStream = file.getInputStream();        
        String fileName = getFileName(file);
        FileOutputStream outputStream = new FileOutputStream("C:\\Users\\hibon_000\\Documents\\NetBeansProjects\\petcatalog\\src\\main\\webapp\\resources\\images\\" + fileName);        
        
        byte[] buffer = new byte[4096];        
        int bytesRead = 0;        
        while (true) {            
            bytesRead = inputStream.read(buffer);            
            if (bytesRead > 0) {                
                outputStream.write(buffer, 0, bytesRead);                
            } else {                
                break;                
            }            
        }        
        outputStream.close();        
        inputStream.close();        
        it.setUrl_img2(fileName);
        itemFacade.edit(it);
        return "list";
    }
    
    public String getFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf("/") + 1).substring(filename.lastIndexOf("\\") + 1);
            }
        }
        return null;
    }

    /**
     * @return the file
     */
    public Part getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(Part file) {
        this.file = file;
    }
    
}