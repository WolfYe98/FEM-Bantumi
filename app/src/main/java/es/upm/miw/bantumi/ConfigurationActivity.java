package es.upm.miw.bantumi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.snackbar.Snackbar;

public class ConfigurationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings,new SettingsFragment())
                .commit();
        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_revert);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.configuration_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            default:
                this.showSnackBarConfigurationMessagesByResourceID(R.string.txtSinImplementar);
                break;
        }
        return false;
    }
    private void showSnackBarConfigurationMessagesByResourceID(int id){
        Snackbar.make(
                findViewById(android.R.id.content),
                getString(id),
                Snackbar.LENGTH_LONG
        ).show();
    }
    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences,rootKey);
            ConfigurationActivity configuration = (ConfigurationActivity) requireActivity();
            findPreference(getString(R.string.prInitialSeedNumberKey)).setOnPreferenceChangeListener((preference, newValue) -> {
                Log.i(MainActivity.LOG_TAG,"Changing initial seed numbers");
                try{
                    Integer.parseInt(newValue.toString());
                } catch (NumberFormatException nfex){
                    Log.i(MainActivity.LOG_TAG,"Trying to set a non-number field to initial seed number preference");
                    configuration.showSnackBarConfigurationMessagesByResourceID(R.string.prSBSeedShouldBeNumberText);
                    return false;
                }
                return true;
            });
        }
    }
}