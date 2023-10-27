package es.upm.miw.bantumi.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import es.upm.miw.bantumi.R;
import es.upm.miw.bantumi.model.game_result_model.GameResult;

public class GameResultViewHolder extends RecyclerView.ViewHolder {
    TextView tvDate;
    TextView tvWinnerName;
    TextView tvWinnerSeeds;
    TextView tvIsTie;
    TextView tvGameDuration;
    public GameResultViewHolder(@NonNull View itemView) {
        super(itemView);
        this.tvDate = itemView.findViewById(R.id.tvResultDate);
        this.tvWinnerName = itemView.findViewById(R.id.tvResultWinner);
        this.tvWinnerSeeds = itemView.findViewById(R.id.tvResultWinnerSeedNumber);
        this.tvIsTie = itemView.findViewById(R.id.tvIsTie);
        this.tvGameDuration = itemView.findViewById(R.id.tvGameDuration);
    }

    static GameResultViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.game_result_recycler_view_item, parent, false);
        return new GameResultViewHolder(view);
    }

    public void bind(GameResult gameResult) {
        this.tvDate.setText(gameResult.getGameDateTime());
        this.tvWinnerName.setText(gameResult.getTie() ? "" : gameResult.getWinnerName());
        this.tvWinnerSeeds.setText(gameResult.getWinnerSeedNumber().toString());
        this.tvIsTie.setText(gameResult.getTie() ?
                itemView.getResources().getString(R.string.txtYes) :
                itemView.getResources().getString(R.string.txtNo));
        this.tvGameDuration.setText(gameResult.getGameDuration());
    }
}
