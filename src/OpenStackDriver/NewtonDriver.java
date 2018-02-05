package OpenStackDriver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
/**
 * Servlet implementation class NewtonDriver
 */
@WebServlet("/NewtonDriver")
public class NewtonDriver extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private DriverRepo driverRepo; 
    private NewtonHttpClient OpenStackHttpClient;
    private OpenStackTimer openstackTimer;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewtonDriver() {
        super();
        driverRepo = new DriverRepo(); 
        this.OpenStackHttpClient = new NewtonHttpClient();
        
        long gap = 1000;
        this.openstackTimer = new OpenStackTimer(gap);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream(),"utf-8"));
			StringBuffer stringBuffer = new StringBuffer();
			String tmp;
			while((tmp = br.readLine()) != null) {
				stringBuffer.append(tmp);
			}
			br.close();
			String acceptJSON = stringBuffer.toString();
			JSONObject monitorObject  = JSONObject.fromObject(acceptJSON);
			String serverId = monitorObject.getString("serverId");
			String type = monitorObject.getString("type");
			String item = monitorObject.getString("item");
			response.getWriter().write("happy");
		}catch(Exception e){
			e.printStackTrace();
			response.getWriter().write("sad");
		}
	}
	public void copy(String serverId, String type, String item) {
		if(!this.driverRepo.containServer(serverId)) {
			this.driverRepo.containServer(serverId);
		}
		if(this.driverRepo.getItemValue(serverId, type, item).equals("null")) {
			//means the timer is not set
			this.openstackTimer.addTimer(serverId);
		}
	}

}
