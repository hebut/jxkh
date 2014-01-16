package com.uniwin.framework.common.idgenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.PersistentIdentifierGenerator;
import org.hibernate.type.Type;

public class IntIdGenerator implements IdentifierGenerator, Configurable {
	private Integer next;
	private String sql;
	private String col;
	private String table;
	private String sch;

	public Serializable generate(SessionImplementor session, Object arg1) throws HibernateException {
		if (sql != null) {
			getNext(session.connection());
		}
		return next;
	}

	private void getNext(Connection conn) throws HibernateException {
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				next = rs.getInt(1) + 1;
				String update = " update WK_T_ID set KID_VALUE=? where KID_KEY='" + col + "'";
				PreparedStatement st2 = conn.prepareStatement(update);
				st2.setLong(1, next);
				st2.executeUpdate();
			} else {
				String s2 = "select max(" + col + ") from " + (sch == null ? table : sch + '.' + table);
				PreparedStatement st2 = conn.prepareStatement(s2);
				ResultSet rs2 = st2.executeQuery();
				if (rs2.next()) {
					next = rs2.getInt(1) + 1;
				} else {
					next = 1;
				}
				String update = " insert into WK_T_ID(KID_KEY,KID_VALUE) values(?,?)";
				PreparedStatement st3 = conn.prepareStatement(update);
				st3.setString(1, col);
				st3.setLong(2, next);
				st3.executeUpdate();
			}
		} catch (SQLException e) {
			throw new HibernateException(e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new HibernateException(e);
			}
		}
	}

	public void configure(Type type, Properties params, Dialect d) throws MappingException {
		String table = params.getProperty("table");
		if (table == null) {
			table = params.getProperty(PersistentIdentifierGenerator.TABLE);
		}
		String column = params.getProperty("column");
		if (column == null) {
			column = params.getProperty(PersistentIdentifierGenerator.PK);
		}
		String schema = params.getProperty(PersistentIdentifierGenerator.SCHEMA);
		this.col = column;
		this.sch = schema;
		this.table = table;
		//System.out.println("table=" + table + ";column=" + column + ";" + schema);
		sql = "select KID_VALUE from WK_T_ID" + " where KID_KEY='" + column + "'";
	}
}
