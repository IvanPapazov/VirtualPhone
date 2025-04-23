package com.os0.navigation;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AddDialogMemo extends AppCompatDialogFragment  {

    private EditText note;
    private DialogListener listener;
    Button addBtn;
    Button backBtn;


    //private ArrayList<Row> list = new ArrayList<Row>();

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.memo_dialog_add, null);

        note = view.findViewById(R.id.addNotesMemo);
        addBtn = view.findViewById(R.id.addBtnDialogMemo);
        backBtn = view.findViewById(R.id.backBtnDialogMemo);

        // AddDialog dialog = new Dialog(this);

        addBtn.setOnClickListener(v -> {
            addBtn.animate().scaleX(1.1f).scaleY(1.1f).setDuration(200).withEndAction(() -> addBtn.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200));

            String noteText = note.getText().toString();
            if(noteText.length() >= 5){
                listener.applyText(noteText);
                dismiss();
            }


        });

        backBtn.setOnClickListener(v -> {
            backBtn.animate().scaleX(1.1f).scaleY(1.1f).setDuration(200).withEndAction(() -> backBtn.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200));

            dismiss();
        });

        builder.setView(view);
        builder.setTitle("Add note");

        return builder.create();

    }





    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context + " must implement dialog listener");
        }
    }

    public interface DialogListener{
        void applyText(String note);
    }

}
