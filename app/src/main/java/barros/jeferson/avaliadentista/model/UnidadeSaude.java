package barros.jeferson.avaliadentista.model;

import java.io.Serializable;

/**
 * Created by aluno on 03/11/2016.
 */

public class UnidadeSaude implements Serializable {
    public String nome;
    public String dataAtendimento;
    public int diasEspera;
    public Float rating;

    public UnidadeSaude(String nome, String dataAtendimento, int diasEspera, Float rating) {
        this.nome = nome;
        this.dataAtendimento = dataAtendimento;
        this.diasEspera = diasEspera;
        this.rating = rating;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataAtendimento() {
        return dataAtendimento;
    }

    public void setDataAtendimento(String dataAtendimento) {
        this.dataAtendimento = dataAtendimento;
    }

    public int getDiasEspera() {
        return diasEspera;
    }

    public void setDiasEspera(int diasEspera) {
        this.diasEspera = diasEspera;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }
}
