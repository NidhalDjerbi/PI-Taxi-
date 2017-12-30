package com.sourcey.TaxiChaser;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    List<Card> items ;

    public Adapter(List<Card> cardList) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.taxi_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
       Card card =items.get(position);
        holder.TaxiNum.setText(card.getNumTaxi());
        holder.NomChauffeur.setText(card.getNomChauff());
        holder.Distance.setText(card.getDistance());

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView NomChauffeur;
        private final TextView TaxiNum;
        private final TextView Distance;



        public MyViewHolder(final View itemView) {
            super(itemView);

            NomChauffeur = ((TextView) itemView.findViewById(R.id.chauffNom));
            TaxiNum = ((TextView) itemView.findViewById(R.id.numTaxi));
            Distance = ((TextView) itemView.findViewById(R.id.distance));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }



    }



}