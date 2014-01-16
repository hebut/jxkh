package org.iti.gh.common.util;

import java.math.BigDecimal;
import java.util.Hashtable;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ������������ת���ɺ����д����
 * @author sitinspring
 * @date 2008-03-25
 */
public class ChineseUpperCaser{
    /**
     * ���ڴ洢��������
     */
    private String integerPart;
    
    /**
     * ���ڴ洢С������
     */
    private String floatPart;
    
    /**
     * ���ڴ洢0-9��д�Ĺ�ϣ��
     */
    private static final Map<String,String> ZerotoNineHt;
    
    /**
     * ���ڴ洢ʮ��ǧ��д�Ĺ�ϣ��
     */
    private static final Map<Integer,String> thHuTenHt;
    
    
    /**
     * ���ڴ洢�f�|�״�д�Ĺ�ϣ��
     */
    private static final Map<Integer,String> wanYiZhaoHt;
    
    static{
        ZerotoNineHt=new Hashtable<String,String>();
        
        ZerotoNineHt.put("0", "��");
        ZerotoNineHt.put("1", "һ");
        ZerotoNineHt.put("2", "��");
        ZerotoNineHt.put("3", "��");
        ZerotoNineHt.put("4", "��");
        ZerotoNineHt.put("5", "��");
        ZerotoNineHt.put("6", "��");
        ZerotoNineHt.put("7", "��");
        ZerotoNineHt.put("8", "��");
        ZerotoNineHt.put("9", "��");
        
        thHuTenHt=new Hashtable<Integer,String>();
        thHuTenHt.put(0, "");
        thHuTenHt.put(1, "ʮ");
        thHuTenHt.put(2, "��");
        thHuTenHt.put(3, "ǧ");
        
        wanYiZhaoHt=new Hashtable<Integer,String>();
        wanYiZhaoHt.put(0, "");
        wanYiZhaoHt.put(1, "��");
        wanYiZhaoHt.put(2, "��");
        wanYiZhaoHt.put(3, "��");
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
     * ���캯��
     * @param number
     * @throws NumberFormatException
     */
    public ChineseUpperCaser(float number) throws NumberFormatException{
        this(String.valueOf(number));
    }
    
    /**
     * ���캯��
     * @param number
     * @throws NumberFormatException
     */
    public ChineseUpperCaser(double number) throws NumberFormatException{
        this(String.valueOf(number));
    }
    
    /**
     * ���캯��
     * @param number
     * @throws NumberFormatException
     */
    public ChineseUpperCaser(int number) throws NumberFormatException{
        this(String.valueOf(number));
    }
    
    /**
     * ���캯��
     * @param number
     * @throws NumberFormatException
     */
    public ChineseUpperCaser(long number) throws NumberFormatException{
        this(String.valueOf(number));
    }
    
    /**
     * ���캯��
     * @param number
     * @throws NumberFormatException
     */
    public ChineseUpperCaser(String number) throws NumberFormatException{
        String formalNumber=formatNumber(number);
        
        // �ٷ��Ը��������ֺ�С�����ָ�ֵ
        String[] arr=formalNumber.split("[.]");
        if(arr.length==2){
            // ��С����
            integerPart=arr[0];
            floatPart=arr[1];
        }
        else{
            // ��С����
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
     * �õ��������ֵĺ��ִ�д��ʾ
     * @return
     */
    private String parseIntegerPart(){
        String retval="";
        
        // ����������������Ϊ��Ҫ�����ȡ
        String reverseIntegerPart="";
        
        for(int i=integerPart.length()-1;i>-1;i--){
            reverseIntegerPart+=integerPart.charAt(i);
        }
        
        // ���������ְ���λ�ֶ�
        Pattern p = Pattern.compile("\\d{4}",Pattern.CASE_INSENSITIVE);

        Matcher m = p.matcher(reverseIntegerPart);
        StringBuffer sb = new StringBuffer();

        boolean result = m.find();
        while (result) {
            // ÿ�ҵ���λ��һ������
            m.appendReplacement(sb, m.group(0) + ",");
            result = m.find();
        }
        m.appendTail(sb);
        
        // ���������֣��õ���λ�������ݵ�����
        String[] arr=sb.toString().split(",");        
        
        int j;
        String str;
        for(int i=arr.length-1;i>=0;i--){
            String temp=arr[i];

            // ����������ת��д���ּӵ�λ��ǧ��ʮ��
            for(j=temp.length()-1;j>=0;j--){
                str=String.valueOf(temp.charAt(j));
                retval+=ZerotoNineHt.get(str)+thHuTenHt.get(j);
            }
            
            retval=retval.replaceAll("(��)($)", "$2");// ����ĩβ��ȥ��
            // �ӵ�λ��������
            retval+=getWanYiZhao(i);
        }

        // ���滻
        retval=retval.replaceAll("(��[ǧ��ʮ])", "��");
        retval=retval.replaceAll("(��{2,})", "��");
        retval=retval.replaceAll("(��)($)", "$2");// ����ĩβ��ȥ��
        retval=retval.replaceAll("һʮ", "ʮ");
        return retval;
    }

    
    /**
     * �õ�С�����ֵĺ��ִ�д��ʾ
     * @return
     */
    private String parseFloatPart(){
        String retval="��";
        
        for(int i=0;i<floatPart.length();i++){
            String temp=String.valueOf(floatPart.charAt(i));
            
            retval+=ZerotoNineHt.get(temp);
        }
        
        return retval;
    }
    
    /**
     * ��������ַ���������֤���������ת��Ϊ������ʽ���׳�����ת���쳣
     * ��ע������һ������ʱ�쳣(�Ǽ�����쳣)����������ʽ����
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
            System.out.println("���������ֵ��ڣ�"+str+" ��д���ֵ��ڣ�"+new ChineseUpperCaser(str));
        }
    }
}

