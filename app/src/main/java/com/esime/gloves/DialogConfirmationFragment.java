package com.esime.gloves;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.esime.gloves.MyApp.InstanceApp;
import com.esime.gloves.Providers.AuthProvider;
import com.esime.gloves.UI.ActivityAssistan.ActivityAssistan;
import com.esime.gloves.UI.ActivityAssistan.ViewModel.RoomViewModel;
import com.esime.gloves.UI.ActivityPresentation.ActivityPresentation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.jetbrains.annotations.NotNull;

public class DialogConfirmationFragment extends DialogFragment {
    private String title, question, approved, declined;
    private Boolean choose;
    private TextView tvTitle, tvQuestion;
    private Button btnApprovaded, btnDeclined;
    private RoomViewModel viewModel;

    public DialogConfirmationFragment() {
        // Required empty public constructor
    }

    public DialogConfirmationFragment(String title,String question,String approved, String declined){
        this.title = title; 
        this.question = question; 
        this.approved = approved; 
        this.declined = declined; 
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,R.style.Dialog_No_Border);
        if(title.equals(InstanceApp.TITLE_DELETE_ACCOUNT)){
           choose = true;
        }else{
           choose = false;
        }
        viewModel = new ViewModelProvider(this).get(RoomViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_dialog_confirmation, container, false);
        findIdView(view);
        initUI();
        setUpButtons();
        return view;

    }

    private void initUI() {
        tvTitle.setText(title);
        tvQuestion.setText(question);
        btnApprovaded.setText(approved);
        btnDeclined.setText(declined);
    }

    private void setUpButtons() {
        btnApprovaded.setOnClickListener(v->{
            if(choose){
                AuthProvider.getInstance().deleteUser().addOnCompleteListener(task -> {
                    dismiss();
                    Toast.makeText(getContext(), "Usuario Borrado", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), ActivityPresentation.class));
                });
            }else{
                viewModel.DeleteNotifications();
                dismiss();
            }
        });
        btnDeclined.setOnClickListener(v->{
            dismiss();
        });
    }

    private void findIdView(View view) {
        tvTitle = view.findViewById(R.id.tv_title_dialog);
        tvQuestion = view.findViewById(R.id.tv_question_dialog);
        btnApprovaded = view.findViewById(R.id.btn_approved);
        btnDeclined = view.findViewById(R.id.btn_declined);
    }
}