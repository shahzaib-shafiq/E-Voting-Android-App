package com.example.smd_final_project;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

public class AdminHomeFragment extends Fragment {

//    EditText test ;
    ImageView img;
//
    Uri imageUri,audioUri,symbolUri;
    String imgName,audioName,symbol;
    TextInputLayout name,cnic,partyName,area,type;
    ExtendedFloatingActionButton btnAudio,btnSymbol;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.admin_home_frag, container, false);
        img = root.findViewById(R.id.imgprofile);
        btnAudio = root.findViewById(R.id.btnaudio);
        btnSymbol = root.findViewById(R.id.btnsymbol);
        name = root.findViewById(R.id.edtcandidatename);
        cnic = root.findViewById(R.id.edtcancnic);
        area = root.findViewById(R.id.edtarea);
        partyName = root.findViewById(R.id.edtpartyname);
        type = root.findViewById(R.id.edttype);
        return root;
    }


//    public String getTest() {
//        return test.getText().toString();
//    }

    public void setImage(Uri result){
        img.setImageURI(result);
        imageUri = result;
    }

    public void setImageByPicasso(String result){
        Picasso.get().load(result).into(img);
    }

    public Uri getImage(){
        return imageUri;
    }

    public String getImgName() {
        imgName = getFileNameFromUri(imageUri);
        return imgName;
    }

    public String getAudioName() {
        audioName = getFileNameFromUri(audioUri);
        return audioName;
    }

    public void setAudio(Uri audioUri) {
        // Do something with the selected audio file URI
        Log.d("AdminHomeFragment", "Selected audio URI: " + audioUri.toString());

        this.audioUri = audioUri;
        String audioFileName = getFileNameFromUri(audioUri);
        btnAudio.setText(audioFileName);
        Toast.makeText(getContext(), "Selected audio: " + audioFileName, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("Range")
    private String getFileNameFromUri(Uri uri) {
        String fileName = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    fileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (fileName == null) {
            fileName = uri.getLastPathSegment();
        }
        return fileName;
    }

    public Uri getAudio(){
        return audioUri;
    }

    public String getName() {
        return name.getEditText().getText().toString();
    }

    public String getCnic() {
        return cnic.getEditText().getText().toString();
    }

    public String getPartyName() {
        return partyName.getEditText().getText().toString();
    }

    public String getArea() {
        return area.getEditText().getText().toString();
    }

    public String getType() {
        return type.getEditText().getText().toString();
    }

    public void SetSymbol(Uri uri) {
        symbolUri = uri;
        btnSymbol.setText(getFileNameFromUri(uri));
    }
    public String getSymbolName(){
        return getFileNameFromUri(symbolUri);
    }
    public Uri getSymbol(){
        return symbolUri;
    }
}

