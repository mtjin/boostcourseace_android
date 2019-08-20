package com.example.boostcourseaceproject4.model;

import java.util.ArrayList;

/*"result": [
        {
        "id": 3445,
        "writer": "sjh",
        "movieId": 1,
        "writer_image": null,
        "time": "2019-03-23 20:59:12",
        "timestamp": 1553342351,
        "rating": 4.4,
        "contents": "헉 넘나 재밌어용하하하",
        "recommend": 0
        },*/
public class CommentList{
    public String message; //다른데서도 사용할 수 있게 public을 붙여줌
    public int code;
    public String resultType;
    public int totalCount;
    //
    public ArrayList<Comment> result = new ArrayList<Comment>();
}
