package es.upm.miw.bantumi;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import es.upm.miw.bantumi.fragments.dialogs.CustomOnlyAcceptDialogFragment;
import es.upm.miw.bantumi.fragments.dialogs.CustomOnlyAcceptDialogFragmentBuilder;
import es.upm.miw.bantumi.model.game_result_model.GameResultViewModel;
import es.upm.miw.bantumi.view.GameResultListAdapter;

public class GameResultActivity extends AppCompatActivity {
    GameResultViewModel gameResultViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(MainActivity.LOG_TAG, "Getting best 10 results");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_result);
        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_revert);
        gameResultViewModel = new ViewModelProvider(this).get(GameResultViewModel.class);
        RecyclerView rv = findViewById(R.id.game_result_recycler_view);
        final GameResultListAdapter gameResultListAdapter = new GameResultListAdapter(new GameResultListAdapter.GameResultDiff());
        rv.setAdapter(gameResultListAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        gameResultViewModel.getBestResults().observe(this, gameResultListAdapter::submitList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.game_result_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.opcBorrarTodo:
                Log.i(MainActivity.LOG_TAG, "Deleting all results");
                this.deleteAll();
                return true;
            default:
                break;
        }
        return true;
    }

    public void deleteAll() {
        CustomOnlyAcceptDialogFragmentBuilder builder = new CustomOnlyAcceptDialogFragment.Builder();
        builder.setTitle("Delete all")
                .setMessage("Delete all messages?")
                .setAcceptAction(((dialog, which) -> this.gameResultViewModel.deleteAll()))
                .build()
                .show(getSupportFragmentManager(), "DeleteAllFragment");
    }
}
