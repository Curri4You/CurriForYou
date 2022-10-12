// 학점관리 학기별 요약을 위한 ViewHolder

package com.example.curriforyou;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GmViewHolder extends  RecyclerView.ViewHolder {

    TextView gm_list_semester, gm_list_totalGrade, gm_list_majorGrade, gm_list_liberalGrade;

    public GmViewHolder(@NonNull View itemView) {
        super(itemView);
        gm_list_semester = itemView.findViewById(R.id.gm_list_semester);
        gm_list_totalGrade = itemView.findViewById(R.id.gm_list_totalGrade);
        gm_list_majorGrade = itemView.findViewById(R.id.gm_list_majorGrade);
        gm_list_liberalGrade = itemView.findViewById(R.id.gm_list_liberalGrade);
    }

    //데이터 이용해서 text 변경
    public void onBind(DataGmSemester data){
        gm_list_semester.setText(data.getSemester());
        gm_list_totalGrade.setText(data.getTotalGrade());
        gm_list_majorGrade.setText(data.getMajorGrade());
        gm_list_liberalGrade.setText(data.getLiberalGrade());
    }
}

