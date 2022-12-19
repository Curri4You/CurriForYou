package com.example.curriforyou;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.Locale;

public class JjimRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private ArrayList<DataJjimList> listData = null;
    private Context context;
    private SparseBooleanArray selecteditems = new SparseBooleanArray();
    ArrayList<DataJjimList> filteredList;
    private int prePosition = -1;
    int CHECK_NUM = 0;
    Activity activity;

    //[찜 기능] 찜 상태를 저장하는 boolean array
    private SparseBooleanArray selectedhearts = new SparseBooleanArray();

    //생성자
    public JjimRecyclerAdapter(ArrayList<DataJjimList> list) {
        listData = list;
        this.filteredList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //LayoutInflater를 이용하여 item.xml을 inflate시킴
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_jjim, parent, false);
        return new ViewHolderJjimList(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //item을 하나하나 보여주는(bind되는) 함수
        ((ViewHolderJjimList) holder).onBind(filteredList.get(position), position);
    }

    @Override
    public int getItemCount() {
        //RecyclerView의 총 개수
        return filteredList.size();
    }

    void addItem(DataJjimList data) {
        //외부에서 item을 추가시킬 함수
        listData.add(data);
    }

    public void setItems(ArrayList<DataJjimList> listData1) {
        listData = listData1;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            protected FilterResults performFiltering(CharSequence charSequence) {
                String open_only = charSequence.toString();
                if(open_only.equals("all")){
                    filteredList = listData;
                } else {
                    ArrayList<DataJjimList> filteringList = new ArrayList<>();
                    for (DataJjimList dataJjim : listData) {
                        if (dataJjim.is_open.equals("1")) {
                            filteringList.add(dataJjim);
                        }
                    }
                    filteredList = filteringList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList = (ArrayList<DataJjimList>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    class ViewHolderJjimList extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_course_name, tv_course_id, tv_is_open, tv_credit;
        ImageView iv_major_division;
        private DataJjimList data;
        private int position;
        private ImageButton ib_heart;
        boolean i = true;
        //[찜, 수강&미수강] 초기화에 사용되는 임의 count 변수
        int count_heart = 0;

        public ViewHolderJjimList(@NonNull View itemView) {
            super(itemView);

            tv_course_name = itemView.findViewById(R.id.tv_course_name);
            tv_course_id = itemView.findViewById(R.id.tv_course_id);
            tv_is_open = itemView.findViewById(R.id.tv_is_open);
            tv_credit = itemView.findViewById(R.id.tv_credit);
            iv_major_division = itemView.findViewById(R.id.iv_major_division);
            ib_heart = itemView.findViewById(R.id.ib_heart);
            Context jjimActivity = JjimActivity.context_jjim;
        }

        public void onBind(DataJjimList data, int position) {
            this.data = data;
            this.position = position;

            tv_course_name.setText(data.getCourse_name());
            tv_course_id.setText(data.getCourse_id());
            tv_credit.setText(data.getCredit());

            //is_open에 따라 개설여부 OX 표시
            switch (data.getIs_open()) {
                case "0":
                    tv_is_open.setText("X");
                    break;
                case "1":
                    tv_is_open.setText("O");
                    break;
            }

            //major_division에 따른 바 색깔 변경
            switch (data.getMajor_division()) {
                case "1":
                    iv_major_division.setImageResource(R.drawable.major_division_1);
                    break;
                case "2":
                    iv_major_division.setImageResource(R.drawable.major_division_2);
                    break;
                case "3":
                    iv_major_division.setImageResource(R.drawable.major_division_3);
                    break;
                case "12":
                    iv_major_division.setImageResource(R.drawable.major_division_12);
                    break;
                case "23":
                    iv_major_division.setImageResource(R.drawable.major_division_23);
                    break;
                case "13":
                    iv_major_division.setImageResource(R.drawable.major_division_13);
                    break;
                case "123":
                    iv_major_division.setImageResource(R.drawable.major_division_123);
                    break;
            }

            //[찜] 기존 데이터에 기반한 찜 초기화
            if (data.getJjim().equals("1") && count_heart == 0) {
                selectedhearts.put(position, true);
                count_heart++;
            }

            //[찜] 찜 기능을 적용하는 함수
            changeImageView(selectedhearts.get(position));
            ib_heart.setOnClickListener(this);
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ib_heart:     //찜 버튼을 클릭했을 경우
                    if (selectedhearts.get(position)) {
                        //선택된 item을 클릭 시(value == 1)
                        selectedhearts.delete(position);
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        Context jjimActivity = JjimActivity.context_jjim;
                        JjimListRequest jjimListRequest = new JjimListRequest(listData.get(position).course_id, "1", responseListener);
                        RequestQueue queue = Volley.newRequestQueue(jjimActivity);
                        queue.add(jjimListRequest);
                    } else {
                        //선택되지 않은 item을 클릭 시(value == 0)
                        //클릭한 item의 position을 저장
                        selectedhearts.put(position, true);
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        Context jjimActivity = JjimActivity.context_jjim;
                        JjimListRequest jjimListRequest = new JjimListRequest(listData.get(position).course_id, "0", responseListener);
                        RequestQueue queue = Volley.newRequestQueue(jjimActivity);
                        queue.add(jjimListRequest);
                    }
                    notifyItemChanged(position);
                    break;
            }
        }

        private void changeImageView(final boolean isExpanded) {
            ib_heart.setImageResource(isExpanded ? R.drawable.filled_heart : R.drawable.empty_heart);
        }
    }
}