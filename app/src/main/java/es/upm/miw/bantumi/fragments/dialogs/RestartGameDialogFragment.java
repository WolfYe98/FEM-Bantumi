package es.upm.miw.bantumi.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import es.upm.miw.bantumi.MainActivity;
import es.upm.miw.bantumi.R;

public class RestartGameDialogFragment extends DialogFragment {
    protected final String LOG_TAG = "MiW - Restart Dialog";
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle bundle){
        MainActivity main = (MainActivity) requireActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder
        .setTitle(R.string.restartTitle)
        .setMessage(R.string.restartMessage)
        .setPositiveButton(android.R.string.ok,(dialogInterface,i)->{
            Log.i(LOG_TAG,"Reiniciando juego");
            main.onDialogAccept();
        })
        .setNegativeButton(android.R.string.cancel,null);
        return builder.create();
    }
}
