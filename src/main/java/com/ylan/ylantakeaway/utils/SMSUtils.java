package com.ylan.ylantakeaway.utils;

import com.cloopen.rest.sdk.BodyType;
import com.cloopen.rest.sdk.CCPRestSmsSDK;

import java.util.HashMap;
import java.util.Random;
import java.util.Set;

/**
 * 容联云短信调用工具类
 *
 * @author by ylan
 * @date 2022-12-23 20:31
 */

public class SMSUtils {

    public static void sendMessage(String phoneNumber, String code) {
        // 生产环境请求地址：app.cloopen.com  请求端口：8883
        String serverIp = "app.cloopen.com";
        String serverPort = "8883";

        // 主账号,登陆云通讯网站后,可在控制台首页看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN 和已创建应用的APPID
        String accountSId = "2c948876853e6a1601853edd1a570003";
        String accountToken = "3f56c18bcbe642b681d73a70b0a36515";
        String appId = "2c948876853e6a1601853eee3c770009";

        CCPRestSmsSDK sdk = new CCPRestSmsSDK();
        sdk.init(serverIp, serverPort);
        sdk.setAccount(accountSId, accountToken);
        sdk.setAppId(appId);
        sdk.setBodyType(BodyType.Type_JSON);

        // 使用一个哈希map来存放手机号、模板ID、模板参数
        HashMap<String, Object> result = sdk.sendTemplateSMS(phoneNumber, "1", new String[]{code, "30分钟"});

        // 返回0000则正常发送，否则返回异常
        if ("000000".equals(result.get("statusCode"))) {
            // 正常返回输出data包体信息（map）
            HashMap<String, Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for (String key : keySet) System.out.println(key + " = " + data.get(key));
        } else {
            //异常返回输出错误码和错误信息
            System.out.println("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
        }

    }

    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {
        // 随机生成6位数验证码
        Integer integer = generateValidateCode(6);
        System.out.println(integer);
        // 发送手机号
        String phoneNumber = "13673330837";
        // 发送
        // sendMessage(phoneNumber, code);
    }

    /**
     * 随机生成验证码
     *
     * @param length 长度为4位或者6位
     * @return
     */
    public static Integer generateValidateCode(int length) {
        Integer code = null;
        if (length == 4) {
            code = new Random().nextInt(9999);//生成随机数，最大为9999
            if (code < 1000) {
                code = code + 1000; //保证随机数为4位数字
            }
        } else if (length == 6) {
            code = new Random().nextInt(999999);//生成随机数，最大为999999
            if (code < 100000) {
                code = code + 100000;//保证随机数为6位数字
            }
        } else {
            throw new RuntimeException("只能生成4位或6位数字验证码");
        }
        return code;
    }

    /**
     * 随机生成指定长度字符串验证码
     *
     * @param length 长度
     * @return
     */
    public static String generateValidateCode4String(int length) {
        Random rdm = new Random();
        String hash = Integer.toHexString(rdm.nextInt());
        return hash.substring(0, length);
    }

}