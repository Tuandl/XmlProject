/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.dao;

import xml.model.AppSetting;

/**
 *
 * @author admin
 */
public class AppSettingDAO extends DAOBase<AppSetting> 
        implements IDAO<AppSetting>{
    
    public AppSettingDAO() {
        super(AppSetting.class);
    }
}
