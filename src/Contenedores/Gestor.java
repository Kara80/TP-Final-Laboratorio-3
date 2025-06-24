package Contenedores;

import Interface.Identificable;

import java.util.ArrayList;
import java.util.List;

public class Gestor <T> {
    private List<T> elementos;

    public Gestor() {
        this.elementos = new ArrayList<>();
    }

    public List<T> getElementos() {
        return elementos;
    }

    public void agregar(T elemento){
        elementos.add(elemento);
    }

    public void eliminar(T elemento){
        elementos.remove(elemento);
    }

    public String mostrar(){
        StringBuilder sb = new StringBuilder();

       for (T elemento : elementos){
           if (elemento instanceof Identificable){
               sb.append(((Identificable) elemento).obtenerIdentificador()).append("\n");
           }
           else{
               sb.append(elemento.toString()).append("\n");
           }
       }

        return sb.toString();
    }

    public void limpiar(){
        elementos.clear();
    }

    public List<T> obtenerTodos(){
        return new ArrayList<>(elementos);
    }

    @Override
    public String toString() {
        return "Gestor{" +
                "elementos=" + elementos +
                '}';
    }
}
