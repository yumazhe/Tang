package com.tang;

import com.tang.core.config.TangConstant;
import com.tang.core.utils.StringUtil;
import org.junit.Test;

import java.util.List;

/**
 * Created by yuma on 2020/2/11.
 */
public class StringUtilTest {

    @Test
    public void split() {
        String content = "123456789123456789qazwsxedcfrvtgbyhnmjuik8olp";
        List<String> datas = StringUtil.split(content, 4);
        for (String data : datas) {
            System.out.println(data);
        }
    }

    @Test
    public void format() {
        String data = StringUtil.formatByFillZero(1242394892, 6);
        System.out.println(data);
    }
}
