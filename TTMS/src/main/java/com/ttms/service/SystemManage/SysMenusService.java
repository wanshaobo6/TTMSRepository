package com.ttms.service.SystemManage;


import com.ttms.Entity.SysMenus;
import com.ttms.Mapper.SysMenusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;


@Service
public class SysMenusService {
    @Autowired
    private SysMenusMapper sysMenusMapper;
    
    /**
     * 功能描述: <br>根据pid查询sysmenu
     * 〈〉
     * @Param: [id]
     * @Return: java.util.List<com.ttms.Entity.SysMenus>
     * @Author: 万少波
     * @Date: 2019/5/26 12:50
     */
    public List<SysMenus> getSysMenusByPid(int id){
        SysMenus sysMenus = new SysMenus();
        sysMenus.setParentid(id);
        List<SysMenus> sysMenusList = sysMenusMapper.select(sysMenus);
        return sysMenusList;
    }

    /**
     * 查询sysMenus树
     * @return
     */
    public List<SysMenus> getSysMenusTree(){
        List<SysMenus> parentSysMenus = getSysMenusByPid(0);
        getSysMensByPidRecursive(parentSysMenus);
        return parentSysMenus;
    }

    /**
     * 功能描述: <br> 递归查询子菜单
     * 〈〉
     * @Param: [sysMenuList]
     * @Return: java.util.List<com.ttms.Entity.SysMenus>
     * @Author: 万少波
     * @Date: 2019/5/26 12:50
     */
    public List<SysMenus> getSysMensByPidRecursive(List<SysMenus> sysMenuList){
        if(sysMenuList == null || sysMenuList.size() ==0)
            return sysMenuList;
        for (SysMenus sysMenu:sysMenuList){
            List<SysMenus> childMenus = getSysMenusByPid(sysMenu.getId());
            sysMenu.setChildMenus(getSysMensByPidRecursive(childMenus));
        }
        return sysMenuList;
    }

    /**
     * 功能描述: <br>获取所有拦截的url 和 需要给的permission
     * 〈〉
     * @Param: []
     * @Return: java.util.Map<java.lang.String,java.lang.String>
     * @Author: 万少波
     * @Date: 2019/5/26 12:46
     */
    public Map<String,String> getUrlPermissionMapping(){
        List<SysMenus> sysMenuTree = getSysMenusTree();
        HashMap<String, String> urlPermissionsMapping = new HashMap<>();
        Stack<String> urlStack = new Stack<>();
        Stack<String>  permissionStack= new Stack<>();
        getUrlPermissionMappingRecursive(urlPermissionsMapping,urlStack,permissionStack,sysMenuTree);
        return urlPermissionsMapping;
    }


    /**
     * 功能描述: <br>递归获取url --> Mapping映射s
     * 〈〉
     * @Param: [urlPermissionsMapping, urlStack, permissionStack, sysMenusList]
     * @Return: void
     * @Author: 万少波
     * @Date: 2019/5/26 12:50
     */
    public void getUrlPermissionMappingRecursive(Map<String,String> urlPermissionsMapping ,
                                                 Stack<String> urlStack ,
                                                 Stack<String> permissionStack,
                                                 List<SysMenus> sysMenusList){
        if(CollectionUtils.isEmpty(sysMenusList))
            return;
        for (SysMenus sysMenu : sysMenusList) {
            //获取子菜单
            List<SysMenus> childMenus = sysMenu.getChildMenus();
            //将当前子菜单urlStack 和 permissionStack 压栈
            urlStack.push(sysMenu.getUrl());
            permissionStack.push(sysMenu.getPermission());
            if(CollectionUtils.isEmpty(childMenus)){
                //当前为子结点  /添加映射
                addUrlPermissionMapping(urlPermissionsMapping , urlStack , permissionStack);
            }else{
                //当前不为子结点
                getUrlPermissionMappingRecursive(urlPermissionsMapping,urlStack,permissionStack,childMenus);
            }
            //urlStack 和 permissionStack 出栈
            urlStack.pop();
            permissionStack.pop();
        }
    }

    /**
     * 功能描述: <br>添加url permission到Map
     * 〈〉
     * @Param: [urlPermissionsMapping, urlStack, permissionStack]
     * @Return: void
     * @Author: 万少波
     * @Date: 2019/5/26 12:50
     */
    private void addUrlPermissionMapping(Map<String, String> urlPermissionsMapping, Stack<String> urlStack, Stack<String> permissionStack) {
        StringBuffer urlBuffer = new StringBuffer();
        for (String surl: urlStack) {
            urlBuffer.append("/"+surl);
        }
        urlBuffer.append("/*");


        StringBuffer permissionBuffer = new StringBuffer();
        permissionBuffer.append("perms[");
        for (String spermission: permissionStack) {
            permissionBuffer.append(spermission+":");
        }
        permissionBuffer.setCharAt(permissionBuffer.length()-1,']');
        urlPermissionsMapping.put(urlBuffer.toString(),permissionBuffer.toString());
    }
}
