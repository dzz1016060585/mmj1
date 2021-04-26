package com.dzz.transfer.util;

import com.dzz.transfer.common.Constant;
import org.apache.commons.codec.digest.DigestUtils;


/**
 *MD5工具
 */
public class MD5Utils {
    public static String getMD5Str(String strValue){
        char[] ca=strValue.toCharArray();
        for (int i = 0; i < ca.length; i++) {
            ca[i]=(char)(ca[i]+ Constant.SALT);
        }
        String str = new String(ca);
        String target = DigestUtils.md5Hex(str);
        return target;
    }

    public static void main(String[] args) {
        String md5 = getMD5Str("1234");
        System.out.println(md5);
    }
}
