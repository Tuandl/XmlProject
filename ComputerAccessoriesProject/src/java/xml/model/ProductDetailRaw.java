/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.model;

/**
 *
 * @author admin
 */
public class ProductDetailRaw extends ModelBase {
    private String description;
    private int productRawId;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getProductRawId() {
        return productRawId;
    }

    public void setProductRawId(int productRawId) {
        this.productRawId = productRawId;
    }
    
    
}
