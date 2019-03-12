/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import xml.dto.ProductDTO;
import xml.dto.ProductDataTable;
import xml.dto.ProductsDTO;
import xml.model.Product;
import xml.param.ProductParam;
import xml.service.ProductService;
import xml.utils.StringUtils;
import xml.utils.XMLUtils;

/**
 *
 * @author admin
 */
@WebServlet(name = "ProductServlet", urlPatterns = {"/Product"})
public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductService productService = new ProductService();
        
        try {
            String paramStr = req.getParameter("param");
            if(paramStr == null || paramStr.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            
            ProductParam param = (ProductParam) XMLUtils.unmarshallFromString(ProductParam.class, paramStr);
            if(param == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            
            if(param.getProductId() != null && param.getProductId() > 0) {
                //get Product Detail
                Product product = productService.getProductDetail(param.getProductId());
                if(product == null) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                } else {
                    ProductDTO dto = new ProductDTO(product);
                    XMLUtils.marshallToOutputStream(dto, resp.getOutputStream());
                    resp.setStatus(HttpServletResponse.SC_OK);
                }
                return;
            } 
            else if(param.getGetTopProduct() == true) {
                //get top product list
                if(param.getCategoryId() != null && param.getCategoryId() > 0) {
                    //get by category
                    ProductsDTO dto = productService.getTopProductsByCategoryId(
                            param.getCategoryId());
                    
                    resp.setStatus(HttpServletResponse.SC_OK);
                    XMLUtils.marshallToOutputStream(dto, resp.getOutputStream());
                }
                else {
                    //get top all category
                    List<Product> products = productService.getTopProducts();
                    List<ProductDTO> productDtos = products.stream()
                            .map((x) -> new ProductDTO(x))
                            .collect(Collectors.toList());

                    ProductsDTO dto = new ProductsDTO();
                    dto.setProducts(productDtos);

                    XMLUtils.marshallToOutputStream(dto, resp.getOutputStream());
                    resp.setStatus(HttpServletResponse.SC_OK);
                }
                
            }
            else if(param.getCategoryId() == null || param.getCategoryId() > 0) {
                //check paging params
                if(param.getPage() == null || param.getPage() <= 0 ||
                        param.getPageSize() == null || param.getPageSize() <= 0) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }                
                //TODO: get product by category
                ProductDataTable dto = productService.getDataTable(param.getPage(),
                        param.getPageSize(), param.getCategoryId(), 
                        param.getSearch());
                
                resp.setStatus(HttpServletResponse.SC_OK);
                XMLUtils.marshallToOutputStream(dto, resp.getOutputStream());
            }
            else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductService productService = new ProductService();
        
        try {
            String categoryRawIdStr = req.getParameter("categoryRawId");
            Integer categoryRawId = StringUtils.parseStringToInt(categoryRawIdStr);
            
            if(categoryRawId == null) {
                //not support create product yet.
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } else {
                boolean updated = productService.updateNewProducts(categoryRawId);
                if(updated) {
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
}
