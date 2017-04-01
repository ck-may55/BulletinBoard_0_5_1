package com.example.chie.bulletinboard_0_5_1;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by chie on 2017/03/29.
 */

public class Board extends RealmObject {


    @PrimaryKey
    public long id;
    public String post;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String name) {
        this.post = name;
    }
}
