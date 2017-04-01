package com.example.chie.bulletinboard_0_5_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {

    private Realm realm;
    private EditText editTextPost;
    private long boardId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        editTextPost = (EditText) findViewById(R.id.edit_text_post);
        findViewById(R.id.button_ok).setOnClickListener(this);
        findViewById(R.id.button_cancel).setOnClickListener(this);

        initData();
    }

    @Override
    public void onClick(View v){
        int buttonId = v.getId();
        if(buttonId == R.id.button_ok){
            insert();
        }
        finish();
    }

    public long nextBoardId() {
        // TODO:レッスンではここにプログラムを追加
        RealmResults<Board> results = realm.where(Board.class).findAll()
                .sort("id", Sort.DESCENDING);
        if(results.size() > 0) {
            return  results.first().getId() + 1;
        }
        return 0;
    }
    private void initData(){
        // TODO:レッスンではここにプログラムを追加
        realm = Realm.getDefaultInstance();

        String post_id = getIntent().getStringExtra("post_id");
        if(TextUtils.isEmpty(post_id)) {
            //データが無い場合は新しいIDを取得
            boardId = nextBoardId();
        } else {
            //データがある場合は更新
            boardId = Long.parseLong(post_id);
            editTextPost.setText(realm.where(Board.class).equalTo("id", boardId).findFirst().getPost());
            editTextPost.setSelection(editTextPost.getText().length());
        }

    }

    public void insert(){
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm){
                Board board = new Board();
                board.setId(boardId);
                board.setPost(editTextPost.getText().toString());
                realm.insertOrUpdate(board);
            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        realm.close();
    }
}
