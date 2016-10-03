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
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RelativeLayout conLayout,mainLayout,deletePopupLayout;
    EditText editText;
    ArrayList<RecyclerData> datas;
    RecyclerData  data;
    RecyclerView recyclerView;
    Button writeBtn,deleteYesBtn,deleteNoBtn;
    StringBuffer strBuffer;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Button cancelBtn         = (Button)findViewById(R.id.cancelBtn);
        writeBtn                 = (Button)findViewById(R.id.writeBtn);
        deleteYesBtn             = (Button)findViewById(R.id.yesBtn);
        deleteNoBtn              = (Button)findViewById(R.id.noBtn);
        editText                 = (EditText)findViewById(R.id.editText);
        conLayout                = (RelativeLayout)findViewById(R.id.relativeLayout);
        mainLayout               = (RelativeLayout) findViewById(R.id.mainLayout);
        deletePopupLayout        = (RelativeLayout) findViewById(R.id.popupLayout);

        // 리사이클 뷰
        datas = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        runRecyclerView();
        ///////여기까지  리사이클 뷰


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // (+) 버튼 클릭시
                mainLayout.setVisibility(View.GONE);
                conLayout.setVisibility(View.VISIBLE);  //메모화면 보이기
                strBuffer = new StringBuffer();
                writeBtn.setText("Write");
            }
        });
        // write 버튼 클릭시(저장)
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (writeBtn.getText().equals("Update")) {
                    writeBtn.setText("Write");
                    updateSave();
                }else {
                    write();
                }
                viewMode(1);
                keyBoardOff();
                runRecyclerView();
                editText.setText("");

            }
        });
        deleteYesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datas.remove(position);
                deletePopupLayout.setVisibility(View.GONE);
                runRecyclerView();
            }
        });
        deleteNoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePopupLayout.setVisibility(View.GONE);
            }
        });

        // cancel 버튼 클릭시
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewMode(1);
            }
        });

    }
    //저장버튼
    public void write(){
        if(editText.getText().length()==0){
            Toast.makeText(getApplicationContext(),"내용을 작성하세요.",Toast.LENGTH_SHORT).show();
        }else {
            data = new RecyclerData();
            data.title = editText.getText().toString();
            datas.add(data);
        }
    }

    // 수정 모드
    public void updateMode(int position){
        StringBuffer strb = new StringBuffer();
        data = datas.get(position);
        writeBtn.setText("update");
        strb.append(data.title.toString());
        editText.setText(strb);
    }
    public void updateSave(){
        data.title = editText.getText().toString();


    }
    public void delete(int position){
        this.position = position;
        deletePopupLayout.setVisibility(View.VISIBLE);

    }

    public void runRecyclerView(){
        RecyclerAdapter adapter = new RecyclerAdapter(datas,R.layout.card_item, this);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
    }
    //키보드 내리기
    protected void keyBoardOff() {
            View view = getCurrentFocus();
            InputMethodManager mgr = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }
    public void viewMode(int mode){      //메인화면으로 전환
        if(mode == 1) { // edit 모드 -> 메인으로
            conLayout.setVisibility(View.GONE);     // 메모화면 숨기기
            mainLayout.setVisibility(View.VISIBLE); // 메인화면 보이기
            //System.out.println(view);
            editText.setText("");
            keyBoardOff();
        } else if (mode == 2){ // 메인에서 -> edit 모드
            conLayout.setVisibility(View.VISIBLE);     // 메모화면 보이기
            mainLayout.setVisibility(View.GONE); // 메인화면 숨기기
        }


    }

}
