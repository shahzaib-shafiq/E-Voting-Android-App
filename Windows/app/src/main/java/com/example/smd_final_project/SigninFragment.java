package com.example.smd_final_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SigninFragment extends Fragment {

    EditText edt_Cnin,edt_pass,edt_Name;
    Button btnLogin;
    TextView txt;
    float opacity = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment,container,false);
        edt_Cnin=root.findViewById(R.id.edtcancnic);
        edt_pass=root.findViewById(R.id.edtpassword);
        txt  = root.findViewById(R.id.txtforgetpass);
        btnLogin = root.findViewById(R.id.btnsignup);
        edt_Name = root.findViewById(R.id.edtname);


        edt_Cnin.setTranslationY(800);
        edt_pass.setTranslationY(800);
        btnLogin.setTranslationY(800);
        edt_Name.setTranslationY(800);


        edt_Cnin.setAlpha(opacity);
        edt_pass.setAlpha(opacity);

        btnLogin.setAlpha(opacity);
        edt_Name.setAlpha(opacity);


        edt_Cnin.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        edt_pass.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();

        edt_Name.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        btnLogin.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();
        return root;
    }

    public String getCnic() {
        return edt_Cnin.getText().toString();
    }

    public String getPassword() {
        return edt_pass.getText().toString();
    }

    public String getName() {
        return edt_Name.getText().toString();
    }

}
