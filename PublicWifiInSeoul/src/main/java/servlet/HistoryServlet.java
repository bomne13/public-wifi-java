package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.HistoryController;

@WebServlet("/deleteHistory")
public class HistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HistoryController controller = new HistoryController();
	
    public HistoryServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int historyNo = Integer.parseInt(request.getParameter("historyNo"));
		
		System.out.println("/deleteHistory:: deleteHistoryNo = " + historyNo);
		
		String result = controller.deleteHistory(historyNo);
		request.setAttribute("deleteResult", result);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("history.jsp");
		dispatcher.forward(request, response);
	}

}
