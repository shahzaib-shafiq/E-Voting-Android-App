package com.example.smd_final_project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VoterAdapter extends RecyclerView.Adapter<VoterAdapter.ViewHolder>{

    private ArrayList<Candidate> candidateArrayList = new ArrayList<>();
    ArrayList<User> userArrayList = new ArrayList<>();

    private Context context;
    public VoterAdapter(Context context,ArrayList<Candidate> candidateArrayList,ArrayList<User> userArrayList) {
        this.context = context;
        this.candidateArrayList = candidateArrayList;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.candidate_layout,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Candidate candidate = candidateArrayList.get(position);
        Picasso.get().load(candidate.getImageUrl()).into(holder.candidateImage);
        Picasso.get().load(candidate.getSymbolUrl()).into(holder.candidateSymbol);
        holder.Name.setText(candidate.getName());
        holder.Party.setText(candidate.getPartyName());
        holder.btnVoter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Candidate candidate = candidateArrayList.get(position);
                User user = userArrayList.get(position);
                Intent intent = new Intent(context,CastVoteMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id",candidate.getId());
                intent.putExtra("name",candidate.getName());
                intent.putExtra("cnic",candidate.getCnic());
                intent.putExtra("usercnic",user.getCnic());
                intent.putExtra("userisvote",user.getVote());
                intent.putExtra("username",user.getName());
                intent.putExtra("password",user.getPassword());
                intent.putExtra("role",user.getRole());
                intent.putExtra("area",candidate.getArea());
                intent.putExtra("type",candidate.getType());
                intent.putExtra("imageurl",candidate.getImageUrl());
                intent.putExtra("audiourl",candidate.getAudioUrl());
                intent.putExtra("symbol",candidate.getSymbolUrl());
                intent.putExtra("party",candidate.getPartyName());
                intent.putExtra("noofvotes",candidate.getNoOfVotes());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return candidateArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView candidateImage,candidateSymbol;
        TextView Name,Party;
        Button btnVoter;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            candidateImage = itemView.findViewById(R.id.imgvotercan);
            candidateSymbol = itemView.findViewById(R.id.imgsymbol);
            Name = itemView.findViewById(R.id.txtvotername);
            Party = itemView.findViewById(R.id.txtpartyname);
            btnVoter = itemView.findViewById(R.id.btncastvote);
        }
    }
}
