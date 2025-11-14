package modelo;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "zonas_geograficas")
public class Zona_geografica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idzona; 

    private String nombrezona;
    private String coordenadas;
    
    // 1: Hogar (capaz es util tener un registro de los hogares donde hay gatos o los de transicion a futuro), 2: Tarea, 3: Zona Gato encontrado
    private int tipo; 
    @OneToMany(mappedBy = "zona")
    private List<Gato> gatos;
    

    public Zona_geografica() {
    }

    public Zona_geografica(String nombrezona, String coordenadas, int tipo) {
        this.nombrezona = nombrezona;
        this.coordenadas = coordenadas;
        this.tipo = tipo;
    }

    public int getIdzona() {
        return idzona;
    }
    public void setIdzona(int idzona) {
        this.idzona = idzona;
    }
    public String getNombrezona() {
        return nombrezona;
    }
    public void setNombrezona(String nombrezona) {
        this.nombrezona = nombrezona;
    }
    public String getCoordenadas() {
        return coordenadas;
    }
    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }
    public int getTipo() {
        return tipo;
    }
    public void setTipo(int tipo) {
        if (tipo != 1 && tipo != 2 && tipo != 3) {
            throw new IllegalArgumentException("Tipo de zona invalido. Debe ser 1 (Hogar), 2 (Tarea) o 3 (Gato encontrado)");
        }
        this.tipo = tipo;
    }
     public List<Gato> getGatos() {
         return gatos; 
     }

    @Override
    public String toString() {
        String tipoStr = "Desconocido";
        if (tipo == 1) tipoStr = "Hogar";
        else if (tipo == 2) tipoStr = "Tarea";
        else if (tipo == 3) tipoStr = "Gato encontrado";
        
        return "Zona_geografica {" +"ID=" + idzona +", Nombre='" + nombrezona + '\'' + ", Coordenadas='" + coordenadas + '\'' + ", Tipo=" + tipoStr +'}';
    }
}