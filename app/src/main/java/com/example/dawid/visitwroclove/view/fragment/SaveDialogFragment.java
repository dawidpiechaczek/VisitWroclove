package com.example.dawid.visitwroclove.view.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.enums.Categories;
import com.example.dawid.visitwroclove.utils.OnSaveFragmentCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by DPIECHAC on 2017-09-14.
 */

public class SaveDialogFragment extends DialogFragment {
    @BindView(R.id.fs_et_notes) EditText name;
    @BindView(R.id.fs_et_description) EditText description;
    @BindView(R.id.fs_sp_types)Spinner types;
    private OnSaveFragmentCallback callback;
    private String[]typesArray= new String[] {
            Categories.COOK.getValue(), Categories.CYCLE.getValue(), Categories.FAVOURITE.getValue(), Categories.FOREST.getValue(), Categories.WALKING.getValue(), Categories.WATER.getValue()
    };

    public static SaveDialogFragment newInstance(OnSaveFragmentCallback callback) {
        SaveDialogFragment noteFragment = new SaveDialogFragment();
        noteFragment.callback = callback;
        return noteFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_save, container, false);
        ButterKnife.bind(this, v);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, typesArray);
        types.setAdapter(adapter);
        return v;
    }

    @OnClick(R.id.dialog_cancel)
    public void cancelNote() {
        dismiss();
    }

    @OnClick(R.id.dialog_save)
    public void saveNote() {
        String nameText = name.getText().toString();
        String descriptionText = description.getText().toString();
        String type = types.getSelectedItem().toString();

        callback.onSave(nameText, descriptionText, type);
        dismiss();
    }
}
