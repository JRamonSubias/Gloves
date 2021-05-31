package com.esime.gloves.Providers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthProvider  {

    private FirebaseAuth mAuth;
    private static AuthProvider instance;

    private AuthProvider(){
        mAuth = FirebaseAuth.getInstance();
    }

    public static AuthProvider getInstance(){
        if(instance == null)
            instance = new AuthProvider();
        return instance;
    }


    public Task<AuthResult> SignUp(String email, String password){
        return  mAuth.createUserWithEmailAndPassword(email,password);
    }

    public Task<AuthResult> SignIn(String email, String password){
        return mAuth.signInWithEmailAndPassword(email,password);
    }

    public void logOut(){
        mAuth.signOut();
    }

    public String getID(){
        return mAuth.getCurrentUser().getUid();
    }

    public boolean existSession(){
        boolean exist = false;
        if(mAuth.getCurrentUser() != null){
            exist=true;
        }
        return exist;
    }

    public Task<Void> deleteUser(){
        return mAuth.getCurrentUser().delete();
    }
}
