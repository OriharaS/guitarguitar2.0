package DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import DAO.IGuitarDao;
import DB.DBUtil;
import guitar.Guitar;
import guitar.GuitarSpec;

public class DaoImpl implements IGuitarDao {
	
	@Override
	public void addGuitar(String serialNumber, String builder, String model, int numStrings,String type, String backWood,
			String topWood, String price) {
		Connection conn = DBUtil.getConnection();
		String sql = "insert into Guitar(serialNumber,builder,model,numStrings,type,backWood,topWood,price) values(?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, serialNumber);
			pstmt.setString(2, builder);
			pstmt.setString(3, model);
			pstmt.setInt(4, numStrings);
			pstmt.setString(5, type);
			pstmt.setString(6, backWood);
			pstmt.setString(7, topWood);
			pstmt.setString(8, price);
			pstmt.executeUpdate();
			conn.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	public List<Guitar> getGuitars() {
		List<Guitar> guitars = new LinkedList<Guitar>();
		Connection conn = DBUtil.getConnection();
		String sql = "Select * From guitar";
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String serialNumber = rs.getString("serialNumber");
				Double price = rs.getDouble("price");
				String builder = rs.getString("builder");
				String model = rs.getString("model");
				int numSprings = rs.getInt("numStrings");
				String type = rs.getString("type");
				String backWood = rs.getString("backWood");
				String topWood = rs.getString("topWood");
				GuitarSpec spec = new GuitarSpec(builder, model, type, numSprings, backWood, topWood);
				Guitar guitar = new Guitar(serialNumber, price, spec);
				guitars.add(guitar);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return guitars;
	}	

	public List<Guitar> searchGuitar(GuitarSpec searchGuitar) {
		IGuitarDao igd = new DaoImpl();
		List<Guitar> guitars = igd.getGuitars();
	    List<Guitar> matchingGuitars = new LinkedList<Guitar>();
	    for (Iterator<Guitar> i = guitars.iterator(); i.hasNext(); ) {
	      Guitar guitar = (Guitar)i.next();
	      if (guitar.getSpec().matches(searchGuitar))
	        matchingGuitars.add(guitar);
	    }
	    return matchingGuitars;
	  }
}
