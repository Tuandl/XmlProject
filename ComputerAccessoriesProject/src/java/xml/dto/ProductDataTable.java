/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author admin
 */

@XmlRootElement(name = "datatable")
@XmlSeeAlso(ProductDTO.class)
public class ProductDataTable extends DataTable<ProductDTO>{
    
}
