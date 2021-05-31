package com.esime.gloves.UI.ActivityAssistan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.esime.gloves.DialogConfirmationFragment;
import com.esime.gloves.MyApp.InstanceApp;
import com.esime.gloves.Providers.AuthProvider;
import com.esime.gloves.R;

public class Assistan_03 extends Fragment {
    private LinearLayout llEmail,llDeleteAccount,llDeleteNotifications;
    private Button btnLogOut;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_assistan_03, container, false);
        initUI(view);
        setUpButtons();
        return view;
    }

    private void setUpButtons() {

        llDeleteAccount.setOnClickListener(v->{ new DialogConfirmationFragment(
                InstanceApp.TITLE_DELETE_ACCOUNT,
                InstanceApp.QUESTION_DELETE_ACCOUNT,
                InstanceApp.DELETE,
                InstanceApp.CANCEL
        ).show(getParentFragmentManager(),"Dialog");});

        llDeleteNotifications.setOnClickListener(v->{new DialogConfirmationFragment(
                InstanceApp.TITLE_DELETE_NOTIFICACIONES,
                InstanceApp.QUESTION_NOTIFICACIONES,
                InstanceApp.DELETE,
                InstanceApp.CANCEL
        ).show(getParentFragmentManager(),"Dialog"); });

        btnLogOut.setOnClickListener(v->{
            AuthProvider.getInstance().logOut();
        });
    }

    private void initUI(View view) {
        llEmail = view.findViewById(R.id.ll_email_user);
        llDeleteAccount = view.findViewById(R.id.ll_delete_account_assistant);
        llDeleteNotifications = view.findViewById(R.id.ll_delete_notifications);
        btnLogOut = view.findViewById(R.id.btnLogOut);
    }
}