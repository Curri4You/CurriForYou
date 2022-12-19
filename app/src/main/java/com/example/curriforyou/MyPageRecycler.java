package com.example.curriforyou;

import static android.view.View.GONE;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyPageRecycler extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<MyPageData> listData = new ArrayList<>();
    // adapter에 들어갈 activity 정보
    Activity activity;

    public MyPageRecycler(Activity activity){
        this. activity = activity;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mypage_majorbtn, parent, false);
        return new ViewHolderMpList(view);
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolderMpList) holder).onBind(listData.get(position));
    }

    public int getItemCount() {
        return listData.size();
    }

    void addItem(MyPageData data) {
        listData.add(data);
    }

    class ViewHolderMpList extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout ll_majorButton;
        TextView tv_major_division;
        Button btn_major;
        ImageView iv_delMajor;
        private MyPageData data;
        //[Dialog] 전공삭제 버튼 누르면 뜨는 dialog(팝업창)
        Dialog delMajor_dialog;

        public ViewHolderMpList(@NonNull View itemView) {
            super(itemView);

            ll_majorButton = itemView.findViewById(R.id.ll_majorButton);
            tv_major_division = itemView.findViewById(R.id.tv_major_division);
            btn_major = itemView.findViewById(R.id.btn_major);
            iv_delMajor = itemView.findViewById(R.id.iv_delMajor);

            //[Dialog] MyPageActivity에서 초기화
            delMajor_dialog = new Dialog(activity);
            //[Dialog] 팝업 화면의 기본 Title 없애기
            delMajor_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            //[Dialog] 사용자가 만든 layout으로 Custom 팝업창 설정
            delMajor_dialog.setContentView(R.layout.mypage_delete_dialog);
        }

        public void onBind(MyPageData data) {
            this.data = data;

            if(data.getMajor_division() == 1){
                tv_major_division.setText("주전공");
            } else if (data.getMajor_division() == 2){
                tv_major_division.setText("복수전공");
            } else if (data.getMajor_division() == 3){
                tv_major_division.setText("부전공");
            } else if (data.getMajor_division() == 4){
                tv_major_division.setText("연계전공");
            } else if (data.getMajor_division() == 5){
                tv_major_division.setText("심화전공");
            } else{
                tv_major_division.setText("ERROR");
            };
            btn_major.setText(data.getMajor_name());

            btn_major.setOnClickListener(this);
            iv_delMajor.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.iv_delMajor:
                    showDelMajorDialog();
            }
        }

        public void showDelMajorDialog() {
            delMajor_dialog.show();
            // 예/아니요 버튼
            Button btn_del_yes = delMajor_dialog.findViewById(R.id.btn_del_yes);
            Button btn_del_no = delMajor_dialog.findViewById(R.id.btn_del_no);

            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    switch (v.getId()) {
                        case R.id.btn_del_yes:
                            delMajor_dialog.dismiss();
                            ll_majorButton.setVisibility(GONE);
                            break;
                        case R.id.btn_del_no:
                            delMajor_dialog.dismiss();
                            break;
                    }
                }
            };
            // 예/아니요 버튼 클릭 리스터 추가
            btn_del_yes.setOnClickListener(onClickListener);
            btn_del_no.setOnClickListener(onClickListener);
        }

    }
}







