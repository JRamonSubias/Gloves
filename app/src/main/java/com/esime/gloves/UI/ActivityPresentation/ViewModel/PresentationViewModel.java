package com.esime.gloves.UI.ActivityPresentation.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.esime.gloves.FirebaseRepository;

public class PresentationViewModel extends ViewModel {
    private FirebaseRepository mRepository;

    public PresentationViewModel(){
        mRepository = new FirebaseRepository();
    }

    public MutableLiveData<Boolean> registerPatient(String username,String password){
        return mRepository.registerPatient(username,password);
    }

    public MutableLiveData<Boolean> signInAuthentication(String email,String password){
        return mRepository.singIn(email,password);
    }

    public MutableLiveData<Boolean> registerAssistant(String email, String password){
        return mRepository.registerAssistant(email,password);
    }
}
