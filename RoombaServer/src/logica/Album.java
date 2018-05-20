/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.io.Serializable;

/**
 *
 * @author Alex Cámara
 */
public class Album implements Serializable{
    private int idAlbum;
    private String nombre;
    private String estudio;
    private int anioLanzamiento;
    private int idUsuario;
    private Genero genero;
}
