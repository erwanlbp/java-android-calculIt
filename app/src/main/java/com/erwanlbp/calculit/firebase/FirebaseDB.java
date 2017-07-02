package com.erwanlbp.calculit.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by eisti on 01/07/17.
 */

public class FirebaseDB {

    public static void writeTest() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.setValue("COUCOU");
    }
}
