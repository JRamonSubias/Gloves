package com.esime.gloves.UI.ActivityAssistan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.esime.gloves.R;

import java.util.List;

public class UserAssistantRecyclerAdapter  extends RecyclerView.Adapter<UserAssistantRecyclerAdapter.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    private List<String> listUserAssistant;
    private FragmentManager fragmentManager;
    View.OnClickListener listener;
    View.OnLongClickListener longClickListener;

    public UserAssistantRecyclerAdapter(List<String> listUserAssistant, FragmentManager fragmentManager){
        this.listUserAssistant = listUserAssistant;
        this.fragmentManager = fragmentManager;
    }


    @NonNull
    @Override
    public UserAssistantRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_assistant,parent,false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAssistantRecyclerAdapter.ViewHolder holder, int position) {
        if(listUserAssistant.get(position).equals("Añadir Usuario")){
                holder.tvUserAssistant.setText("Añadir Usuario");
                holder.imUser.setImageResource(R.drawable.ic_add);
                holder.imUser.setOnClickListener(v -> {
                    AddUserPatient addUserPatient = new AddUserPatient();
                    addUserPatient.show(fragmentManager,"AddUserAssistant");
                });
            }
        holder.tvUserAssistant.setText(listUserAssistant.get(position));
    }
    @Override
    public int getItemCount() {
        if(listUserAssistant != null){
            return listUserAssistant.size();
        }else{
            return 0;
        }
    }

    public void setData(List<String> listUserAssistant){
        this.listUserAssistant = listUserAssistant;
        notifyDataSetChanged();
    }

    public void SetOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    public void SetOnLongClickListener(View.OnLongClickListener longClickListener){this.longClickListener = longClickListener;}

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if(longClickListener != null){
            longClickListener.onLongClick(v);
            return true;
        }
        return false;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imUser;
        public TextView tvUserAssistant;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imUser = itemView.findViewById(R.id.profile_image);
            tvUserAssistant = itemView.findViewById(R.id.tvUserAssistant);

        }
    }
}
