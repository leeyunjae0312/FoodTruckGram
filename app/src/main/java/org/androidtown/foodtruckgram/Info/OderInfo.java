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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFoodTruckName() {
        return foodTruckName;
    }

    public void setFoodTruckName(String foodTruckName) {
        this.foodTruckName = foodTruckName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(String menuPrice) {
        this.menuPrice = menuPrice;
    }

    public ArrayList<MenuInfo> getOrderMenuList() {
        return orderMenuList;
    }

    public void setOrderMenuList(ArrayList<MenuInfo> orderMenuList) {
        this.orderMenuList = orderMenuList;
    }
}
