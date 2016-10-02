package com.baekcedar.android.notepad;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RelativeLayout conLayout;
    RelativeLayout mainLayout;
    EditText editText;
    ArrayList<RecyclerData> datas;
    RecyclerData  data;
    RecyclerView recyclerView;
    Button writeBtn,updateBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Button cancelBtn         = (Button)findViewById(R.id.cancelBtn);
        writeBtn                 = (Button)findViewById(R.id.writeBtn);
        updateBtn                 = (Button)findViewById(R.id.updateBtn);
        editText                 = (EditText)findViewById(R.id.editText);
        conLayout                = (RelativeLayout)findViewById(R.id.relativeLayout);
        mainLayout               = (RelativeLayout) findViewById(R.id.mainLayout);


        // 리사이클 뷰
        datas = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);



        runRecyclerView();
        ///////여기까지  리사이클 뷰


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainLayout.setVisibility(View.GONE);
                conLayout.setVisibility(View.VISIBLE);  //메모화면 보이기


                System.out.println("fab");

            }
        });
        // write 버튼 클릭시(저장)
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("write");
                conLayout.setVisibility(View.GONE);     // 메모화면 숨기기
                mainLayout.setVisibility(View.VISIBLE); // 메인화면 보이기
                StringBuffer strb = new StringBuffer();
                strb.append(editText.getText().toString());
                write(1,strb);
                runRecyclerView();
                keyBoardOff(view.getContext(), editText);
                editText.setText("");
            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("write");
                conLayout.setVisibility(View.GONE);     // 메모화면 숨기기
                mainLayout.setVisibility(View.VISIBLE); // 메인화면 보이기
                writeBtn.setVisibility(View.VISIBLE);
                updateBtn.setVisibility(View.GONE);
                StringBuffer strb = new StringBuffer();
                strb.append(editText.getText().toString());
                write(0,strb);
                runRecyclerView();
                keyBoardOff(view.getContext(), editText);
                editText.setText("");
            }
        });
        // cancel 버튼 클릭시
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("cancel");
                conLayout.setVisibility(View.GONE);     // 메모화면 숨기기
                mainLayout.setVisibility(View.VISIBLE); // 메인화면 보이기
                writeBtn.setVisibility(View.VISIBLE);
                updateBtn.setVisibility(View.GONE);
                editText.setText("");
                keyBoardOff(view.getContext(), editText);
            }
        });

    }
    //저장버튼
    public void write(int flag, StringBuffer str){
        if(str.toString().equals("")){

        }else {
            if(flag==1){
                data = new RecyclerData();
                data.title = str.toString();
                datas.add(data);
            }else {

                data.title = str.toString();

            }
        }
    }

    // 수정 모드
    public void update(RecyclerData data){
        StringBuffer strb = new StringBuffer();
        strb.append(data.title.toString());
        System.out.println(strb);
        editText.setText(strb);
        conLayout.setVisibility(View.VISIBLE);      // 메모화면 숨기기
        mainLayout.setVisibility(View.GONE);        // 메인화면 보이기
        writeBtn.setVisibility(View.GONE);
        updateBtn.setVisibility(View.VISIBLE);


    }
    public void delete(RecyclerData data){
        datas.remove(data);
        runRecyclerView();

    }
    public void runRecyclerView(){


        RecyclerAdapter adapter = new RecyclerAdapter(datas,R.layout.card_item, this);
        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
    }
    //키보드 내리기
    public static void keyBoardOff(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(),0);
    }


}
