package com.juanito.friendlystalk;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.Date;


public class FragmentCreateAccountWithGoogle extends Fragment {

private Button btnCreate;
private UserRepository repository = new UserRepository();

private EditText fName;
private EditText lName;
private EditText pseudo;
    public FragmentCreateAccountWithGoogle() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_fragment_create_account_with_google, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fName = getView().findViewById(R.id.googleFName);
        lName = getView().findViewById(R.id.googleLName);
        pseudo = getView().findViewById(R.id.googlePseudo);
        btnCreate = getView().findViewById(R.id.btnCreateAccountWithGoogle);

        final GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());

        fName.setText(acct.getGivenName());
        lName.setText(acct.getFamilyName());
        pseudo.setText(acct.getDisplayName());

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!fName.getText().toString().isEmpty() || !lName.getText().toString().isEmpty() || !pseudo.getText().toString().isEmpty()){
                    btnCreate = getView().findViewById(R.id.btnCreateAccountWithGoogle);
                    User user = new User(fName.getText().toString(),lName.getText().toString(),acct.getEmail(),pseudo.getText().toString());
                    repository.insert(user);
                    startActivity(new Intent(getView().getContext(),HomeActivity.class));
                }else{
                    fName.setError("Tous les champs doivent être renseigné");
                    lName.setError("Tous les champs doivent être renseigné");
                    pseudo.setError("Tous les champs doivent être renseigné");
                    return;
                }

            }
        });




    }




}




