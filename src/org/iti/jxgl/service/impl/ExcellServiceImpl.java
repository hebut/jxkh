package org.iti.jxgl.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Session;
import org.iti.jxgl.service.ExcellService;

import com.uniwin.basehs.service.impl.BaseServiceImpl;

public class ExcellServiceImpl extends BaseServiceImpl implements ExcellService {
	public String changeweekToweeks(String week) {
		int[] weeks = new int[25];// �ܴι̶���Ϊ25��,���������weeks�У����������ܸ�ֵΪ1
		for (int k = 0; k < 25; k++) { // Ϊ���鸳��ֵ0
			weeks[k] = 0;
		}

		if (week.indexOf("��") != -1) {// �����ʽΪ1��2-12��13����
			String[] w3 = week.split("��"); // ���ԡ��ܡ��ָ�
			if (w3[0].indexOf(",") != -1) {// 1��3-6��8��17����ʽ
				String[] w1 = w3[0].split(","); // �ԡ������ָ�
				for (int x = 0; x < w1.length; x++) {
					if (w1[x].indexOf("-") != -1) {
						String[] w2 = w1[x].split("-"); // ���ԡ�-���ָ�
						for (int y = (Integer.parseInt(w2[0])); y <= (Integer.parseInt(w2[1])); y++) {
							weeks[y] += 1;
						}
					} else {

						if (w1[x].indexOf("(") != -1) {
							String[] w5 = w1[x].split("\\("); // ���ԡ�(���ָ�
							weeks[Integer.parseInt(w5[0])] += 1;
						} else
							weeks[Integer.parseInt(w1[x])] += 1;
					}
				}// end for(int x=0;x<w1.length;x++)
			}// end if(w3[m].indexOf(",")!=-1)
			else { // û�С�������ʽ
				if (w3[0].indexOf("-") != -1) {// 1-16����ʽ
					String[] w4 = w3[0].split("-");
					for (int n = (Integer.parseInt(w4[0])); n <= (Integer.parseInt(w4[1])); n++) {
						weeks[n] += 1;
					}
				} else
					weeks[Integer.parseInt(w3[0])] += 1; // ֻ��16��һ������ʽ
			}
		}// end if(array[i][j].indexOf("��")!=-1)
		else {// �������ܡ��ֵ�
			if (week.indexOf(",") != -1) {// 1��3-6��8��17��ʽ
				String[] w1 = week.split(","); // �ԡ������ָ�
				for (int x = 0; x < w1.length; x++) {
					if (w1[x].indexOf("-") != -1) {
						String[] w2 = w1[x].split("-"); // ���ԡ�-���ָ�
						for (int y = (Integer.parseInt(w2[0])); y <= (Integer.parseInt(w2[1])); y++) {
							weeks[y] += 1;
						}
					} else {
						if (w1[x].indexOf("(") != -1) {
							String[] w5 = w1[x].split("\\("); // ���ԡ�(���ָ�
							weeks[Integer.parseInt(w5[0])] += 1;
						} else {
							weeks[Integer.parseInt(w1[x])] += 1;
						}
					}
				}// end for(int x=0;x<w1.length;x++)
			}// end if(w3[m].indexOf(",")!=-1)
			else { // û�С�������ʽ
				if (week.indexOf("-") != -1) {// 1-16��ʽ
					String[] w4 = week.split("-");
					for (int n = (Integer.parseInt(w4[0])); n <= (Integer.parseInt(w4[1])); n++) {
						weeks[n] += 1;
					}
				} else {
					weeks[Integer.parseInt(week)] += 1; // ֻ��16һ������ʽ
				}
			}
		}// end �������ܡ��ֵ�
		String str = "";
		for (int z = 0; z < 25; z++) {
			str += weeks[z];
		}

		return str;

	}

	public Integer findMaxSumstate() {
		String queryString = "select Max(jtSumstate)from JxTask";
		return (Integer) getHibernateTemplate().find(queryString).get(0);
	}
	
