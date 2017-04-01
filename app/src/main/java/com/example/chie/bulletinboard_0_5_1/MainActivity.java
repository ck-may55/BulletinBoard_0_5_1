package com.example.chie.bulletinboard_0_5_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements BoardAdapter.DeleteListener {


    private static final String[] initData = {
            "ここに投稿内容が表示されます",
            "各投稿を長押しすると投稿内容の編集",
            "ごみ箱マークを長押しすると投稿内容の削除ができます",
            "こんな風に\nたくさん\n改行\nしても\n大丈夫\nです。"};


    public Realm realm;
    private BoardAdapter adapter;
    private ListView listView;

    //RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
    //realm = Realm.getInstance()
       //Realm.setDefaultConfiguration(realmConfiguration);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView = (ListView) findViewById(R.id.list_view);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("post_id", Long.toString(id));
                startActivity(intent);
                return true;

            }
        });

        initBoards();
    }

    private void initBoards(){
    Log.d("myapp","initBoards");
        realm = Realm.getDefaultInstance();
        RealmResults<Board> boards = realm.where(Board.class).findAll().sort("id");
        if(boards.size() == 0){
            realm.beginTransaction();
            for (int i = 0; i < initData.length; i++){
                Board board = realm.createObject(Board.class, i);
                board.setPost(initData[i]);
            }
            realm.commitTransaction();
        }
        adapter = new BoardAdapter(this, boards);
        listView.setAdapter(adapter);
        adapter.setCallback(this);
    }

    private void deleteBoard(long boardId){
        // TODO:レッスンではここにプログラムを追加
        final long id = boardId;
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Board board = realm.where(Board.class).equalTo("id", id).findFirst();
                board.deleteFromRealm();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // TODO:レッスンではここにプログラムを追加
        realm.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_add) {

            // 詳細画面開始
            Intent intent = new Intent(MainActivity.this, EditActivity.class);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void delete(long boardId){
        deleteBoard(boardId);
    }
}
