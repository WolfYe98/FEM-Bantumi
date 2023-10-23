package es.upm.miw.bantumi.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import es.upm.miw.bantumi.MainActivity;
import es.upm.miw.bantumi.R;

public class CustomOnlyAcceptDialogFragment extends DialogFragment {
    protected final String LOG_TAG = "MiW - Restart Dialog";
    private String title;
    private String message;
    DialogInterface.OnClickListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle bundle){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder
        .setTitle(this.title)
        .setMessage(this.message)
        .setPositiveButton(android.R.string.ok,this.listener)
        .setNegativeButton(android.R.string.cancel,null);
        return builder.create();
    }
    public static class Builder implements CustomOnlyAcceptDialogFragmentBuilder{
        private final CustomOnlyAcceptDialogFragment dialog;
        public Builder(){
            this.dialog = new CustomOnlyAcceptDialogFragment();
        }
        @Override
        public CustomOnlyAcceptDialogFragmentBuilder setTitle(String title) {
            this.dialog.title = title;
            return this;
        }

        @Override
        public CustomOnlyAcceptDialogFragmentBuilder setMessage(String message) {
            this.dialog.message = message;
            return this;
        }

        @Override
        public CustomOnlyAcceptDialogFragmentBuilder setAcceptAction(DialogInterface.OnClickListener listener) {
            this.dialog.listener = listener;
            return this;
        }

        @Override
        public CustomOnlyAcceptDialogFragment build() {
            return this.dialog;
        }
    }
}