	public Integer findStudentMaxSumstate() {
		String queryString = "select Max(jtSumstate)from JxStudenttask";
		return (Integer) getHibernateTemplate().find(queryString).get(0);
	}


	// �жϸ�ʽ�Ƿ���ϵĺ�������ȷ����1�����󷵻�0
	public String is_format(String filename, List btlist) throws FileNotFoundException, IOException {
		String is_format = "0";
		// ������Excel�������ļ�������
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filename));
		// �����Թ���������á�
		HSSFSheet sheet = workbook.getSheetAt(0);// ��һ�Ź�����
		HSSFRow row = sheet.getRow(sheet.getFirstRowNum());
		String sheetname = new String(workbook.getSheetName(0).getBytes("UTF-8"));
		int columns = row.getLastCellNum(); // ����ÿ�е�����
		//System.out.println("col"+columns+"blist"+btlist.size());
		String array[] = new String[columns]; // ����һ����ŵ�һ���ֶε�����
		int i, j;

		for (i = 0, j = 0; j < columns; j++) { // ��ѭ�� for2
			row = sheet.getRow(i);
			if (row == null) {
				array[j] = null;
				continue;
			}
			HSSFCell cell = row.getCell((short) j); // ȡ�õ�Ԫ���λ��
			if (cell != null) {
				switch (cell.getCellType()) { // ��ͬ���͵��ò�ͬ�ĺ���
				case HSSFCell.CELL_TYPE_NUMERIC:
					array[j] = String.valueOf(cell.getNumericCellValue()).trim().replaceAll(" ", "");
					;
					break;
				default:
					array[j] = cell.getStringCellValue().trim().replaceAll(" ", "");
					;
				}
			} // end if(cell!=null)
		//	System.out.println("col"+array[j]);
		}// end for(i=0;j<columns;j++)
