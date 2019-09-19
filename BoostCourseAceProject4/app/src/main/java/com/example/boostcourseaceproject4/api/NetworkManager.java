package com.example.boostcourseaceproject4.api;

import com.android.volley.RequestQueue;

public class NetworkRequestHelper {
    public static RequestQueue requestQueue;
    public static String host = "boostcourse-appapi.connect.or.kr";
    public static int port = 10000;
    //먼저 요청(Request) 객체를 만들고 이 요청 객체를 요청 큐(RequestQueue)라는 곳에 넣어주기만 하면 됩니다.
    //그러면 요청 큐가 알아서 웹서버에 요청하고 응답까지 받아 사용자가 사용할 수 있도록 지정된 메소드를 호출해줍니다.
}
