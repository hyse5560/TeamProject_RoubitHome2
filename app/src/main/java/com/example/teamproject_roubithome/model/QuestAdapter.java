package com.example.teamproject_roubithome.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamproject_roubithome.R; // R.id.tv_quest_title, R.id.tv_quest_reward, R.id.btn_quest_action

import java.util.List;

public class QuestAdapter extends RecyclerView.Adapter<QuestAdapter.QuestViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(QuestItem item);
    }

    private List<QuestItem> questList;
    private OnItemClickListener listener;
    private Context context; // Context might not be strictly needed in adapter if not doing Toast/Dialogs

    public QuestAdapter(Context context, List<QuestItem> questList, OnItemClickListener listener) {
        this.context = context;
        this.questList = questList;
        this.listener = listener;
    }
    public void setQuestList(List<QuestItem> newQuestList) {
        this.questList = newQuestList;
        // notifyDataSetChanged()는 QuestFragment에서 호출할 것이므로 여기서는 제거합니다.
        // this.notifyDataSetChanged(); // QuestFragment에서 직접 호출할 예정
    }


    @NonNull
    @Override
    public QuestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quest, parent, false);
        return new QuestViewHolder(view);
    }

    // test
    @Override
    public void onBindViewHolder(@NonNull QuestViewHolder holder, int position) {
        QuestItem currentItem = questList.get(position);

        holder.title.setText(currentItem.getTitle());
        String rewardText = "🥕 당근 " + currentItem.getReward() + "개";
        holder.reward.setText(rewardText);

        // 퀘스트 상태에 따라 버튼 텍스트와 활성화 여부 결정
        if (currentItem.isClaimed()) {
            // 퀘스트 완료 및 보상 받기까지 완료된 상태
            holder.actionButton.setText("보상 완료");
            holder.actionButton.setEnabled(false); // 버튼 비활성화
            holder.actionButton.setAlpha(0.6f);
        } else if (currentItem.isCompleted()) {
            // 퀘스트는 완료되었지만 보상은 아직 받지 않은 상태
            holder.actionButton.setText("보상 받기");
            holder.actionButton.setEnabled(true); // 버튼 활성화
            // holder.actionButton.setAlpha(1.0f);
        } else {
            // 퀘스트가 아직 완료되지 않은 상태
            holder.actionButton.setText("하러가기");
            holder.actionButton.setEnabled(true); // 버튼 활성화
            // holder.actionButton.setAlpha(1.0f);
        }

        // 버튼 클릭 리스너 설정 (상태에 따라 동작이 달라지므로, 항상 동일한 리스너를 붙이고 내부에서 분기)
        holder.actionButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(currentItem); // 클릭 시 해당 아이템을 리스너로 전달
            }
        });
    }

    @Override
    public int getItemCount() {
        return questList.size();
    }

    // 퀘스트 아이템을 업데이트하고 UI를 갱신하는 메서드
    public void updateQuestItem(int position, QuestItem updatedItem) {
        if (position >= 0 && position < questList.size()) {
            questList.set(position, updatedItem);
            notifyItemChanged(position); // 해당 아이템만 갱신
        }
    }

    static class QuestViewHolder extends RecyclerView.ViewHolder {
        TextView title, reward;
        Button actionButton; // 'button' 대신 'actionButton'으로 변수명 통일

        public QuestViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_quest_title);
            reward = itemView.findViewById(R.id.tv_quest_reward);
            actionButton = itemView.findViewById(R.id.btn_quest_action);
        }
    }
}