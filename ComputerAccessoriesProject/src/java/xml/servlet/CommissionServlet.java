/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import xml.service.AppSettingService;
import xml.utils.StringUtils;

/**
 *
 * @author admin
 */
@WebServlet(name = "CommissionServlet", urlPatterns = {"/AppSetting/Commission"})
public class CommissionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AppSettingService appSettingService = new AppSettingService();
        try (PrintWriter out = resp.getWriter()){
            int commission = appSettingService.getCommission();
            out.append(commission + "");
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AppSettingService appSettingService = new AppSettingService();
        try {
            String commissionStr = req.getParameter("commission");
            if(commissionStr == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            
            Integer commission = StringUtils.parseStringToInt(commissionStr);
            if(commission == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            
            appSettingService.saveCommission(commission);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
}
