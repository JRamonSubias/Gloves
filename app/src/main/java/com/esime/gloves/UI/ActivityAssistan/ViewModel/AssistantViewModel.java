package com.esime.gloves.UI.ActivityAssistan.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.esime.gloves.FirebaseRepository;

import java.util.List;

public class AssistantViewModel extends ViewModel {
    private FirebaseRepository mRepository;

    public AssistantViewModel(){
      mRepository = new FirebaseRepository();
    }


    public MutableLiveData<Boolean> addUserPatient(String username){
        return mRepository.addUserPatient(username);
    }

    public MutableLiveData<List<String>> getPatientsSaved(){
        return mRepository.getPatientsSaved();
    }

    public void UnsubscribeToTopicAndDeletePatient(String topic){
        mRepository.UnsubscribeToTopicAndDeletePatient(topic);
    }

}
