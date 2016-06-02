package guitarServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.IGuitarDao;
import DAOImpl.DaoImpl;
import guitar.Guitar;
import guitar.GuitarSpec;



/**
 * Servlet implementation class SearchGuitar
 */
@WebServlet("/SearchGuitar")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/x-json, charset=utf-8");   
		String j = "{\"success\": true, \"row\": [";
		
		PrintWriter out = response.getWriter();
		
		String builder = request.getParameter("builder");
		String type = request.getParameter("type");
		String backWood = request.getParameter("backWood");
		String topWood = request.getParameter("topWood");
		String model = request.getParameter("model");
		int numStrings = Integer.parseInt(request.getParameter("numStrings"));		
		
		GuitarSpec specSearch = new GuitarSpec(builder, model, type, numStrings, backWood, topWood);
		IGuitarDao inv = new DaoImpl();
		List<Guitar> guitars = inv.searchGuitar(specSearch);
		if(! guitars.isEmpty()) {
			for(Guitar guitar : guitars) {
				GuitarSpec spec = guitar.getSpec();
				j += "{";
				j += "\"serialNumber\":\"" + guitar.getSerialNumber() + "\", ";
				j += "\"builder\":\"" + spec.getBuilder() + "\", ";
				j += "\"model\":\"" + spec.getModel() + "\", ";
				j += "\"stringNumber\":\"" + spec.getNumStrings() + "\", ";
				j += "\"type\":\"" + spec.getType() +"\", ";
				j += "\"backWood\":\"" + spec.getBackWood() + "\", ";
				j += "\"topWood\":\"" + spec.getTopWood() + "\", ";
				j += "\"price\":" + guitar.getPrice() + "}";
				j += ", ";				
			}
		}
		
		if (j != "{\"success\": true, \"row\": [") {
			j = j.substring(0, j.length() - 2);
			j += "]}";
		} else {
			j = "{\"success\": false, \"row\": [{\"Tips\": \"查询失败！\"}]}";
		}
		
		out.print(j);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