//
//		if (!"sheet1".equalsIgnoreCase(sheetname)) {
//			is_format = "0"; // ��ʽ����ȷ
//			return is_format;
//		} else {
			// System.out.println("�б�=="+columns);
			for (int t = 0; t < columns; t++) {
				if(array[t]!=null){
					//System.out.println("t="+t+"m"+array[t]);
					if (!btlist.get(t).toString().equalsIgnoreCase(array[t])) {						
						is_format = "0";
						return is_format;
					}
					
				}	
			}
			is_format = "1";
			return is_format;
		//}

	}

	public void deletetemptask() {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		String queryString = "delete from jximporttask";
		session.createSQLQuery(queryString).executeUpdate();
		session.close();
	}

	public void deletetempSche(Long kdid) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		String queryString = "delete from Jx_ImportSche where kdid='"+kdid+"'";
		session.createSQLQuery(queryString).executeUpdate();
		session.close();
	}

	// /////////�����ӵ�
	public void deletetemptask2() {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		String queryString = "delete from jximporttask2";
		session.createSQLQuery(queryString).executeUpdate();
		session.close();
	}

	public List findTemptask2Bykdid(Long kdid) {
		String queryString = "from Jximporttask2 as temp3 where  temp3.��λ���=?";
		return getHibernateTemplate().find(queryString, new Object[] { kdid });
	}

	public List findTemptaskBykdid(Long kdid) {
		String queryString = "from Jximporttask3 as temp3 where  temp3.��λ���=?";
		return getHibernateTemplate().find(queryString, new Object[] { kdid });
	}

	public List findSumByYearAndTermAndkdidAndCourseid(String year, short term, Long cid, Long courseid) {
		StringBuffer queryString = new StringBuffer("from JxTask as task where task.jtYear=? and task.jtTerm=? and task.kdId=? ");
		if (courseid != 0) {
			queryString.append("and task.jcId=?");
		} else {
			queryString.append("and task.jcId<>?");
		}
		queryString.append(" and task.jtSumstate<>0 order by task.jtSumstate, task.jcId");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { year, term, cid, courseid });
	}

	public List findNoSumByYearAndTermAndkdidAndCourseid(String year, short term, Long cid, Long courseid) {
		StringBuffer queryString = new StringBuffer("from JxTask as task where task.jtYear=? and task.jtTerm=? and task.kdId=? ");
		if (courseid != 0) {
			queryString.append("and task.jcId=?");
		} else {
			queryString.append("and task.jcId<>?");
		}
		queryString.append(" and task.jtSumstate=0 order by task.jcId");
		return getHibernateTemplate().find(queryString.toString(), new Object[] { year, term, cid, courseid });
	}
	
	 public List findTemptask4Bykdid(Long kdid){
		 String queryString = "from Jximporttask4 as temp4 where  temp4.��λ���=?";
			return getHibernateTemplate().find(queryString, new Object[] { kdid });
	 }
	 
	 public List findCoursefromTaskByfromid(String year,short term,Long kdid,Long fromid){
		 String queryString = "from JxStudenttask as task where task.jtYear=? and task.jtTerm=? and task.kdId=? and task.jcId in " +
		 		"(select distinct c.jcId from JxCourse as c where c.jcFromkdid=? ) order by task.jcId" ;
			return getHibernateTemplate().find(queryString.toString(), new Object[] { year, term, kdid, fromid});
	 }
	 
	 public List FindTaskByYTK(String year, short term, Long kdid) {
			String queryString = "from JxStudenttask as task where task.jtYear=? and task.jtTerm=? and task.kdId=?";
			return getHibernateTemplate().find(queryString, new Object[] { year, term, kdid });
		}
	 public List FindCourseFromTask(String year, short term, Long kdid) {
			String queryString = "select distinct task.jcId from JxStudenttask as task where task.jtYear=? and task.jtTerm=? and task.kdId=? order by task.jcId";
			return getHibernateTemplate().find(queryString, new Object[] { year, term, kdid });
		}
	 
	 public List findSum(String year, short term, Long kdid,Long fromid) {
		 String queryString = "from JxStudenttask as task where task.jtYear=? and task.jtTerm=? and task.kdId=? and task.jcId in " +
	 		"(select distinct c.jcId from JxCourse as c where c.jcFromkdid=? )"  +
		 		" and task.jtSumstate<>0 order by task.jtSumstate, task.jcId";
			return getHibernateTemplate().find(queryString.toString(), new Object[] { year, term, kdid, fromid });
		}
	 
	 public List findNotSum(String year, short term, Long kdid,Long fromid) {
		 String queryString = "from JxStudenttask as task where task.jtYear=? and task.jtTerm=? and task.kdId=? and task.jcId in " +
	 		"(select distinct c.jcId from JxCourse as c where c.jcFromkdid=? )" +
		 		" and task.jtSumstate=0";
			return getHibernateTemplate().find(queryString.toString(), new Object[] { year, term, kdid, fromid });
		}
	 
	 public List FindTaskBySumState(int sumstate) {
			String queryString = "from JxStudenttask as task where task.jtSumstate=?";
			return getHibernateTemplate().find(queryString, new Object[] { sumstate });
		}
	 public List Findbkmd(Integer year,short term,Long kdid){
		 String queryString="from JxMakeupexam as jm where jm.jmYear=? and jm.jmTerm=? and jm.kdId=? ";
		 return getHibernateTemplate().find(queryString, new Object[] { year,term,kdid });
	 }
		public List FindbyYTKD(String year,short term,Long kdid){
			String queryString="from JxXxcourse as jx where jx.jxYear=? and jx.jxTerm=? and jx.kdId=? ";
			 return getHibernateTemplate().find(queryString, new Object[] { year,term,kdid });
		}
		public List FindbyYTSidCname(Integer year,short term,String sid,String cname){
			String queryString="from JxMakeupexam as jm where jm.jmYear=? and jm.jmTerm=? and jm.stId=? and jm.jcName=? ";
			 return getHibernateTemplate().find(queryString, new Object[] { year,term,sid,cname });	
		}	
}
