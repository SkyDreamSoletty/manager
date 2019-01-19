package com.manager.controller.business;

import com.manager.pojo.Business;
import com.manager.pojo.Meals;
import com.manager.pojo.Order;
import com.manager.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.*;

@RestController
@RequestMapping("/business")
public class businessController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static List<String> mealSet = new ArrayList<String>();
    private static List<String> img = new ArrayList<String>();
    static {
        mealSet.add("上海菜");
        mealSet.add("四川菜");

        img.add("http://img3.imgtn.bdimg.com/it/u=3941047538,2308899029&fm=200&gp=0.jpg");
        img.add("http://img1.imgtn.bdimg.com/it/u=264708195,1230202552&fm=27&gp=0.jpg");
    }


    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Map<?,?> getBusinessList(WebRequest webRequest){
        Map<String,Object> result = new HashMap<String, Object>();
        List<Business> list = new ArrayList<Business>();
        for (int i=0;i<150;i++){
            Business business = new Business();
            business.setId(i);
            business.setName("商家_"+i);
            business.setAddress("地址_"+i);
            business.setPhone("123456xxxxx");
            business.setManger("李小狼");
            business.setDate("2017/07/07");
            business.setQuota(1236547);
            list.add(business);
        }
        result.put("data",list);
        result.put("count",list.size());
        return result;
    }

    @RequestMapping(value = "/mealsList",method = RequestMethod.GET)
    public Map<?,?> getMealsList(WebRequest webRequest){
        Map<String,Object> result = new HashMap<String, Object>();
        List<Meals> list = new ArrayList<Meals>();
        for (int i=0;i<50;i++){
            Meals meals = new Meals();
            meals.setId(i);
            meals.setName("菜品_"+i);
            int meal = 0;
            if(i%2==0){
                meals.setImage(img.get(0));
                meals.setSpecies(mealSet.get(0));
            }else{
                meals.setSpecies(mealSet.get(1));
                meals.setImage(img.get(1));
            }
            meals.setTaste("清淡");
            Random random = new Random();
            meals.setPrice(Double.valueOf(random.nextInt(100)));
            list.add(meals);
        }
        result.put("data",list);
        result.put("count",list.size());
        return result;
    }

    @RequestMapping(value = "/orderList",method = RequestMethod.GET)
    public Map<?,?> getOrderList(WebRequest webRequest){
        Map<String,Object> result = new HashMap<String, Object>();
        List<Order> list = new ArrayList<Order>();
        Random random = new Random(100);
        for (int i=0;i<30;i++){
            Order order = new Order();
            order.setId(i);
            order.setAddress("测试地址_"+i);
            order.setOrderId(UUID.randomUUID().toString());
            order.setPrice(random.nextDouble());
            if(i%2==0){
                order.setStatus(0);
                order.setPrincipal("未分配");
                order.setPhone("");
            }else{
                order.setStatus(1);
                order.setPrincipal("负责人_"+i);
                order.setPhone("158944xxxxx");
            }
            list.add(order);
        }
        result.put("data",list);
        result.put("count",list.size());
        return result;
    }

    @RequestMapping(value = "/userList",method = RequestMethod.GET)
    public Map<?,?> getUserList(WebRequest webRequest){
        Map<String,Object> result = new HashMap<String, Object>();
        List<User> list = new ArrayList<User>();
        for (int i=0;i<30;i++){
            User user = new User();
            user.setId(i);
            user.setName("ceshi"+i);
            user.setPhone("155989"+i+"xxxx");
            if(i%3==0){
                user.setStatus(0);//停用
            }else{
                user.setStatus(1);//启用
            }
            list.add(user);

        }
        result.put("data",list);
        result.put("count",list.size());
        return result;
    }
}
