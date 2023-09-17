package com.varchar.biz.category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository("categoryDAO")
public class CategoryDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	static final private String SQL_SELECTALL = "SELECT CATEGORY_NUM, CATEGORY_NAME FROM CATEGORY ";
	
	static final private String SQL_SELECTALL_CHART = "SELECT c.CATEGORY_NUM, SUM(bd.BUY_CNT) AS BUY_CNT, MAX(c.CATEGORY_NAME) AS CATEGORY_NAME "
			+ "FROM BUY_DETAIL bd JOIN TEA t ON bd.TEA_NUM = t.TEA_NUM "
			+ "JOIN CATEGORY c ON c.CATEGORY_NUM = t.CATEGORY_NUM "
			+ "GROUP BY c.CATEGORY_NUM "
			+ "ORDER BY c.CATEGORY_NUM ";
	
	
	static final private String SQL_INSERT = "INSERT INTO CATEGORY(CATEGORY_NUM, CATEGORY_NAME) "
											+ "VALUES ((SELECT NVL(MAX(CATEGORY_NUM), O) + 1 FROM CATEGORY), ?)";
	// static final private String SQL_UPDATE = "";
	// static final private String SQL_DELETE = "";
	
	
	public List<CategoryVO> selectAll(CategoryVO categoryVO) {		
		Object[] args = { };
		return jdbcTemplate.query(SQL_SELECTALL_CHART, args, new CategoryRowMapper());
	}
	
	public CategoryVO selectOne(CategoryVO categoryVO) {
		return null;
	}
	
	public boolean insert(CategoryVO categoryVO) {
		
		int result = jdbcTemplate.update(SQL_INSERT, categoryVO.getCategoryName());
		
		if(result <= 0) {
			return false;
		}
		return true;
	}
	
	public boolean update(CategoryVO categoryVO) {
		return false;
	}
	
	public boolean delete(CategoryVO categoryVO) {
		return false;
	}
}

//-----------------------------------------------------------------------

//[ selectAll ]
class CategoryRowMapper implements RowMapper<CategoryVO> { 

	@Override
	public CategoryVO mapRow(ResultSet rs, int rowNum) throws SQLException { 
		
		CategoryVO data = new CategoryVO();
		data.setCategoryNum(rs.getInt("CATEGORY_NUM"));
		data.setCategoryName(rs.getString("CATEGORY_NAME"));
		data.setBuyCnt(rs.getInt("BUY_CNT"));
		return data;
	}
}
