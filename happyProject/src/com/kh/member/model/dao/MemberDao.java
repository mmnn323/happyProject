package com.kh.member.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import static com.kh.common.JDBCTemplate.*;
import com.kh.member.model.vo.Member;

public class MemberDao {
	
	private Properties prop = new Properties();
	
	// 기본생성자
	public MemberDao() {
		
		String fileName = MemberDao.class.getResource("/sql/member/member-mapper.xml").getPath();
		
		try {
			prop.loadFromXML(new FileInputStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public Member loginMember(Connection conn, String userId, String userPwd) {
		// select문 => ResultSet객체 (한행) => Member객체
		Member m = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("loginMember"); // 미완성된 sql문
		
		try {
			pstmt = conn.prepareStatement(sql); // 미완성된sql문 담음
			pstmt.setString(1, userId);
			pstmt.setString(2, userPwd);
			
			// 실행
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				m = new Member(rset.getInt("mem_no"),
							   rset.getString("mem_id"),
							   rset.getString("mem_pwd"),
							   rset.getString("mem_name"),
							   rset.getString("mem_address"),
							   rset.getString("mem_phone"),
							   rset.getString("email"),
							   rset.getString("email_yn"),
							   rset.getString("enroll_route"),
							   rset.getDate("enroll_date"),
							   rset.getDate("recent_date"),
							   rset.getString("admin_yn"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return m;
	}
	

}











