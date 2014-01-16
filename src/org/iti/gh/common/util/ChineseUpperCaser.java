package org.iti.gh.common.util;

import java.math.BigDecimal;
import java.util.Hashtable;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 将阿拉伯数字转换成汉语大写数字
 * @author sitinspring
 * @date 2008-03-25
 */
public class ChineseUpperCaser{
    /**
     * 用于存储整数部分
     */
    private String integerPart;
    
    /**
     * 用于存储小数部分
     */
    private String floatPart;
    
    /**
     * 用于存储0-9大写的哈希表
     */
    private static final Map<String,String> ZerotoNineHt;
    
    /**
     * 用于存储十百千大写的哈希表
     */
    private static final Map<Integer,String> thHuTenHt;
    
    
    /**
     * 用于存储萬億兆大写的哈希表
     */
    private static final Map<Integer,String> wanYiZhaoHt;
    
    static{
        ZerotoNineHt=new Hashtable<String,String>();
        
        ZerotoNineHt.put("0", "零");
        ZerotoNineHt.put("1", "一");
        ZerotoNineHt.put("2", "二");
        ZerotoNineHt.put("3", "三");
        ZerotoNineHt.put("4", "四");
        ZerotoNineHt.put("5", "五");
        ZerotoNineHt.put("6", "六");
        ZerotoNineHt.put("7", "七");
        ZerotoNineHt.put("8", "八");
        ZerotoNineHt.put("9", "九");
        
        thHuTenHt=new Hashtable<Integer,String>();
        thHuTenHt.put(0, "");
        thHuTenHt.put(1, "十");
        thHuTenHt.put(2, "百");
        thHuTenHt.put(3, "千");
        
        wanYiZhaoHt=new Hashtable<Integer,String>();
        wanYiZhaoHt.put(0, "");
        wanYiZhaoHt.put(1, "万");
        wanYiZhaoHt.put(2, "亿");
        wanYiZhaoHt.put(3, "兆");
    }
    
    
    private static String getWanYiZhao(int level){
        String retval="";
        
        do{
            retval+=wanYiZhaoHt.get(level % 4);
            level-=3;
        }while(level>3);
        
        return retval;
    }
    
    /**
     * 构造函数
     * @param number
     * @throws NumberFormatException
     */
    public ChineseUpperCaser(float number) throws NumberFormatException{
        this(String.valueOf(number));
    }
    
    /**
     * 构造函数
     * @param number
     * @throws NumberFormatException
     */
    public ChineseUpperCaser(double number) throws NumberFormatException{
        this(String.valueOf(number));
    }
    
    /**
     * 构造函数
     * @param number
     * @throws NumberFormatException
     */
    public ChineseUpperCaser(int number) throws NumberFormatException{
        this(String.valueOf(number));
    }
    
    /**
     * 构造函数
     * @param number
     * @throws NumberFormatException
     */
    public ChineseUpperCaser(long number) throws NumberFormatException{
        this(String.valueOf(number));
    }
    
    /**
     * 构造函数
     * @param number
     * @throws NumberFormatException
     */
    public ChineseUpperCaser(String number) throws NumberFormatException{
        String formalNumber=formatNumber(number);
        
        // 辟分以给整数部分和小数部分赋值
        String[] arr=formalNumber.split("[.]");
        if(arr.length==2){
            // 有小数点
            integerPart=arr[0];
            floatPart=arr[1];
        }
        else{
            // 无小数点
            integerPart=arr[0];
        }
    }
    
    public String toString(){
        String retval="";
        
        if(integerPart!=null){
            retval+=parseIntegerPart();
        }
        
        if(floatPart!=null){
            retval+=parseFloatPart();
        }
                
        return retval;
    }
    
    /**
     * 得到整数部分的汉字大写表示
     * @return
     */
    private String parseIntegerPart(){
        String retval="";
        
        // 将整数部分逆序，因为需要反向读取
        String reverseIntegerPart="";
        
        for(int i=integerPart.length()-1;i>-1;i--){
            reverseIntegerPart+=integerPart.charAt(i);
        }
        
        // 将整数部分按四位分段
        Pattern p = Pattern.compile("\\d{4}",Pattern.CASE_INSENSITIVE);

        Matcher m = p.matcher(reverseIntegerPart);
        StringBuffer sb = new StringBuffer();

        boolean result = m.find();
        while (result) {
            // 每找到四位放一个逗号
            m.appendReplacement(sb, m.group(0) + ",");
            result = m.find();
        }
        m.appendTail(sb);
        
        // 按逗号劈分，得到四位分组数据的数组
        String[] arr=sb.toString().split(",");        
        
        int j;
        String str;
        for(int i=arr.length-1;i>=0;i--){
            String temp=arr[i];

            // 阿拉伯数字转大写汉字加单位（千百十）
            for(j=temp.length()-1;j>=0;j--){
                str=String.valueOf(temp.charAt(j));
                retval+=ZerotoNineHt.get(str)+thHuTenHt.get(j);
            }
            
            retval=retval.replaceAll("(零)($)", "$2");// 零在末尾则去掉
            // 加单位（兆亿万）
            retval+=getWanYiZhao(i);
        }

        // 零替换
        retval=retval.replaceAll("(零[千百十])", "零");
        retval=retval.replaceAll("(零{2,})", "零");
        retval=retval.replaceAll("(零)($)", "$2");// 零在末尾则去掉
        retval=retval.replaceAll("一十", "十");
        return retval;
    }

    
    /**
     * 得到小数部分的汉字大写表示
     * @return
     */
    private String parseFloatPart(){
        String retval="点";
        
        for(int i=0;i<floatPart.length();i++){
            String temp=String.valueOf(floatPart.charAt(i));
            
            retval+=ZerotoNineHt.get(temp);
        }
        
        return retval;
    }
    
    /**
     * 对输入的字符串进行验证，如果不能转化为数字形式则抛出数字转化异常
     * ，注意这是一个运行时异常(非检查型异常)，程序不用显式捕获
     * @param number
     * @throws NumberFormatException
     */
    private String formatNumber(String number) throws NumberFormatException{        
        return (new BigDecimal(number)).toString();        
    }
    
    public static void main(String[] args){
        String[] arr={"1","10","11","20","1.543524304302432","12.432423432","123.454235","1234","12345","123456","1234567",
"12345678","123456789","1234567891","12345678912","123456789123","1234567891234","12345678912345",
"123456789123456","1234567891234567","12345678912345678","123456789123456789",
"123456789123456789123456089123456789123456789123450780","0","00","000","0000","01","001","0001",
"00001","10","100","1000","10000","101","1001","10001","100001","1.23","21.234","243400031.233234",
"5400035.980","543.6545"};
        //String[] arr={"0","00","000","0000","01","001","0001","00001","10","100","1000","10000","101","1001","10001","100001"};
        //String[] arr={"1.23","21.234","243400031.233234","5400035.980","543.6545"};
        for(String str:arr){
            System.out.println("阿拉伯数字等于："+str+" 大写汉字等于："+new ChineseUpperCaser(str));
        }
    }
}

