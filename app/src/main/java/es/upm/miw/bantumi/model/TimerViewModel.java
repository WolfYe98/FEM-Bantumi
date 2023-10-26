package es.upm.miw.bantumi.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Locale;

public class TimerViewModel extends ViewModel {
    private static final String INITIAL_VALUE = "00:00";
    MutableLiveData<String> timerValue = new MutableLiveData<>();

    public TimerViewModel() {
        this.timerValue.setValue(INITIAL_VALUE);
    }

    public MutableLiveData<String> getTimerValue() {
        return timerValue;
    }

    public void addSecondToTimer() {
        String[] minutesAndSeconds = this.timerValue.getValue().split(":");
        int minutes = Integer.parseInt(minutesAndSeconds[0]);
        int seconds = Integer.parseInt(minutesAndSeconds[1]);
        seconds += 1;
        if (seconds == 60) {
            seconds = 0;
            minutes += 1;
        }
        String minutesString = String.format(Locale.getDefault(), "%02d", minutes);
        String secondsString = String.format(Locale.getDefault(), "%02d", seconds);
        this.timerValue.postValue(minutesString + ":" + secondsString);
    }

    public void setTimerToZero() {
        this.timerValue.postValue(INITIAL_VALUE);
    }
}
