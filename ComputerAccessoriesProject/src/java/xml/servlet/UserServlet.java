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
import xml.dto.UserDTO;
import xml.model.User;
import xml.service.UserService;
import xml.utils.XMLUtils;

/**
 *
 * @author admin
 */
@WebServlet(name = "UserServlet", urlPatterns = {"/UserServlet"})
public class UserServlet extends HttpServlet {

    private final UserService userService;
    
    public UserServlet() {
        super();
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (PrintWriter out = resp.getWriter()) {
            String username = req.getParameter("username");
            
            if(username == null) {
                //Not support get all username
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.append("Bad request.");
            } else {
                UserDTO user = userService.getUserByUsername(username);
                
                user.setFullname("<p><span style=\"font-size: 10pt; color: #3366ff;\"><strong>Dahua IPC-HFW4239TP-ASE</strong></span> là dòng sản phẩm camera IP Eco-Savvy Full-Color Starlight hỗ trợ ePoE mới nhất của Dahua, có độ phân giải 2.0 megapixel, hỗ trợ chống ngược sáng thực WDR(120dB) và công nghệ Starlight cho hình ảnh có màu trong môi trường ánh sáng cực thấp phù hợp lắp đặt cho nhà xưởng, kho bãi, bãi giữ xe, vv.....</p><p><span style=\"color: #3366ff;\"><strong>Ưu điểm của camera công nghệ ePoE của Dahua</strong></span></p><ul><li>Giải pháp bổ xung cho điểm yếu của camera IP truyền thống là không đi dây cap mạng được xa, với dòng công nghệ mới ePoE khắc phục được vấn đề trên, chúng có khả năng truyền dẫn tín hiệu, 800m với băng thông 10Mbps và 300m với băng thông 100Mbps.</li><li>ePoE còn hỗ trợ truyền dẫn tín hiệu số của CAM IP qua cáp đồng trục, giảm rất nhiều chi phí đối với khách hàng có nhu cầu cải tạo hệ thống giám sát dựa trên dây cáp đồng trục trong hệ thông cũ.</li><li>Khả năng phân tích hình ảnh và cung cấp các tính năng thông minh, hữu ích</li><li>Công nghệ Startlight mang đến hình ảnh rõ nét trong các điều kiện thiếu sáng</li></ul><p><span style=\"color: #3366ff;\"><strong>Thông số kỹ thuật camera IP 2MP Dahua IPC-HFW5231EP-ZE</strong></span></p><ul><li>Camera IP công nghệ ePoE thân hồng ngoại chất lượng cao</li><li>Cảm biến 1/2.8” 2.0megapixel STARVIS™ CMOS</li><li><strong>Hỗ trợ mã hóa 3 luồng</strong> với định dạng H.265 và H.264, 50/60fps@1080P(1920×1080)</li><li>Tiêu cự 3.6mm (Có thể lựa chọn 6mm)</li><li>Hỗ trợ các tính năng thông minh.</li><li><strong>Chống ngược sáng WDR</strong>(120dB), Chế độ Ngày Đêm ICR, chống nhiễu hình ảnh 3DNR, Tự động cân bằng trắng AWB, Tự động bù sáng AGC, Chống ngược sáng BLC</li><li>Công nghệ Full-color Start Light với độ nhạy sáng cực thấp 0.001Lux/F1.0 (ảnh màu) Cho hình ảnh có màu trong môi trường ánh sáng cưc thấp.</li><li>Hỗ trợ xem hình bằng nhiều công cụ: Web, phần mềm CMS (DSS/PSS) và DMSS</li><li>1/1 Alarm in/out, 1/1 audio in/out</li><li><strong>Hỗ trợ thẻ nhớ tối đa 128Gb</strong></li><li>IP67, IK10, công nghệ ePoE</li><li>Vỏ kim loại</li><li>Nguồn cấp: DC12V, PoE (802.3af)</li><li>Công suất: &amp;lt;4W</li><li><strong>Kích thước</strong>: 244.1mm×79mm×75.9mm</li><li>Trọng lượng: 0.815kg</li></ul>");
                
                resp.setStatus(HttpServletResponse.SC_OK);
                
                String test = XMLUtils.marshallToString(user);
                
                System.out.println(test);
                
                out.append(XMLUtils.marshallToString(user));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    //Register
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()){
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String fullName = request.getParameter("fullName");
            
            User newUser = new User();
            newUser.setUsername(username);;
            newUser.setPassword(password);
            newUser.setFullName(fullName);
            
            String registerResult = userService.register(newUser);
            
            if(registerResult.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_OK);
                out.append("Register Success");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.append(registerResult);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
