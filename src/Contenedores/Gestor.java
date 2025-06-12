package Contenedores;

import java.util.ArrayList;
import java.util.List;

public class Gestor <T> {
    private List<T> elementos;

    public Gestor() {
        this.elementos = new ArrayList<>();
    }

    public void agregar(T elemento){
        elementos.add(elemento);
    }

    public void eliminar(T elemento){
        elementos.remove(elemento);
    }

    public void mostrar(){
        for (T elemento : elementos){
            System.out.println(elemento + " ");
        }
        System.out.println();
    }

    public List<T> obtenerTodos(){
        return new ArrayList<>(elementos);
    }

}
