package com.example.curriforyou;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

public class RecyclerVierAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //adapter에 들어갈 list
    private ArrayList<DataCourseList> listData = null;
    private Context context;

    //item의 클릭 상태를 저장할 array 객체
    private SparseBooleanArray selecteditems = new SparseBooleanArray();

    //직전에 클릭됐던 item의 position
    private int prePosition = -1;
    //직전에 클릭했던 heart position
    private int prePosition_heart = -1;
    //직전에 클릭했던 수강&미수강 position
    private int prePosition_listened = -1;

    //[찜 기능] 찜 상태를 저장하는 boolean array
    private SparseBooleanArray selectedhearts = new SparseBooleanArray();
    //[수강&미수강] 수강/미수강 상태를 저장하는 boolean array
    private SparseBooleanArray selectedlistened = new SparseBooleanArray();
    ////
    private SparseBooleanArray selecteddetails = new SparseBooleanArray();

    //생성자
    RecyclerVierAdapter(ArrayList<DataCourseList> list){
        listData = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //LayoutInflater를 이용하여 item.xml을 inflate시킴
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item, parent, false);
        return new ViewHolderCourseList(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //item을 하나하나 보여주는(bind되는) 함수
        ((ViewHolderCourseList)holder).onBind(listData.get(position), position);
    }

    @Override
    public int getItemCount() {
        //RecyclerView의 총 개수
        return listData.size();
    }

    void addItem(DataCourseList data){
        //외부에서 item을 추가시킬 함수
        listData.add(data);
    }

    //[Search]
    public void setItems(ArrayList<DataCourseList> listData1){
        listData = listData1;
        notifyDataSetChanged();
    }

    //Recycler의 핵심; ViewHolder
    //subView를 setting
    class ViewHolderCourseList extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tv_course_name, tv_course_id, tv_is_open, tv_credit,
                tv_course_year, tv_course_semester, tv_grade;
        ImageView iv_major_division;
        private LinearLayout tv_item_open, ll_listitem;
        private DataCourseList data;
        private int position;
        private ImageButton ib_heart;
        CheckBox cb_taken, cb_major1, cb_major2, cb_major3;
        boolean i = true;
        //다이얼로그
        Dialog dialog;


        //[찜, 수강&미수강] 초기화에 사용되는 임의 count 변수
        int count_heart = 0;
        int count_listened = 0;

        public ViewHolderCourseList(@NonNull View itemView) {
            super(itemView);

            tv_course_name = itemView.findViewById(R.id.tv_course_name);
            tv_course_id = itemView.findViewById(R.id.tv_course_id);
            tv_is_open = itemView.findViewById(R.id.tv_is_open);
            tv_credit = itemView.findViewById(R.id.tv_credit);
            ///
            tv_course_year = itemView.findViewById(R.id.tv_course_year);
            tv_course_semester = itemView.findViewById(R.id.tv_course_semester);
            tv_grade = itemView.findViewById(R.id.tv_grade);
            iv_major_division = itemView.findViewById(R.id.iv_major_division);

            tv_item_open = itemView.findViewById(R.id.tv_item_open);
            ib_heart = itemView.findViewById(R.id.ib_heart);
            cb_taken = itemView.findViewById(R.id.cb_taken);
            ll_listitem = itemView.findViewById(R.id.ll_listitem);

            cb_major1 = itemView.findViewById(R.id.cb_major1);
            cb_major2 = itemView.findViewById(R.id.cb_major2);
            cb_major3 = itemView.findViewById(R.id.cb_major3);

            ///
            Context mainActivity = MainActivity.context_main;
            dialog = new Dialog(mainActivity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.cb_taken_detail);

        }

        public void onBind(DataCourseList data, int position) {

            this.data = data;
            this.position = position;

            tv_course_name.setText(data.getCourse_name());
            tv_course_id.setText(data.getCourse_id());
            tv_is_open.setText(data.getIs_open());
            tv_credit.setText(data.getCredit());
            ///
            tv_course_year.setText(data.getCourse_year());
            tv_course_semester.setText(data.getCourse_semester());
            tv_grade.setText(data.getGrade());

            //major_division에 따른 바 색깔 변경
            switch (data.getMajor_division()) {
                case "1":
                    iv_major_division.setImageResource(R.drawable.major_division_1);
                    cb_major1.setChecked(true);
                    cb_major2.setChecked(false);
                    cb_major3.setChecked(false);
                    break;
                case "2":
                    iv_major_division.setImageResource(R.drawable.major_division_2);
                    cb_major1.setChecked(false);
                    cb_major2.setChecked(true);
                    cb_major3.setChecked(false);
                    break;
                case "3":
                    iv_major_division.setImageResource(R.drawable.major_division_3);
                    cb_major1.setChecked(false);
                    cb_major2.setChecked(false);
                    cb_major3.setChecked(true);
                    break;
                case "12":
                    iv_major_division.setImageResource(R.drawable.major_division_12);
                    cb_major1.setChecked(true);
                    cb_major2.setChecked(true);
                    cb_major3.setChecked(false);
                    break;
                case "23":
                    iv_major_division.setImageResource(R.drawable.major_division_23);
                    cb_major1.setChecked(false);
                    cb_major2.setChecked(true);
                    cb_major3.setChecked(true);
                    break;
                case "13":
                    iv_major_division.setImageResource(R.drawable.major_division_13);
                    cb_major1.setChecked(true);
                    cb_major2.setChecked(false);
                    cb_major3.setChecked(true);
                    break;
                case "123":
                    iv_major_division.setImageResource(R.drawable.major_division_123);
                    cb_major1.setChecked(true);
                    cb_major2.setChecked(true);
                    cb_major3.setChecked(true);
                    break;
            }

            //[찜] 기존 데이터에 기반한 찜 초기화
            if(data.getJjim().equals("1") && count_heart == 0){
                selectedhearts.put(position, true);
                count_heart ++;
            }

            //[수강&미수강] 기존 데이터에 기반한 수강 깃발 초기화
            if ((!data.getCourse_year().equals("미정")) && count_listened == 0){
                selectedlistened.put(position, true);
                count_listened ++;
            }

            //[펼침기능] 펼침 기능을 적용하는 함수
            changeVisibility(selecteditems.get(position));
            //[찜] 찜 기능을 적용하는 함수
            changeImageView(selectedhearts.get(position));
            //[수강&미수강] 버튼을 클릭하면 깃발 기능을 적용하는 함수
            changeListened(selectedlistened.get(position));

            itemView.setOnClickListener(this);
            ib_heart.setOnClickListener(this);
            cb_taken.setOnClickListener(this);
            ll_listitem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ll_listitem:
                    if(selecteditems.get(position)){
                        //펼쳐진 item을 클릭 시
                        selecteditems.delete(position);
                    } else {
                        //직전의 클릭했던 item의 클릭상태를 지움
                        /*selecteditems.delete(prePosition);*/
                        //클릭한 item의 position을 저장
                        selecteditems.put(position, true);
                    }
                    //해당 포지션의 변화를 알림
                    if (prePosition != -1)
                        notifyItemChanged(prePosition);
                    notifyItemChanged(position);
                    //클릭된 position 저장
                    prePosition = position;
                    break;
                case R.id.ib_heart:     //찜 버튼을 클릭했을 경우
                    if(selectedhearts.get(position)){
                        //선택된 item을 클릭 시(value == 1)
                        selectedhearts.delete(position);
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");
                                    if(success) {
                                        Toast.makeText(context.getApplicationContext(),"등록에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context.getApplicationContext(),"등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }
                        };
                        Context mainActivity = MainActivity.context_main;
                        JjimListRequest jjimListRequest = new JjimListRequest(listData.get(position).course_id, "1", responseListener);
                        RequestQueue queue = Volley.newRequestQueue(mainActivity);
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
                                    if(success) {
                                        Toast.makeText(context.getApplicationContext(),listData.get(position).course_id, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context.getApplicationContext(),"등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }
                        };
                        Context mainActivity = MainActivity.context_main;
                        JjimListRequest jjimListRequest = new JjimListRequest(listData.get(position).course_id, "0", responseListener);
                        RequestQueue queue = Volley.newRequestQueue(mainActivity);
                        queue.add(jjimListRequest);
                    }
                    notifyItemChanged(position);
                    break;
                case R.id.cb_taken:     //수강&미수강 버튼을 클릭했을 경우
                    if(selectedlistened.get(position)){
                        //선택된 item을 클릭 시(value == 1)
                        selectedlistened.delete(position);
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");
                                    if(success) {
                                        Toast.makeText(context.getApplicationContext(),"등록에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context.getApplicationContext(),"등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }
                        };
                        Context mainActivity = MainActivity.context_main;
                        ListenedRequest listenedRequest = new ListenedRequest(listData.get(position).course_id, "1", responseListener);
                        RequestQueue queue = Volley.newRequestQueue(mainActivity);
                        queue.add(listenedRequest);
                    } else {
                        /////
                        showDialog();
                        //선택되지 않은 item을 클릭 시(value == 0)
                        //클릭한 item의 position을 저장
                        selectedlistened.put(position, true);
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");
                                    if(success) {
                                        Toast.makeText(context.getApplicationContext(),listData.get(position).course_id, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context.getApplicationContext(),"등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }
                        };
                        Context mainActivity = MainActivity.context_main;
                        ListenedRequest listenedRequest = new ListenedRequest(listData.get(position).course_id, "0", responseListener);
                        RequestQueue queue = Volley.newRequestQueue(mainActivity);
                        queue.add(listenedRequest);

                    }
                    notifyItemChanged(position);
                    break;
            }
        }

        /*
         * 클릭된 Item의 상태 변경
         * @param isExpanded Item을 펼칠 것인지 여부
         */
        private void changeVisibility(final boolean isExpanded) {
            //height 값을 dp로 지정해서 넣고 싶으면 아래 소스를 이용
            int dpValue = 150;
            float d = context.getResources().getDisplayMetrics().density;
            int height = (int)(dpValue*d);

            // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
            ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, height) : ValueAnimator.ofInt(height, 0);
            // Animation이 실행되는 시간, n/1000초
            va.setDuration(600);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    // value는 height 값
                    int value = (int) animation.getAnimatedValue();
                    // imageView가 실제로 사라지게하는 부분
                    tv_item_open.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                }
            });
            //Animation start
            va.start();
        }

        private void changeImageView(final boolean isExpanded){
            ib_heart.setImageResource(isExpanded ? R.drawable.filled_heart : R.drawable.empty_heart);
        }

        private void changeListened(final boolean isExpanded){
            if(isExpanded){
                cb_taken.setText("수강");
                cb_taken.setChecked(true);
                tv_course_year.setText("2021");
                tv_course_semester.setText("1");
                tv_grade.setText("A0");
            } else {
                cb_taken.setText("미수강");
                cb_taken.setChecked(false);
                tv_course_year.setText("미정");
                tv_course_semester.setText("미정");
                tv_grade.setText("미정");
            }
        }


        String choice_year="", choice_semester="", choice_grade="";

        public void showDialog() {
            dialog.show();

            ArrayAdapter<CharSequence> spin_yearAdapter, spin_semesterAdapter, spin_gradeAdapter;
            /*String choice_year="", choice_semester="", choice_grade="";*/

            Spinner spin_course_year = dialog.findViewById(R.id.spin_course_year);
            Spinner spin_course_semester = dialog.findViewById(R.id.spin_course_semester);
            Spinner spin_grade = dialog.findViewById(R.id.spin_grade);

            Context mainActivity = MainActivity.context_main;

            spin_yearAdapter = ArrayAdapter.createFromResource(mainActivity, R.array.spin_course_year, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
            spin_yearAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
            spin_course_year.setAdapter(spin_yearAdapter);
            spin_course_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    choice_year = spin_yearAdapter.getItem(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spin_semesterAdapter = ArrayAdapter.createFromResource(mainActivity, R.array.spin_course_semester, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
            spin_semesterAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
            spin_course_semester.setAdapter(spin_semesterAdapter);
            spin_course_semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    choice_semester = spin_semesterAdapter.getItem(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spin_gradeAdapter = ArrayAdapter.createFromResource(mainActivity, R.array.spin_grade, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
            spin_gradeAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
            spin_grade.setAdapter(spin_gradeAdapter);
            spin_grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    choice_grade = spin_gradeAdapter.getItem(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            Button btn_confirm = dialog.findViewById(R.id.btn_course_detail_confirm);
            btn_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String course_year = choice_year;
                    String course_semester = choice_semester;
                    String course_grade = choice_grade;



                    dialog.dismiss();
                }
            });
        }


    }

}