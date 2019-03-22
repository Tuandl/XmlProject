/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml.service;

import xml.dao.AppSettingDAO;
import xml.model.AppSetting;

/**
 *
 * @author admin
 */
public class AppSettingService {
    
    private final AppSettingDAO appSettingDAO;
    
    public AppSettingService() {
        appSettingDAO = new AppSettingDAO();
    }
    
    public int getCommission() {
        AppSetting setting = appSettingDAO.getSingle(null);
        
        if(setting != null) {
            return setting.getCommission();
        } 
        return 0;
    }
    
    public void saveCommission(int commission) {
        AppSetting setting = appSettingDAO.getSingle(null);
        if(setting == null) {
            setting = new AppSetting();
            setting.setCommission(commission);
            appSettingDAO.insert(setting);
        } else {
            setting.setCommission(commission);
            appSettingDAO.update(setting);
        }
    }
}
