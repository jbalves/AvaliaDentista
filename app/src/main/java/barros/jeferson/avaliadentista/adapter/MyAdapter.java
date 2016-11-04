package barros.jeferson.avaliadentista.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import barros.jeferson.avaliadentista.R;
import barros.jeferson.avaliadentista.model.UnidadeSaude;

/**
 * Created by Jeferson Barros <im.jbalves@gmail.com> on 04/11/2016.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private final Context context;
    private ArrayList<UnidadeSaude> lista;

    private AdapterListener mAdapterListener;

    public MyAdapter (Context context, ArrayList<UnidadeSaude> lista) {
        this.lista = lista;
        this.context = context;
    }

    public AdapterListener getmAdapterListener() {
        return mAdapterListener;
    }

    public void setmAdapterListener(AdapterListener mAdapterListener) {
        this.mAdapterListener = mAdapterListener;
    }

    //#2 monta o layout da lista
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_consulta_list, null);
        return new ViewHolder(view);
    }

    //#3 recupera uma posição da lista no layout
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Recupera a referência do meu livro
        UnidadeSaude unidadeSaude = lista.get(position);

        //Seta os valores do livro para o layout dentro do holder
        holder
                .setNomeUnidade(unidadeSaude.getNome())
                .setDataAtendimento(unidadeSaude.getDataAtendimento())
                .setDiasEspera(unidadeSaude.getDiasEspera())
                .setRating(unidadeSaude.getRating());
    }
    //#4 conta a quantidade de elementos existente na lista
    @Override
    public int getItemCount() {
        //tamanho da lista
        return lista.size();
    }

    //#1 método a ser implementado
    //mapeia os elementos de layout
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView nomeUnidadeView;
        private TextView dataAtendimentoView;
        private TextView diasEsperaView;
        private RatingBar ratingBarView;

        public ViewHolder(View itemView) {
            super(itemView);


            //Recuperei as referências do layout
            nomeUnidadeView = (TextView) itemView.findViewById(R.id.nomeUnidade);
            dataAtendimentoView = (TextView) itemView.findViewById(R.id.dataAtendimento);
            diasEsperaView = (TextView) itemView.findViewById(R.id.diasEspera);
            ratingBarView = (RatingBar) itemView.findViewById(R.id.ratingBar);

            //Simula o click
            itemView.setOnClickListener(this);

        }

        public ViewHolder setNomeUnidade(String nomeUnidade) {
            if (nomeUnidadeView == null) return this;
            nomeUnidadeView.setText(nomeUnidade);
            return this;
        }

        public ViewHolder setDataAtendimento(String dataAtendimento) {
            if (dataAtendimentoView == null) return this;
            dataAtendimentoView.setText(dataAtendimento);
            return this;
        }

        public ViewHolder setDiasEspera(int diasEspera) {
            if (diasEsperaView == null) return this;
            diasEsperaView.setText(diasEspera+"");
            return this;
        }

        public ViewHolder setRating(float rating) {
            if (ratingBarView == null) return this;
            ratingBarView.setRating(rating);
                    //.setText(ano+"");
            return this;
        }

        @Override
        public void onClick(View view) {
            int position = getPosition();

            if (mAdapterListener != null) {
                mAdapterListener.onItemClick(view, position);
            }
        }
    }

    public interface AdapterListener {
        public void onItemClick (View view, int position);
    }

}
