package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.WifiController;
import model.WifiInfo;

@WebServlet("/getNearWifi")
public class WifiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private WifiController controller = new WifiController();
       
    public WifiServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		double latitude = Double.parseDouble(request.getParameter("latitude"));
		double longitude = Double.parseDouble(request.getParameter("longitude"));
		
		System.out.println("/getNearWifi:: latitude = " + latitude + ", longitude = " + longitude);
		
		List<WifiInfo> wifiList = controller.getNearWifiList(latitude, longitude);
		request.setAttribute("latitude", Double.toString(latitude));
		request.setAttribute("longitude", Double.toString(longitude));
        request.setAttribute("wifiList", wifiList);
        
		RequestDispatcher dispatcher = request.getRequestDispatcher("main.jsp");
		dispatcher.forward(request, response);
	}

}
