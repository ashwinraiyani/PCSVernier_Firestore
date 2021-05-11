package com.pcs.pcsvernier_firebase.adapters;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pcs.pcsvernier_firebase.MyUtil;

import java.util.Map;

public class MachineSpinnerAdapter extends ArrayAdapter {
    public MachineSpinnerAdapter(@NonNull Context context) {
        super(context, android.R.layout.simple_spinner_dropdown_item, MyUtil.machines);
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = fStore.collection("machines");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() && task.getResult().size() > 0) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map machineObj = document.getData();
                        MyUtil.machines.add(machineObj.get("machine"));
                        Log.d("TAG", document.getId() + " => " + document.getData());
                    }
                    MachineSpinnerAdapter.this.notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Data not fetched.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
