package es.upm.miw.bantumi.fragments.dialogs;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;

import es.upm.miw.bantumi.MainActivity;

public class StopTimerDialogFragment extends CustomOnlyAcceptDialogFragment {
    MainActivity mainActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) requireActivity();
        mainActivity.stopTimer();
    }

    public static class Builder implements CustomOnlyAcceptDialogFragmentBuilder {
        private final StopTimerDialogFragment fragment;

        public Builder() {
            this.fragment = new StopTimerDialogFragment();
        }

        @Override
        public CustomOnlyAcceptDialogFragmentBuilder setTitle(String title) {
            this.fragment.title = title;
            return this;
        }

        @Override
        public CustomOnlyAcceptDialogFragmentBuilder setMessage(String message) {
            this.fragment.message = message;
            return this;
        }

        @Override
        public CustomOnlyAcceptDialogFragmentBuilder setAcceptAction(DialogInterface.OnClickListener listener) {
            this.fragment.acceptListener = listener;
            return this;
        }

        @Override
        public CustomOnlyAcceptDialogFragmentBuilder setCancelAction(DialogInterface.OnClickListener listener) {
            this.fragment.cancelListener = listener;
            return this;
        }

        @Override
        public StopTimerDialogFragment build() {
            return this.fragment;
        }
    }
}
