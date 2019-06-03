package com.ttms.service.ProductManage.ServiceImpl;

import com.ttms.Entity.ProGroup;
import com.ttms.Entity.ProProduct;
import com.ttms.Entity.ProProductCat;
import com.ttms.Entity.SysUser;
import com.ttms.Enum.ExceptionEnum;
import com.ttms.Exception.TTMSException;
import com.ttms.Mapper.ProGroupMapper;
import com.ttms.Mapper.ProProductMapper;
import com.ttms.service.ProductManage.ICreateProductService;
import com.ttms.service.ProductManage.IGroupService;
import com.ttms.service.ProductManage.IProductCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class CreateProductService implements ICreateProductService {
    @Autowired
    private IGroupService groupService;
    @Autowired
    private ProGroupMapper groupMapper;

    @Autowired
    private IProductCatService productCatService;
    @Autowired
    private ProProductMapper proProductMapper;



    /**
    * 功能描述: <br>
    * 〈〉创建产品完善
    * @Param: [groupId, productCatId1, productCatId2, productCatId3, productName, serverStartTime, serverEndTime, preSellNumber, selledNumber, lowestNumber, onsellTime, productPrice, upsellTime, hotTip, productIntroduction, user]
    * @Return: void
    * @Author: 吴彬
    * @Date: 15:29 15:29
     */
    @Override
    @Transactional
    public void createProduct(Integer groupId, Integer productCatId1, Integer productCatId2,
                              Integer productCatId3, String productName, Date serverStartTime,
                              Date serverEndTime, Integer preSellNumber, Integer selledNumber,
                              Integer lowestNumber, Date onsellTime, Integer productPrice, Date upsellTime,
                              String hotTip, String productIntroduction, SysUser user) {
        ProProduct proProduct = new ProProduct();
        //查询团号是否存在
        ProGroup proGroup = groupService.getGroupById(groupId);
        proProduct.setCreateuserid(user.getId());
        //当前用户是否和团负责人一致
        if(!user.getId().equals(proGroup.getCreateuserid())){
            throw new TTMSException(ExceptionEnum.USER_NOT_GRUOPCHARGEUSER);
        }
        //团下面是否有产品，有，则不创建
        //查询改用户是否有创建了产品
        ProProduct product=new ProProduct();
        product.setCreateuserid(user.getId());
        List<ProProduct> productList = this.proProductMapper.select(product);
        if(!CollectionUtils.isEmpty(productList)){
            throw new TTMSException(ExceptionEnum.YOU_CREATE_PRODUCT);
        }

        proProduct.setGroupid(groupId);

        //查询三级分类是否是父子关系 和是否存在
        ProProductCat proProductCat3 = productCatService.getProductCatById(productCatId3);
        if(!proProductCat3.getParentid().equals(productCatId2)) {
            throw new TTMSException(ExceptionEnum.NOT_FOUND_PARENTID);
        }
        ProProductCat proProductCat2 = productCatService.getProductCatById(productCatId2);
        if(!proProductCat2.getParentid().equals(productCatId1)) {
            throw new TTMSException(ExceptionEnum.NOT_FOUND_PARENTID);
        }
        proProduct.setCreateuserid(user.getId());
        proProduct.setProductcatid1(productCatId1);
        proProduct.setProductcatid2(productCatId2);
        proProduct.setProductcatid3(productCatId3);
        proProduct.setProductname(productName);
        proProduct.setProductnumber(productName());
        //根据groupId查询补全参数
        //ProGroup group = this.groupMapper.selectByPrimaryKey(groupId);
        ProGroup groupById = this.groupService.getGroupById(groupId);
        proProduct.setProjectid(groupById.getProjectid());
        // proProduct.setProductname(null);
        proProduct.setProjectname(groupById.getProjectname());
        // proProduct.setPresellnumber(0);
        proProduct.setServerstarttime(serverStartTime);
        proProduct.setServerendtime(serverEndTime);
        proProduct.setOnselltime(onsellTime);
        proProduct.setUpselltime(upsellTime);
        proProduct.setPresellnumber(preSellNumber);
        proProduct.setSellednumber(selledNumber);
        proProduct.setLowestnumber(lowestNumber);
        proProduct.setProductprice(productPrice);
        proProduct.setHottip(hotTip);
        proProduct.setProductintroduction(productIntroduction);
        proProduct.setProductstatus(1);
        proProduct.setCreatetime(new Date());
        int i = this.proProductMapper.insert(proProduct);
        if (i != 1) {
            throw new TTMSException(ExceptionEnum.PRODUCT_ADD_FAIL);
        }
        return;
    }

    /**
    * 功能描述: <br>
    * 〈〉查询属于当前用户的所有团
    * @Param: [user]
    * @Return: java.util.List<com.ttms.Entity.ProGroup>
    * @Author: 吴彬
    * @Date: 15:57 15:57
     */
    @Override
    public List<ProGroup> queryGroupByCuruser(SysUser user) {
        Example example=new Example(ProGroup.class);
        example.createCriteria().andEqualTo("chargeUserId", user.getId());
        List<ProGroup> proGroups = this.groupMapper.selectByExample(example);
        return proGroups;
    }
    /**
    * 功能描述: <br>
    * 〈〉生成产品编号
    * @Param: []
    * @Return: java.lang.String
    * @Author: 吴彬
    * @Date: 17:44 17:44
     */
    private String getProductName(){
        String dataString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(52);
            String msg = dataString.substring(index, index + 1);
            buffer.append(msg);
        }
        return buffer.toString().toUpperCase();
    }

    private String GetTime(){
        SimpleDateFormat sd=new SimpleDateFormat("yyyyMMdd");
        String format = sd.format(new Date());
        return format;
    }
    /**
    * 功能描述: <br>
    * 〈〉
    * @Param: [len]
    * @Return: java.lang.String
    * @Author: 吴彬
    * @Date: 17:45 17:45
     */
    private  String generateCode(int len){
        len = Math.min(len, 8);
        int min = Double.valueOf(Math.pow(10, len - 1)).intValue();
        int num = new Random().nextInt(Double.valueOf(Math.pow(10, len + 1)).intValue() - 1) + min;
        return String.valueOf(num).substring(0,len);
    }

    /**
    * 功能描述: <br>
    * 〈〉生成产品编号
    * @Param: []
    * @Return: java.lang.String
    * @Author: 吴彬
    * @Date: 17:50 17:50
     */
    private String productName(){
        StringBuilder sb=new StringBuilder();
        StringBuilder append = sb.append(getProductName()).append("-").append(getProductName().substring(0, 3)).append("-")
                .append(GetTime()).append("-").append(getProductName().substring(0, 3)).append("-").append("-")
                .append(generateCode(3));
        return append.toString();
    }

    /**
    * 功能描述: <br>
    * 〈〉修改产品
    * @Param: [groupId, productCatId1, productCatId2, productCatId3, productName, serverStartTime, serverEndTime, preSellNumber, selledNumber, lowestNumber, onsellTime, productPrice, upsellTime, hotTip, productIntroduction, user]
    * @Return: void
    * @Author: 吴彬
    * @Date: 16:25 16:25
     */
    @Override
    @Transactional
    public void UpdateProduct(Integer pid,Integer groupId, Integer productCatId1, Integer productCatId2,
                              Integer productCatId3, String productName, Date serverStartTime,
                              Date serverEndTime, Integer preSellNumber, Integer selledNumber,
                              Integer lowestNumber, Date onsellTime, Integer productPrice,
                              Date upsellTime, String hotTip, String productIntroduction, SysUser user) {
        ProProduct proProduct=new ProProduct();
        proProduct.setId(pid);
        proProduct.setProductcatid1(productCatId1);
        proProduct.setProductcatid2(productCatId2);
        proProduct.setProductcatid3(productCatId3);
        proProduct.setProductcatnames(productName);
        proProduct.setServerstarttime(serverStartTime);
        proProduct.setServerendtime(serverEndTime);
        proProduct.setOnselltime(onsellTime);
        proProduct.setUpselltime(upsellTime);
        proProduct.setPresellnumber(preSellNumber);
        proProduct.setSellednumber(selledNumber);
        proProduct.setLowestnumber(lowestNumber);
        proProduct.setProductprice(productPrice);
        proProduct.setHottip(hotTip);
        proProduct.setProductintroduction(productIntroduction);
        proProduct.setUpdatetime(new Date());
        proProduct.setUpdateuserid(user.getId());
        int i = this.proProductMapper.updateByPrimaryKeySelective(proProduct);
        if(i!=1){
            throw new TTMSException(ExceptionEnum.PRODUCT_EDIT_FAIL);
        }
        return;
    }



}
