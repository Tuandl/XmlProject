/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.w3c.dom.Document;
import xml.dto.OrderDTO;
import xml.service.OrderService;
import xml.utils.FopUtils;
import xml.utils.XMLUtils;

/**
 *
 * @author admin
 */
@WebServlet(name = "OrderReceiptServlet", urlPatterns = {"/Order/Receipt"})
public class OrderReceiptServlet extends HttpServlet {
    private final String ORDER_RECEIPT_XSL_PATH = "xsl\\order.receipt.xsl";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderService orderService = new OrderService();
        resp.setContentType("application/pdf");

        try {
            String orderCode = req.getParameter("orderCode");
            if(orderCode == null || orderCode.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }
            
            OrderDTO orderDto = orderService.getOrder(orderCode);
            if(orderDto == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            
            Document dom = XMLUtils.marshallToDom(orderDto);
            if(dom == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                return;
            }
            
            ServletContext context = req.getServletContext();
            String xslPath = context.getRealPath("/") + ORDER_RECEIPT_XSL_PATH;
//            System.out.println(xslPath);
            OutputStream out = resp.getOutputStream();
            
            String title = "Invoice " + orderCode;
            FopUtils.transformDomToPdf(dom, xslPath, out, context.getRealPath("/"),
                    title);
            
            out.flush();
            out.close();
            
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    
}
