package com.system.controller.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// 日期类型协助
public class CustomDateConverter implements Converter<String, Date> {


    public Date convert(String s) {
        // 将日期串转成日期类型（格式YYYY-MM-DD）
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            // 转换成功
            return simpleDateFormat.parse(s);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // 转换失败返回null
        return null;
    }
}
