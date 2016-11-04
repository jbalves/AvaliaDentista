package barros.jeferson.avaliadentista.model;

import java.io.Serializable;

/**
 * Created by aluno on 03/11/2016.
 */

public class UnidadeSaude implements Serializable {
    public String nome;
    public String endereco;

    public UnidadeSaude(String nome, String endereco) {
        this.nome = nome;
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return nome;
    }
}
