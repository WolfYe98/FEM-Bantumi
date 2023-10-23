package es.upm.miw.bantumi;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

import es.upm.miw.bantumi.fragments.dialogs.CustomOnlyAcceptDialogFragment;
import es.upm.miw.bantumi.fragments.dialogs.CustomOnlyAcceptDialogFragmentBuilder;
import es.upm.miw.bantumi.model.BantumiViewModel;
import es.upm.miw.bantumi.model.game_result_model.GameResult;
import es.upm.miw.bantumi.model.game_result_model.GameResultViewModel;

public class MainActivity extends AppCompatActivity {

    protected static final String LOG_TAG = "MiW";
    protected final String LOG_TAG_ERROR = "MiW-ERROR";
    JuegoBantumi juegoBantumi;
    BantumiViewModel bantumiVM;
    int numInicialSemillas;
    SharedPreferences preferences;
    GameResultViewModel gameResultViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        setPlayerName();
        JuegoBantumi.Turno turn = this.getFirstMovementTurnFromPreferences();
        // Instancia el ViewModel y el juego, y asigna observadores a los huecos
        numInicialSemillas = this.getInitialSeedsNumber();
        bantumiVM = new ViewModelProvider(this).get(BantumiViewModel.class);
        juegoBantumi = new JuegoBantumi(bantumiVM, turn, numInicialSemillas);
        crearObservadores();
        this.gameResultViewModel = new ViewModelProvider(this).get(GameResultViewModel.class);
    }

    private void setPlayerName() {
        String playerName = this.getSettingPlayerNameOrDefault();
        TextView tvPlayer1 = findViewById(R.id.tvPlayer1);
        tvPlayer1.setText(playerName);
    }

    private JuegoBantumi.Turno getFirstMovementTurnFromPreferences() {
        String prTurn = preferences.getString(getString(R.string.prFirstMovementKey), JuegoBantumi.Turno.turnoJ1.name());
        JuegoBantumi.Turno turn = JuegoBantumi.Turno.valueOf(prTurn);
        return turn;
    }

    private String getSettingPlayerNameOrDefault() {
        String playerName = preferences.getString(getString(R.string.prPlayerNameKey), getString(R.string.txtPlayer1));
        return playerName.isEmpty() ? getString(R.string.txtPlayer1) : playerName;
    }

    private int getInitialSeedsNumber() {
        int defaultNumber = getResources().getInteger(R.integer.intNumInicialSemillas);
        String preferenceNumber = preferences.getString(getString(R.string.prInitialSeedNumberKey), Integer.toString(defaultNumber));
        return Integer.parseInt(preferenceNumber);
    }

    /**
     * Crea y subscribe los observadores asignados a las posiciones del tablero.
     * Si se modifica el contenido del tablero -> se actualiza la vista.
     */
    private void crearObservadores() {
        for (int i = 0; i < JuegoBantumi.NUM_POSICIONES; i++) {
            int finalI = i;
            bantumiVM.getNumSemillas(i).observe(    // Huecos y almacenes
                    this,
                    new Observer<Integer>() {
                        @Override
                        public void onChanged(Integer integer) {
                            mostrarValor(finalI, juegoBantumi.getSemillas(finalI));
                        }
                    });
        }
        bantumiVM.getTurno().observe(   // Turno
                this,
                new Observer<JuegoBantumi.Turno>() {
                    @Override
                    public void onChanged(JuegoBantumi.Turno turno) {
                        marcarTurno(juegoBantumi.turnoActual());
                    }
                }
        );
    }

    /**
     * Indica el turno actual cambiando el color del texto
     *
     * @param turnoActual turno actual
     */
    private void marcarTurno(@NonNull JuegoBantumi.Turno turnoActual) {
        TextView tvJugador1 = findViewById(R.id.tvPlayer1);
        TextView tvJugador2 = findViewById(R.id.tvPlayer2);
        switch (turnoActual) {
            case turnoJ1:
                tvJugador1.setTextColor(getColor(R.color.white));
                tvJugador1.setBackgroundColor(getColor(android.R.color.holo_blue_light));
                tvJugador2.setTextColor(getColor(R.color.black));
                tvJugador2.setBackgroundColor(getColor(R.color.white));
                break;
            case turnoJ2:
                tvJugador1.setTextColor(getColor(R.color.black));
                tvJugador1.setBackgroundColor(getColor(R.color.white));
                tvJugador2.setTextColor(getColor(R.color.white));
                tvJugador2.setBackgroundColor(getColor(android.R.color.holo_blue_light));
                break;
            default:
                tvJugador1.setTextColor(getColor(R.color.black));
                tvJugador2.setTextColor(getColor(R.color.black));
        }
    }

    /**
     * Muestra el valor <i>valor</i> en la posición <i>pos</i>
     *
     * @param pos   posición a actualizar
     * @param valor valor a mostrar
     */
    private void mostrarValor(int pos, int valor) {
        String num2digitos = String.format(Locale.getDefault(), "%02d", pos);
        // Los identificadores de los huecos tienen el formato casilla_XX
        int idBoton = getResources().getIdentifier("casilla_" + num2digitos, "id", getPackageName());
        if (0 != idBoton) {
            TextView viewHueco = findViewById(idBoton);
            viewHueco.setText(String.valueOf(valor));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.opciones_menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.opcAjustes: // @todo Preferencias
//                startActivity(new Intent(this, BantumiPrefs.class));
//                return true;
            case R.id.opcAcercaDe:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.aboutTitle)
                        .setMessage(R.string.aboutMessage)
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
                return true;
            case R.id.opcReiniciarPartida:
                this.showRestartDialog();
                return true;
            case R.id.opcAjustes:
                this.showAjustes();
                return true;
            case R.id.opcGuardarPartida:
                this.showSaveGameDialog();
                return true;
            case R.id.opcRecuperarPartida:
                this.restoreGame();
                return true;
            case R.id.opcMejoresResultados:
                this.openBestResults();
                return true;

            default:
                this.showSnackBarWithMessageId(R.string.txtSinImplementar);
        }
        return true;
    }

    private void openBestResults() {
        Intent intent = new Intent(MainActivity.this, GameResultActivity.class);
        startActivity(intent);
    }

    private void restoreGame() {
        if (this.juegoBantumi.isGamePlayed()) {
            Log.i(LOG_TAG, "Mostrando dialogo de restaurar una partida");
            this.showCustomDialogWithTitleMessageAndAcceptAction(R.string.restartDialogTitle, R.string.restoreGameDialogMessage, this::restoreGameFromFile);
        } else {
            this.restoreGameFromFile();
        }
    }

    private void restoreGameFromFile() {
        try {
            Log.i(LOG_TAG, "Restaurando partida desde fichero");
            FileInputStream fInput = openFileInput(getString(R.string.savedGamesFileName));
            BufferedReader reader = new BufferedReader(new InputStreamReader(fInput));
            String endl = "\n";
            String line = reader.readLine();
            StringBuilder builder = new StringBuilder();
            builder.append(line)
                    .append(endl);
            while (line != null) {
                line = reader.readLine();
                builder.append(line)
                        .append(endl);
            }
            this.juegoBantumi.deserializa(builder.toString());
            fInput.close();
            this.showSnackBarWithMessageId(R.string.txtRestoredGame);
        } catch (FileNotFoundException fEx) {
            this.showSnackBarWithMessageId(R.string.noSavedGameMessage);
        } catch (IOException iex) {
            Log.e(LOG_TAG_ERROR, Objects.requireNonNull(iex.getMessage()));
            iex.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showSaveGameDialog() {
        this.showCustomDialogWithTitleMessageAndAcceptAction(R.string.saveGameDialogTitle, R.string.saveGameDialogMessage, this::saveGame);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void saveGame() {
        Log.i(LOG_TAG, "Guardando la partida");
        this.writeGameInFile(this.juegoBantumi.serializa());
        this.showSnackBarWithMessageId(R.string.txtGameSaved);
    }

    private void showSnackBarWithMessageId(int id) {
        Snackbar.make(
                findViewById(android.R.id.content),
                getString(id),
                Snackbar.LENGTH_LONG
        ).show();
    }

    private void writeGameInFile(String partidaSerializada) {
        try {
            Log.i(LOG_TAG, "Escribiendo la partida en el fichero");
            FileOutputStream fOut = openFileOutput(getString(R.string.savedGamesFileName), MODE_PRIVATE);
            fOut.write(partidaSerializada.getBytes());
            fOut.close();
        } catch (IOException iex) {
            Log.e(LOG_TAG_ERROR, Objects.requireNonNull(iex.getMessage()));
            iex.printStackTrace();
        }
    }

    private void showRestartDialog() {
        Log.i(LOG_TAG, "Mostrando dialogo reiniciar");
        this.showCustomDialogWithTitleMessageAndAcceptAction(R.string.restartDialogTitle, R.string.restartDialogMessage, this::onRestartGameDialogAccept);
    }

    private void showCustomDialogWithTitleMessageAndAcceptAction(int idTitle, int idMessage, Runnable acceptAction) {
        CustomOnlyAcceptDialogFragmentBuilder builder = new CustomOnlyAcceptDialogFragment.Builder();
        builder.setTitle(getString(idTitle))
                .setMessage(getString(idMessage))
                .setAcceptAction(((dialog, which) -> {
                    acceptAction.run();
                }))
                .build()
                .show(getSupportFragmentManager(), "RestartGameDialogFragment");
    }

    private void showAjustes() {
        Log.i(LOG_TAG, "Abriendo configuracion");
        Intent intent = new Intent(getApplicationContext(), ConfigurationActivity.class);
        startActivity(intent);
    }

    /**
     * Acción que se ejecuta al pulsar sobre cualquier hueco
     *
     * @param v Vista pulsada (hueco)
     */
    public void huecoPulsado(@NonNull View v) {
        String resourceName = getResources().getResourceEntryName(v.getId()); // pXY
        int num = Integer.parseInt(resourceName.substring(resourceName.length() - 2));
        Log.i(LOG_TAG, "huecoPulsado(" + resourceName + ") num=" + num);
        switch (juegoBantumi.turnoActual()) {
            case turnoJ1:
                juegoBantumi.jugar(num);
                break;
            case turnoJ2:
                juegaComputador();
                break;
            default:    // JUEGO TERMINADO
                finJuego();
        }
        if (juegoBantumi.juegoTerminado()) {
            finJuego();
        }
    }

    /**
     * Elige una posición aleatoria del campo del jugador2 y realiza la siembra
     * Si mantiene turno -> vuelve a jugar
     */
    void juegaComputador() {
        while (juegoBantumi.turnoActual() == JuegoBantumi.Turno.turnoJ2) {
            int pos = 7 + (int) (Math.random() * 6);    // posición aleatoria [7..12]
            Log.i(LOG_TAG, "juegaComputador(), pos=" + pos);
            if (juegoBantumi.getSemillas(pos) != 0 && (pos < 13)) {
                juegoBantumi.jugar(pos);
            } else {
                Log.i(LOG_TAG, "\t posición vacía");
            }
        }
    }

    /**
     * El juego ha terminado. Volver a jugar?
     */
    private void finJuego() {
        boolean isTie = false;
        String texto = "Gana ";
        String winnerName = (juegoBantumi.getSemillas(6) > 6 * numInicialSemillas)
                ? this.getSettingPlayerNameOrDefault()
                : getString(R.string.txtPlayer2);
        String loserName = (juegoBantumi.getSemillas(6) < 6 * numInicialSemillas)
                ? this.getSettingPlayerNameOrDefault()
                : getString(R.string.txtPlayer2);
        texto += winnerName;
        if (juegoBantumi.getSemillas(6) == 6 * numInicialSemillas) {
            texto = "¡¡¡ EMPATE !!!";
            isTie = true;
        }
        Snackbar.make(
                        findViewById(android.R.id.content),
                        texto,
                        Snackbar.LENGTH_LONG
                )
                .show();

        int winnerPos = winnerName.equals(this.getSettingPlayerNameOrDefault()) ? 6 : JuegoBantumi.NUM_POSICIONES - 1;
        int loserPos = winnerPos == 6 ? JuegoBantumi.NUM_POSICIONES - 1 : 6;
        GameResult.Builder builder = new GameResult.Builder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        GameResult result = builder.setWinnerName(winnerName)
                .setLoserName(loserName)
                .setDateTime(LocalDateTime.now().format(formatter))
                .setWinnerSeedNumber(this.juegoBantumi.getSemillas(winnerPos))
                .setLoserSeedNumber(this.juegoBantumi.getSemillas(loserPos))
                .setIsTie(isTie)
                .build();
        this.gameResultViewModel.insert(result);
        // terminar
        new FinalAlertDialog().show(getSupportFragmentManager(), "ALERT_DIALOG");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("MiW", "Destroying main activity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.setPlayerName();
        if (!juegoBantumi.isGamePlayed()) {
            this.restartGame();
            this.showSnackBarWithMessageId(R.string.resetGameWithNewPreferences);
        } else {
            this.showSnackBarWithMessageId(R.string.resetNextGameWithNewPreferences);
        }
    }

    private void restartGame() {
        int numeroSemillas = this.getInitialSeedsNumber();
        JuegoBantumi.Turno turn = this.getFirstMovementTurnFromPreferences();
        this.juegoBantumi.restartGame(numeroSemillas, turn);
    }

    public void onRestartGameDialogAccept() {
        Log.i(LOG_TAG, "Reiniciando la partida");
        this.restartGame();
        this.showSnackBarWithMessageId(R.string.txtRestartedGame);
        Log.i(LOG_TAG, "Partida reiniciada");
    }
}