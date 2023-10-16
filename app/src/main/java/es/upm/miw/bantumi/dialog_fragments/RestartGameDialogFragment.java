package es.upm.miw.bantumi.dialog_fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.DialogFragment;

import es.upm.miw.bantumi.R;

public class RestartGameDialogFragment extends DialogFragment {
    protected final String LOG_TAG = "MiW - Restart Dialog";
    public interface RestartGameDialogListener{
        void onAcceptClick(DialogFragment dialogFragment);
    }
    RestartGameDialogListener listener;
    @Override
    public Dialog onCreateDialog(Bundle bundle){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
        .setTitle(R.string.restartTitle)
        .setMessage(R.string.restartMessage)
        .setPositiveButton(android.R.string.ok,(dialogInterface,i)->{
            Log.i(LOG_TAG,"Reiniciando juego");
            this.listener.onAcceptClick(this);
        })
        .setNegativeButton(android.R.string.cancel,null);
        return builder.create();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (RestartGameDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }
}
