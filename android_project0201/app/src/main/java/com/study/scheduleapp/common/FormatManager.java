package com.study.scheduleapp.common;
//각종 통화, 문자열 자리수 처리 등등의 공통 기능 처리..
public class FormatManager {

        //일의 자리수 앞에 0 붙이기
        public static String getNumberString(int num){
            String str = Integer.toString(num);
            if(num < 10){//일의 자리수 일경우
                str  = "0"+num;
            }
            return str;
        }


}
