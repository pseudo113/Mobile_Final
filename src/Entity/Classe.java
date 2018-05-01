/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author LENOVO
 */
public class Classe {
    private int id;
    private int iduser;
    private int numero;
    private String niveau;

    public Classe() {
    }

    public Classe(int id, int iduser, int numero, String niveau) {
        this.id = id;
        this.iduser = iduser;
        this.numero = numero;
        this.niveau = niveau;
    }

    

    public Classe( int iduser, int numero, String niveau) {

        this.iduser = iduser;
        this.numero = numero;
        this.niveau = niveau;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }
    
    
}
