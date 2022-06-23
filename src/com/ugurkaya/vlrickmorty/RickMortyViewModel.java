package com.ugurkaya.vlrickmorty;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ugurkaya.vlrickmorty.model.Location;
import com.ugurkaya.vlrickmorty.model.Origin;
import com.ugurkaya.vlrickmorty.model.Result;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RickMortyViewModel extends RecyclerView.Adapter<RickMortyViewModel.ViewHolder> {

    List<Result> resultList;
    Location location;
    Origin origin;
    RecyclerView recyclerView;
    Context context;



    List<String> episodes;
    private final RecyclerOnItemClickListenerInterface listener;


    public interface RecyclerOnItemClickListenerInterface {
        void onItemClick(Result item, int position);
    }


    public void setResults(List<Result> resultList) {
        this.resultList = resultList;
        notifyDataSetChanged();
    }

    public RickMortyViewModel(List<Result> resultList, Context context, RecyclerView recyclerView, RecyclerOnItemClickListenerInterface listener) {
        this.resultList = resultList;
        this.context = context;
        this.recyclerView = recyclerView;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RickMortyViewModel.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.character_list_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RickMortyViewModel.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Result resultModel = resultList.get(position);


        location = new Location();

        origin = new Origin();
        episodes = new ArrayList<String>();




        location = resultModel.getLocation();

        SpannableString spannableLocation = new SpannableString(String.format(context.getResources().getString(R.string.lastKnownLocationText),System.lineSeparator() + location.getName()));
        SpannableString spannableStatus = new SpannableString(String.format(context.getResources().getString(R.string.statusText), resultModel.getStatus()));
        SpannableString spannableType = new SpannableString(String.format(context.getResources().getString(R.string.typeText), resultModel.getType()));
        SpannableString spannableGender = new SpannableString(String.format(context.getResources().getString(R.string.genderText), resultModel.getGender()));

        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        try{
            spannableLocation.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.gold)), 0,19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableStatus.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.gold)), 0,8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableType.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.gold)), 0,6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableGender.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.gold)), 0,8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableLocation.setSpan(boldSpan, 0,19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableStatus.setSpan(boldSpan, 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableType.setSpan(boldSpan, 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableGender.setSpan(boldSpan, 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } catch (Exception e) {
            e.printStackTrace();
        }



        holder.characterName.setText(resultModel.getName());




        try{
            holder.lastLocationText.setText(spannableLocation);
            holder.characterStatus.setText(spannableStatus);
            holder.characterGender.setText(spannableGender);
            holder.characterType.setText(spannableType);
        } catch (Exception e) {
            holder.lastLocationText.setText(String.format(context.getResources().getString(R.string.lastKnownLocationText),System.lineSeparator() + location.getName()));
            holder.characterStatus.setText(String.format(context.getResources().getString(R.string.statusText), resultModel.getStatus()));
            holder.characterGender.setText(String.format(context.getResources().getString(R.string.genderText), resultModel.getGender()));
            holder.characterType.setText(String.format(context.getResources().getString(R.string.typeText), resultModel.getType()));
            e.printStackTrace();
        }




        if(resultModel.getType().equals("")){
            SpannableString spannableString = new SpannableString(String.format(context.getResources().getString(R.string.typeText), context.getResources().getString(R.string.noneText)));


            try{
                spannableString.setSpan(boldSpan, 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.gold)), 0,6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }
            try{
                holder.characterType.setText(spannableString);
            } catch (Exception e) {
                holder.characterType.setText(String.format(context.getResources().getString(R.string.typeText), context.getResources().getString(R.string.noneText)));
                e.printStackTrace();
            }


        }
        try {
            Glide.with(context).load(resultModel.getImage()).into(holder.characterPhoto);

            holder.photoBorder.startAnimation(AnimationUtils.loadAnimation(context, R.anim.rotateanim));
        } catch (Exception e) {
            e.printStackTrace();
        }



        holder.relativeListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listener.onItemClick(resultModel, position);


            }
        });

    }

    @Override
    public int getItemCount() {

        return resultList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout charactersCardView,relativeListItem;
        TextView lastLocationText, characterName, characterStatus, characterGender, characterType;
        CircleImageView characterPhoto,photoBorder;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            charactersCardView = (RelativeLayout) itemView.findViewById(R.id.charactersCardView);
            relativeListItem = (RelativeLayout) itemView.findViewById(R.id.relativeListItem);
            lastLocationText = (TextView) itemView.findViewById(R.id.lastLocationText);
            characterName = (TextView) itemView.findViewById(R.id.characterName);
            characterStatus = (TextView) itemView.findViewById(R.id.characterStatus);
            characterGender = (TextView) itemView.findViewById(R.id.characterGender);
            characterType = (TextView) itemView.findViewById(R.id.characterType);
            characterPhoto = (CircleImageView) itemView.findViewById(R.id.characterPhoto);
            photoBorder = (CircleImageView) itemView.findViewById(R.id.photoBorder);

        }
    }


}
