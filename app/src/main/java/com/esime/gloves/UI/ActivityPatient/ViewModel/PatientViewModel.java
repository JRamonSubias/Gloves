package com.esime.gloves.UI.ActivityPatient.ViewModel;

import androidx.lifecycle.ViewModel;

import com.esime.gloves.FirebaseRepository;

public class PatientViewModel extends ViewModel {
    private FirebaseRepository mRepository;

    public PatientViewModel(){
        mRepository = new FirebaseRepository();
    }

    public void ChangeStatusPatient(Boolean conection){
        mRepository.ChangeStatusPatient(conection);
    }


}
