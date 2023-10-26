package es.upm.miw.bantumi.fragments.dialogs;

import android.content.DialogInterface;

public interface CustomOnlyAcceptDialogFragmentBuilder {
    CustomOnlyAcceptDialogFragmentBuilder setTitle(String title);

    CustomOnlyAcceptDialogFragmentBuilder setMessage(String message);

    CustomOnlyAcceptDialogFragmentBuilder setAcceptAction(DialogInterface.OnClickListener listener);

    CustomOnlyAcceptDialogFragmentBuilder setCancelAction(DialogInterface.OnClickListener listener);

    CustomOnlyAcceptDialogFragment build();
}
