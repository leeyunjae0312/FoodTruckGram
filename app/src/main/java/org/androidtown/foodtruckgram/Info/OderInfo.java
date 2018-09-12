package org.androidtown.foodtruckgram.Info;

import java.util.ArrayList;

/**
 * Created by 이예지 on 2018-09-11.
 */

public class OderInfo {
    private String userName,tel,foodTruckName,userId;
    private String menuPrice;
    private ArrayList<MenuInfo> orderMenuList;

    public void addMenu(MenuInfo newMenu){
        orderMenuList.add(newMenu);
    }

    public void removeMenu(MenuInfo menu){
        //품절 메뉴 설정
        for(int i=0;i<orderMenuList.size();i++){
            if(orderMenuList.get(i).equals(menu))
                orderMenuList.remove(i);
        }
    }


}
