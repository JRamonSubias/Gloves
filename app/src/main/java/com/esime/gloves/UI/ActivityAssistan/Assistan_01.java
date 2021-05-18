package com.esime.gloves.UI.ActivityAssistan;



import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.esime.gloves.MyApp.InstanceApp;
import com.esime.gloves.Providers.AuthProvider;
import com.esime.gloves.Providers.FCMProvider;
import com.esime.gloves.Providers.FDBProvider;
import com.esime.gloves.R;
import com.esime.gloves.UI.ActivityAssistan.ViewModel.AssistantViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;


public class Assistan_01 extends Fragment {
    private RecyclerView rvUserAssistant;
    private List<String> listUserAssistant;

    private UserAssistantRecyclerAdapter mRvAdapter;
    private TextView tvStatus, tvName;
    private AssistantViewModel viewModel;

    public Assistan_01() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listUserAssistant = new ArrayList<>();
        viewModel = new ViewModelProvider(this).get(AssistantViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_assistan_01, container, false);
        ConfigViewById(view);
        loadPatientSaved();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(InstanceApp.getContext());
        layoutManager.setOrientation(layoutManager.HORIZONTAL);
        rvUserAssistant.setLayoutManager(layoutManager);
        listUserAssistant.add("addUser");

    }


    private void loadPatientSaved(){
        viewModel.getPatientsSaved().observe(getActivity(), listPatientSaved ->{
            if(!listPatientSaved.contains("Añadir Usuario"))
                    listPatientSaved.add("Añadir Usuario");
            mRvAdapter = new UserAssistantRecyclerAdapter(listPatientSaved,getChildFragmentManager());
            rvUserAssistant.setAdapter(mRvAdapter);
            mRvAdapter.SetOnClickListener(v -> {
                tvName.setText(listPatientSaved.get(rvUserAssistant.getChildAdapterPosition(v)));
            });
            mRvAdapter.SetOnLongClickListener(v -> {
                showMenu(v,listPatientSaved,rvUserAssistant.getChildAdapterPosition(v));
                return true;
            });
        });
    }

    private void showMenu(View v, List<String> listUserAssistant, int childAdapterPosition) {
        PopupMenu popupMenu = new PopupMenu(InstanceApp.getContext(),v);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_patients,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            switch ((item.getItemId())){
                case R.id.menu_delete:
                    viewModel.UnsubscribeToTopicAndDeletePatient(listUserAssistant.get(childAdapterPosition));
                    listUserAssistant.remove(childAdapterPosition);
                    mRvAdapter.setData(listUserAssistant);
                    break;
                default: return false;
            }
            return true;
        });
        popupMenu.show();
    }

    private void ConfigViewById(ViewGroup view) {
        rvUserAssistant = view.findViewById(R.id.rv_userAssistant);
        tvStatus = view.findViewById(R.id.tvStatusPatient);
        tvName = view.findViewById(R.id.tvPatientName);
    }
}