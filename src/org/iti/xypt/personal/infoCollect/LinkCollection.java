package org.iti.xypt.personal.infoCollect;
/**
 * type 0 ��ȡ����ʼ��ַ
 * type 1 ��ȡ����һҳ��ַ
 * type 2 �ɼ��ķ���ģ�����ַ
 * type 3 �������
 */
import java.util.HashSet;
import java.util.Set;

public class LinkCollection {

	private static Set<String> visitedUrl=new HashSet<String>();

	public synchronized static Set<String> getVisitedUrl() {
		return visitedUrl;
	}
	public synchronized static void setVisitedUrl(Set<String> visitedUrl) {
		LinkCollection.visitedUrl = visitedUrl;
	}
	
	private static Queue<String> unVisitedUrl=new Queue<String>();

	public synchronized static  Queue<String> getUnVisitedUrl() {
		return unVisitedUrl;
	}
	public static synchronized void setUnVisitedUrl(Queue<String> unVisitedUrl) {
		LinkCollection.unVisitedUrl = unVisitedUrl;
	}
	
	public synchronized static  void addVisitedUrl(String url){
		visitedUrl.add(url);
	}
	
	public synchronized static  void removeVisitedUrl(String url){
		visitedUrl.remove(url);
	}
	
	public synchronized static  String unVisitedUrlDeQueue() {
		return unVisitedUrl.delQueue();
	}
	
	// ÿ��url��֤����һ��
	public synchronized static void addUnvisitedUrl(String url,Integer type) {
		if (url != null && !url.trim().equals("")
 && !visitedUrl.contains(url) && !unVisitedUrl.contains(url)){
			
			if(type==0){
				System.out.println("��ʼ��ַ�� "+url);
			}else if(type==1){
				System.out.println("��ȡ��һҳ�ɹ���");
			}else if(type==2){
//				System.out.println("��ȡ����ַ�� "+url);
			}
			unVisitedUrl.enQueue(url);
			
		}
	}
	
	public static int getVisitedUrlNum() {
		return visitedUrl.size();
	}
	
	public static int getunVisitedUrlNum(){
		return unVisitedUrl.queueLength();
	}
	
	public synchronized static boolean unVisitedUrlsEmpty() {
		return unVisitedUrl.isQueueEmpty();
	}
	
}
