package org.josan.reproductoryoutube.model;

/**
 * Created by Josan on 13/02/2018.
 */

public class Video {
    private String id;
    private String imagen;
    private String titulo;
    private String autor;

    public Video(){

    }

    public Video(String id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    public Video(String id, String imagen, String titulo) {
        this.id = id;
        this.imagen = imagen;
        this.titulo = titulo;
    }

    public Video(String id, String imagen, String titulo, String autor) {
        this.id = id;
        this.imagen = imagen;
        this.titulo = titulo;
        this.autor = autor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Titulo: " + getTitulo() + "-" + getAutor();
    }
}
