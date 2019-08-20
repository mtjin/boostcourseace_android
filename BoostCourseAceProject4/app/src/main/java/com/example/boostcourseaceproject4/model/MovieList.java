package com.example.boostcourseaceproject4.model;

import java.util.ArrayList;
/*
"message": "movie readMovieList 성공",
"code": 200,
"resultType": "list",
"result":
* */
public class MovieList{
    public String message; //다른데서도 사용할 수 있게 public을 붙여줌
    public int code;
    public String resultType;
    //result
    public ArrayList<Movie> result = new ArrayList<Movie>();
}
